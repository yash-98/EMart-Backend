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
import bean.UserBean;

public class ReviewDAO {
	
	private DataSource ds;
	
	public ReviewDAO() throws ClassNotFoundException{
		try {
			
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, ReviewBean> retrieveAll() throws SQLException{
		
		String query = "select * from Reviews";
		Map<String, ReviewBean> rv = new HashMap<String, ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			String reviewId = r.getString("REVIEWId");
			String userPostId = r.getString("USERPOSTId");
			String reviewDesc = r.getString("REVIEW");
			String itemId = r.getString("ITEMId");
			double rating = r.getInt("RATING");
			
			rv.put(reviewId, new ReviewBean(reviewId, userPostId, reviewDesc, itemId, rating));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<String, ReviewBean> retrieveAllByItem(String itemIdRequested) throws SQLException{
		
		String query = "select * from Reviews where itemId like '%" + itemIdRequested +"%'";
		Map<String, ReviewBean> rv = new HashMap<String, ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			String reviewId = r.getString("REVIEWId");
			String userPostId = r.getString("USERPOSTId");
			String reviewDesc = r.getString("REVIEW");
			String itemId = r.getString("ITEMId");
			double rating = r.getInt("RATING");
			
			rv.put(reviewId, new ReviewBean(reviewId, userPostId, reviewDesc, itemId, rating));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public int insertReview (String reviewId, String userPostId, String reviewDesc, String itemId, double rating) 
			throws SQLException, NamingException{
		// query parameters are set as ?
		String preparedStatement = "insert into Reviews values(?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatement to prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		// Set individual parameters through method calls
		stmt.setString(1, reviewId);
		stmt.setString(2, userPostId);
		stmt.setString(3, reviewDesc);
		stmt.setString(4, itemId);
		stmt.setDouble(5, rating);
		
		return stmt.executeUpdate();
	}

}
