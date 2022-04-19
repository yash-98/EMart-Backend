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
import Authentication.SecurityFilter;
import bean.AuthBean;
import bean.UserBean;
import model.EMartModel;

@Path("user") //this is the path of the resource
@CORS

public class IdentityManagementController {

	private EMartModel emart;
	private Gson jsonConvertor;
	
	public IdentityManagementController() {
		
		emart = EMartModel.getInstance();
		
		GsonBuilder builder = new GsonBuilder();
		jsonConvertor = builder.create();
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam("email") String email, @QueryParam("password") String password) {
		
		String out = "";
		
		if(emart.retrieveUserToAuthenticate(email, password) != null && !emart.retrieveUserToAuthenticate(email, password).isEmpty()) {
			
			UserBean user = emart.retrieveUserToAuthenticate(email, password).get(email);
			AuthBean auth = SecurityFilter.tokenGenerator(email);
			
			auth.setEmail(email);
			auth.setRole(user.getRole());
			
			out = jsonConvertor.toJson(auth);
		}else {
			out = "{ \"error\": \"UnSuccessful attempt, Wrong email/password.\"}";
		}
		return out;
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public String register(@QueryParam("email") String email, @QueryParam("password") String password,
			@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname, @QueryParam("phone") String phone,
			@QueryParam("streetShip") String streetShip, @QueryParam("provinceShip") String provinceShip, @QueryParam("countryShip") String countryShip, 
			@QueryParam("zipShip") String zipShip, @QueryParam("streetBill") String streetBill, @QueryParam("provinceBill") String provinceBill, @QueryParam("countryBill") String countryBill, 
			@QueryParam("zipBill") String zipBill, @QueryParam("role") String role) {
		
		String out = "";
		
		if(emart.retrieveUser(email) == null  || emart.retrieveUser(email).isEmpty()) {
			
			int res = emart.addUser(email, password, firstname, lastname, phone, role, streetShip, provinceShip, countryShip, zipShip, streetBill, provinceBill, countryBill, zipBill);
			if (res >=0) {
				out = "{ \"result\": ";
				out += "\"Successful, You can now sign in.\"";
			} else {
				out = "{ \"error\": ";
				out += "\"UnSuccessful, could not register user.\"";
			}
		}
		else {
			out = "{ \"error\": ";
			out += "\"UnSuccessful, could not register user.\"";
		}
		
		out += "}";
		return out;
	}
}