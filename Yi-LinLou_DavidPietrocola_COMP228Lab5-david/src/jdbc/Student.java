package jdbc;


//this class represents a Customer object
public class Student {
	//customer's properties
	
	private String studentID,firstName, lastName, address, city, province, postalCode;
	String record[] = new String[7]; //holds the data for the current record
	private StudentData studentData = null;
	
	//no-argument constructor
	public Student() throws Exception{
		studentData = new StudentData();
		// load the current record
		record=studentData.loadCurrentRecord("Select * from Students");
		//populate the instance variables
		studentID = record[0];
		firstName=record[1];
		lastName = record[2];
		address=record[3];
		city=record[4];
		province = record[5];
		postalCode=record[6];
	}
	// 7-argument constructor
	public Student(String studentID, String firstName,String lastname, String address, String city, String province, String postalCode)
	{
		 this.studentID=studentID;
		 this.firstName=firstName;
		 this.lastName=firstName;
		 this.address=address;
		 this.city=city;
		 this.province=province;
		 this.postalCode=postalCode;
	}
	//setter and getter methods
	public String getStudentID()
	{
		return studentID;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public String getAddress()
	{
		return address;
	}
	public String getCity()
	{
		return city;
	}
	public String getProvince()
	{
		return province;
	}
	public String getPostalCode()
	{
		return postalCode;
	}
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}
	public void setCity(String city)
	{
		this.city=city;
	}
	public void setProvince(String province)
	{
		this.province=province;
	}
	public void setPostalCode(String postalCode)
	{
		this.postalCode=postalCode;
	}
	//
	//Business methods for Student object
	//
	// - Navigational methods
	public String[] moveToNext()
	{
		 record=studentData.moveNext();
		 refreshStudentData(record);
		 return record;
	}
	public String[] moveToLast()
	{
		 record=studentData.moveLast();
		 //refresh customer's data with current record's data
		 refreshStudentData(record);
		 return record;
	}
	public String[] moveToPrevious()
	{
		 record=studentData.movePrevious();
		 refreshStudentData(record);
		 return record;
	}
	public String[] moveToFirst()
	{
		 record=studentData.moveFirst();
		 refreshStudentData(record);
		 return record;
	}
	// - database manipulation methods
	public void saveStudent(String[] row)
	{
		 refreshStudentData(row);
		 studentData.saveRow(row);
	}
	public void updateStudent(String[] row)
	{
		 refreshStudentData(row);
		 studentData.updateRow(row);
	}
	public void deleteStudent()
	{
		studentData.deleteRow();
	}

//	public void searchStudent(String studentID)
//	{
//		record=studentData.searchRow(studentID);
//		refreshStudentData(record);
//	}
	public void refreshStudentData(String data[])
	{
		 this.studentID=data[0];
		 this.firstName=data[1];
		 this.lastName=data[2];
		 this.address=data[3];
		 this.city=data[4];
		 this.province=data[5];
		 this.postalCode=data[6];
	}

} // end of Student
