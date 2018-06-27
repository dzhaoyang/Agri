package weixin.popular.bean.shakearound.result;

import weixin.popular.bean.BaseResult;
/**
 * 查询设备ID申请审核状态结果
 * @author ylr
 *
 */
public class DeviceApplyStatusResult extends BaseResult {
	
	private Data data;
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data{
		/**
		 * 提交申请的时间戳
		 */
		private String apply_time;
		/**
		 * 审核状态。0：审核未通过、1：审核中、2：审核已通过；<br>
		 * 若单次申请的设备ID数量小于等于500个，系统会进行快速审核；若单次申请的设备ID数量大于500个，会在三个工作日内完成审核；
		 */
		private String audit_status;
		/**
		 * 审核备注，对审核状态的文字说明
		 */
		private String audit_comment;
		/**
		 * 确定审核结果的时间戳，若状态为审核中，则该时间值为0
		 */
		private String audit_time;
		
		public String getApply_time() {
			return apply_time;
		}
		public void setApply_time(String apply_time) {
			this.apply_time = apply_time;
		}
		public String getAudit_status() {
			return audit_status;
		}
		public void setAudit_status(String audit_status) {
			this.audit_status = audit_status;
		}
		public String getAudit_comment() {
			return audit_comment;
		}
		public void setAudit_comment(String audit_comment) {
			this.audit_comment = audit_comment;
		}
		public String getAudit_time() {
			return audit_time;
		}
		public void setAudit_time(String audit_time) {
			this.audit_time = audit_time;
		}
	}
}
