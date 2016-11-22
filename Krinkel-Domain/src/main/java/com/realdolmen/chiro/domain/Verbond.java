package com.realdolmen.chiro.domain;

/**
 * Created by MBTAZ48 on 22/11/2016.
 */
public enum Verbond {
    WEST_VLAANDEREN,
    MECHELEN,
    LEUVEN,
    ANTWERPEN,
    BRUSSEL,
    REINAERT,
    KEMPEN,
    HEUVELLAND,
    ROELAND,
    LIMBURG;


    public static Verbond getVerbondFromStamNumber(String stamNumber) {
        switch (stamNumber.substring(0, 2)) {
            case "WG0":
                return Verbond.WEST_VLAANDEREN;
            case "MG0":
                return Verbond.MECHELEN;
            case "LEG":
                return Verbond.LEUVEN;
            case "AG0":
                return Verbond.ANTWERPEN;
            case "BG0":
                return Verbond.BRUSSEL;
            case "OG2":
                return Verbond.REINAERT;
            case "KG0":
                return Verbond.KEMPEN;
            case "OG3":
                return Verbond.HEUVELLAND;
            case "OG1":
                return Verbond.ROELAND;
            case "LG0":
                return Verbond.LIMBURG;
            default:
                throw new RuntimeException("Verbond in statistics switch not found. Implement it.");
        }
    }

}
