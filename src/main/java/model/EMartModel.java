package model;

import dao.*;

import java.util.*;

import bean.*;

public class EMartModel {

	private ItemDAO itemData;
	private ReviewDAO reviewData;
	private UserDAO userData;
	private PODAO poData;
	private AddressDAO addressData;
	private POItemDAO poitemData;
	
	// Static ID variables
	private int reviewId;
	private int addressId;
	private int itemId, bId, pOId, poitemId;
	
	
	private static EMartModel instance;
	
	public static EMartModel getInstance() {
		if (instance == null) {
			instance = new EMartModel();
			try {
				//DAO instantiation
				instance.itemData = new ItemDAO();
				instance.reviewData = new ReviewDAO();
				instance.userData = new UserDAO();
				instance.poData = new PODAO();
				instance.addressData = new AddressDAO();
				instance.poitemData = new POItemDAO();
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private EMartModel() {
		try {
			// DAO instantiation
			itemData = new ItemDAO();
			reviewData = new ReviewDAO();
			userData = new UserDAO();
			poData = new PODAO();
			addressData = new AddressDAO();
			poitemData = new POItemDAO();
			// Static variable ID instantiation
			// TODO instantiate IDs with the largest ID
			this.addressId = 0;
			this.itemId = 0;
			this.reviewId = 0;
			this.bId = 0;
			this.pOId = 0;
			this.poitemId = 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// VisitEvent DAO based functions
//	public int insertVisitEvent()
	
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
			errstr.append("The bid paramter of POItem insertion is invallid.\n");
			throwError = true;
		}
		if (throwError) {
			System.out.println(errstr.toString());
			throw new IllegalArgumentException();
		}
	}
	
	public int insertPOItem(String price, String bid) {
		try {
			this.poitemId++;
			checkPOItemParamters(price, bid);
			double tempPrice = Double.parseDouble(price);
			int tempBid = Integer.parseInt(bid);
			return this.poitemData.insert(this.poitemId, tempPrice, tempBid);
		} catch (Exception e) {
			// TODO: handle exception
			this.poitemId--;
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
	
	// Address DAO based functions
	public void checkAddressParamters(String street, String province, String country, String zip) {
		StringBuilder errstr = new StringBuilder("");
		boolean throwError = false;
		
		if (street.length() < 1) {
			errstr.append("The street paramter is incorrect for the address insert.\n");
			throwError = true;
		}
		if (province.length() < 1) {
			errstr.append("The province paramter is incorrect for the address insert.\n");
			throwError = true;
		}
		if (country.length() < 1) {
			errstr.append("The country paramter is incorrect for the address insert.\n");
			throwError = true;
		}
		if (zip.length() < 1) {
			errstr.append("The zip paramter is incorrect for the address insert.\n");
			throwError = true;
		}
		
		if (throwError) {
			System.out.println(errstr.toString());
			throw new IllegalArgumentException();
		}
	}
	
	public int insertAddress(String street, String province, String country, String zip) {
		try {
			this.addressId++;
			checkAddressParamters(street, province, country, zip);
			street = street.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			province = province.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			country = country.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			zip = zip.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			return this.addressData.insert(addressId, street, province, country, zip);
		} catch (Exception e) {
			// TODO: handle exception
			this.addressId--;
			System.out.println("There was an error when trying to insert the address into the db.");
			return 0;
		}
	}
	
	public int deleteAddress(String id) {
		try {
			if (id.length() < 1 || id.matches("[^0-9]")) {
				System.out.println("The id of the Address to delete is invalid.");
				throw new IllegalArgumentException();
			}
			int tempId = Integer.parseInt(id);
			return this.addressData.delete(tempId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was a problem trying to delete the Address.");
			return 0;
		}
	}
	
	public Map<Integer, AddressBean> retrieveAllAddresses() {
		try {
			return this.addressData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error trying to retrieve all the Addresses.");
			return null;
		}
	}
	
	public Map<Integer, AddressBean> retrieveAllAddressesByID (String id) {
		try {
			if (id.length() < 1 || id.matches("[^0-9]")) {
				System.out.println("The id of the Address to retrieve is invalid.");
				throw new IllegalArgumentException();
			}
			int tempId = Integer.parseInt(id);
			return this.addressData.retrieveById(tempId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was a problem trying to retrieve the Address by it's ID.");
			return null;
		}
	}
	
	public Map<Integer, AddressBean> retrieveAllAddressesByAllParameters(String street, String province, String country, String zip) {
		try {
			checkAddressParamters(street, province, country, zip);
			street = street.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			province = province.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			country = country.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			zip = zip.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			return this.addressData.retrieveByAll(street, province, country, zip);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error when trying to retrieve the address using all parameters.");
			return null;
		}
	}
	
	// PODAO based functions
	public void checkPOParameters(String email, String firstname, String lastname, String status) {
		if (email.length() < 1) {
			System.out.println("Purchase order email is not valid.");
			throw new IllegalArgumentException();
		}
		if (firstname.length() < 1) {
			System.out.println("Purchase order firstname is not valid.");
			throw new IllegalArgumentException();
		}
		if (lastname.length() < 1) {
			System.out.println("Purchase order lastname is not valid.");
			throw new IllegalArgumentException();
		}
		if (status.length() < 1) {
			System.out.println("Purchase order status is not valid.");
			throw new IllegalArgumentException();
		}
	}
	
	public int insertPurchaseOrder(String addressId, String email, String firstname, String lastname, String status) {
		try {
			this.pOId++;
			int addrId = Integer.parseInt(addressId);
			checkPOParameters(email, firstname, lastname, status);
			email = email.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			firstname = firstname.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			lastname = lastname.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			status = status.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			
			return this.poData.insert(pOId, addrId, email, lastname, firstname, status);
		} catch (Exception e) {
			// TODO: handle exception
			this.pOId--;
			System.out.println("There was an error trying to insert the Purchase Order.");
			return 0;
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
	
	
	// Item based functions
	public void checkItemParameters(String name, String description, String type,
			String brand) {
		if (name.length() < 1) {
			System.out.println("Item name is not valid.");
			throw new IllegalArgumentException();
		}
		if (description.length() < 1) {
			System.out.println("Item Description is not valid.");
			throw new IllegalArgumentException();
		}
		if (type.length() < 1) {
			System.out.println("Item type is not valid.");
			throw new IllegalArgumentException();
		}
		if (brand.length() < 1) {
			System.out.println("Item brand is not valid.");
			throw new IllegalArgumentException();
		}
		
		
	}
	
	public int insertItem(String name, String description, String type,
			String brand, String quantity, String price){
		try {
			this.bId++;
			checkItemParameters(name, description, type, brand);
			name = name.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			description = description.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			type = type.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			brand = brand.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			checkItemParameters(name, description, type, brand);
			
			Integer qnty = Integer.parseInt(quantity);
			Double priceItem = Double.parseDouble(price);
			
			return this.itemData.insert(bId, name, description, type, brand, qnty, priceItem);
		} catch (Exception e) {
			// TODO: handle exception
			this.bId--;
			System.out.println("There was an error when trying to insert the item.");
			return 0;
		}
		
	}
	
	public Map<Integer, ItemBean> retrieveAllItems() {
		try {
			return itemData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error with trying to retrieve all the items.");
			return null;
		}
	}
	
	public Map<Integer, ItemBean> retrieveItemByType(String type) {
		try {
			if (type.length() < 1) {
				System.out.println("Invalid type of items to be retrieved.");
				throw new IllegalArgumentException();
			}
			type = type.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			return this.itemData.retrieveByType(type);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error with trying to retrieve the items by type.");
			return null;
		}
		
	}
	
	public Map<Integer, ItemBean> retrieveItemByBrand(String brand) {
		try {
			if (brand.length() < 1) {
				System.out.println("Invalid brand of items to be retrieved.");
				throw new IllegalArgumentException();
			}
			brand = brand.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			return this.itemData.retrieveByBrand(brand);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error with trying to retrieve the items by brand.");
			return null;
		}
		
	}
	
	public int deleteItem(String bId) {
		try {
			if (bId.length() < 1) {
				System.out.println("The item Id for the deleted item is invalid");
				throw new IllegalArgumentException();
			}
			Integer tempBId = Integer.parseInt(bId);
			return this.itemData.delete(tempBId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error when trying to delete the item with id=" + bId);
			return 0;
		}
	}
	
	//Review based functions 
	
	public Map<String, ReviewBean> retrieveAllReviews() {
		try {
			return reviewData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an problem when retrieving all the reviews.");
			return null;
		}
	}
	
	public Map<String, ReviewBean> retrieveAllItemReviews(String itemId_requested) {
		try {
			if (itemId_requested.length() < 1) 
				throw new IllegalArgumentException();
			
			itemId_requested = itemId_requested.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			if (itemId_requested.length() < 1) 
				throw new IllegalArgumentException();
			
			return reviewData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("The itemId of the reviews requested was invalid.");
			return null;
		}
	}
	
	public void checkReviewParameters(String userPostId, String reviewDesc) {
		if (userPostId.length() < 1) {
			System.out.println("User Post ID is not valid.");
			throw new IllegalArgumentException();
		}
		if (reviewDesc.length() < 1) {
			System.out.println("Review Description is not valid.");
			throw new IllegalArgumentException();
		}
//		if (itemId.length() < 1) {
//			System.out.println("Item Id for review is not valid.");
//			throw new IllegalArgumentException();
//		}
		
	}
	
	public int addReview(String userPostId, String reviewDesc, String rating) {
		try {
			checkReviewParameters(userPostId, reviewDesc);
//			reviewId = reviewId.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			userPostId = userPostId.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			reviewDesc = reviewDesc.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			
			
			if (Double.parseDouble(rating) < 0 || Double.parseDouble(rating) > 5) {
				System.out.println("Review rating is invalid. Must be a number between 0 and 5.");
				throw new IllegalArgumentException();
			}
			reviewId++;
			itemId++;
			checkReviewParameters(userPostId, reviewDesc);
			return reviewData.insertReview(reviewId+"", userPostId, reviewDesc, itemId+"", Double.parseDouble(rating));
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error! Could not process the request to insert the Review!");
			reviewId--;
			itemId--;
			return 0;
		}
	}
	
	//TODO Add deleteReview
	
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
	
	public void checkUserParamters(String email, String password, String firstname, String lastname, String phonenumber) {
		if (email.length() < 1) {
			System.out.println("Email is not valid.");
			throw new IllegalArgumentException();
		}
		if (password.length() < 1) {
			System.out.println("Entered password is not valid.");
			throw new IllegalArgumentException();
		}
		if (firstname.length() < 1) {
			System.out.println("Entered first name is not valid.");
			throw new IllegalArgumentException();
		}
		if (lastname.length() < 1) {
			System.out.println("Entered last name is not valid.");
			throw new IllegalArgumentException();
		}
		if (phonenumber.length() < 1 || phonenumber.length() > 10) {
			System.out.println("Entered phonenumber is not valid.");
			throw new IllegalArgumentException();
		}
	}
	
	public int addUser(String email, String password, String firstname, String lastname, String phonenumber, String addressId) {
		try {
			checkUserParamters(email, password, firstname, lastname, phonenumber);
			email = email.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			password = password.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			firstname = firstname.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			lastname = lastname.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			phonenumber = phonenumber.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			checkUserParamters(email, password, firstname, lastname, phonenumber);
			//TODO ADD IN ADDRESS INFORMATION FOR ADDUSER
			int addId = Integer.parseInt(addressId);
			return userData.insertUser(email, password, firstname, lastname, phonenumber, addId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error when trying to create/insert the user.");
			return 0;
		}
	}
	
}
