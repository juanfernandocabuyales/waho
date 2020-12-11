package co.com.woaho.dto;

public class PaisDTO {

	private String id;
	private String name;
	private String code;
	private String image;
	
	public PaisDTO(String id, String name, String code,String image) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.image = image;
	}	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}	
}
