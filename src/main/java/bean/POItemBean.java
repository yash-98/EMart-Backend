package bean;

public class POItemBean {

	private int id, price;
	private String bid;
	
	public POItemBean(int id, int price, String bid) {
		super();
		this.id = id;
		this.price = price;
		this.bid = bid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}
	
	
	
}
