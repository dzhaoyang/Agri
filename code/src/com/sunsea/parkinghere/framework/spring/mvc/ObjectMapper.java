package com.sunsea.parkinghere.framework.spring.mvc;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

public class ObjectMapper extends org.codehaus.jackson.map.ObjectMapper {
    
    public static final SimpleDateFormat longFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public ObjectMapper() {
        configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS,
                  false);
        configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        getSerializationConfig().withDateFormat(longFormatter);
        getDeserializationConfig().withDateFormat(longFormatter);
        setDateFormat(longFormatter);
        CustomSerializerFactory sf = new CustomSerializerFactory();
        setSerializerFactory(sf);
    }
    
    public void setPrettyPrint(boolean prettyPrint) {
        configure(SerializationConfig.Feature.INDENT_OUTPUT, prettyPrint);
    }
    
}
