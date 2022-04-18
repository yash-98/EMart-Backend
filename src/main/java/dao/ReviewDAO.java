package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import bean.ReviewBean;

public class ReviewDAO {
	
	private DataSource ds;
	
	public ReviewDAO() throws ClassNotFoundException{
		try {
			
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public int LastID() throws SQLException{
		
		String query = "select max(reviewid) as reviewid from Reviews";
		int lastID = 0;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			
			lastID = r.getInt("reviewid");
		}
		
		r.close();
		p.close();
		con.close();
		
		return lastID;
	}
	
	public Map<Integer, ReviewBean> retrieveAll() throws SQLException{
		
		String query = "select * from Reviews";
		Map<Integer, ReviewBean> rv = new HashMap<Integer, ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			int reviewId = r.getInt("REVIEWId");
			String userPostId = r.getString("USERPOSTId");
			String reviewDesc = r.getString("REVIEW");
			int itemId = r.getInt("ITEMId");
			double rating = r.getInt("RATING");
			
			rv.put(reviewId, new ReviewBean(reviewId, userPostId, reviewDesc, itemId, rating));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<Integer, ReviewBean> retrieveAllByItem(int itemIdRequested) throws SQLException{
		
		String query = "select * from Reviews where itemId = " + itemIdRequested +"";
		System.out.println("Query: " + query);
		Map<Integer, ReviewBean> rv = new HashMap<Integer, ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			int reviewId = r.getInt("REVIEWId");
			String userPostId = r.getString("USERPOSTId");
			String reviewDesc = r.getString("REVIEW");
			int itemId = r.getInt("ITEMId");
			double rating = r.getInt("RATING");
			
			rv.put(reviewId, new ReviewBean(reviewId, userPostId, reviewDesc, itemId, rating));
		}
		
		r.close();
		p.close();
		con.close();
		
		
		return rv;
	}
	
	public int insertReview (int reviewId, String userPostId, String reviewDesc, int itemId, double rating) 
			throws SQLException, NamingException{
		// query parameters are set as ?
		String preparedStatement = "insert into Reviews values(?,?,?,?,?)";
		System.out.println("Inserting review");
		System.out.println("Query: " + preparedStatement + " " + reviewId + " " + userPostId + " " + reviewDesc + " " + itemId + " "+rating);

		Connection con = this.ds.getConnection();
		//PreparedStatement to prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);

		// Set individual parameters through method calls
		stmt.setInt(1, reviewId);
		stmt.setString(2, userPostId);
		stmt.setString(3, reviewDesc);
		stmt.setInt(4, itemId);
		stmt.setDouble(5, rating);

		return stmt.executeUpdate();
	}

}
