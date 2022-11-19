package jdbc;

import java.sql.*;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;

public class DisplayGamesTable extends JFrame {
    private JTable table;
    private Vector rows, columns;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    //
    private Connection conn;
    private Statement st;
    private ResultSet rs;

    // JDBC driver name and database URL for MySQL
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";

    //
    public DisplayGamesTable(String playerID) {

        rows = new Vector();
        columns = new Vector();

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);//ScrollPane
        //
        mainPanel = new JPanel();
        setSize(750, 300);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add("Center", scrollPane);

        mainPanel.setBackground(Color.white);
        table.getParent().setBackground(Color.cyan);
        getContentPane().add(mainPanel);
        setVisible(true);

        try {
            // load the driver class
            Class.forName(DRIVER);

            // establish connection to database
            conn = DriverManager.getConnection(DATABASE_URL, "COMP228_m22_sl_40", "password");


            st = conn.createStatement();
            rs = st.executeQuery("SELECT first_name, last_name, game_title, score," +
                    "playing_date FROM player join PlayerAndGame USING(player_id) join Game USING (game_id) WHERE player_id=" + playerID);

            ResultSetMetaData md = rs.getMetaData();
            //create columns headers
            for (int i = 1; i <= md.getColumnCount(); i++) {
                columns.addElement(md.getColumnName(i));
            }
            //
            int row = 0;
            while (rs.next()) {
                Vector vRow = new Vector(); //to store the current row
                //System.out.println("Row " +row+"\n");
                for (int i = 1; i <= md.getColumnCount(); i++) {

                    Object columnValue = rs.getObject(i);
                    vRow.addElement(columnValue.toString());
                }
                row += 1;
                rows.addElement(vRow);

            }

            tableModel.setDataVector(rows, columns);
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                try {
                    conn.close(); // close the connection after you're finished with it
                } catch (SQLException ex) {/*nothing here*/}
                conn = null;
            }

        }

    }


}
