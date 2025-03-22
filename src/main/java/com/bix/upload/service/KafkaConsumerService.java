package com.bix.upload.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.bix.upload.constant.ImageDir;
import com.bix.upload.model.Image;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class KafkaConsumerService {
	
	@Autowired
	ImageService imageService;
	
    //@KafkaListener(topics = "image-upload", groupId = "upload-image-group")
    public void consume(Long message) {
        if (message == null || message == 0l) {
        	return;
        }
        
        this.validateDirectory();
        
        try {
	        Optional<Image> optionalImage = imageService.findImage(message);
	        
	        if(optionalImage.isEmpty()) {
	        	return;
	        }
	        
	        Image image = optionalImage.get();
	        
	        File originalFile = new File(ImageDir.ORIGINAL_IMAGE.getImageDir() + image.getImageName());
        
	        if (!originalFile.exists()) {
                return;
            }
	        
			BufferedImage originalImage = ImageIO.read(originalFile);
			
			if(image.isFilterImage()) {
				originalImage = this.grayScaleImage(originalImage);
			}
			
			File processedFile = this.compressImage(originalImage, image);
			
			if(processedFile.exists()) {
				image.setDirectoryNewImage(ImageDir.PROCESSED_IMAGE.getImageDir());
				
				imageService.saveImage(image);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private BufferedImage grayScaleImage(BufferedImage originalImage) {
    	int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        
        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = originalImage.getRGB(x, y);
                Color color = new Color(rgba, true);
                
                // Calcula a intensidade média de vermelho, verde e azul
                int gray = (int) (0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue());
                
                // Define o valor de pixel na nova imagem
                gray = (gray << 16) | (gray << 8) | gray;
                grayscaleImage.setRGB(x, y, gray);
            }
        }
        
        return grayscaleImage;
    }
    
    private File compressImage(BufferedImage originalImage, Image image) {
    	File processedFile = new File(ImageDir.PROCESSED_IMAGE.getImageDir() + image.getImageName());
    	
    	try {
			Thumbnails.of(originalImage)
			          .scale((image.getPercentNewSize()/100))  // Mantém o tamanho original
			          .toFile(processedFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return processedFile;
    }
    
    private void validateDirectory() {
    	File directory = new File(ImageDir.ORIGINAL_IMAGE.getImageDir());
    	File directoryProcessed = new File(ImageDir.PROCESSED_IMAGE.getImageDir());
		
    	if (!directory.exists()) {
			directory.mkdirs();
		}
    	
    	if (!directoryProcessed.exists()) {
    		directoryProcessed.mkdirs();
		}
    }
}