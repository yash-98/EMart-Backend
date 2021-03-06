package restService;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Authentication.CORS;
import Authentication.SecureAuth;
import model.ReviewModel;
import bean.ReviewBean;

@Path("review")
@CORS

public class ReviewController {
	
	private ReviewModel reviewModel;
	private Gson jsonConvertor;
	
	public ReviewController() {
		// TODO Auto-generated constructor stub
		reviewModel = ReviewModel.getInstance();
		
		GsonBuilder builder = new GsonBuilder();
		jsonConvertor = builder.create();
	}
	
	@POST
	@Path("/newReview")
	@Consumes(MediaType.APPLICATION_JSON)
	@SecureAuth
	public String createReview(@QueryParam("userPostId") String userPostId, @QueryParam("reviewDesc") String reviewDesc, 
			@QueryParam("rating") String rating, @QueryParam("itemId") String itemId) {
		try {
			System.out.println(reviewDesc);
			int result = reviewModel.addReview(userPostId, reviewDesc, rating, itemId);
			return "{ \"response\" : " + "\"Reviews Added:" + result + "\"" +"}";
		} catch (Exception e) {
			// TODO: handle exception
			return "{ \"response\" : " + "\"Error! Could not insert rows!\"" +"}";
		}
		
	}
	
	@GET
	@Path("/getReviewsItem")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getReviewByItem(@QueryParam("itemId") String itemId) {
		try {
			Map<Integer, ReviewBean> dbresult = reviewModel.retrieveAllItemReviews(itemId);

			Boolean firstReview = true;
			System.out.println("Have recieved an ajax call.");
			StringBuilder res = new StringBuilder();
			res.append("{ \"reviews\" :  [");
			if (dbresult != null && !dbresult.isEmpty()) {
				for (Integer s : dbresult.keySet()) {
					ReviewBean sb = dbresult.get(s);
					res.append(firstReview ? "" : ", ");
					res.append("{ \"reviewId\" : \"" + sb.getReview_id() + "\", ");
					res.append(" \"userPostId\" : " + sb.getUserPost_id() + ",");
					res.append(" \"reviewDesc\" : " + sb.getReview() + ",");
					res.append(" \"itemId\" : " + sb.getItem_id() + ",");
					res.append(" \"rating\" : " + sb.getRating() + "}");
					firstReview = false;
				}
			}
			res.append("] }");
			return res.toString();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error trace when retrieving rows.");
			e.printStackTrace();
			return "{ \"response\" : " + "\"Error! Could not insert rows!\"" +"}";
		}
		
	}
	
	@GET
	@Path("/getAverageReviewItem")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAverageReviewItem(@QueryParam("itemId") String itemId) {
		try {
			double dbresult = reviewModel.retrieveItemAvgRating(itemId);
			StringBuilder res = new StringBuilder();
			res.append("{ \"rating\" :  ");
			if (dbresult >= 0) {
				res.append(dbresult);
			} else if (dbresult == -1) {
				res.append("None");
			} else {
				res.append("\"Error! Could not retrieve review avg from DB.\"");
			}

			res.append("}");
			return res.toString();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error trace when retrieving rows.");
			e.printStackTrace();
			return "{ \"response\" : " + "\"Error! Could not retrieve review avg from DB.\"" +"}";
		}
		
	}

}
