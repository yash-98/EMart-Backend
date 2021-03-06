package restService;

import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Authentication.AdminFilter;
import Authentication.CORS;
import Authentication.SecurityFilter;
import bean.AuthBean;
import bean.UserBean;
import model.EMartModel;
import model.UserModel;

@Path("user") //this is the path of the resource
@CORS

public class IdentityManagementController {

//	private EMartModel emart;
	private Gson jsonConvertor;
	private UserModel userModel;
	
	public IdentityManagementController() {
		
//		emart = EMartModel.getInstance();
		userModel = UserModel.getInstance();
		
		GsonBuilder builder = new GsonBuilder();
		jsonConvertor = builder.create();
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam("email") String email, @QueryParam("password") String password) {
		
		String out = "";
		
		if(userModel.retrieveUserToAuthenticate(email, password) != null && !userModel.retrieveUserToAuthenticate(email, password).isEmpty()) {
			
			UserBean user = userModel.retrieveUserToAuthenticate(email, password).get(email);
			AuthBean auth = null;
			
			if(user.getRole().equals("CUSTOMER"))
				auth = SecurityFilter.tokenGenerator(email);
			
			if(user.getRole().equals("ADMIN"))
				auth = AdminFilter.tokenGenerator(email);
			
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
		
		String out = "{ \"result\": ";
		
		if(role == null || role == "")
			role = "CUSTOMER";
		
		if(userModel.retrieveUser(email) == null  || userModel.retrieveUser(email).isEmpty()) {
			
			int res = userModel.addUser(email, password, firstname, lastname, phone, role, streetShip, provinceShip, countryShip, zipShip, streetBill, provinceBill, countryBill, zipBill);
			if (res >=0)
				out += "\"Successful, You can now sign in.\"";
			else
				out += "\"UnSuccessful, User with same email already exists.\"";
		}
		else {
			out += "\"UnSuccessful, User with same email already exists.\"";
		}
		
		out += "}";
		return out;
	}
}