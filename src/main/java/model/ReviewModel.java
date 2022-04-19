package model;

import java.util.Map;

import bean.ReviewBean;
import dao.*;

public class ReviewModel {
	private ReviewDAO reviewData;
	
	// Static ID variables
	private int reviewId;
	
	
	private static ReviewModel instance;
	private ItemModel itemModel;
	private UserModel userModel;
	
	public static ReviewModel getInstance() {
		if (instance == null) {
			instance = new ReviewModel();
			try {
				//DAO instantiation
				instance.reviewData = new ReviewDAO();
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private ReviewModel() {
		try {
			// DAO instantiation
			reviewData = new ReviewDAO();
			// Static variable ID instantiation
			// TODO instantiate IDs with the largest ID			
			this.reviewId = this.reviewData.LastID();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//Review based functions 
	public Map<Integer, ReviewBean> retrieveAllReviews() {
		try {
			return this.reviewData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an problem when retrieving all the reviews.");
			return null;
		}
	}
	
	public Map<Integer, ReviewBean> retrieveAllItemReviews(String itemId_requested) {
		try {
			if (itemId_requested.length() < 1) 
				throw new IllegalArgumentException();
			
			int tempid = Integer.parseInt(itemId_requested);
			
			return reviewData.retrieveAllByItem(tempid);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("The itemId of the reviews requested was invalid.");
			return null;
		}
	}
	
	public void checkReviewParameters(String userPostId, String reviewDesc) {
		boolean throwError = false;
		if (userPostId.length() < 1) {
			System.out.println("User Post ID is not valid.");
			throwError = true;
		}
		if (reviewDesc.length() < 1) {
			System.out.println("Review Description is not valid.");
			throwError = true;
		}
		if (throwError) {
			throw new IllegalArgumentException();
		}
//			if (itemId.length() < 1) {
//				System.out.println("Item Id for review is not valid.");
//				throw new IllegalArgumentException();
//			}
		
	}
	
	public int addReview(String userPostId, String reviewDesc, String rating, String itemId) {
		try {
			checkReviewParameters(userPostId, reviewDesc);
//				reviewId = reviewId.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			userPostId = userPostId.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			reviewDesc = reviewDesc.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			
			if (Double.parseDouble(rating) < 0 || Double.parseDouble(rating) > 5) {
				System.out.println("Review rating is invalid. Must be a number between 0 and 5.");
				throw new IllegalArgumentException();
			}
			//NEED TO CHECK IF USERID EXISTS IN USERS
			int tempid = Integer.parseInt(itemId);
			reviewId++;
			checkReviewParameters(userPostId, reviewDesc);
			return reviewData.insertReview(reviewId, userPostId, reviewDesc, tempid, Double.parseDouble(rating));
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error! Could not process the request to insert the Review!");
			reviewId--;
			return 0;
		}
	}
	
	public double retrieveItemAvgRating(String itemId) {
		try {
			int tempid = Integer.parseInt(itemId);
			return reviewData.getAvgReview(tempid);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error when trying to retrieve the average item rating.");
			e.printStackTrace();
			return -2;
		}
	}
	
	//TODO Add deleteReview
		
}
