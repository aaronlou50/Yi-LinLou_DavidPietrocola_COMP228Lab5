package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JOptionPane;

public class PlayerData {
	//driver and database info
	  static final String DRIVER = "oracle.jdbc.OracleDriver";             
	  static final String DATABASE_URL = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";
	  static final String USERNAME ="COMP228_m22_sl_40";
	  static final String PASSWORD = "password";
	  //
	  private String record[] = new String[7];
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
	  public PlayerData() throws SQLException {
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
		    connection = DriverManager.getConnection( DATABASE_URL, USERNAME, PASSWORD);

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
	        //record=getRow();
	        
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
	  /*
	  public void insertRow(String playerId, String playerFirstName, String playerLastName, String address, String postCode, String province, String phoneNum)
		{
			try {
				
				pst = connection.prepareStatement("INSERT INTO Player (PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);");
				//pst.setString(1, playerId);
				pst.setString(1, playerFirstName); 
				pst.setString(2, playerLastName);
				
				pst.setString(3, address);
				pst.setString(4, postCode);
				pst.setString(5, province);
				pst.setString(6, phoneNum);

				
				//Execute the prepared statement using executeUpdate method:  
				int val = pst.executeUpdate(); //returns the row count
				pst.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		*/
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
	  	    	pst = connection.prepareStatement("INSERT INTO Player (PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER) VALUES (?, ?, ?, ?, ?, ?, ?)");

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
	            //loadCurrentRecord("Select Player.* from Player"); //open it again
	            loadCurrentRecord("SELECT PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER FROM player");

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
	            		"Update Player set first_name = ?, last_name = ?,address = ?,postal_code = ?, province = ?,phone_number = ? where player_id = ?");
	            //set values for all parameters excluding player id
	    	  	for(int i=2; i<= nCols; i++)
	            {
	    	  		updateSt.setString(i-1,record[i-1]);
	            }
	    	  	//set the value for player id parameter
	    	  	updateSt.setString(7,record[0]);
	            int val = updateSt.executeUpdate();
	            updateSt.close(); //close to make changes completed
	            //loadCurrentRecord("Select Player.* from Player"); //open it again
	            loadCurrentRecord("SELECT PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER FROM player");
	      }
	      catch(Exception e) {
	      	e.printStackTrace();
	      }
	  } // updateRow
	  public void deleteRow()
	  {
	      try {
	            String[] currentRecord = loadCurrentRecord("SELECT PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER FROM player");
	              //rs.moveToCurrentRow();
	            rs.beforeFirst();
	             while( rs.next()) {
	            	 if (rs.getString(rs.getRow()).equals(currentRecord)) {
	            		//need to fix this and make the pointer pointing at the curent record 
	            	 }
	            	 
	             }
	              rs.deleteRow();
	              rs.close();
		            loadCurrentRecord("SELECT PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER FROM player");

	              //loadCurrentRecord("Select Player.* from Player"); //open it again
	      }
	      catch(Exception e) {
	      	e.printStackTrace();
	      }
	      JOptionPane.showMessageDialog(null,"The last record has been deleted.", "OK",JOptionPane.INFORMATION_MESSAGE);

	  } // deleteRow
}