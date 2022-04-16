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
		
		String query = "select * from Review";
		Map<String, ReviewBean> rv = new HashMap<String, ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			String review_id = r.getString("REVIEW_ID");
			String userPost_id = r.getString("USERPOST_ID");
			String reviewDesc = r.getString("REVIEW");
			String item_id = r.getString("ITEM_ID");
			double rating = r.getInt("RATING");
			
			rv.put(review_id, new ReviewBean(review_id, userPost_id, reviewDesc, item_id, rating));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<String, ReviewBean> retrieveAllByItem(String item_id_requested) throws SQLException{
		
		String query = "select * from Review where item_id like '%" + item_id_requested +"%'";
		Map<String, ReviewBean> rv = new HashMap<String, ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			String review_id = r.getString("REVIEW_ID");
			String userPost_id = r.getString("USERPOST_ID");
			String reviewDesc = r.getString("REVIEW");
			String item_id = r.getString("ITEM_ID");
			double rating = r.getInt("RATING");
			
			rv.put(review_id, new ReviewBean(review_id, userPost_id, reviewDesc, item_id, rating));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}

}
