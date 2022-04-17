package restService;

import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Authentication.SecureAuth;
import model.EMartModel;
import bean.ItemBean;

@Path("order") //this is the path of the resource
@SecureAuth

public class OrderController {

	private EMartModel emart;
	private Gson jsonConvertor;
	
	public OrderController() {
		
		emart = EMartModel.getInstance();
		
		GsonBuilder builder = new GsonBuilder();
		jsonConvertor = builder.create();
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public String createOrder(@QueryParam("email") String email, @HeaderParam("street") String street, 
			@HeaderParam("province") String province,  @HeaderParam("country") String country, 
			@HeaderParam("zip") String zip) {
		
		String out = "";

		return out;
	}
	
	@GET
	@Path("/itemsByBrand")
	@Produces(MediaType.APPLICATION_JSON)
	public String getItemsByBrand(@QueryParam("brand") String brand) {
		
		Map<Integer, ItemBean> items = emart.retrieveItemByBrand(brand);
		
		String out = "";
		
		if(items != null && !items.isEmpty()) {
			out = "{ \"items\" : " +jsonConvertor.toJson(items) +"}";
		}

		return out;
	}
	
	@GET
	@Path("/itemsByType")
	@Produces(MediaType.APPLICATION_JSON)
	public String getItemsByType(@QueryParam("type") String type) {
		
		Map<Integer, ItemBean> items = emart.retrieveItemByType(type);
		
		String out = "";
		
		if(items != null && !items.isEmpty()) {
			out = "{ \"items\" : " +jsonConvertor.toJson(items) +"}";
		}

		return out;
	}
	
}