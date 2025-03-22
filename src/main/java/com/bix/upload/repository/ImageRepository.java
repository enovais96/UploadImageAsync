package com.bix.upload.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.bix.upload.model.Image;
import com.bix.upload.model.User;

public interface ImageRepository extends JpaRepository<Image, Long> {
	
	long countByUserIdAndDateUploadBetween(User userId, Date startOfDay, Date endOfDay);
	
}
