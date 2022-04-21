package restService;

import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Authentication.AdminAuth;
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
	@SecureAuth
	@Produces(MediaType.APPLICATION_JSON)
	public String createOrder(@QueryParam("email") String email) {
		
		String out = "";
		int result = poModel.insertPurchaseOrder(email, "ORDERING");
		
		if(result >= 1) {
			out = "{ \"PurchaseOrderId\" : \"" +result +"\" }";
		}
		else {
			out = "{ \"Error\" : \"The Purchase Order Could not be created\" }";
		}
		
		return out;
	}
	
	@POST
	@Path("/addItems")
	@SecureAuth
	@Produces(MediaType.APPLICATION_JSON)
	public String addItemsToPO(@QueryParam("PurchaseOrderId") String poID, @QueryParam("items") String items) {
		
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
	@SecureAuth
	@Produces(MediaType.APPLICATION_JSON)
	public String processOrder(@QueryParam("PurchaseOrderId") String poID) {
		
		String status = poModel.processOrder(Integer.parseInt(poID));
		
		String out = "{ \"Order Status\": \"" +status +"\" }";
		return out;
	}
	
	@POST
	@Path("/changeOrderStatus")
	@AdminAuth
	@Produces(MediaType.APPLICATION_JSON)
	public String changeOrderStatus(@QueryParam("PurchaseOrderID") String poID, @QueryParam("Status") String status) {
		
		String out = "{ \"Orders Changed\": \"" +poModel +"\" }";
		return out;
	}
	
}