package restService;

import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Authentication.SecurityFilter;
import bean.AuthBean;
import bean.UserBean;
import model.EMartModel;

@Path("user") //this is the path of the resource


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
	public String login(@HeaderParam("email") String email, @HeaderParam("password") String password) {
		
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
	public String register(@HeaderParam("email") String email, @HeaderParam("password") String password,
			@HeaderParam("firstname") String firstname, @HeaderParam("lastname") String lastname, @HeaderParam("phone") String phone,
			@HeaderParam("street") String street, @HeaderParam("province") String province, @HeaderParam("country") String country, 
			@HeaderParam("zip") String zip, @HeaderParam("role") String role) {
		
		String out = "{ \"result\": ";
		
		if(emart.retrieveUser(email) == null  || emart.retrieveUser(email).isEmpty()) {
			emart.addUser(email, password, firstname, lastname, phone, role, street, province, country, zip);
			
			out += "\"Successful, You can now sign in.\"";
		}
		else {
			out += "\"UnSuccessful, User with same email already exists.\"";
		}
		
		out += "}";
		return out;
	}
}