package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	public int insert(int bid, String name, String description, String type, String brand,
			int quantity, double price, String link)throws SQLException, NamingException {
			
		//note that the query parameters are set as ?
		String preparedStatement ="insert into Item values(?,?,?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatements prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		//here we set individual parameters through method calls
		//first parameter is the place holder position in the ? //pattern above
		stmt.setInt(1, bid);
		stmt.setString(2, name);
		stmt.setString(3, description);
		stmt.setString(4, type);
		stmt.setString(5, brand);
		stmt.setInt(6, quantity);
		stmt.setDouble(7, price);
		stmt.setString(8, link);

		return stmt.executeUpdate();
	 }
	
	
	public int delete(int bid)throws SQLException, NamingException{

		String preparedStatement ="delete from Item where bid=?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		stmt.setInt(1, bid);
		
		return stmt.executeUpdate();
	}
	
	public int LastID() throws SQLException{
		
		String query = "select max(bid) as BID from Item";
		int lastID = 0;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			
			lastID = r.getInt("BID");
		}
		
		r.close();
		p.close();
		con.close();
		
		return lastID;
	}
	
	public Map<Integer, ItemBean> retrieveAll() throws SQLException{
		
		String query = "select * from Item";
		Map<Integer, ItemBean> rv = new HashMap<Integer, ItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int iBid = r.getInt("BID");
			String iName = r.getString("NAME");
			String iDescription = r.getString("DESCRIPTION");
			String iBrand = r.getString("BRAND");
			String iType = r.getString("TYPE");
			int iQty = r.getInt("QUANTITY");
			double iPrice = r.getInt("PRICE");
			String link = r.getString("LINK");
			
			rv.put(iBid, new ItemBean(iBid, iName, iDescription, iType, iBrand, iQty, iPrice, link));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<Integer, ItemBean> retrieveByType(String type) throws SQLException{
		
		String query = "select * from Item where type like '%" + type +"%'";
		Map<Integer, ItemBean> rv = new HashMap<Integer, ItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int iBid = r.getInt("BID");
			String iName = r.getString("NAME");
			String iDescription = r.getString("DESCRIPTION");
			String iBrand = r.getString("BRAND");
			String iType = r.getString("TYPE");
			int iQty = r.getInt("QUANTITY");
			double iPrice = r.getInt("PRICE");
			String link = r.getString("LINK");
			
			rv.put(iBid, new ItemBean(iBid, iName, iDescription, iType, iBrand, iQty, iPrice, link));
			}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<Integer, ItemBean> retrieveByBrand(String brand) throws SQLException{
		
		String query = "select * from Item where brand like '%" + brand +"%'";
		Map<Integer, ItemBean> rv = new HashMap<Integer, ItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int iBid = r.getInt("BID");
			String iName = r.getString("NAME");
			String iDescription = r.getString("DESCRIPTION");
			String iBrand = r.getString("BRAND");
			String iType = r.getString("TYPE");
			int iQty = r.getInt("QUANTITY");
			double iPrice = r.getInt("PRICE");
			String link = r.getString("LINK");
			
			rv.put(iBid, new ItemBean(iBid, iName, iDescription, iType, iBrand, iQty, iPrice, link));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public List<String> retrieveAllBrands() throws SQLException{
		
		String query = "select distinct brand from Item";
		List<String> rv = new ArrayList<String>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			String bName = r.getString("BRAND");
			
			rv.add(bName);
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public List<String> retrieveAllType() throws SQLException{
		
		String query = "select distinct type from Item";
		List<String> rv = new ArrayList<String>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			String tName = r.getString("TYPE");
			
			rv.add(tName);
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
}
