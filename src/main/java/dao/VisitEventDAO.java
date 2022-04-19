package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

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
	
	public int insert(String ipAddress, String day, int bid, String eventType)throws SQLException, NamingException {
			
		//note that the query parameters are set as ?
		String preparedStatement ="insert into VisitEvent values(?,?,?,?)";
		Connection con = this.ds.getConnection();
		
		//PreparedStatements prevent SQL injection
		PreparedStatement stmt = con.prepareStatement(preparedStatement);
		
		//here we set individual parameters through method calls
		//first parameter is the place holder position in the ? //pattern above
		stmt.setString(1, ipAddress);
		stmt.setString(2, day);
		stmt.setInt(3, bid);
		stmt.setString(4, eventType);
		
		return stmt.executeUpdate();
	 }
	
	public List<VisitEventBean> retrieveAll() throws SQLException{
		
		String query = "select * from VisitEvent";
		List<VisitEventBean> rv = new ArrayList<VisitEventBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()) {
			
			String veIP = r.getString("IPADDRESS");
			String veDay = r.getString("DAY");
			int veBid = r.getInt("BID");
			String veEventType = r.getString("EVENTTYPE");
			
			rv.add(new VisitEventBean(veIP, veDay, veBid, veEventType));
		}
		
		r.close();
		p.close();
		con.close();
		
		return rv;
	}
	
	public Map<String, Set<Integer>> retreiveItemsSold() throws SQLException{
		
		int start = 202204;
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		
		int today = cal.get(Calendar.YEAR)*100 + cal.get(Calendar.MONTH);
		Map<String, Set<Integer>> rv = new HashMap<String, Set<Integer>>();
		
		for(; start < today; start++) {
			
			String query = "select * from VisitEvent where day like '%" +start +"%' and eventtype = 'PURCHASE'";
			Set<Integer> ids = new HashSet<Integer>();
			Connection con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			ResultSet r = p.executeQuery();
			
			while (r.next()) {
				
				int veBid = r.getInt("BID");
				ids.add(veBid);
			}
			
			r.close();
			p.close();
			con.close();
			
			rv.put(Integer.toString(start), ids);
		}

		
		return rv;
		
	}
	
	
}
