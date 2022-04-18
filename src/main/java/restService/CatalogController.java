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
import model.EMartModel;
import bean.ItemBean;

@Path("catalog") //this is the path of the resource
@CORS

public class CatalogController {

	private EMartModel emart;
	private Gson jsonConvertor;
	
	public CatalogController() {
		
		emart = EMartModel.getInstance();
		
		ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
		    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
		        if("bid".equals(fieldAttributes.getName())){
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
	@Path("/allItems")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllItems() {
		
		Map<Integer, ItemBean> items = emart.retrieveAllItems();
		
		String out = "";
		
		if(items != null && !items.isEmpty()) {
			out = "{ \"items\" : " +jsonConvertor.toJson(items) +"}";
		}

		return out;
	}
	
	/*@GET
	@Path("/allBrands")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllBrands() {
		
		Map<Integer, ItemBean> items = emart.retrieveAllBrands();
		
		String out = "";
		
		if(items != null && !items.isEmpty()) {
			out = "{ \"items\" : " +jsonConvertor.toJson(items) +"}";
		}

		return out;
	}
	
	@GET
	@Path("/allTypes")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTypes() {
		
		Map<Integer, ItemBean> items = emart.retrieveAllTypes();
		
		String out = "";
		
		if(items != null && !items.isEmpty()) {
			out = "{ \"items\" : " +jsonConvertor.toJson(items) +"}";
		}

		return out;
	}*/
	
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