package bean;

public class UserBean {
	private String user_id, password, firstname, lastname, phone_number;
	private int address_id;

	public UserBean(String user_id, String password, String firstname, String lastname,
			String phone_number, int address_id) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone_number = phone_number;
		this.address_id = address_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public int getAddress_id() {
		return address_id;
	}
	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}
	
	public String getPhone_number() {
		return this.phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	
	
}
