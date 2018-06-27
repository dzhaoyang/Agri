package weixin.popular.bean.shakearound.result;

import weixin.popular.bean.BaseResult;
/**
 * 新增页面结果
 * @author ylr
 *
 */
public class PageAddResult extends BaseResult {
	
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data{
		/**新增页面的页面id*/
		private String page_id;

		public String getPage_id() {
			return page_id;
		}

		public void setPage_id(String page_id) {
			this.page_id = page_id;
		}
	}
}
