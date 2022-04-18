package bean;

public class UserBean {
	private String userId, password, firstname, lastname, phoneNumber, role;
	private int addressIdShip, addressIdBill;

	public UserBean(String userId, String password, String firstname, String lastname,
			String phoneNumber, String role, int addressIdShip, int addressIdBill) {
		super();
		this.userId = userId;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phoneNumber = phoneNumber;
		this.addressIdShip = addressIdShip;
		this.addressIdBill = addressIdBill;
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public int getAddressIdShip() {
		return addressIdShip;
	}

	public void setAddressIdShip(int addressIdShip) {
		this.addressIdShip = addressIdShip;
	}

	public int getAddressIdBill() {
		return addressIdBill;
	}

	public void setAddressIdBill(int addressIdBill) {
		this.addressIdBill = addressIdBill;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
