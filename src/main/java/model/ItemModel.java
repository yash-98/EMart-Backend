package model;

import java.util.Map;

import bean.ItemBean;
import dao.ItemDAO;


public class ItemModel {
	private ItemDAO itemData;
	
	public ItemDAO getItemData() {
		return itemData;
	}

	// Static ID variables
	private int itemId;
	
	
	private static ItemModel instance;
	
	public static ItemModel getInstance() {
		if (instance == null) {
			instance = new ItemModel();
			try {
				//DAO instantiation
				instance.itemData = new ItemDAO();
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private ItemModel() {
		try {
			// DAO instantiation
			itemData = new ItemDAO();
			// Static variable ID instantiation
			// TODO instantiate IDs with the largest ID			
			this.itemId = itemData.LastID();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// Item based functions
	public void checkItemParameters(String name, String description, String type,
			String brand) {
		boolean throwError = false;
		if (name.length() < 1) {
			System.out.println("Item name is not valid.");
			throwError = true;
		}
		if (description.length() < 1) {
			System.out.println("Item Description is not valid.");
			throwError = true;
		}
		if (type.length() < 1) {
			System.out.println("Item type is not valid.");
			throwError = true;
		}
		if (brand.length() < 1) {
			System.out.println("Item brand is not valid.");
			throwError = true;
		}
		
		if(throwError) {
			throw new IllegalArgumentException();
		}
	}
	
	public int insertItem(String name, String description, String type,
			String brand, String quantity, String price, String link){
		try {
			this.itemId++;
			checkItemParameters(name, description, type, brand);
			name = name.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			description = description.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			type = type.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			brand = brand.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			checkItemParameters(name, description, type, brand);
			
			Integer qnty = Integer.parseInt(quantity);
			Double priceItem = Double.parseDouble(price);
			
			return this.itemData.insert(this.itemId, name, description, type, brand, qnty, priceItem, link);
		} catch (Exception e) {
			// TODO: handle exception
			this.itemId--;
			System.out.println("There was an error when trying to insert the item.");
			return 0;
		}
		
	}
	
	public Map<Integer, ItemBean> retrieveAllItems() {
		try {
			return itemData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error with trying to retrieve all the items.");
			return null;
		}
	}
	
	public Map<Integer, ItemBean> retrieveItemByType(String type) {
		try {
			if (type.length() < 1) {
				System.out.println("Invalid type of items to be retrieved.");
				throw new IllegalArgumentException();
			}
			type = type.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			return this.itemData.retrieveByType(type);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error with trying to retrieve the items by type.");
			return null;
		}
		
	}
	
	public Map<Integer, ItemBean> retrieveItemByBrand(String brand) {
		try {
			if (brand.length() < 1) {
				System.out.println("Invalid brand of items to be retrieved.");
				throw new IllegalArgumentException();
			}
			brand = brand.replaceAll(" ", "").replaceAll("[\"\"'']", "");
			return this.itemData.retrieveByBrand(brand);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error with trying to retrieve the items by brand.");
			return null;
		}
		
	}
	
	public int deleteItem(String bId) {
		try {
			if (bId.length() < 1) {
				System.out.println("The item Id for the deleted item is invalid");
				throw new IllegalArgumentException();
			}
			Integer tempBId = Integer.parseInt(bId);
			return this.itemData.delete(tempBId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error when trying to delete the item with id=" + bId);
			return 0;
		}
	}
}
