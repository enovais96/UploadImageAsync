package com.bix.upload.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bix.upload.model.Image;
import com.bix.upload.service.ImageService;

@RestController
@RequestMapping("/upload")
public class ImageUploadController {
	
	@Autowired
	private ImageService service;
	
	@PostMapping
	public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file,
										 @RequestParam("percentNewSize") Double percentNewSize,
										 @RequestParam("filterImage") boolean filterImage) {
		
		Image image = this.service.saveImage(file, percentNewSize, filterImage);
		
		return ResponseEntity.ok(image);
	}

}
