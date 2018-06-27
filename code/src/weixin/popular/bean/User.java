package weixin.popular.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 微信用户信息
 * @author ylr
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseResult {
    /**用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息*/
    private Integer subscribe;
    /**用户的标识，对当前公众号唯一*/
    private String openid;
    /**用户昵称*/
    private String nickname;
    /**用户的性别，值为1时是男性，值为2时是女性，值为0时是未知*/
    private Integer sex;
    /***/
    private String language;
    /**普通用户个人资料填写的城市*/
    private String city;
    /**用户个人资料填写的省份*/
    private String province;
    /**国家，如中国为CN*/
    private String country;
    /**用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效*/
    private String headimgurl;
    /***/
    private Integer subscribe_time;
    /**用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）*/
    private String[] privilege;
    
    public Integer getSubscribe() {
        return subscribe;
    }
    
    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }
    
    public String getOpenid() {
        return openid;
    }
    
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public Integer getSex() {
        return sex;
    }
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getHeadimgurl() {
        return headimgurl;
    }
    
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
    
    public Integer getSubscribe_time() {
        return subscribe_time;
    }
    
    public void setSubscribe_time(Integer subscribeTime) {
        subscribe_time = subscribeTime;
    }
    
    public String[] getPrivilege() {
        return privilege;
    }
    
    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }
    
}
