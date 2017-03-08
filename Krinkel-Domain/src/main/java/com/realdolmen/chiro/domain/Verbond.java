package com.realdolmen.chiro.domain;

import java.util.Arrays;
import java.util.List;

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
    RD("0RD"),
    OTHERS("OTHERS"),
    NATIONAAL("NAT"),
    INTERNATIONAAL("INT");// For testing...

    private static List<String> nationaleStamnummers = Arrays.asList("4AF", "4AG", "4AL", "4CF", "4CR", "4KL", "4WB", "4WJ", "4WK", "5AA", "5CA", "5CC", "5CD", "5CG", "5CJ", "5CL", "5CP", "5CV", "5IG", "5IP", "5IT", "5KA", "5PA", "5PG", "5PM", "5PP", "5PV", "5RA", "5RD", "5RI", "5RP", "5RV", "5RW", "5SB", "5UG", "5UK", "5UL", "6KV", "7WD", "7WH", "7WK", "7WO", "7WW", "8BB", "8BC", "8BH", "8BR", "8BZ", "8DB", "8HD", "8HH", "8HK", "8HO", "8HW", "9KO");
    private static String internationaalStamnummer = "5DI";
        public static Verbond getVerbondFromStamNumber(String stamNumber) {
        /**
         * Most stamNumbers are length 6, except for Leuven which is 7. The stamNumbers with OG's also have to be checked by the first 3 letters. That's why this logic is here.
         */

        if(stamNumber == null) return Verbond.OTHERS;

        if(stamNumber.contains("/")) { //remove the slashy thing before checking
            if (stamNumber.contains(" /")) {
                stamNumber = stamNumber.replace(" /", "");
            }
            else {
                stamNumber = stamNumber.replace("/", "");
            }
        }
        //System.out.println(stamNumber + " stamNumber");
        if (stamNumber.length() == 6) {
            if (stamNumber.startsWith("O")) {
                return switchVerbondOnFirst3Letters(stamNumber);
            }
            return switchVerbondOnFirst2Letters(stamNumber);

        } else if (stamNumber.length() == 7) {
            return switchVerbondOnFirst3Letters(stamNumber);
        } else if (stamNumber.equals(internationaalStamnummer)) {
            return Verbond.INTERNATIONAAL;
        } else if (nationaleStamnummers.contains(stamNumber)){
            return Verbond.NATIONAAL;
        } else {
            return Verbond.OTHERS;
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
                //throw new RuntimeException("Verbond " + stamNumber.substring(0, 2).toUpperCase() + " in statistics switch not found. Implement it.");
                return Verbond.OTHERS;
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
                //throw new RuntimeException("Verbond " + stamNumber.substring(0, 3).toUpperCase() + " in statistics switch not found. Implement it.");
                return Verbond.OTHERS;
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
