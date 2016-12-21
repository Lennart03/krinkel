package com.realdolmen.chiro.util;

import org.springframework.stereotype.Component;

@Component
public class StamNumberTrimmer {

    /**
     * Removes slashes and spaces from the given stamnumber.
     *
     * @param stam
     * @return Normalized stamNumber or empty string if the input is null.
     */
    public String trim(String stam) {
        if (stam == null) {
            return "";
        } else {
            return stam.replace("/", "")
                    .replace("\\s", "")
                    .replace(" ", "");
        }
    }

    // TODO: test on stamnummers for Leuven
    // Untrims a trimmed stam nummer
    public String untrim(String verbondStamNummer) {
        if(verbondStamNummer != null && !verbondStamNummer.contains("/")){
            // check if character at position 2 is a number
            if(isADigit(verbondStamNummer.charAt(2))){
                verbondStamNummer = verbondStamNummer.substring(0,2) + " /" + verbondStamNummer.substring(2);
            }
            // check if character at position 3 is a number for leuven
            else if(isADigit(verbondStamNummer.charAt(3))){
                if(verbondStamNummer.endsWith("0000")){
                    // with space and slash if ends with 0000
                    verbondStamNummer = verbondStamNummer.substring(0,3) + " /" + verbondStamNummer.substring(3);
                }
                else{
                    // no space but with a slash if otherwise
                    verbondStamNummer = verbondStamNummer.substring(0,3) + "/" + verbondStamNummer.substring(3);
                }
            }
        }
        return verbondStamNummer;
    }

    private boolean isADigit(char c){
        return (c >= '0' && c <= '9');
    }
}
