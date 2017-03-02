package com.realdolmen.chiro.domain;

/**
 * 'Kampgrond'
 */
public enum CampGround {
    ANTWERPEN("Antwerpen"),
    KEMPEN("Kempen"),
    MECHELEN("Mechelen"),
    LIMBURG("Limburg"),
    LEUVEN("Leuven"),
    BRUSSEL("Brussel"),
    WEST_VLAANDEREN("West-Vlaanderen"),
    WESTVLAANDEREN("WestVlaanderen"),
    HEUVELLAND("Heuvelland"),
    ROELAND("Roeland"),
    REINAERT("Reinaert"),
    NATIONAAL("Nationaal"),
    INTERNATIONAAL("Internationaal");


    private String description;

    private CampGround(String description) {
        this.setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static CampGround getCamgroundByName(String name){
        switch (name) {
            case "Antwerpen":
                return CampGround.ANTWERPEN;
            case "Kempen":
                return CampGround.KEMPEN;
            case "Mechelen":
                return CampGround.MECHELEN;
            case "Limburg":
                return CampGround.LIMBURG;
            case "Leuven":
                return CampGround.LEUVEN;
            case "Brussel":
                return CampGround.BRUSSEL;
            case "West-Vlaanderen":
                return CampGround.WEST_VLAANDEREN;
            case "Heuvelland": return CampGround.HEUVELLAND;
            case "Roeland": return CampGround.ROELAND;
            case "Reinaert": return CampGround.REINAERT;
            case "Nationaal": return CampGround.NATIONAAL;
            case "Internationaal": return CampGround.INTERNATIONAAL;
            default: return null;
        }
    }
}
