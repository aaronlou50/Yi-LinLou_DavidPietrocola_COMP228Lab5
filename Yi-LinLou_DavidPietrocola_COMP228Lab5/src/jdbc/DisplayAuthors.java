package jdbc;

//Fig. 25.23: DisplayAuthors.java
//Displaying the contents of the authors table.
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DisplayAuthors 
{
// JDBC driver name and database URL                              
static final String DRIVER = "oracle.jdbc.OracleDriver";             
static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";

// launch the application
public static void main( String args[] )
{
   Connection connection = null; // manages connection
   Statement statement = null; // query statement
   ResultSet resultSet = null; // manages results
 
   // connect to database books and query database
   try 
   {
      // load the driver class
      Class.forName( DRIVER );

      // establish connection to database                              
      connection =                                                     
         DriverManager.getConnection( DATABASE_URL, "COMP228_m22_sl_24", "password" );

      // create Statement for querying database
      statement = connection.createStatement();
      //String query= "SELECT authorID, firstName, lastName FROM authors";
      String query = "Select * from Authors";
      // query database                                        
      resultSet = statement.executeQuery(query);
      
      // process query results
      ResultSetMetaData metaData = resultSet.getMetaData();
      int numberOfColumns = metaData.getColumnCount();     
      System.out.println( "Authors Table of Books Database:\n" );
      
      for ( int i = 1; i <= numberOfColumns; i++ )
         System.out.printf( "%-8s\t", metaData.getColumnName( i ) );
      System.out.println();
      
      while ( resultSet.next() ) 
      {
         for ( int i = 1; i <= numberOfColumns; i++ )
            System.out.printf( "%-8s\t", resultSet.getObject( i ) );
         System.out.println();
      } // end while
   }  // end try
   catch ( SQLException sqlException )                                
   {                                                                  
      sqlException.printStackTrace();
   } // end catch                                                     
   catch ( ClassNotFoundException classNotFound )                     
   {                                                                  
      classNotFound.printStackTrace();            
   } // end catch                                                     
   finally // ensure resultSet, statement and connection are closed
   {                                                             
      try                                                        
      {                                                          
         resultSet.close();                                      
         statement.close();                                      
         connection.close();                                     
      } // end try                                               
      catch ( Exception exception )                              
      {                                                          
         exception.printStackTrace();                            
      } // end catch                                             
   } // end finally                                              
} // end main
} // end class DisplayAuthors
