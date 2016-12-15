package com.realdolmen.chiro.conversion;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.realdolmen.chiro.domain.Gender;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@JsonComponent
public class GenderDeserializer extends StdDeserializer<Gender> {
    protected GenderDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Gender deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int ordinal = p.getNumberValue().intValue();
        if (ordinal < 0 && ordinal > Gender.values().length) {
            return null;
        } else {
            return Gender.values()[ordinal];
        }
    }
}
