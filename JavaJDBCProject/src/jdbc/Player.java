package jdbc;

public class Player {
	//variables
	private String playerID;
	private String playerFirstName;
	private String playerLastName;
	private String address;
	private String postCode;
	private String province;
	private String phoneNum;
	
	public Player(String playerID, String playerFirstName, String playerLastName, String address, String postCode,
			String province, String phoneNum) {
		super();
		this.playerID = playerID;
		this.playerFirstName = playerFirstName;
		this.playerLastName = playerLastName;
		this.address = address;
		this.postCode = postCode;
		this.province = province;
		this.phoneNum = phoneNum;
	}

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public String getPlayerFirstName() {
		return playerFirstName;
	}

	public void setPlayerFirstName(String playerFirstName) {
		this.playerFirstName = playerFirstName;
	}

	public String getPlayerLastName() {
		return playerLastName;
	}

	public void setPlayerLastName(String playerLastName) {
		this.playerLastName = playerLastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	
	

}
