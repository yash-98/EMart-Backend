package bean;

public class POBean {

	private int id, addressID;
	private String lname, fname, status;
	
	public POBean(int id, int addressID, String lname, String fname, String status) {
		super();
		this.id = id;
		this.addressID = addressID;
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
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
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
	
	
}
