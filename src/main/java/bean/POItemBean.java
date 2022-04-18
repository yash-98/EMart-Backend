package bean;

public class POItemBean {

	private int id, bid;
	private double price;
	
	public POItemBean(int id, double price, int bid) {
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}
	
	
	
}
