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
import bean.POItemBean;

public class POItemDAO {

	DataSource ds;
	
	public POItemDAO() throws ClassNotFoundException{
		
		try {
			
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(int id, double price, int bid)throws SQLException, NamingException {
			
		//note that the query parameters are set as ?
		String preparedStatement ="insert into POItem values(?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatements prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		//here we set individual parameters through method calls
		//first parameter is the place holder position in the ? //pattern above
		stmt.setInt(1, id);
		stmt.setDouble(2, price);
		stmt.setInt(3, bid);

		return stmt.executeUpdate();
	 }
	
	
	public int delete(int id)throws SQLException, NamingException{

		String preparedStatement ="delete from POItem where id=?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		stmt.setInt(1, id);
		
		return stmt.executeUpdate();
	}
	
	
	public Map<Integer, POItemBean> retrieveAll() throws SQLException{
		
		String query = "select * from POItem";
		Map<Integer, POItemBean> rv = new HashMap<Integer, POItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int poiID = r.getInt("ID");
			double poiPrice = r.getDouble("PRICE");
			int poiBid = r.getInt("BID");
			
			rv.put(poiID, new POItemBean(poiID, poiPrice, poiBid));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<Integer, POItemBean> retrieveById(int id) throws SQLException{
		
		String query = "select * from POItem where id=" +id;
		Map<Integer, POItemBean> rv = new HashMap<Integer, POItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int poiID = r.getInt("ID");
			double poiPrice = r.getDouble("PRICE");
			int poiBid = r.getInt("BID");
			
			rv.put(poiID, new POItemBean(poiID, poiPrice, poiBid));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<Integer, POItemBean> retrieveByBid(int bid) throws SQLException{
		
		String query = "select * from POItem where bid=" +bid;
		Map<Integer, POItemBean> rv = new HashMap<Integer, POItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int poiID = r.getInt("ID");
			double poiPrice = r.getDouble("PRICE");
			int poiBid = r.getInt("BID");
			
			rv.put(poiID, new POItemBean(poiID, poiPrice, poiBid));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
}
