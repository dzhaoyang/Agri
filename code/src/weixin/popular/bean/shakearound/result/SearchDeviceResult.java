package weixin.popular.bean.shakearound.result;

import weixin.popular.bean.BaseResult;
/**
 * 查询设备列表结果
 * @author ylr
 *
 */
public class SearchDeviceResult extends BaseResult {
	
	private Data data;
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data{
		/**指定的设备信息列表*/
		private DeviceResult[] deviceResults;
		/**商户名下的设备总量*/
		private long total_count;

		public DeviceResult[] getDeviceResults() {
			return deviceResults;
		}

		public void setDeviceResults(DeviceResult[] deviceResults) {
			this.deviceResults = deviceResults;
		}

		public long getTotal_count() {
			return total_count;
		}

		public void setTotal_count(long total_count) {
			this.total_count = total_count;
		}
	}
	


	public class DeviceResult{
		/**设备编号*/
		private String device_id;
		/***/
		private String uuid;
		/***/
		private String major;
		/***/
		private String minor;
		/**激活状态，0：未激活，1：已激活*/
		private String status;
		/**设备最近一次被摇到的日期（最早只能获取前一天的数据）；新申请的设备该字段值为0*/
		private String last_active_time;
		/**设备关联的门店ID，关联门店后，在门店1KM的范围内有优先摇出信息的机会*/
		private String poi_id;
		/**设备的备注信息*/
		private String comment;

		public String getDevice_id() {
			return device_id;
		}

		public void setDevice_id(String device_id) {
			this.device_id = device_id;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public String getMajor() {
			return major;
		}

		public void setMajor(String major) {
			this.major = major;
		}

		public String getMinor() {
			return minor;
		}

		public void setMinor(String minor) {
			this.minor = minor;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getLast_active_time() {
			return last_active_time;
		}

		public void setLast_active_time(String last_active_time) {
			this.last_active_time = last_active_time;
		}

		public String getPoi_id() {
			return poi_id;
		}

		public void setPoi_id(String poi_id) {
			this.poi_id = poi_id;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}
	}
}
