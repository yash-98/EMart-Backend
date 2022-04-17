package bean;

public class ReviewBean {
	private String review_id, userPost_id, review, item_id;
	private double rating;
	public ReviewBean(String review_id, String userPost_id, String review, String item_id, double rating) {
		super();
		this.review_id = review_id;
		this.userPost_id = userPost_id;
		this.review = review;
		this.item_id = item_id;
		this.rating = rating;
	}
	public String getReview_id() {
		return review_id;
	}
	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}
	public String getUserPost_id() {
		return userPost_id;
	}
	public void setUserPost_id(String userPost_id) {
		this.userPost_id = userPost_id;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
}
