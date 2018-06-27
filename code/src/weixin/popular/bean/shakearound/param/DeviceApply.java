package weixin.popular.bean.shakearound.param;
/**
 * 申请设备ID参数
 * @author ylr
 *
 */
public class DeviceApply {

	/**
	 * 申请的设备ID的数量，单次新增设备超过500个，需走人工审核流程
	 */
	private String quantity;
	/**
	 * 申请理由，不超过100个字
	 */
	private String apply_reason;
	/**
	 * 备注，不超过15个汉字或30个英文字母
	 */
	private String comment;
	/**
	 * 设备关联的门店ID，关联门店后，在门店1KM的范围内有优先摇出信息的机会
	 */
	private String poi_id;
	
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getApply_reason() {
		return apply_reason;
	}
	public void setApply_reason(String apply_reason) {
		this.apply_reason = apply_reason;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPoi_id() {
		return poi_id;
	}
	public void setPoi_id(String poi_id) {
		this.poi_id = poi_id;
	}
}
