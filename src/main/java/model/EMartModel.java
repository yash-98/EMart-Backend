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
	
	public void checkReviewParameters(String reviewId, String userPostId, String reviewDesc, String itemId) {
		if (reviewId.length() < 1) {
			System.out.println("Review ID is not valid.");
			throw new IllegalArgumentException();
		}
		if (userPostId.length() < 1) {
			System.out.println("User Post ID is not valid.");
			throw new IllegalArgumentException();
		}
		if (reviewDesc.length() < 1) {
			System.out.println("Review Description is not valid.");
			throw new IllegalArgumentException();
		}
		if (itemId.length() < 1) {
			System.out.println("Item Id for review is not valid.");
			throw new IllegalArgumentException();
		}
		
	}
	
	public int addReview(String reviewId, String userPostId, String reviewDesc, String itemId, String rating) {
		try {
			checkReviewParameters(reviewId, userPostId, reviewDesc, itemId);
			reviewId = reviewId.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			userPostId = userPostId.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			reviewDesc = reviewDesc.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			reviewId = reviewId.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			
			if (Double.parseDouble(rating) < 0 || Double.parseDouble(rating) > 5) {
				System.out.println("Review rating is invalid. Must be a number between 0 and 5.");
			}
			checkReviewParameters(reviewId, userPostId, reviewDesc, itemId);
			return reviewData.insertReview(reviewId, userPostId, reviewDesc, itemId, Double.parseDouble(rating));
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error! Could not process the request to insert the Review!");
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
