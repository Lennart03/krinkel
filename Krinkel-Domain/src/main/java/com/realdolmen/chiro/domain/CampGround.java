package com.realdolmen.chiro.domain;

/**
 * 'Kampgrond'
 *
 */
public enum CampGround {
    ANTWERPEN("Antwerpen"),
    KEMPEN("Kempen"),
    MECHELEN("Mechelen"),
    LIMBURG("Limburg"),
    LEUVEN("Leuven"),
    BRUSSEL("Brussel"),
    WEST_VLAANDEREN("West-Vlaanderen"),
    HEUVELLAND("Heuvelland"),
    ROELAND("Roeland"),
    REINAERT("Reinaert"),
    NATIONAAL("Nationaal"),
    INTERNATIONAAL("Internationaal");
	
	private String description;
	
	private CampGround(String description){
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
