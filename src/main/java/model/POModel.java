package model;

import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;

import bean.POBean;
import bean.UserBean;
import dao.*;

public class POModel {
	private PODAO poData;
	private int ordersProcessed;
	
	// Static ID variables
	private int pOId;

	
	private static POModel instance;
	private UserModel userModel;
	
	public static POModel getInstance() {
		if (instance == null) {
			instance = new POModel();
			try {
				//DAO instantiation
				instance.poData = new PODAO();
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private POModel() {
		try {
			// DAO instantiation
			poData = new PODAO();
			userModel = UserModel.getInstance();
			// Static variable ID instantiation
			// TODO instantiate IDs with the largest ID			
			this.pOId = poData.LastID();
			this.ordersProcessed = 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// PODAO based functions
		public void checkPOParameters(String email, String firstname, String lastname, String status) {
			boolean throwError = false;
			if (email.length() < 1) {
				System.out.println("Purchase order email is not valid.");
				throwError = true;
			}
			if (firstname.length() < 1) {
				System.out.println("Purchase order firstname is not valid.");
				throwError = true;
			}
			if (lastname.length() < 1) {
				System.out.println("Purchase order lastname is not valid.");
				throwError = true;
			}
			if (status.length() < 1) {
				System.out.println("Purchase order status is not valid.");
				throwError = true;
			}
			if (throwError) {
				throw new IllegalArgumentException();
			}
		}
		
		public int insertPurchaseOrder(String email, String status) {
			try {
				this.pOId++;
				
				UserBean user = userModel.retrieveUser(email).get(email);
				
				int saddrId = user.getAddressIdShip();
				int baddrId = user.getAddressIdBill();
				
				checkPOParameters(email, user.getFirstname(), user.getLastname(), status);
				email = email.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				status = status.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				
				this.poData.insert(pOId, saddrId, baddrId, email, user.getLastname(), user.getFirstname(), status);
				return this.pOId;
			} catch (Exception e) {
				// TODO: handle exception
				this.pOId--;
				System.out.println("There was an error trying to insert the Purchase Order.");
				return 0;
			}
		}
		
		public String processOrder(int poId) {
			
			try {
				this.ordersProcessed++;
				
				String status = this.ordersProcessed%3==0?"Declined":"Processed";
				
				poData.changeOrderStatus(poId, status);
				
				return status;
			}catch (Exception e) {
				
				System.out.println("There was a problem trying to process the Purchase Order.");
				return "Error, Try Again";
			}
		}
		
		public int deletePurchaseOrder (String id) {
			try {
				if (id.length() < 1 || id.matches("[^0-9]")) {
					System.out.println("The id of the purchase order to delete is invalid.");
					throw new IllegalArgumentException();
				}
				int tempId = Integer.parseInt(id);
				return poData.delete(tempId);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("There was a problem trying to delete the Purchase Order.");
				return 0;
			}
		}
		
		public Map<Integer, POBean> retrieveAllPO() {
			try {
				return this.poData.retrieveAll();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("There was an error trying to retrieve all the Purchase Orders.");
				return null;
			}
		}
		
		public Map<Integer, POBean> retrieveAllPOByID(String id) {
			try {
				if (id.length() < 1 || id.matches("[^0-9]")) {
					System.out.println("The id of the purchase order to retrieve is invalid.");
					throw new IllegalArgumentException();
				}
				int tempId = Integer.parseInt(id);
				return this.poData.retrieveById(tempId);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("There was an error trying to retrieve all the Purchase Orders by their ID.");
				return null;
			}
		}
		
		public Map<Integer, POBean> retrieveAllPOByEmail(String email) {
			try {
				if (email.length() < 1) {
					System.out.println("The email of the purchase order to retrieve is invalid.");
					throw new IllegalArgumentException();
				}
				email = email.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				return this.poData.retrieveByEmail(email);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("There was an error trying to retrieve all the Purchase Orders by their email.");
				return null;
			}
		}
		
		public int changeStatus(String id, String status) {
			
			try {
				return poData.changeOrderStatus(Integer.parseInt(id), status);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return 0;
		}
}
