package weixin.popular.bean.shakearound.param;

import java.util.List;
/**
 * 摇一摇申请参数
 * @author ylr
 *
 */
public class AccountRegister {

	/**
	 * 联系人姓名
	 */
	private String name;
	/**
	 * 联系人电话
	 */
	private String phone_number;
	/**
	 * 联系人邮箱
	 */
	private String email;
	/**
	 * 平台定义的行业代号，具体请查看链接行业代号
	 */
	private String industry_id;
	/**
	 * 相关资质文件的图片url，图片需先上传至微信侧服务器，用“素材管理-上传图片素材”接口上传图片，
	 * 返回的图片URL再配置在此处；当不需要资质文件时，数组内可以不填写url
	 */
	private List<String> qualification_cert_urls;
	/**
	 * 申请理由
	 */
	private String apply_reason;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIndustry_id() {
		return industry_id;
	}
	public void setIndustry_id(String industry_id) {
		this.industry_id = industry_id;
	}
	public List<String> getQualification_cert_urls() {
		return qualification_cert_urls;
	}
	public void setQualification_cert_urls(List<String> qualification_cert_urls) {
		this.qualification_cert_urls = qualification_cert_urls;
	}
	public String getApply_reason() {
		return apply_reason;
	}
	public void setApply_reason(String apply_reason) {
		this.apply_reason = apply_reason;
	}
}
