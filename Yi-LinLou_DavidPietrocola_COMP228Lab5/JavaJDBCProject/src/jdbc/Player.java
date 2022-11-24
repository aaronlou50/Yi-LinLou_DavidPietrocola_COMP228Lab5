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
	String record[] = new String[7]; //holds the data for the current record
	private PlayerData playerData = null;

	//no-argument constructor
		public Player() throws Exception{
			playerData = new PlayerData();
			// load the current record
			record=playerData.loadCurrentRecord("Select * from Player");
			//populate the instance variables
			playerID = record[0];
			playerFirstName=record[1];
			playerLastName = record[2];
			address=record[3];
			postCode=record[4];
			province = record[5];
			phoneNum=record[6];
		}
	public Player( String playerID, String playerFirstName, String playerLastName, String address, String postCode,
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
	//method to get current record
	public String[]getCurrentRecord(){
		record = playerData.getRow();
		refreshPlayerData(record);
		return record;
	}
	
	public String[] moveToNext()
	{
		 record=playerData.moveNext();
		 refreshPlayerData(record);
		 return record;
	}
	public String[] moveToLast()
	{
		 record=playerData.moveLast();
		 //refresh customer's data with current record's data
		 refreshPlayerData(record);
		 return record;
	}
	public String[] moveToPrevious()
	{
		 record=playerData.movePrevious();
		 refreshPlayerData(record);
		 return record;
	}
	public String[] moveToFirst()
	{
		 record=playerData.moveFirst();
		 refreshPlayerData(record);
		 return record;
	}
	// - database manipulation methods
	
	public void savePlayer(String[] row)
	{
		refreshPlayerData(row);
		 playerData.saveRow(row);
	}
	public void updatePlayer(String[] row)
	{
		refreshPlayerData(row);
		 playerData.updateRow(row);
	}
	public void deletePlayer()
	{
		
		playerData.deleteRow();
	}

//	public void searchStudent(String studentID)
//	{
//		record=studentData.searchRow(studentID);
//		refreshStudentData(record);
//	}
	public void refreshPlayerData(String data[])
	{
		 this.playerID=data[0];
		 this.playerFirstName=data[1];
		 this.playerLastName=data[2];
		 this.address=data[3];
		 this.postCode=data[4];
		 this.province=data[5];
		 this.phoneNum=data[6];
	}

	

}
