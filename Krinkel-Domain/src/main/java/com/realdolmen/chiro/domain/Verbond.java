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
            case "WJ0":
            case "WM0":
                return Verbond.WEST_VLAANDEREN;
            case "MG0":
            case "MM0":
            case "MJ0":
                return Verbond.MECHELEN;
            case "LEG":
            case "LEJ":
            case "LEM":
                return Verbond.LEUVEN;
            case "AG0":
            case "AJ0":
            case "AM0":
                return Verbond.ANTWERPEN;
            case "BG0":
            case "BJ0":
            case "BM0":
                return Verbond.BRUSSEL;
            case "OG2":
            case "OJ2":
            case "OM2":
                return Verbond.REINAERT;
            case "KG0":
            case "KJ0":
            case "KM0":
                return Verbond.KEMPEN;
            case "OG3":
            case "OJ3":
            case "OM3":
                return Verbond.HEUVELLAND;
            case "OG1":
            case "OJ1":
            case "OM1":
                return Verbond.ROELAND;
            case "LG0":
            case "LJ0":
            case "LM0":
                return Verbond.LIMBURG;
            default:
                throw new RuntimeException("Verbond in statistics switch not found. Implement it.");
        }
    }

}