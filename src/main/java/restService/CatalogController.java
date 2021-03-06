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
import model.EMartModel;
import model.ItemModel;
import bean.ItemBean;

@Path("catalog") //this is the path of the resource
@CORS

public class CatalogController {

//	private EMartModel emart;
	private ItemModel itemModel;
	private Gson jsonConvertor;
	
	public CatalogController() {
		
//		emart = EMartModel.getInstance();
		itemModel = ItemModel.getInstance();
		
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
		
		Map<Integer, ItemBean> items = itemModel.retrieveAllItems();
		
		String out = "";
		
		if(items != null && !items.isEmpty()) {
			out = "{ \"items\" : " +jsonConvertor.toJson(items) +"}";
		}

		return out;
	}
	
	@GET
	@Path("/allBrands")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllBrands() {
		
		List<String> brands = itemModel.retrieveAllBrands();
		
		String out = "";
		
		if(brands != null && !brands.isEmpty()) {
			out = "{ \"items\" : " +jsonConvertor.toJson(brands) +"}";
		}else {
			out = "{ \"Error\" : \"Could not retrieve all brands\" }";
		}

		return out;
	}
	
	@GET
	@Path("/allTypes")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTypes() {
		
		List<String> types = itemModel.retrieveAllTypes();
		
		String out = "";
		
		if(types != null && !types.isEmpty()) {
			out = "{ \"items\" : " +jsonConvertor.toJson(types) +"}";
		}else {
			out = "{ \"Error\" : \"Could not retrieve all brands\" }";
		}

		return out;
	}
	
	@GET
	@Path("/itemsByBrand")
	@Produces(MediaType.APPLICATION_JSON)
	public String getItemsByBrand(@QueryParam("brand") String brand) {
		
		Map<Integer, ItemBean> items = itemModel.retrieveItemByBrand(brand);
		
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
		
		Map<Integer, ItemBean> items = itemModel.retrieveItemByType(type);
		
		String out = "";
		
		if(items != null && !items.isEmpty()) {
			out = "{ \"items\" : " +jsonConvertor.toJson(items) +"}";
		}

		return out;
	}
	
	@POST
	@Path("/insertItem")
	@Produces(MediaType.APPLICATION_JSON)
	@AdminAuth
	public String addItem(@QueryParam("name") String name,@QueryParam("description") String description,@QueryParam("type") String type,@QueryParam("brand") String brand,@QueryParam("quantity") String quantity,@QueryParam("price") String price,@QueryParam("link") String link) {
		
		String out = "";
		
		int added = itemModel.insertItem(name, description, type, brand, quantity, price, link);
		
		if(added > 0) {
			out = "{ \"items added\" : \"" +added  +"\" }";
		}else {
			out = "{ \"Error\" : \"Could not retrieve all brands\" }";
		}

		return out;
	}
	
}