package restService;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import bean.VisitEventBean;
import model.SisModel;

@Path("analytics") //this is the path of the resource


public class AnalyticsController {

	private SisModel sis;
	private Gson jsonConvertor;
	
	public AnalyticsController() {
		
		sis = SisModel.getInstance();
		
		GsonBuilder builder = new GsonBuilder();
		jsonConvertor = builder.create();
	}
	
	@GET
	@Path("/appusage")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAppUsage() {
		
		List<VisitEventBean> events = sis.retrieveEvents();
		
		String out = "";
		
		if(events != null && !events.isEmpty()) {
			out = "{ \"events\" : " +jsonConvertor.toJson(events) +"}";
		}

		return out;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String addVisitEvent(@QueryParam("ip")String ip,
			@QueryParam("day")String day, @QueryParam("bid")String
			bid, @QueryParam("event")String event) {
		
		return "insertedRows:" +sis.addVisitEvent(ip, day, bid, event);
	}
	
}
