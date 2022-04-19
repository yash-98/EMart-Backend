package restService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.ItemBean;
import bean.VisitEventBean;
import model.EMartModel;
import model.VisitEventModel;

@Path("analytics") //this is the path of the resource


public class AnalyticsController {

//	private EMartModel emart;
	private VisitEventModel visitEventModel;
	private Gson jsonConvertor;
	
	public AnalyticsController() {
		
//		emart = EMartModel.getInstance();
		visitEventModel = VisitEventModel.getInstance();
		GsonBuilder builder = new GsonBuilder();
		jsonConvertor = builder.create();
	}
	
	@GET
	@Path("/appusage")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAppUsage() {
		
		List<VisitEventBean> events = visitEventModel.retrieveAllVisitEvents();
		
		String out = "";
		
		if(events != null && !events.isEmpty()) {
			out = "{ \"events\" : " +jsonConvertor.toJson(events) +"}";
		}

		return out;
	}
	
	@GET
	@Path("/salesreport")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSalesReport() {
		
		Map<String, Set<ItemBean>> events;
		String out = "";
		
		try {
			
			events = visitEventModel.monthlyReport();
			
			if(events != null && !events.isEmpty()) {
				out = "{ \"events\" : " +jsonConvertor.toJson(events) +"}";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
			out = "{ \"Error\" : \"No Event Data\" }";
		}
		
		return out;
	}
	
	@POST
	@Path("/addappevent")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addVisitEvent(@QueryParam("ip")String ip,
			@QueryParam("day")String day, @QueryParam("bid")String
			bid, @QueryParam("event")String event) {
		
		return "{ \"insertedRows\":" +visitEventModel.insertVisitEvent(ip, day, bid, event) + "}";
	}
	
}
