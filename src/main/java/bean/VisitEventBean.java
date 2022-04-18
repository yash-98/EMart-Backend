package bean;

public class VisitEventBean {

	
	private String ipAddress, day, eventType;
	private int bid;

	public VisitEventBean(String ipAddress, String day, int bid, String eventType) {
		super();
		this.ipAddress = ipAddress;
		this.day = day;
		this.bid = bid;
		this.eventType = eventType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	
}
