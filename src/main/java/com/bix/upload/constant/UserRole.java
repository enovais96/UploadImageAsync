package com.bix.upload.constant;

public enum UserRole {
	
    PREMIUM("premium"),
    FREE("free");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}