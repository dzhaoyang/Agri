package weixin.popular.bean.shakearound.result;

import weixin.popular.bean.BaseResult;
/**
 * 查询页面列表结果
 * @author ylr
 *
 */
public class PageSearchResult extends BaseResult {
	
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data{
		/**商户名下的页面总数*/
		private long total_count;
		/**页面*/
		private Page pages;

		public Page getPages() {
			return pages;
		}

		public void setPages(Page pages) {
			this.pages = pages;
		}

		public long getTotal_count() {
			return total_count;
		}

		public void setTotal_count(long total_count) {
			this.total_count = total_count;
		}
	}
	
	public class Page{
		/**摇周边页面唯一ID*/
		private String page_id;
		/**在摇一摇页面展示的主标题*/
		private String title;
		/**在摇一摇页面展示的副标题*/
		private String description;
		/**在摇一摇页面展示的图片*/
		private String icon_url;
		/**跳转链接*/
		private String page_url;
		/**页面的备注信息*/
		private String comment;
		
		public String getPage_id() {
			return page_id;
		}
		public void setPage_id(String page_id) {
			this.page_id = page_id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getIcon_url() {
			return icon_url;
		}
		public void setIcon_url(String icon_url) {
			this.icon_url = icon_url;
		}
		public String getPage_url() {
			return page_url;
		}
		public void setPage_url(String page_url) {
			this.page_url = page_url;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
	}
}
