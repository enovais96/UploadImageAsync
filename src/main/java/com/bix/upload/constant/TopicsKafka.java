package com.bix.upload.constant;

public enum TopicsKafka {
	
    IMAGE_UPLOAD("image-upload");

    private String role;

    TopicsKafka(String role){
        this.role = role;
    }

    public String getTopicKafka(){
        return role;
    }
}