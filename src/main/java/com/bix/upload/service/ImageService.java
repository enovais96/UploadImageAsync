package com.bix.upload.service;

import com.bix.upload.constant.ImageDir;
import com.bix.upload.constant.LimitFreeUpload;
import com.bix.upload.constant.TopicsKafka;
import com.bix.upload.constant.UserRole;
import com.bix.upload.dto.UploadImageDTO;
import com.bix.upload.exception.ImageUploadException;
import com.bix.upload.model.Image;
import com.bix.upload.model.User;
import com.bix.upload.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
    @Autowired
    ImageRepository repository;
    
    @Autowired
    KafkaProducerService kafkaProducerService;
    
    @Autowired
    AuthorizationService authorizationService;
    
    public Image saveImage(UploadImageDTO uploadImageDTO) {
    	this.validateDirectory();
    	Image image = new Image();
    	User user = authorizationService.getUserLogged();
    	
    	if(!this.validatePercentNewSize(uploadImageDTO.percentNewSize())) {		
            throw new IllegalArgumentException("'percentNewSize' should be between 1 and 100.");
    	}
    	
    	if(!this.validateLimitProccesImage(user)) {
    		throw new ImageUploadException("You have used your daily upload limit of " + LimitFreeUpload.LIMIT.getlimit() + ". If you want more, sign up for the PREMIUM plan.");
    	}
    	
    	try {
	    	
    		image.setUserId(user);
	    	image.setDateUpload(new Date());
	    	image.setPercentNewSize(uploadImageDTO.percentNewSize().doubleValue());
	    	image.setFilterImage(uploadImageDTO.filterImage());
	    	
	        image = this.saveImage(image);
	        
	        image.setImageName(image.getId().toString() + "_" + uploadImageDTO.file().getOriginalFilename());
	        image.setDirectoryOriginalImage(ImageDir.ORIGINAL_IMAGE.getImageDir());
	        
	        File destinationFile = new File(ImageDir.ORIGINAL_IMAGE.getImageDir() + image.getId() + "_" + uploadImageDTO.file().getOriginalFilename());
	        uploadImageDTO.file().transferTo(destinationFile);
		
			this.saveImage(image);
			
			kafkaProducerService.sendMessage(TopicsKafka.IMAGE_UPLOAD.getTopicKafka(), image.getId().toString());
			
    	} catch (IOException e) {
			throw new ImageUploadException(e.getMessage());
		}
        
    	return image;
    }
    
    private boolean validatePercentNewSize(BigDecimal percentNewSize) {
        if (percentNewSize.compareTo(BigDecimal.ONE) < 0 || percentNewSize.compareTo(new BigDecimal(100)) > 0) {
            return false;
        }
        
        return true;
    }
    
    public Optional<Image> findImage(Long id) {
    	Optional<Image> image = repository.findById(id);
    	
    	return image;
    }
    
    public Image saveImage(Image image) {
    	return repository.save(image);
    }
    
    public boolean validateLimitProccesImage(User user) {

    	if(user.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.PREMIUM.getRole()))) {
			return true;
		}
		
		Long imagesUploadedToday = this.countImagesUploadedToday(user);
		
    	if(imagesUploadedToday.compareTo(LimitFreeUpload.LIMIT.getlimit()) < 0) {
    		return true;
    	}
		
    	return false;
    }
    
    public long countImagesUploadedToday(User user) {
        LocalDate today = LocalDate.now(); // Data de hoje
        LocalDateTime startOfDay = today.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = today.atTime(23, 59, 59, 999999999); // 23:59:59.999999999

        Date start = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        return repository.countByUserIdAndDateUploadBetween(user, start, end);
    }
    
    private void validateDirectory() {
    	File directory = new File(ImageDir.ORIGINAL_IMAGE.getImageDir());
		
		if (!directory.exists()) {
			directory.mkdirs();
		}
    }
}