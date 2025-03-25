package com.bix.upload.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bix.upload.dto.UploadImageDTO;
import com.bix.upload.model.Image;
import com.bix.upload.service.ImageService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/upload")
public class ImageUploadController {
	
	@Autowired
	private ImageService service;
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadImage(@RequestPart(value = "file") 
	 									 MultipartFile file,
 									 	 @NotNull(message = "'percentNewSize' is required")
	 									 BigDecimal percentNewSize,
 									 	 boolean filterImage) {
		
		try {
			UploadImageDTO uploadImageDTO = new UploadImageDTO(file, percentNewSize, filterImage);
			Image image = this.service.saveImage(uploadImageDTO);
		
			return ResponseEntity.ok(image);
		} catch (IllegalStateException e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
	}

}
