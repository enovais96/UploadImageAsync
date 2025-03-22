package com.bix.upload.constant;

public enum ImageDir {
	
	ORIGINAL_IMAGE("/tmp/images/"),
	PROCESSED_IMAGE("/tmp/images/processed/");

    private String imageDir;

    ImageDir(String imageDir){
        this.imageDir = imageDir;
    }

    public String getImageDir(){
        return imageDir;
    }
}