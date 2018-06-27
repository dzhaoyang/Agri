package com.sunsea.parkinghere.biz.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class EnumSerializer extends JsonSerializer<Enum> {
    
    @Override
    public void serialize(Enum value,
                          JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
                                                      JsonProcessingException {
        jgen.writeRawValue(String.format("{\"name\":\"%s\",\"ordinal\":%d,\"label\":\"%s\"}",
                                         value.name(),
                                         value.ordinal(),
                                         value.toString()));
    }
    
}
