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
import bean.POBean;

public class PODAO {

	DataSource ds;
	
	public PODAO() throws ClassNotFoundException{
		
		try {
			
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(int id, int shippingAddressID, int billingAddressID, String email, String lname, String fname, String status)throws SQLException, NamingException {
			
		//note that the query parameters are set as ?
		String preparedStatement ="insert into PO values(?,?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatements prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		//here we set individual parameters through method calls
		//first parameter is the place holder position in the ? //pattern above
		stmt.setInt(1, id);
		stmt.setInt(5, shippingAddressID);
		stmt.setInt(6, billingAddressID);
		stmt.setString(7, email);
		stmt.setString(2, lname);
		stmt.setString(3, fname);
		stmt.setString(4, status);

		return stmt.executeUpdate();
	 }
	
	public int changeOrderStatus(int id, String status) throws SQLException, NamingException {
		
		//note that the query parameters are set as ?
		String preparedStatement ="update PO set status = ? where id = ?";
		Connection con = this.ds.getConnection();
		
		//PreparedStatements prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		//here we set individual parameters through method calls
		//first parameter is the place holder position in the ? //pattern above
		stmt.setString(1, status);
		stmt.setInt(2, id);

		return stmt.executeUpdate();
	}
	
	
	public int delete(int id)throws SQLException, NamingException{

		String preparedStatement ="delete from PO where id=?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		stmt.setInt(1, id);
		
		return stmt.executeUpdate();
	}
	
	public int LastID() throws SQLException{
		
		String query = "select max(id) as ID from PO";
		int lastID = 0;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			
			lastID = r.getInt(1);
		}
		
		r.close();
		p.close();
		con.close();
		
		return lastID;
	}
	
	
	public Map<Integer, POBean> retrieveAll() throws SQLException{
		
		String query = "select * from PO";
		Map<Integer, POBean> rv = new HashMap<Integer, POBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int poId = r.getInt("ID");
			int poShippingAddressID = r.getInt("SHIPPINGADDRESS");
			int poBillingAddressID = r.getInt("BILLINGADDRESS");
			String poEmail = r.getString("EMAIL");
			String poLName = r.getString("LNAME");
			String poFName = r.getString("FNAME");
			String poStatus = r.getString("STATUS");
			
			rv.put(poId, new POBean(poId, poShippingAddressID, poBillingAddressID, poEmail, poLName, poFName, poStatus));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<Integer, POBean> retrieveById(int id) throws SQLException{
		
		String query = "select * from PO where id=" +id;
		Map<Integer, POBean> rv = new HashMap<Integer, POBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int poId = r.getInt("ID");
			int poShippingAddressID = r.getInt("SHIPPINGADDRESS");
			int poBillingAddressID = r.getInt("BILLINGADDRESS");
			String poEmail = r.getString("EMAIL");
			String poLName = r.getString("LNAME");
			String poFName = r.getString("FNAME");
			String poStatus = r.getString("STATUS");
			
			rv.put(poId, new POBean(poId, poShippingAddressID, poBillingAddressID, poEmail, poLName, poFName, poStatus));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<Integer, POBean> retrieveByEmail(String email) throws SQLException{
		
		String query = "select * from PO where email=" +email;
		Map<Integer, POBean> rv = new HashMap<Integer, POBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int poId = r.getInt("ID");
			int poShippingAddressID = r.getInt("SHIPPINGADDRESS");
			int poBillingAddressID = r.getInt("BILLINGADDRESS");
			String poEmail = r.getString("EMAIL");
			String poLName = r.getString("LNAME");
			String poFName = r.getString("FNAME");
			String poStatus = r.getString("STATUS");
			
			rv.put(poId, new POBean(poId, poShippingAddressID, poBillingAddressID, poEmail, poLName, poFName, poStatus));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
}
