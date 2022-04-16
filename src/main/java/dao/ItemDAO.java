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
	
	public Map<String, ItemBean> retrieve(String namePrefix, String brand) throws SQLException{
		
		String query = "select * from Item where name like '%" + namePrefix +"%'and brand like '%" + brand +"%'";
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
