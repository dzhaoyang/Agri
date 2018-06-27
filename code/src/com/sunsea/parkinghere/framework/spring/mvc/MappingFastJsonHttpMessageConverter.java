package com.sunsea.parkinghere.framework.spring.mvc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * <pre>
 * &lt;bean class="com.google.code.p.sagdidp.spring.mvc.MappingFastJsonHttpMessageConverter" &gt;  
 *     &lt;property name="supportedMediaTypes" value="application/json" /&gt;  
 *     &lt;property name="serializerFeature"&gt;  
 *         &lt;array&gt;  
 *             &lt;value&gt;WriteMapNullValue&lt;/value&gt;  
 *             &lt;value&gt;QuoteFieldNames&lt;/value&gt;  
 *             &lt;value&gt;UseSingleQuotes&lt;/value&gt;  
 *         &lt;/array&gt;  
 *     &lt;/property&gt;  
 * &lt;/bean&gt;
 * </pre>
 */
public class MappingFastJsonHttpMessageConverter extends
                                                AbstractHttpMessageConverter<Object> {
    
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    
    private SerializerFeature[] serializerFeature = new SerializerFeature[] { SerializerFeature.QuoteFieldNames,
                                                                             SerializerFeature.WriteDateUseDateFormat,
                                                                             SerializerFeature.WriteMapNullValue,
                                                                             SerializerFeature.DisableCircularReferenceDetect };
    
    private SerializeConfig serializeConfig = new SerializeConfig();
    
    public MappingFastJsonHttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }
    
    public SerializerFeature[] getSerializerFeature() {
        return serializerFeature;
    }
    
    public void setSerializerFeature(SerializerFeature[] serializerFeature) {
        this.serializerFeature = serializerFeature;
    }
    
    public SerializeConfig getSerializeConfig() {
        return serializeConfig;
    }
    
    public void setSerializeConfig(SerializeConfig serializeConfig) {
        this.serializeConfig = serializeConfig;
    }
    
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        // JavaType javaType = getJavaType(clazz);
        // return this.objectMapper.canDeserialize(javaType) &&
        // canRead(mediaType);
        return true;
    }
    
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        // return this.objectMapper.canSerialize(clazz) && canWrite(mediaType);
        return true;
    }
    
    protected boolean supports(Class<?> clazz) {
        // should not be called, since we override canRead/Write instead
        throw new UnsupportedOperationException();
    }
    
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException,
                                                                                HttpMessageNotReadableException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i;
            while ((i = inputMessage.getBody().read()) != -1) {
                baos.write(i);
            }
            return JSON.parseObject(baos.toString(), clazz);
        }
        catch (RuntimeException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(),
                                                      ex);
        }
    }
    
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException,
                                                                           HttpMessageNotWritableException {
        
        Charset encoding = getJsonEncoding(outputMessage.getHeaders()
                                                        .getContentType());
        try {
            String jsonString = JSON.toJSONString(o,
                                                  serializeConfig,
                                                  serializerFeature);
            OutputStream out = outputMessage.getBody();
            out.write(jsonString.getBytes(encoding));
            out.flush();
        }
        catch (RuntimeException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(),
                                                      ex);
        }
    }
    
    /**
     * Determine the JSON encoding to use for the given content type.
     * 
     * @param contentType
     *            the media type as requested by the caller
     * @return the JSON encoding to use (never <code>null</code>)
     */
    protected Charset getJsonEncoding(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            return contentType.getCharSet();
        }
        return DEFAULT_CHARSET;
    }
    
}
