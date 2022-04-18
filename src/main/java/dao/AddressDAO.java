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
import bean.AddressBean;

public class AddressDAO {

	DataSource ds;
	
	public AddressDAO() throws ClassNotFoundException{
		
		try {
			
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(int id, String street, String province, String country, String zip)throws SQLException, NamingException {
			
		//note that the query parameters are set as ?
		String preparedStatement ="insert into Address values(?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatements prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		//here we set individual parameters through method calls
		//first parameter is the place holder position in the ? //pattern above
		stmt.setInt(1, id);
		stmt.setString(2, street);
		stmt.setString(3, province);
		stmt.setString(4, country);
		stmt.setString(5, zip);

		return stmt.executeUpdate();
	 }
	
	
	public int delete(int id)throws SQLException, NamingException{

		String preparedStatement ="delete from Address where id=?";
		Connection con = this.ds.getConnection();
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		stmt.setInt(1, id);
		
		return stmt.executeUpdate();
	}
	
	public int LastID() throws SQLException{
		
		String query = "select max(id) as ID from Address";
		int lastID = 0;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			
			lastID = r.getInt("ID");
		}
		
		r.close();
		p.close();
		con.close();
		
		return lastID;
	}
	
	public Map<Integer, AddressBean> retrieveAll() throws SQLException{
		
		String query = "select * from Address";
		Map<Integer, AddressBean> rv = new HashMap<Integer, AddressBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int addId = r.getInt("ID");
			String addStreet = r.getString("STREET");
			String addProvince = r.getString("PROVINCE");
			String addCountry = r.getString("COUNTRY");
			String addZip = r.getString("ZIP");
			
			rv.put(addId, new AddressBean(addId, addStreet, addProvince, addCountry, addZip));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<Integer, AddressBean> retrieveById(int id) throws SQLException{
		
		String query = "select * from Address where id=" +id;
		Map<Integer, AddressBean> rv = new HashMap<Integer, AddressBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			int addId = r.getInt("ID");
			String addStreet = r.getString("STREET");
			String addProvince = r.getString("PROVINCE");
			String addCountry = r.getString("COUNTRY");
			String addZip = r.getString("ZIP");
			
			rv.put(addId, new AddressBean(addId, addStreet, addProvince, addCountry, addZip));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public int retrieveByAll(String street, String province, String country, String zip) throws SQLException{
		
		String query = "select * from Address where street = '" +street 
				+"' and province = '" +province +"'" +" and country = '" +country +"'" 
				+" and zip = '" +zip +"'";
		int addId = -1;
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			addId = r.getInt("ID");
		}
		
		r.close();
		p.close();
		con.close();
		
		return addId;
	}
}
