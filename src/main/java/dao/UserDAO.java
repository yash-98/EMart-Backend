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

import org.apache.catalina.User;

import bean.ItemBean;
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
	
	public Map<String, UserBean> retrieveUser(String user_id, String password) throws SQLException{
		
		String query = "select * from User where user_id like '%" + user_id +"%'and password like '%" + password +"%'";
		Map<String, UserBean> rv = new HashMap<String, UserBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			String email = r.getString("USER_ID");
			String pass = r.getString("PASSWORD");
			String firstname = r.getString("FIRSTNAME");
			String lastname = r.getString("LASTNAME");
			int address_id = r.getInt("ADDRESS_ID");
			
			rv.put(email, new UserBean(email, pass, firstname, lastname, address_id));
		}
		
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public int insertUser(String email, String password, String firstname, String lastname, int address_id) 
			throws SQLException, NamingException{
		// query parameters are set as ?
		String preparedStatement = "insert into User values(?,?,?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatement to prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		// Set individual parameters through method calls
		stmt.setString(1, email);
		stmt.setString(2, password);
		stmt.setString(3, firstname);
		stmt.setString(4, lastname);
		stmt.setInt(5, address_id);
		
		return stmt.executeUpdate();
	}
}
