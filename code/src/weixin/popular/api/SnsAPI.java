package weixin.popular.api;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import weixin.popular.bean.SnsToken;
import weixin.popular.bean.User;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunsea.parkinghere.http.HttpsMultiThreadUtil;

/**
 * 网页授权
 * 
 * @author LiYi
 */
public class SnsAPI extends BaseAPI {
	
	public void oauth2Authorize(String appid,String redirect_uri,String scope,String state){
		String url = BASE_URI + "/connect/oauth2/authorize?appid={appid}&redirect_uri={redirect_uri}&response_type=code&scope={scope}&state={state}#wechat_redirect";
        restTemplate.postForEntity(url,null,null,appid,redirect_uri,scope,state);
	}
    
    /**
     * 通过code换取网页授权access_token
     * 
     * @param appid
     * @param secret
     * @param code
     * @return
     */
    public SnsToken oauth2AccessToken(String appid, String secret, String code) {
    	String url = BASE_URI + "/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
    	System.out.println("oauth2AccessToken url ==== "+url);
    	ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,null,String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(responseEntity.getBody(),SnsToken.class);
        }catch (JsonParseException e) {
            e.printStackTrace();
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 刷新access_token
     * 
     * @param appid
     * @param refresh_token
     * @return
     */
    public SnsToken oauth2RefreshToken(String appid, String refresh_token) {
    	String url = BASE_URI + "/sns/oauth2/refresh_token?appid={appid}&refresh_token={refresh_token}&grant_type=refresh_token";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,null,String.class,appid,refresh_token);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(responseEntity.getBody(),SnsToken.class);
        }catch (JsonParseException e) {
            e.printStackTrace();
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * 
     * @param access_token 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     * @param openid 用户的唯一标识
     * @param lang 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     * @return
     */
    public User userinfo(String access_token, String openid, String lang) {
        String url = BASE_URI + "/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang="+lang;
        System.out.println("userinfo url ==== "+url);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,null,String.class);
        System.out.println("userinfo responseEntity.getBody() ==== "+responseEntity.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new String(responseEntity.getBody().getBytes("iso-8859-1"),"utf-8"),User.class);
        }catch (JsonParseException e) {
            e.printStackTrace();
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 检验授权凭证（access_token）是否有效
     * @param openid		用户的唯一标识
     * @param access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     * @return
     */
    public boolean authIsEffective(String openid,String access_token){
    	/*String url = BASE_URI+"/sns/auth?access_token={access_token}&openid={openid}";
    	ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,null,String.class,access_token,openid);
    	
    	try{
    		
    	}catch (JsonParseException e) {
            e.printStackTrace();
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }*/
    	return false;
    }
}
