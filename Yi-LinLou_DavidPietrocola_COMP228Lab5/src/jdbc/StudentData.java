package jdbc;

//Data Access tier
import java.sql.*;
import javax.swing.*;

public class StudentData {
  //driver and database info
  static final String DRIVER = "oracle.jdbc.OracleDriver";             
  static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
  static final String USERNAME = "COMP228_m22_sl_24";
  static final String PASSWORD = "password";
  //
  private String record[] = new String[8];
  //JDBC objects
  private ResultSet rs;
  private Connection connection;
  private Statement st;
  private PreparedStatement pst, updateSt;
  private Statement maxSt;
  private ResultSet maxrs;
  boolean more=true;
  int nCols;
  //
  public StudentData() throws SQLException {
    super();
    openConnection();
    //maxSt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
  }
  //
  public void openConnection()
  {
    try {
    	// load the driver class
	    Class.forName( DRIVER );
	    // establish connection to database                              
	    connection = DriverManager.getConnection( DATABASE_URL, USERNAME, PASSWORD );

    }
    catch(Exception e) {
      System.out.println( e.getMessage());
    }
  } //openConnection
  
  public String[] loadCurrentRecord(String strQuery)
  {
      try {
        st = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        rs = st.executeQuery(strQuery);
        ResultSetMetaData md = rs.getMetaData();
        nCols=md.getColumnCount();
        if(rs.last())
        {
            record=getRow();
        }
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
    return record;
  } //loadCurrentRecord
  public void closeConnection()
  {
      try {
        rs.close();
      }
      catch(Exception e) {
      System.out.println("Database close failed");
      System.out.println(e.toString());
      }
  } //closeConnection
  public String[] moveNext()
  {
      try {
        if(rs.next() && !rs.isAfterLast())
        {
          record=getRow();
        }
        else
        {
            record=moveLast();
            JOptionPane.showMessageDialog(null,"End of Records!", "TestJDBC",JOptionPane.INFORMATION_MESSAGE);
        }
      }
      catch(Exception e) {
      }
    return record;
  }
  public String[] moveLast()
  {
      try {
            rs.last();
            record=getRow();
      }
      catch(Exception e) {
      System.out.println(e.toString());
      }
      return record;
  } //moveLast()
  public String[] moveFirst()
  {
      try {
            rs.first();
            record=getRow();
      }
      catch(Exception e) {
      System.out.println(e.toString());
      }
      return record;
  } //moveFirst()
  public String[] movePrevious()
  {
      try {
            if(rs.previous() && !rs.isBeforeFirst())
            {
              record=getRow();
            }
            else
            {
              record=moveFirst();
              JOptionPane.showMessageDialog(null,"Beginning of Records!", "TestJDBC",JOptionPane.INFORMATION_MESSAGE);
            }
      }
      catch(Exception e) {
      System.out.println(e.toString());
      }
    return record;
  } //movePrevious()
  public String[] getRow()
  {
      try{
              for(int i=1; i<= nCols; i++)
              	record[i-1]=rs.getString(i);
        }
      catch(SQLException e)
      	{e.printStackTrace();}
    return record;
  }
  public void saveRow(String record[])
  {

      try {
  	    	pst = connection.prepareStatement("insert into Students (studentID, firstName, lastName, address, city, province, postalCode) VALUES(?,?,?,?,?,?,?)");

            /** The following works when the driver supports insertRow method
            rs.moveToInsertRow();
            for(int i=1; i<= nCols; i++)
              {
                rs.updateString(i,record[i-1]);
              }
              rs.insertRow();
            */
            //this code inserts the row using a prepared statement
    	  	for(int i=1; i<= nCols; i++)
            {
              pst.setString(i,record[i-1]);
            }
            int val = pst.executeUpdate();
            pst.close(); //close to make changes completed
            loadCurrentRecord("Select * from Students"); //open it again
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
  } // saveRow
  public void updateRow(String record[])
  {
      JOptionPane.showMessageDialog(null,record, "update row",JOptionPane.INFORMATION_MESSAGE);

      try 
      {
            /*for(int i=1; i<= nCols; i++)
            {
              //JOptionPane.showMessageDialog(null,String.valueOf(i), "TestJDBC",JOptionPane.INFORMATION_MESSAGE);
              rs.updateString(i,record[i-1]);
            }
            rs.updateRow();*/
            //
          	//this code updates the row using a prepared statement
            updateSt = connection.prepareStatement(
            		"Update Students set firstname = ?, lastname = ?,address = ?,city = ?, province = ?,postalCode = ? where studentid = ?");
            //set values for all parameters excluding studentid
    	  	for(int i=2; i<= nCols; i++)
            {
    	  		updateSt.setString(i-1,record[i-1]);
            }
    	  	//set the value for studentid parameter
    	  	updateSt.setString(7,record[0]);
            int val = updateSt.executeUpdate();
            updateSt.close(); //close to make changes completed
            loadCurrentRecord("Select * from Students"); //open it again
            
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
  } // updateRow
  public void deleteRow()
  {
      try {
              rs.deleteRow();
              rs.close();
              loadCurrentRecord("Select * from Students"); //open it again
      }
      catch(Exception e) {
      	e.printStackTrace();
      }
  } // deleteRow
} // end of StudentData
