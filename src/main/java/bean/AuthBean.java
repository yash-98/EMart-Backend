package bean;

public class AuthBean {

	private String email, token, date, role;

	public AuthBean(String token, String date) {
		super();
		this.token = token;
		this.date = date;
		this.role = "CUSTOMER";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
