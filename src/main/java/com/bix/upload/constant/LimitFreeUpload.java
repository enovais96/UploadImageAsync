package com.bix.upload.constant;

public enum LimitFreeUpload {
	
	LIMIT(2L);

    private Long limit;

    LimitFreeUpload(Long limit){
        this.limit = limit;
    }

    public Long getlimit(){
        return limit;
    }
}