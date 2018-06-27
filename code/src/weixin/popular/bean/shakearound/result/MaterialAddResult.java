package weixin.popular.bean.shakearound.result;

import weixin.popular.bean.BaseResult;
/**
 * 上传图片素材结果
 * @author ylr
 *
 */
public class MaterialAddResult extends BaseResult {
	
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		/**
		 * 图片url地址，<br>
		 * 若type=icon，可用在“新增页面”和“编辑页面”的“icon_url”字段；<br>
		 * 若type= license，可用在“申请入驻”的“qualification_cert_urls”字段
		 */
		private String pic_url;

		public String getPic_url() {
			return pic_url;
		}

		public void setPic_url(String pic_url) {
			this.pic_url = pic_url;
		}
	}
}
