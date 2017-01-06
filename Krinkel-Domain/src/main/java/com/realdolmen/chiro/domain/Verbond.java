package com.realdolmen.chiro.domain;

/**
 * Created by MBTAZ48 on 22/11/2016.
 */
public enum Verbond {
    WEST_VLAANDEREN("WG0000"),
    MECHELEN("MG0000"),
    LEUVEN("LEG0000"),
    ANTWERPEN("AG0000"),
    BRUSSEL("BG0000"),
    REINAERT("OG2000"),
    KEMPEN("KG0000"),
    HEUVELLAND("OG3000"),
    ROELAND("OG1000"),
    LIMBURG("LG0000"),
    RD("0RD"); // For testing...


    public static Verbond getVerbondFromStamNumber(String stamNumber) {
        /**
         * Most stamNumbers are length 6, except for Leuven which is 7. The stamNumbers with OG's also have to be checked by the first 3 letters. That's why this logic is here.
         */
        //System.out.println(stamNumber + " stamNumber");
        if (stamNumber.length() == 6) {
            if (stamNumber.startsWith("O")) {
                return switchVerbondOnFirst3Letters(stamNumber);
            }
            return switchVerbondOnFirst2Letters(stamNumber);

        } else if (stamNumber.length() == 7) {
            return switchVerbondOnFirst3Letters(stamNumber);
        } else if (stamNumber.equals("0RD")){ // For testing ...
            return Verbond.RD;
        } else {
            throw new RuntimeException("Invalid adNumber length in Verbond.getVerbondFromStamNumber()" + stamNumber);
        }
    }

    private static Verbond switchVerbondOnFirst2Letters(String stamNumber) {
        switch (stamNumber.substring(0, 2).toUpperCase()) {
            case "WG":
            case "WJ":
            case "WM":
                return Verbond.WEST_VLAANDEREN;
            case "MG":
            case "MM":
            case "MJ":
                return Verbond.MECHELEN;
            case "AG":
            case "AJ":
            case "AM":
                return Verbond.ANTWERPEN;
            case "BG":
            case "BJ":
            case "BM":
                return Verbond.BRUSSEL;
            case "KG":
            case "KJ":
            case "KM":
                return Verbond.KEMPEN;
            case "LG":
            case "LJ":
            case "LM":
                return Verbond.LIMBURG;
            default:
                throw new RuntimeException("Verbond " + stamNumber.substring(0, 2).toUpperCase() + " in statistics switch not found. Implement it.");
        }

    }

    private static Verbond switchVerbondOnFirst3Letters(String stamNumber) {
        switch (stamNumber.substring(0, 3).toUpperCase()) {
            case "LEG":
            case "LEJ":
            case "LEM":
                return Verbond.LEUVEN;
            case "OG1":
            case "OJ1":
            case "OM1":
                return Verbond.ROELAND;
            case "OG3":
            case "OJ3":
            case "OM3":
                return Verbond.HEUVELLAND;
            case "OG2":
            case "OJ2":
            case "OM2":
                return Verbond.REINAERT;
            default:
                throw new RuntimeException("Verbond " + stamNumber.substring(0, 3).toUpperCase() + " in statistics switch not found. Implement it.");
        }
    }

    private String stam;

    Verbond(String stam) {
        this.stam = stam;
    }

    public String getStam(){
        return stam;
    }

}
