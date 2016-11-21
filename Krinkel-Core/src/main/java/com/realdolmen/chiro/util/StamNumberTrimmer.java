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
}
