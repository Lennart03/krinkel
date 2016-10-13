package com.realdolmen.chiro.domain;

public enum Role {
    ASPI("Aspi"), 
    LEADER("Leid(st)er"),
    MENTOR("Mentor"), 
    VOLUNTEER("Vrijwilliger"), 
    ADMIN("Admin");
    
    private String description;
    
    private Role(String description) {
    	this.description = description;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

