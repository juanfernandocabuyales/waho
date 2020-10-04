package co.com.woaho.response;

import java.util.List;

public class ConsultarPantallasResponse extends BaseResponse{
	
	private List<Slide> listSlides;
	
	public List<Slide> getListSlides() {
		return listSlides;
	}

	public void setListSlides(List<Slide> listSlides) {
		this.listSlides = listSlides;
	}

	public static class Slide{
		
		private String image;
		
		private String title;
		
		private String subtitle;
		
		private String footer;

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSubtitle() {
			return subtitle;
		}

		public void setSubtitle(String subtitle) {
			this.subtitle = subtitle;
		}

		public String getFooter() {
			return footer;
		}

		public void setFooter(String footer) {
			this.footer = footer;
		}		
	}
}
