package com.bix.upload.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Table(name = "images")
@Entity(name = "images")
@EqualsAndHashCode(of = "id")
public class Image {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String imageName;
	@ManyToOne
    @JoinColumn(name = "user_id")
	private User userId;
    private String directoryOriginalImage;
    private Date dateUpload;
    private String directoryNewImage;
    private Double percentNewSize;
    private boolean filterImage;
    
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getDirectoryOriginalImage() {
		return directoryOriginalImage;
	}
	
	public void setDirectoryOriginalImage(String directoryOriginalImage) {
		this.directoryOriginalImage = directoryOriginalImage;
	}
	
	public Date getDateUpload() {
		return dateUpload;
	}
	
	public void setDateUpload(Date dateUpload) {
		this.dateUpload = dateUpload;
	}
	
	public String getDirectoryNewImage() {
		return directoryNewImage;
	}
	
	public void setDirectoryNewImage(String directoryNewImage) {
		this.directoryNewImage = directoryNewImage;
	}
	
	public Double getPercentNewSize() {
		return percentNewSize;
	}
	
	public void setPercentNewSize(Double percentNewSize) {
		this.percentNewSize = percentNewSize;
	}

	public boolean isFilterImage() {
		return filterImage;
	}

	public void setFilterImage(boolean filterImage) {
		this.filterImage = filterImage;
	}
}