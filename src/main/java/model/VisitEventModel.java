package model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bean.ItemBean;
import bean.VisitEventBean;
import dao.AddressDAO;
import dao.ItemDAO;
import dao.PODAO;
import dao.POItemDAO;
import dao.ReviewDAO;
import dao.UserDAO;
import dao.VisitEventDAO;

public class VisitEventModel {
	private VisitEventDAO visitData;
	
	private static VisitEventModel instance;
	private ItemModel itemModel;
	
	public static VisitEventModel getInstance() {
		if (instance == null) {
			instance = new VisitEventModel();
			try {
				//DAO instantiation
				instance.visitData = new VisitEventDAO();
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private VisitEventModel() {
		try {
			// DAO instantiation
			visitData = new VisitEventDAO();
			this.itemModel = ItemModel.getInstance();
			// Static variable ID instantiation
			// TODO instantiate IDs with the largest ID			
			} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// VisitEvent DAO based functions
	public void checkVisitEventParameters(String ipAddress, String day, String bid, String eventType) {
		StringBuilder errstr = new StringBuilder("");
		boolean throwError = false;
		if (ipAddress.length() < 1) {
			errstr.append("The IP Address paramter is incorrect for the EventType insert.\n");
			throwError = true;
		}
		if (day.length() < 1) {
			errstr.append("The day paramter is incorrect for the EventType insert.\n");
			throwError = true;
		}
		int tempbid;
		if (bid.length() < 1) {
			errstr.append("The BID paramter is incorrect for the EventType insert.\n");
			throwError = true;
		}
		try {
			tempbid = Integer.parseInt(bid);
//				bid = bid.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			boolean exists = this.itemModel.getItemData().retrieveAll().containsKey(tempbid);
			if (!exists) {
				errstr.append("The BID parameter does not exist in the Items table.");
				throwError = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (eventType.length() < 1) {
			errstr.append("The eventType paramter is incorrect for the EventType insert.\n");
			throwError = true;
		}
		if (throwError) {
			System.out.println(errstr.toString());
			throw new IllegalArgumentException();
		}
	}
	
	public int insertVisitEvent(String ipAddress, String day, String bid, String eventType) {
		try {
			checkVisitEventParameters(ipAddress, day, bid, eventType);
			ipAddress = ipAddress.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			day = day.replaceAll(" ", "").replaceAll("[\"\"'']", "");
//				bid = bid.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			eventType = eventType.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			
			int tempbid = Integer.parseInt(bid);
			
			return this.visitData.insert(ipAddress, day, tempbid, eventType);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There wan an error when trying to insert the visit Event Data.");
			return 0;
		}
	}
	
	public Map<String, Set<ItemBean>> monthlyReport() throws SQLException{
		
		Map<String, Set<ItemBean>> rv = new HashMap<String, Set<ItemBean>>();
		Map<Integer, ItemBean> items = this.itemModel.getItemData().retrieveAll();
//		System.out.println("here " + items.size());
		for(Map.Entry<String, Set<Integer>> e : visitData.retreiveItemsSold().entrySet()) {
			
			Set<ItemBean> itemNames = new HashSet<ItemBean>();
			
			for(Integer i : e.getValue()) {
				
				ItemBean it = items.get(i);
				itemNames.add(it);
			}
			
			rv.put(e.getKey(), itemNames);
		}
		
		
		return rv;
	}
	
	public List<VisitEventBean> retrieveAllVisitEvents() {
		try {
			return this.visitData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an problem when trying to retrieve all the Visit Events.");
			return null;
		}
	}
}
