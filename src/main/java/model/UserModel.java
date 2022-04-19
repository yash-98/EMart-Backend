package model;

import java.util.Map;

import bean.UserBean;
import dao.AddressDAO;
import dao.UserDAO;

public class UserModel {
	
	private UserDAO userData;
	private AddressDAO addressData;
	
	// Static ID variables
		
	private static UserModel instance;
	private AddressModel addressModel; 
	
	public static UserModel getInstance() {
		if (instance == null) {
			instance = new UserModel();
			try {
				//DAO instantiation
				instance.userData = new UserDAO();
				instance.addressData = new AddressDAO();
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private UserModel() {
		try {
			this.addressModel = AddressModel.getInstance();
			// DAO instantiation
			userData = new UserDAO();
			addressData = new AddressDAO();
			// Static variable ID instantiation
			// TODO instantiate IDs with the largest ID			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//User based functions
		public Map<String, UserBean> retrieveUserToAuthenticate(String userId, String password) {
			try {
				if (userId.length() < 1) {
					System.out.println("Invalid User id.");
				}
				if (password.length() < 1) {
					System.out.println("Invalid password.");
				}
				userId = userId.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				password = password.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				return userData.retrieveUserAuth(userId, password);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Incorrect or Invalid user id/password.");
				return null;
			}
		}
		
		public Map<String, UserBean> retrieveUser(String userId) {
			try {
				if (userId.length() < 1) {
					System.out.println("Invalid User id.");
				}
				userId = userId.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				return userData.retrieveUser(userId);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Incorrect or Invalid user id.");
				return null;
			}
		}
		
		public void checkUserParamters(String email, String password, String firstname, String lastname, String phonenumber, String role) {
			boolean throwError = false;
			if (email.length() < 1) {
				System.out.println("Email is not valid.");
				throwError = true;
			}
			if (password.length() < 1) {
				System.out.println("Entered password is not valid.");
				throwError = true;
			}
			if (firstname.length() < 1) {
				System.out.println("Entered first name is not valid.");
				throwError = true;
			}
			if (lastname.length() < 1) {
				System.out.println("Entered last name is not valid.");
				throwError = true;
			}
			if (phonenumber.length() < 1 || phonenumber.length() > 10) {
				System.out.println("Entered phonenumber is not valid.");
				throwError = true;
			}
			if (!(role.equals("CUSTOMER") || role.equals("ADMIN"))) {
				System.out.println("The entered user role is not valid.");
				throwError = true;
			}
			if (throwError) {
				throw new IllegalArgumentException();
			}
		}
		
		public int addUser(String email, String password, String firstname, String lastname, String phonenumber, String role, 
				String streetShip, String provinceShip, String countryShip, String zipShip, String streetBill, String provinceBill, String countryBill, String zipBill) {
			try {
				checkUserParamters(email, password, firstname, lastname, phonenumber, role);
				email = email.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				password = password.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				firstname = firstname.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				lastname = lastname.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				phonenumber = phonenumber.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				checkUserParamters(email, password, firstname, lastname, phonenumber, role);
				//TODO ADD IN ADDRESS INFORMATION FOR ADDUSER
				int addIdShip = addressData.retrieveByAll(streetShip, provinceShip, countryShip, zipShip);
				
				if(addIdShip == -1) {
					
					addIdShip = this.addressModel.insertAddress(streetShip, provinceShip, countryShip, zipShip);
				}
				int addIdBill = this.addressModel.getAddressData().retrieveByAll(streetBill, provinceBill, countryBill, zipBill);
				
				if(addIdBill == -1) {
					
					addIdBill = this.addressModel.insertAddress(streetBill, provinceBill, countryBill, zipBill);
				}
				
				return userData.insertUser(email, password, firstname, lastname, phonenumber, role, addIdShip, addIdBill);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("There was an error when trying to create/insert the user.");
				e.printStackTrace();
				return -1;
			}
		}
}
