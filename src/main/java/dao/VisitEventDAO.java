package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import bean.VisitEventBean;

public class VisitEventDAO {

	DataSource ds;
	
	public VisitEventDAO() throws ClassNotFoundException{
		
		try {
			
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(String ipAddress, String day, String bid, String eventType)throws SQLException, NamingException {
			
		//note that the query parameters are set as ?
		String preparedStatement ="insert into visitevent values(?,?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatements prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		//here we set individual parameters through method calls
		//first parameter is the place holder position in the ? //pattern above
		stmt.setString(1, ipAddress);
		stmt.setString(2, day);
		stmt.setString(3, bid);
		stmt.setString(4, eventType);
		
		return stmt.executeUpdate();
	 }
	
	public List<VisitEventBean> retrieveAll() throws SQLException{
		
		String query = "select * from visitevent";
		List<VisitEventBean> rv = new ArrayList<VisitEventBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			String veIP = r.getString("IPADDRESS");
			String veDay = r.getString("DAY");
			String veBid = r.getString("BID");
			String veEventType = r.getString("EVENTTYPE");
			
			rv.add(new VisitEventBean(veIP, veDay, veBid, veEventType));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	
}
