package restService;

import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Authentication.CORS;
import Authentication.SecureAuth;
import model.AddressModel;
import model.EMartModel;
import model.POItemModel;
import model.POModel;
import model.UserModel;
import bean.ItemBean;
import bean.UserBean;

@Path("order") //this is the path of the resource
@SecureAuth
@CORS

public class OrderController {

//	private EMartModel emart;
	private AddressModel addressModel;
	private POModel poModel;
	private POItemModel poItemModel;
	private UserModel userModel;
	private Gson jsonConvertor;
	
	public OrderController() {
		
//		emart = EMartModel.getInstance();
		addressModel = AddressModel.getInstance();
		poModel = POModel.getInstance();
		poItemModel = POItemModel.getInstance();
		userModel = UserModel.getInstance();
		GsonBuilder builder = new GsonBuilder();
		jsonConvertor = builder.create();
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public String createOrder(String body) {
		
		Map json = jsonConvertor.fromJson(body, Map.class);
		String email = (String) json.get("email");
		UserBean user = userModel.retrieveUser(email).get(email);
		Map shipping = (Map<String, String>)json.get("shipping");
		Map billing = (Map<String, String>)json.get("billing");

		
		int sAddId = addressModel.retrieveAllAddressesByAllParameters((String)shipping.get("street"), (String)shipping.get("province")
				, (String)shipping.get("country"), (String)shipping.get("zip"));
		int bAddId = addressModel.retrieveAllAddressesByAllParameters((String)billing.get("street"), (String)billing.get("province")
				, (String)billing.get("country"), (String)billing.get("zip"));
		
		sAddId = sAddId!=-1?sAddId:addressModel.insertAddress((String)shipping.get("street"), (String)shipping.get("province")
				, (String)shipping.get("country"), (String)shipping.get("zip"));
		bAddId = bAddId!=-1?bAddId:addressModel.insertAddress((String)billing.get("street"), (String)billing.get("province")
				, (String)billing.get("country"), (String)billing.get("zip"));
		
		poModel.insertPurchaseOrder(Integer.toString(sAddId), Integer.toString(bAddId), user.getUserId(), user.getFirstname(), user.getLastname(), "ORDERING");
		
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
			poItemModel.insertPOItem(Integer.parseInt(poID), price, bid);
		}
		
		return "{ \"Result\" : \"Added all\" }";
	}
	
	@POST
	@Path("/process")
	@Produces(MediaType.APPLICATION_JSON)
	public String processOrder(@QueryParam("PurchaseOrderID") String poID) {
		
		String status = poModel.processOrder(Integer.parseInt(poID));
		
		String out = "{ \"Order Status\": \"" +status +"\" }";
		return out;
	}
	
}