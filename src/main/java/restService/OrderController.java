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
import bean.UserBean;

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
	public String createOrder(String body) {
		
		Map json = jsonConvertor.fromJson(body, Map.class);
		String email = (String) json.get("email");
		UserBean user = emart.retrieveUser(email).get(email);
		Map shipping = (Map<String, String>)json.get("shipping");
		Map billing = (Map<String, String>)json.get("billing");

		
		int sAddId = emart.retrieveAllAddressesByAllParameters((String)shipping.get("street"), (String)shipping.get("province")
				, (String)shipping.get("country"), (String)shipping.get("zip"));
		int bAddId = emart.retrieveAllAddressesByAllParameters((String)billing.get("street"), (String)billing.get("province")
				, (String)billing.get("country"), (String)billing.get("zip"));
		
		sAddId = sAddId!=-1?sAddId:emart.insertAddress((String)shipping.get("street"), (String)shipping.get("province")
				, (String)shipping.get("country"), (String)shipping.get("zip"));
		bAddId = bAddId!=-1?bAddId:emart.insertAddress((String)billing.get("street"), (String)billing.get("province")
				, (String)billing.get("country"), (String)billing.get("zip"));
		
		emart.insertPurchaseOrder(Integer.toString(sAddId), Integer.toString(bAddId), user.getUserId(), user.getFirstname(), user.getLastname(), "ORDERING");
		
		String out = "";
		return out;
	}
	
	@POST
	@Path("/addItems")
	@Produces(MediaType.APPLICATION_JSON)
	public String addItemsToPO(@QueryParam("PurchaseOrderID") String poID, @QueryParam("items") String items) {
		
		String[] itemPair = items.split(",");
		
		for (String i : itemPair) {
			
			String bid = i.split("-")[0];
			String price = i.split("-")[1];
			emart.insertPOItem(Integer.parseInt(poID), price, bid);
		}
		
		return "{ \"Result\" : \"Added all\" }";
	}
	
	@POST
	@Path("/process")
	@Produces(MediaType.APPLICATION_JSON)
	public String processOrder(@QueryParam("PurchaseOrderID") String poID) {
		
		String status = emart.processOrder(Integer.parseInt(poID));
		
		String out = "{ \"Order Status\": \"" +status +"\" }";
		return out;
	}
	
}