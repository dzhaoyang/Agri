package com.sunsea.parkinghere;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configuration {
    
    private static final Log logger = LogFactory.getLog(Configuration.class);
    
    static class ConfigurationHolder {
        static Configuration instance = new Configuration();
    }
    
    public static Configuration getInstance() {
        return ConfigurationHolder.instance;
    }
    
    private Properties properties = new Properties();
    
    public Configuration() {
        InputStream is = Configuration.class.getResourceAsStream("/application-settings.properties");
        if (is != null) {
            try {
                properties.load(is);
            }
            catch (IOException e) {
                logger.error(e, e);
            }
        }
    }
    
    public void reload() throws Exception{
    	InputStream is = Configuration.class.getResourceAsStream("/application-settings.properties");
        if (is != null) {
        	properties.clear();
            properties.load(is);
        }
    }
    
    public Properties getProperties() {
        return properties;
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String getProperty(String key, String defaultValue) {
        String result = properties.getProperty(key);
        if (StringUtils.isEmpty(result)) {
            return defaultValue;
        }
        return result;
    }
    
    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(value);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public String getAdministratorEmail() {
        return getProperty("administratorEmail");
    }
    
    public String getBaiduMapApi() {
        return getProperty("baidu.map.api");
    }
    
    public String getBaiMapAk() {
        return getProperty("baidu.map.ak");
    }
    
    public String getAndroidAppVersion() {
        return getProperty("android.app.version");
    }
    
    public String getAndroidAppDownloadUrl() {
        return getProperty("android.app.download.url");
    }

    public String getServerDomainUrl() {
        return getProperty("server.domain.url");
    }
    public String getServerIpUrl() {
        return getProperty("server.ip.url");
    }
}
