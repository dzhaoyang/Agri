package weixin.popular.bean.shakearound.result;

import weixin.popular.bean.BaseResult;
/**
 * 获取摇周边的设备及用户信息结果
 * @author ylr
 *
 */
public class UserShakeInfoResult extends BaseResult {
	
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data{
		/**摇周边页面唯一ID*/
		private String page_id;
		/**商户AppID下用户的唯一标识*/
		private String openid;
		/**门店ID，有的话则返回，反之不会在JSON格式内*/
		private String poi_id;
		/**设备信息*/
		private BeaconInfo beacon_info;
		
		public String getPage_id() {
			return page_id;
		}
		public void setPage_id(String page_id) {
			this.page_id = page_id;
		}
		public String getOpenid() {
			return openid;
		}
		public void setOpenid(String openid) {
			this.openid = openid;
		}
		public String getPoi_id() {
			return poi_id;
		}
		public void setPoi_id(String poi_id) {
			this.poi_id = poi_id;
		}
		public BeaconInfo getBeacon_info() {
			return beacon_info;
		}
		public void setBeacon_info(BeaconInfo beacon_info) {
			this.beacon_info = beacon_info;
		}
	}
	
	/**
	 * 设备信息
	 * @author ylr
	 *
	 */
	public class BeaconInfo{
		/**Beacon信号与手机的距离，单位为米*/
		private Double distance;
		/***/
		private String uuid;
		/***/
		private String major;
		/***/
		private String minor;
		
		public Double getDistance() {
			return distance;
		}
		public void setDistance(Double distance) {
			this.distance = distance;
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
	}
}
