package model;

import java.sql.SQLException;
import java.util.Map;

import bean.POItemBean;
import dao.*;

public class POItemModel {

	private POItemDAO poitemData;
	
	// Static ID variables
	private int poitemId;
	
	private static POItemModel instance;
	private POModel poModel;
	private ItemModel itemModel;
	
	public static POItemModel getInstance() {
		if (instance == null) {
			instance = new POItemModel();
			try {
				//DAO instantiation
				instance.poitemData = new POItemDAO();
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private POItemModel() {
		try {
			this.itemModel = ItemModel.getInstance();
			this.poModel = POModel.getInstance();
			// DAO instantiation
			poitemData = new POItemDAO();
			// Static variable ID instantiation
			// TODO instantiate IDs with the largest ID			
			this.poitemId = 0; //The Item ID will be provided as part of arg right.
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// POItem DAO based functions
		public void checkPOItemParamters(String price, String bid) {
		StringBuilder errstr = new StringBuilder("");
		boolean throwError = false;
		
		if (price.length() < 1 || price.matches("[^0-9]")) {
			errstr.append("The price parameter of POItem insertion is invalid.\n");
			throwError = true;
		}
		//TODO create function which checks if bid actually exists in Item table as it's a foreign key
		if (bid.length() < 1 || bid.matches("[^0-9]")) {
			
			//Function not needed
			try {
				bid = bid.replaceAll(" ", "").replaceAll("[\"\"'']", "");
				boolean exists = this.itemModel.getItemData().retrieveAll().containsKey(bid);
				if (!exists) {
					errstr.append("The BID parameter does not exist in the Items table.");
					throwError = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Added implementation
			
			errstr.append("The bid paramter of POItem insertion is invallid.\n");
			throwError = true;
		}
		if (throwError) {
			System.out.println(errstr.toString());
			throw new IllegalArgumentException();
		}
	}
	
	public int insertPOItem(int poID, String price, String bid) {
		try {
			
			checkPOItemParamters(price, bid);
			double tempPrice = Double.parseDouble(price);
			int tempBid = Integer.parseInt(bid);
			return this.poitemData.insert(poID, tempPrice, tempBid);
		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("There was an error when trying to insert the POItem.");
			return 0;
		}
	}
	
	public int deletePOItem(String id) {
		try {
			if (id.length() < 1 || id.matches("[^0-9]")) {
				System.out.println("The id of the POItem to delete is invalid.");
				throw new IllegalArgumentException();
			}
			int tempId = Integer.parseInt(id);
			return this.poitemData.delete(tempId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was a problem trying to delete the POItem with ID" + id +".");
			return 0;
		}
	}
	
	public Map<Integer, POItemBean> retrieveAllPOItem() {
		try {
			return this.poitemData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error trying to retrieve all the POItems.");
			return null;
		}
	}
	
	public Map<Integer, POItemBean> retrievePOItemsByID(String id) {
		try {
			if (id.length() < 1 || id.matches("[^0-9]")) {
				System.out.println("The id of the POItem to retrieve is invalid.");
				throw new IllegalArgumentException();
			}
			int tempId = Integer.parseInt(id);
			return this.poitemData.retrieveById(tempId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was a problem trying to retrieve the POItem by it's ID=" + id);
			return null;
		}
	}
	
	public Map<Integer, POItemBean> retrievePOItemsByBID(String bid) {
		try {
			if (bid.length() < 1 || bid.matches("[^0-9]")) {
				System.out.println("The bid of the Address to retrieve is invalid.");
				throw new IllegalArgumentException();
			}
			int tempBId = Integer.parseInt(bid);
			return this.poitemData.retrieveByBid(tempBId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was a problem trying to retrieve the Address by it's BID.");
			return null;
		}
	}
}
