package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JOptionPane;

public class GameData {

    //driver and database info
    static final String DRIVER = "oracle.jdbc.OracleDriver";
    //			  static final String DATABASE_URL = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";
    static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";

    static final String USERNAME = "COMP228_m22_sl_24";
    static final String PASSWORD = "password";
    //
    private String record[] = new String[7];
    //JDBC objects
    private ResultSet rs;
    private Connection connection;
    private Statement st;
    private PreparedStatement pst, pst1;
    private Statement maxSt;
    private ResultSet maxrs;
    boolean more = true;
    int nCols;

    //
    public GameData() throws SQLException {
        super();
        openConnection();
        //maxSt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    //
    public void openConnection() {
        try {
            // load the driver class
            Class.forName(DRIVER);
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } //openConnection



    public void saveRow(String playerId,String gameId, String gameTitle,String score) {

        try {
            pst = connection.prepareStatement("INSERT INTO PLAYERANDGAME (GAME_ID, PLAYER_ID,PLAYING_DATE,SCORE) VALUES ("+gameId+","+playerId+",sysdate,"+score+")");

            pst1 = connection.prepareStatement("INSERT INTO GAME(game_id,game_title) VALUES ("+gameId+",'"+gameTitle+"')");

            /** The following works when the driver supports insertRow method
             rs.moveToInsertRow();
             for(int i=1; i<= nCols; i++)
             {
             rs.updateString(i,record[i-1]);
             }
             rs.insertRow();
             */
//            //this code inserts the row using a prepared statement
//            for (int i = 1; i <= nCols; i++) {
//                pst.setString(i, record[i - 1]);
//            }
            int val1 = pst1.executeUpdate();
            int val = pst.executeUpdate();
            pst.close();
            pst1.close();
            //close to make changes completed
            //loadCurrentRecord("Select Player.* from Player"); //open it again

        } catch (Exception e) {
            e.printStackTrace();
        }
    } // saveRow


}
