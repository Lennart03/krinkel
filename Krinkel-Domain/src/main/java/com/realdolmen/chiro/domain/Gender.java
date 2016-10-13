package com.realdolmen.chiro.domain;

public enum Gender {
    MAN("Man"),
    WOMAN("Vrouw"),
    X("X"); // Gender unknown or unspecified.
    
    
    private String description;

    private Gender(String description) {
    	this.description = description;
    }
    
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}



