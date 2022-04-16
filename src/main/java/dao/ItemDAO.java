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
import bean.ItemBean;

public class ItemDAO {

	DataSource ds;
	
	public ItemDAO() throws ClassNotFoundException{
		
		try {
			
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(String bid, String name, String description, String type, String brand,
			int quantity, int price)throws SQLException, NamingException {
			
		//note that the query parameters are set as ?
		String preparedStatement ="insert into item values(?,?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatements prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		//here we set individual parameters through method calls
		//first parameter is the place holder position in the ? //pattern above
		stmt.setString(1, bid);
		stmt.setString(2, name);
		stmt.setString(3, description);
		stmt.setString(4, type);
		stmt.setString(5, brand);
		stmt.setInt(6, quantity);
		stmt.setInt(7, price);

		return stmt.executeUpdate();
	 }
	
	
	public int delete(String bid)throws SQLException, NamingException{

		String preparedStatement ="delete from item where bid=?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		stmt.setString(1, bid);
		
		return stmt.executeUpdate();
	}
	
	public Map<String, ItemBean> retrieveAll() throws SQLException{
		
		String query = "select * from item";
		Map<String, ItemBean> rv = new HashMap<String, ItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			String iBid = r.getString("BID");
			String iName = r.getString("NAME");
			String iDescription = r.getString("DESCRIPTION");
			String iBrand = r.getString("BRAND");
			String iType = r.getString("TYPE");
			int iQty = r.getInt("QUANTITY");
			int iPrice = r.getInt("PRICE");
			
			rv.put(iBid, new ItemBean(iBid, iName, iDescription, iType, iBrand, iQty, iPrice));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<String, ItemBean> retrieveByType(String type) throws SQLException{
		
		String query = "select * from item where type like '%" + type +"%'";
		Map<String, ItemBean> rv = new HashMap<String, ItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			String iBid = r.getString("BID");
			String iName = r.getString("NAME");
			String iDescription = r.getString("DESCRIPTION");
			String iBrand = r.getString("BRAND");
			String iType = r.getString("TYPE");
			int iQty = r.getInt("QUANTITY");
			int iPrice = r.getInt("PRICE");
			
			rv.put(iBid, new ItemBean(iBid, iName, iDescription, iType, iBrand, iQty, iPrice));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<String, ItemBean> retrieveByBrand(String brand) throws SQLException{
		
		String query = "select * from item where brand like '%" + brand +"%'";
		Map<String, ItemBean> rv = new HashMap<String, ItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			String iBid = r.getString("BID");
			String iName = r.getString("NAME");
			String iDescription = r.getString("DESCRIPTION");
			String iBrand = r.getString("BRAND");
			String iType = r.getString("TYPE");
			int iQty = r.getInt("QUANTITY");
			int iPrice = r.getInt("PRICE");
			
			rv.put(iBid, new ItemBean(iBid, iName, iDescription, iType, iBrand, iQty, iPrice));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
}
