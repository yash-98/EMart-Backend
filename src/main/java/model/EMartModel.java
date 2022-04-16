package model;

import dao.*;

import java.util.*;

import bean.*;

public class EMartModel {

	private ItemDAO itemData;
	private ReviewDAO reviewData;
	private UserDAO userData;
	
	private static EMartModel instance;
	
	public static EMartModel getInstance() {
		if (instance == null) {
			instance = new EMartModel();
			try {
				instance.itemData = new ItemDAO();
				instance.reviewData = new ReviewDAO();
				instance.userData = new UserDAO();
				
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private EMartModel() {
		try {
			this.instance.itemData = new ItemDAO();
			this.instance.reviewData = new ReviewDAO();
			this.instance.userData = new UserDAO();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
	
	public Map<String, ReviewBean> retrieveAllItemReviews(String item_id_requested) {
		try {
			if (item_id_requested.length() < 1) 
				throw new IllegalArgumentException();
			
			item_id_requested = item_id_requested.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			if (item_id_requested.length() < 1) 
				throw new IllegalArgumentException();
			
			return reviewData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("The item_id of the reviews requested was invalid.");
			return null;
		}
	}
	
	public void checkReviewParameters(String review_id, String userPost_id, String reviewDesc, String item_id) {
		if (review_id.length() < 1) {
			System.out.println("Review ID is not valid.");
			throw new IllegalArgumentException();
		}
		if (userPost_id.length() < 1) {
			System.out.println("User Post ID is not valid.");
			throw new IllegalArgumentException();
		}
		if (reviewDesc.length() < 1) {
			System.out.println("Review Description is not valid.");
			throw new IllegalArgumentException();
		}
		if (item_id.length() < 1) {
			System.out.println("Item Id for review is not valid.");
			throw new IllegalArgumentException();
		}
		
	}
	
	public int addReview(String review_id, String userPost_id, String reviewDesc, String item_id, String rating) {
		try {
			checkReviewParameters(review_id, userPost_id, reviewDesc, item_id);
			review_id = review_id.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			userPost_id = userPost_id.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			reviewDesc = reviewDesc.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			review_id = review_id.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			
			if (Double.parseDouble(rating) < 0 || Double.parseDouble(rating) > 5) {
				System.out.println("Review rating is invalid. Must be a number between 0 and 5.");
			}
			checkReviewParameters(review_id, userPost_id, reviewDesc, item_id);
			return reviewData.insertReview(review_id, userPost_id, reviewDesc, item_id, Double.parseDouble(rating));
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error! Could not process the request to insert the Review!");
			return 0;
		}
	}
	
	//TODO Add deleteReview
	
	//User based functions
	public Map<String, UserBean> retrieveUserToAuthenticate(String user_id, String password) {
		try {
			if (user_id.length() < 1) {
				System.out.println("Invalid User id.");
			}
			if (password.length() < 1) {
				System.out.println("Invalid password.");
			}
			user_id = user_id.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			password = password.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			return userData.retrieveUserAuth(user_id, password);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Incorrect or Invalid user id/password.");
			return null;
		}
	}
	
	public Map<String, UserBean> retrieveUser(String user_id) {
		try {
			if (user_id.length() < 1) {
				System.out.println("Invalid User id.");
			}
			user_id = user_id.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			return userData.retrieveUser(user_id);
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
	
	public int addUser(String email, String password, String firstname, String lastname, String phonenumber, String address_id) {
		try {
			checkUserParamters(email, password, firstname, lastname, phonenumber);
			email = email.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			password = password.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			firstname = firstname.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			lastname = lastname.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			phonenumber = phonenumber.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			checkUserParamters(email, password, firstname, lastname, phonenumber);
			//TODO ADD IN ADDRESS INFORMATION FOR ADDUSER
			int add_id = Integer.parseInt(address_id);
			return userData.insertUser(email, password, firstname, lastname, phonenumber, add_id);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error when trying to create/insert the user.");
			return 0;
		}
	}
	
}
