package jdbc;


import java.sql.*;
import javax.swing.JFrame;

public class PreparedStatementTest {
	private PreparedStatement pst;
	private Connection conn;
	// JDBC driver name and database URL for Oracle                             
	private static final String DRIVER = "oracle.jdbc.OracleDriver";             
	private static final String DATABASE_URL = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";
	//
	public PreparedStatementTest()
	{
		try {
			// load the driver class
		      Class.forName( DRIVER );

		      // establish connection to database                              
		      conn = DriverManager.getConnection( DATABASE_URL, "user", "password" );

			pst = conn.prepareStatement("Insert into Authors (authorID, firstname, lastname) VALUES(?,?,?)");
			//populate the fields
			pst.setInt(1, 5); 
			pst.setString(2, "Sam"); 
			pst.setString(3, "Malone");
			
			//Execute the prepared statement using executeUpdate method:  
			int val = pst.executeUpdate(); //returns the row count


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Done!");
		}
	}
	//
	public static void main(String[] args) {
		PreparedStatementTest tc=new PreparedStatementTest();
		
		
	}
	
}
