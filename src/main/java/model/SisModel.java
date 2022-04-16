package model;

import java.sql.SQLException;
import java.util.Map;

import bean.EnrollmentBean;
import bean.StudentBean;
import dao.EnrollmentDAO;
import dao.StudentDAO;

public class SisModel {

	
	private StudentDAO studentData;
	private EnrollmentDAO enrollmentData;
	
	private static SisModel instance;
	
	public static SisModel getInstance(){
		
		if (instance==null) {
			instance =new SisModel();
			try {
				instance.studentData = new StudentDAO();
				instance.enrollmentData = new EnrollmentDAO();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return instance;
	}
	
	private SisModel() {
		
		try {
			studentData = new StudentDAO();
			enrollmentData = new EnrollmentDAO();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public Map<String, StudentBean> retriveStudent(String namePrefix, String credit_taken){
		
		try {
			
			if(namePrefix.length() < 1 || Integer.parseInt(credit_taken) < 0 || namePrefix.equalsIgnoreCase("create")
					|| namePrefix.equalsIgnoreCase("delete") || namePrefix.equalsIgnoreCase("update")
					|| namePrefix.matches("[^A-Za-z0-9]") || namePrefix.equalsIgnoreCase("select")) {
				throw new IllegalArgumentException();
			}
			
			return studentData.retrieve(namePrefix, Integer.parseInt(credit_taken));
			
		}catch (Exception e) {
			
			System.err.println("The NamePrefix, or credit taken input is wrong.");
			return null;
		}
	}
	
	public int addStudent (String sid, String givenname, String surname, String credittake, String
			creditgraduate) {
		
		try {
			
			if(sid.length() < 1 || sid.equalsIgnoreCase("create")
					|| sid.equalsIgnoreCase("delete") || sid.equalsIgnoreCase("update")
					|| sid.matches("[^A-Za-z0-9]") || sid.equalsIgnoreCase("select")) {
				
				System.err.println("SID input incorrect.");
				throw new IllegalArgumentException();
			}
			
			if(givenname.length() < 1 || givenname.equalsIgnoreCase("create")
					|| givenname.equalsIgnoreCase("delete") || givenname.equalsIgnoreCase("update")
					|| givenname.matches("[^A-Za-z0-9]") || givenname.equalsIgnoreCase("select")) {
				
				System.err.println("Given Name input incorrect.");
				throw new IllegalArgumentException();
			}
			
			if(surname.length() < 1 || surname.equalsIgnoreCase("create")
					|| surname.equalsIgnoreCase("delete") || surname.equalsIgnoreCase("update")
					|| surname.matches("[^A-Za-z0-9]") || surname.equalsIgnoreCase("select")) {
				
				System.err.println("Surname input incorrect.");
				throw new IllegalArgumentException();
			}
			
			if(Integer.parseInt(credittake) < 0 || Integer.parseInt(creditgraduate) < 0) {
				
				System.err.println("Credit Taken/Graduate input incorrect.");
				throw new IllegalAccessException();
			}
			
			return studentData.insert(sid, givenname, surname, Integer.parseInt(credittake), Integer.parseInt(creditgraduate));
		} catch (Exception e) {
			
			System.err.println("Error! couldn't process the request.");
			return 0;
		}
	}
	
	public int deleteStudent (String sid) {
		
		try {
		
			if(sid.length() < 1 || sid.equalsIgnoreCase("create") 
					|| sid.equalsIgnoreCase("delete") || sid.equalsIgnoreCase("update")
					|| sid.matches("[^A-Za-z0-9]") || sid.equalsIgnoreCase("select")) {
				throw new IllegalArgumentException();
			}
			
			return studentData.delete(sid);
		} catch (Exception e) {
			
			System.err.println("The sid, provided had wrong input.");
			return 0;
		}
	}
	
	public Map<String, EnrollmentBean> retriveEnrollment(){
		
		try {
			return enrollmentData.retrieve();
		} catch (SQLException e) {

			System.err.println("The Enrollment wasn't able to be retrieved.");
			return null;
		}
	}
	
}
