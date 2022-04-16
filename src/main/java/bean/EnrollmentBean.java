package bean;

public class EnrollmentBean {

	private String cid, students;
	private int credit;
	
	public EnrollmentBean(String cid, String students, int credit) {
		super();
		this.cid = cid;
		this.students = students;
		this.credit = credit;	
	}

	public String getCid() {
		
		return cid;
	}
	
	public void setCid(String cid) {
		
		this.cid = cid;
	}
	
	public String getStudents() {
		
		return students;
	}
	
	public void setStudents(String students) {
		
		this.students = students;
	}
	
	public int getCredit() {
		
		return credit;
	}
	
	public void setCredit(int credit) {
		
		this.credit = credit;
	}
	
}
