package tedu.panmengquan.mine;

public class Book {
	private String title11111111111;
	private String title;
	private String Catalog;
	private String img;
	public Book(){
		
	}
	public Book(String title, String catalog, String img) {
		super();
		this.title = title;
		Catalog = catalog;
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCatalog() {
		return Catalog;
	}
	public void setCatalog(String catalog) {
		Catalog = catalog;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

}
