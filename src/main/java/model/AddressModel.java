package model;

import java.util.Map;

import bean.AddressBean;
import dao.AddressDAO;
import dao.ItemDAO;
import dao.PODAO;
import dao.POItemDAO;
import dao.ReviewDAO;
import dao.UserDAO;
import dao.VisitEventDAO;

public class AddressModel {
	private AddressDAO addressData;
	
	public AddressDAO getAddressData() {
		return addressData;
	}

	// Static ID variables
	private int addressId;
	
	
	private static AddressModel instance;
	
	public static AddressModel getInstance() {
		if (instance == null) {
			instance = new AddressModel();
			try {
				//DAO instantiation
				instance.addressData = new AddressDAO();
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private AddressModel() {
		try {
			// DAO instantiation
			addressData = new AddressDAO();
			// Static variable ID instantiation
			// TODO instantiate IDs with the largest ID			
			this.addressId = addressData.LastID();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// Address DAO based functions
	public void checkAddressParamters(String street, String province, String country, String zip) {
		StringBuilder errstr = new StringBuilder("");
		boolean throwError = false;
		
		if (street.length() < 1) {
			errstr.append("The street paramter is incorrect for the address insert.\n");
			throwError = true;
		}
		if (province.length() < 1) {
			errstr.append("The province paramter is incorrect for the address insert.\n");
			throwError = true;
		}
		if (country.length() < 1) {
			errstr.append("The country paramter is incorrect for the address insert.\n");
			throwError = true;
		}
		if (zip.length() < 1) {
			errstr.append("The zip paramter is incorrect for the address insert.\n");
			throwError = true;
		}
		
		if (throwError) {
			System.out.println(errstr.toString());
			throw new IllegalArgumentException();
		}
	}
	
	public int insertAddress(String street, String province, String country, String zip) {
		try {
			this.addressId++;
			checkAddressParamters(street, province, country, zip);
			street = street.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			province = province.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			country = country.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			zip = zip.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			this.addressData.insert(addressId, street, province, country, zip);
			
			return this.addressId;
		} catch (Exception e) {
			// TODO: handle exception
			this.addressId--;
			System.out.println("There was an error when trying to insert the address into the db.");
			return 0;
		}
	}
	
	public int deleteAddress(String id) {
		try {
			if (id.length() < 1 || id.matches("[^0-9]")) {
				System.out.println("The id of the Address to delete is invalid.");
				throw new IllegalArgumentException();
			}
			int tempId = Integer.parseInt(id);
			return this.addressData.delete(tempId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was a problem trying to delete the Address.");
			return 0;
		}
	}
	
	public Map<Integer, AddressBean> retrieveAllAddresses() {
		try {
			return this.addressData.retrieveAll();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error trying to retrieve all the Addresses.");
			return null;
		}
	}
	
	public Map<Integer, AddressBean> retrieveAllAddressesByID (String id) {
		try {
			if (id.length() < 1 || id.matches("[^0-9]")) {
				System.out.println("The id of the Address to retrieve is invalid.");
				throw new IllegalArgumentException();
			}
			int tempId = Integer.parseInt(id);
			return this.addressData.retrieveById(tempId);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was a problem trying to retrieve the Address by it's ID.");
			return null;
		}
	}
	
	// TODO: Change this since, there can only be 1 address with all the values same. So I can try to send a Tuple, if need be, but id should be fine.
	public int retrieveAllAddressesByAllParameters(String street, String province, String country, String zip) {
		try {
			checkAddressParamters(street, province, country, zip);
			street = street.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			province = province.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			country = country.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			zip = zip.replaceAll(" ", "___").replaceAll("[\"\"'']", "");
			return this.addressData.retrieveByAll(street, province, country, zip);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("There was an error when trying to retrieve the address using all parameters.");
			return -1;
		}
	}
}
