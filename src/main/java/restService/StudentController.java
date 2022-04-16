package restService;

import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.StudentBean;
import model.SisModel;

@Path("students") //this is the path of the resource


public class StudentController {

	private SisModel sis;
	private Gson jsonConvertor;
	
	public StudentController() {
		
		sis = SisModel.getInstance();

		
		ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
		    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
		        if("sid".equals(fieldAttributes.getName())){
		            return true;
		        }
		        return false;
		    }

		    public boolean shouldSkipClass(Class aClass) {
		        return false;
		    }
		};
		
		GsonBuilder builder = new GsonBuilder();
		builder.setExclusionStrategies(exclusionStrategy);
		jsonConvertor = builder.create();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getStudent(@QueryParam("namePrefix") String name,
			@QueryParam("credits") int credits) {
		
		Map<String, StudentBean> students = sis.retriveStudent(name, Integer.toString(credits));
		
		String out = "";
		
		if(students != null && !students.isEmpty()) {
			
			out = "{ \"students\" : " +jsonConvertor.toJson(students.values()) +"}";
		}
		return out;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createStudent(@QueryParam("sid")String sid,
			@QueryParam("givenName")String givenname, @QueryParam("surName")String
			surname, @QueryParam("creditTaken")String credittaken,
			@QueryParam("creditGraduate")String creditgraduate) {
		
		return "insertedRows:" +sis.addStudent(sid, givenname, surname, credittaken, creditgraduate);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{sid}")
	public String delete(@PathParam("sid")String sid) {
		
		return "deletedRows:" +sis.deleteStudent(sid);
	}
	
}
