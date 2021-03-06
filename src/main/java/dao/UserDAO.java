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
import bean.UserBean;

public class UserDAO {
	private DataSource ds;
	
	public UserDAO() throws ClassNotFoundException{
		
		try {
			
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, UserBean> retrieveUserAuth(String user_id, String password) throws SQLException{
		
		String query = "select * from Users where user_id = '" + user_id +"' and password = '" + password +"'";
		System.out.println("Retrieve userAuth");
		System.out.println(query);


		Map<String, UserBean> rv = new HashMap<String, UserBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		System.out.println("Query ran");
		while (r.next()) {
			
			String email = r.getString("USER_ID");
			String pass = r.getString("PASSWORD");
			String firstname = r.getString("FIRSTNAME");
			String lastname = r.getString("LASTNAME");
			String phonenumber = r.getString("PHONENUMBER");
			int address_id_ship = r.getInt("ADDRESSIDSHIP");
			int address_id_bill = r.getInt("ADDRESSIDBILL");
			String role = r.getString("ROLE");

			rv.put(email, new UserBean(email, pass, firstname, lastname, phonenumber, role, address_id_ship, address_id_bill));

		}
		
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
public Map<String, UserBean> retrieveUser(String user_id) throws SQLException{
		
		String query = "select * from Users where user_id = '" + user_id +"'";
		System.out.println("Retrieve User");
		System.out.println(query);


		Map<String, UserBean> rv = new HashMap<String, UserBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			String email = r.getString("USER_ID");
			String pass = r.getString("PASSWORD");
			String firstname = r.getString("FIRSTNAME");
			String lastname = r.getString("LASTNAME");
			String phonenumber = r.getString("PHONENUMBER");
			int address_id_ship = r.getInt("ADDRESSIDSHIP");
			int address_id_bill = r.getInt("ADDRESSIDBILL");
			String role = r.getString("ROLE");

			rv.put(email, new UserBean(email, pass, firstname, lastname, phonenumber, role, address_id_ship, address_id_bill));

		}
		
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public int insertUser(String email, String password, String firstname, String lastname, String phonenumber, String role, int address_id_ship, int address_id_bill) 
			throws SQLException, NamingException{
		// query parameters are set as ?
		String preparedStatement = "insert into Users values(?,?,?,?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		System.out.println("Inserting User info:\n Query: " + preparedStatement + " -" + email + " -" + firstname +  " -" + lastname + " -" + phonenumber + " -" + role + " -" + address_id_ship + " -" + address_id_bill);
		//PreparedStatement to prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		// Set individual parameters through method calls
		stmt.setString(1, email);
		stmt.setString(2, password);
		stmt.setString(3, firstname);
		stmt.setString(4, lastname);
		stmt.setString(5, phonenumber);
		stmt.setString(8, role);
		stmt.setInt(6, address_id_ship);
		stmt.setInt(7, address_id_bill);
		
		return stmt.executeUpdate();
	}
}
