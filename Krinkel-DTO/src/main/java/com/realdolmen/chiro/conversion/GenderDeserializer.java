package com.realdolmen.chiro.conversion;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.realdolmen.chiro.domain.Gender;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class GenderDeserializer extends StdDeserializer<Gender> {

    public GenderDeserializer() {
        super(Gender.class);
    }

    @Override
    public Gender deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            int ordinal = Integer.parseInt(p.getValueAsString());
            ordinal--; //chiro API start from 1
            if (ordinal < 0 || ordinal >= Gender.values().length) {
                return null;
            } else {
                return Gender.values()[ordinal];
            }
        } catch (NumberFormatException e) {
            String name = p.getValueAsString();
            return Gender.valueOf(name);
        }
    }
}
