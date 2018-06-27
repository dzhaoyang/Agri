package weixin.popular.api;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import weixin.popular.bean.BaseResult;
import weixin.popular.bean.shakearound.param.AccountRegister;
import weixin.popular.bean.shakearound.param.DeviceApply;
import weixin.popular.bean.shakearound.result.DeviceApplyResult;
import weixin.popular.bean.shakearound.result.DeviceApplyStatusResult;
import weixin.popular.bean.shakearound.result.MaterialAddResult;
import weixin.popular.bean.shakearound.result.PageAddResult;
import weixin.popular.bean.shakearound.result.PageSearchResult;
import weixin.popular.bean.shakearound.result.SearchDeviceResult;
import weixin.popular.bean.shakearound.result.UserShakeInfoResult;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 摇一摇周边微信接口
 * @author ylr
 *
 */
public class ShakeAroundAPI extends BaseAPI {

	/**
	 * 申请开通功能
	 * @param access_token
	 * @param accountRegister
	 * @return
	 */
	public BaseResult accountRegister(String access_token, AccountRegister accountRegister) {
		String url = BASE_URI+"/shakearound/account/register?access_token={access_token}";
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,false);
        try{
            String json = objectMapper.writeValueAsString(accountRegister);
            
            MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
			ResponseEntity<BaseResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,BaseResult.class,access_token);
			return responseEntity.getBody();
        }catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 查询审核状态
	 * @param access_token
	 * @return
	 */
	public String accountAuditstatus(String access_token) {
		String url = BASE_URI+"/shakearound/account/auditstatus?access_token={access_token}";
		try{
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,null,String.class,access_token);
			return responseEntity.getBody();
		}catch(Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/**
	 * 申请设备ID
	 * @param access_token
	 * @param deviceApply
	 * @return
	 */
	public DeviceApplyResult deviceApply(String access_token,DeviceApply deviceApply) {
		String url = BASE_URI+"/shakearound/device/applyid?access_token={access_token}";
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,false);
		try{
			String json = objectMapper.writeValueAsString(deviceApply);
            
            MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mediaType);
			HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
			ResponseEntity<DeviceApplyResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,DeviceApplyResult.class,access_token);
			return responseEntity.getBody();
		}catch(JsonProcessingException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/**
	 * 查询设备ID申请审核状态
	 * @param access_token
	 * @param apply_id		批次ID，申请设备ID时所返回的批次ID
	 * @return
	 */
	public DeviceApplyStatusResult deviceApplyStatus(String access_token,String apply_id) {
		try{
			String url = BASE_URI+"/shakearound/device/applystatus?access_token={access_token}";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("apply_id", apply_id);
			
			HttpEntity<String> httpEntity = this.buildHttpEntity(map);
			
			ResponseEntity<DeviceApplyStatusResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,DeviceApplyStatusResult.class,access_token);
			return responseEntity.getBody();
		}catch(JsonProcessingException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/**
	 * 编辑设备信息
	 * @param access_token		调用接口凭证
	 * @param device_id			设备编号，若填了UUID、major、minor，则可不填设备编号，若二者都填，则以设备编号为优先
	 * @param UUID				ibeancion设备UUID
	 * @param major				ibeancion设备major
	 * @param minor				ibeancion设备minor
	 * @param comment			设备的备注信息，不超过15个汉字或30个英文字母。
	 * @return
	 */
	public BaseResult deviceEdit(String access_token,String device_id,String UUID,String major,String minor,String comment){
		try{
			String url = BASE_URI+"/shakearound/device/update?access_token={access_token}";
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,String> device_identifier = new HashMap<String,String>();
			device_identifier.put("device_id", device_id);
			device_identifier.put("UUID", UUID);
			device_identifier.put("major", major);
			map.put("device_identifier", device_identifier);
			map.put("comment", comment);
			
			HttpEntity<String> httpEntity = this.buildHttpEntity(map);
			
			ResponseEntity<BaseResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,BaseResult.class,access_token);
			return responseEntity.getBody();
		}catch(JsonProcessingException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/**
	 * 查询设备列表，根据设备id查询
	 * @param access_token
	 * @param maps			key:device_id,uuid,major,minor
	 * @return
	 */
	public SearchDeviceResult deviceSearchById(String access_token,Map<String,Object>[] maps){
		try{
			String url = BASE_URI+"/shakearound/device/search?access_token={access_token}";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", 1);
			map.put("device_identifiers", maps);
			
			HttpEntity<String> httpEntity = this.buildHttpEntity(map);
			
			ResponseEntity<SearchDeviceResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,SearchDeviceResult.class,access_token);
			return responseEntity.getBody();
		}catch(JsonProcessingException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/**
	 * 查询设备列表，分页查询
	 * @param access_token
	 * @param apply_id		可以不传
	 * @return
	 */
	public SearchDeviceResult deviceSearchByPage(String access_token,String apply_id,int begin,int count){
		try{
			String url = BASE_URI+"/shakearound/device/search?access_token={access_token}";
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("type", StringUtils.isNotBlank(apply_id)?3:2);
			map.put("begin", begin);
			map.put("count", count);
			if(StringUtils.isNotBlank(apply_id)){
				map.put("apply_id", apply_id);
			}
			
			HttpEntity<String> httpEntity = this.buildHttpEntity(map);
			
			ResponseEntity<SearchDeviceResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,SearchDeviceResult.class,access_token);
			return responseEntity.getBody();
		}catch(JsonProcessingException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/**
	 * 新增页面
	 * @param access_token	调用接口凭证
	 * @param title			在摇一摇页面展示的主标题，不超过6个字
	 * @param description	在摇一摇页面展示的副标题，不超过7个字
	 * @param icon_url		在摇一摇页面展示的图片。图片需先上传至微信侧服务器，用“素材管理-上传图片素材”接口上传图片，返回的图片URL再配置在此处
	 * @param comment		页面的备注信息，不超过15个字。非必填
	 * @return
	 */
	public PageAddResult pageAdd(String access_token,String title,String description,String icon_url,String comment){
		String url = BASE_URI+"/shakearound/page/add?access_token={access_token}";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("title", title);
		map.put("description", description);
		map.put("icon_url", icon_url);
		map.put("comment", comment);
		
		HttpEntity<String> httpEntity;
		try {
			httpEntity = this.buildHttpEntity(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		ResponseEntity<PageAddResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,PageAddResult.class,access_token);
		return responseEntity.getBody();
	}
	
	/**
	 * 编辑页面信息
	 * @param access_token	调用接口凭证
	 * @param page_id		摇周边页面唯一ID
	 * @param title			在摇一摇页面展示的主标题，不超过6个字
	 * @param description	在摇一摇页面展示的副标题，不超过7个字
	 * @param icon_url		在摇一摇页面展示的图片。图片需先上传至微信侧服务器，用“素材管理-上传图片素材”接口上传图片，返回的图片URL再配置在此处
	 * @param comment		页面的备注信息，不超过15个字。非必填
	 * @return
	 */
	public BaseResult pageUpdate(String access_token,String page_id,String title,String description,String icon_url,String comment){
		String url = BASE_URI+"/shakearound/page/update?access_token={access_token}";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page_id", page_id);
		map.put("title", title);
		map.put("description", description);
		map.put("icon_url", icon_url);
		map.put("comment", comment);
		
		HttpEntity<String> httpEntity;
		try {
			httpEntity = this.buildHttpEntity(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		ResponseEntity<BaseResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,BaseResult.class,access_token);
		return responseEntity.getBody();
	}
	
	/**
	 * 查询页面列表
	 * @param access_token
	 * @param page_ids		指定页面的id列表
	 * @param begin			页面列表的起始索引值
	 * @param count			待查询的页面数量，不能超过50个
	 * @return
	 */
	public PageSearchResult pageSearch(String access_token,String[] page_ids,int begin,int count){
		String url = BASE_URI+"/shakearound/page/search?access_token={access_token}";
		Map<String,Object> map = new HashMap<String,Object>();
		int type;
		if(page_ids!=null&&page_ids.length!=0){
			type = 1;
			map.put("page_ids", page_ids);
		}else{
			type = 2;
			map.put("begin", begin);
			map.put("count", count);
		}
		map.put("type", type);
		
		HttpEntity<String> httpEntity;
		try {
			httpEntity = this.buildHttpEntity(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		ResponseEntity<PageSearchResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,PageSearchResult.class,access_token);
		return responseEntity.getBody();
	}
	
	/**
	 * 删除页面
	 * @param access_token
	 * @param page_id		页面id
	 * @return
	 */
	public BaseResult pageDelete(String access_token,String page_id){
		String url = BASE_URI+"/shakearound/page/delete?access_token={access_token}";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page_id", page_id);
		
		HttpEntity<String> httpEntity;
		try {
			httpEntity = this.buildHttpEntity(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		ResponseEntity<BaseResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,BaseResult.class,access_token);
		return responseEntity.getBody();
	}
	
	/**
	 * 上传图片素材 <br>
	 * 上传在摇一摇功能中需使用到的图片素材，素材保存在微信侧服务器上。图片格式限定为：jpg,jpeg,png,gif。 <br>
	 * 若图片为在摇一摇页面展示的图片，则其素材为icon类型的图片，图片大小建议120px*120 px，限制不超过200 px *200 px，图片需为正方形。 <br>
	 * 若图片为申请开通摇一摇周边功能需要上传的资质文件图片，则其素材为license类型的图片，图片的文件大小不超过2MB，尺寸不限，形状不限
	 * @param access_token
	 * @param fileName		图片名称
	 * @param resource		图片资源
	 * @return
	 */
	public MaterialAddResult materialAdd(String access_token,String fileName,FileSystemResource resource){
		String url = BASE_URI+"/shakearound/material/add?access_token={access_token}&type={type}";
		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
        try {
            multiValueMap.add("media", resource);
            String result = super.restTemplate.postForObject(url,multiValueMap,String.class,access_token,fileName);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(result, MaterialAddResult.class);
        }
        catch (JsonParseException e) {
            e.printStackTrace();
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 配置设备与页面的关联关系<br>
	 * 配置设备与页面的关联关系。配置时传入该设备需要关联的页面的id列表（该设备原有的关联关系将被直接清除）；<br>
	 * 页面的id列表允许为空，当页面的id列表为空时则会清除该设备的所有关联关系。配置完成后，在此设备的信号范围内，即可摇出关联的页面信息。<br>
	 * 在申请设备ID后，可直接使用接口直接配置页面。<br>
	 * 若设备配置多个页面，则随机出现页面信息。一个设备最多可配置30个关联页面<br>
	 * @param access_token
	 * @param device_id		设备编号，若填了UUID、major、minor，则可不填设备编号，若二者都填，则以设备编号为优先
	 * @param uuid			
	 * @param major			
	 * @param minor			
	 * @param page_ids		待关联的页面列表
	 * @return
	 */
	public BaseResult deviceBindPage(String access_token,String device_id,String uuid,String major,String minor,String[] page_ids){
		String url = BASE_URI+"/shakearound/device/bindpage?access_token={access_token}";
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> device_identifier = new HashMap<String,Object>();
		device_identifier.put("device_id", device_id);
		device_identifier.put("uuid", uuid);
		device_identifier.put("major", major);
		device_identifier.put("minor", minor);
		map.put("device_identifier", device_identifier);
		map.put("page_ids", page_ids);
		
		HttpEntity<String> httpEntity;
		try {
			httpEntity = this.buildHttpEntity(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		ResponseEntity<BaseResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,BaseResult.class,access_token);
		return responseEntity.getBody();
	}
	
	/**
	 * 获取摇周边的设备及用户信息
	 * @param access_token
	 * @param ticket		摇周边业务的ticket，可在摇到的URL中得到，ticket生效时间为30分钟，每一次摇都会重新生成新的ticket
	 * @param need_poi		是否需要返回门店poi_id，传1则返回，否则不返回
	 * @return
	 */
	public UserShakeInfoResult getShakeInfo(String access_token,String ticket,String need_poi){
		String url = BASE_URI+"/shakearound/user/getshakeinfo?access_token={access_token}";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ticket", ticket);
		if(StringUtils.isNotBlank(need_poi)){
			map.put("need_poi", need_poi);
		}
		
		HttpEntity<String> httpEntity;
		try {
			httpEntity = this.buildHttpEntity(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		ResponseEntity<UserShakeInfoResult> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,UserShakeInfoResult.class,access_token);
		return responseEntity.getBody();
	}
	
	
	private HttpEntity<String> buildHttpEntity(Map<String,Object> map) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,false);
        String json = objectMapper.writeValueAsString(map);
        MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
		return httpEntity;
	}
	
}
