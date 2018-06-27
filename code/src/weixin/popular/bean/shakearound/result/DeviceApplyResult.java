package weixin.popular.bean.shakearound.result;

import weixin.popular.bean.BaseResult;
/**
 * 申请设备结果
 * @author ylr
 *
 */
public class DeviceApplyResult extends BaseResult {
	
	private Data data;
	
	
	public Data getData() {
		return data;
	}


	public void setData(Data data) {
		this.data = data;
	}


	public class Data{
		/**
		 * 申请的批次ID，可用在“查询设备列表”接口按批次查询本次申请成功的设备ID
		 */
		private String apply_id;
		/**
		 * 审核备注，对审核状态的文字说明
		 */
		private String audit_status;
		/**
		 * 审核状态。0：审核未通过、1：审核中、2：审核已通过；<br>
		 * 若单次申请的设备ID数量小于等于500个，系统会进行快速审核；若单次申请的设备ID数量大于500个，会在三个工作日内完成审核；此时返回值全部为1(审核中)
		 */
		private String audit_comment;
		
		public String getApply_id() {
			return apply_id;
		}
		public void setApply_id(String apply_id) {
			this.apply_id = apply_id;
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
	}
}
