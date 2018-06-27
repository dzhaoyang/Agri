package weixin.popular.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 网页授权access_token
 * @author ylr
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SnsToken extends BaseResult {
    /**网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同*/
    private String access_token;
    /**access_token接口调用凭证超时时间，单位（秒）*/
    private Integer expires_in;
    /**用户刷新access_token*/
    private String refresh_token;
    /**用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID*/
    private String openid;
    /**用户授权的作用域，使用逗号（,）分隔*/
    private String scope;
    /**只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段*/
    private String unionid;
    
    /**网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同*/
    public String getAccess_token() {
        return access_token;
    }
    
    /**网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同*/
    public void setAccess_token(String accessToken) {
        access_token = accessToken;
    }
    
    /**access_token接口调用凭证超时时间，单位（秒）*/
    public Integer getExpires_in() {
        return expires_in;
    }
    
    /**access_token接口调用凭证超时时间，单位（秒）*/
    public void setExpires_in(Integer expiresIn) {
        expires_in = expiresIn;
    }
    
    /**用户刷新access_token*/
    public String getRefresh_token() {
        return refresh_token;
    }
    
    /**用户刷新access_token*/
    public void setRefresh_token(String refreshToken) {
        refresh_token = refreshToken;
    }
    
    /**用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID*/
    public String getOpenid() {
        return openid;
    }
    
    /**用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID*/
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    
    /**用户授权的作用域，使用逗号（,）分隔*/
    public String getScope() {
        return scope;
    }
    
    /**用户授权的作用域，使用逗号（,）分隔*/
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段*/
	public String getUnionid() {
		return unionid;
	}

	/**只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段*/
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
    
}
