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
	
	public int insert(int id, int addressID, String email, String lname, String fname, String status)throws SQLException, NamingException {
			
		//note that the query parameters are set as ?
		String preparedStatement ="insert into PO values(?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatements prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		//here we set individual parameters through method calls
		//first parameter is the place holder position in the ? //pattern above
		stmt.setInt(1, id);
		stmt.setInt(2, addressID);
		stmt.setString(3, email);
		stmt.setString(4, lname);
		stmt.setString(5, fname);
		stmt.setString(6, status);

		return stmt.executeUpdate();
	 }
	
	
	public int delete(int id)throws SQLException, NamingException{

		String preparedStatement ="delete from PO where id=?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		stmt.setInt(1, id);
		
		return stmt.executeUpdate();
	}
	
	
	public Map<Integer, POBean> retrieveAll() throws SQLException{
		
		String query = "select * from PO";
		Map<Integer, POBean> rv = new HashMap<Integer, POBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int poId = r.getInt("ID");
			int poAddressId = r.getInt("ADDRESS");
			String poEmail = r.getString("EMAIL");
			String poLName = r.getString("LNAME");
			String poFName = r.getString("FNAME");
			String poStatus = r.getString("STATUS");
			
			rv.put(poId, new POBean(poId, poAddressId, poEmail, poLName, poFName, poStatus));
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
			int poAddressId = r.getInt("ADDRESS");
			String poEmail = r.getString("EMAIL");
			String poLName = r.getString("LNAME");
			String poFName = r.getString("FNAME");
			String poStatus = r.getString("STATUS");
			
			rv.put(poId, new POBean(poId, poAddressId, poEmail, poLName, poFName, poStatus));
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
			int poAddressId = r.getInt("ADDRESS");
			String poEmail = r.getString("EMAIL");
			String poLName = r.getString("LNAME");
			String poFName = r.getString("FNAME");
			String poStatus = r.getString("STATUS");
			
			rv.put(poId, new POBean(poId, poAddressId, poEmail, poLName, poFName, poStatus));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
}