package com.realdolmen.chiro.conversion;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.realdolmen.chiro.domain.Gender;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class GenderSerializer extends StdSerializer<Gender> {

    protected GenderSerializer(Class<Gender> t) {
        super(t);
    }

    @Override
    public void serialize(Gender value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.ordinal());
    }
}
