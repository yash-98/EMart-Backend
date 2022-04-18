package bean;

public class POBean {

	private int id, shippingAddressID, billingAddressID;
	private String email, lname, fname, status;
	
	public POBean(int id, int shippingAddressID, int billingAddressID, String email, String lname, String fname, String status) {
		super();
		this.id = id;
		this.shippingAddressID = shippingAddressID;
		this.email = email;
		this.lname = lname;
		this.fname = fname;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAddressID() {
		return shippingAddressID;
	}

	public void setAddressID(int shippingAddressID) {
		this.shippingAddressID = shippingAddressID;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getBillingAddressID() {
		return billingAddressID;
	}

	public void setBillingAddressID(int billingAddressID) {
		this.billingAddressID = billingAddressID;
	}
	
	
}
