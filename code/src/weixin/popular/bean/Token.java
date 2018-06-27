package weixin.popular.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token extends BaseResult {
    
    private String access_token;
    
    private int expires_in;
    
    private long createdAt = System.currentTimeMillis();
    
    public String getAccess_token() {
        return access_token;
    }
    
    public void setAccess_token(String accessToken) {
        access_token = accessToken;
    }
    
    public int getExpires_in() {
        return expires_in;
    }
    
    public void setExpires_in(int expiresIn) {
        expires_in = expiresIn;
    }
    
    public boolean wasExpired() {
        return System.currentTimeMillis() > createdAt + getExpires_in() * 1000;
    }
    
}
