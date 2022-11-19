package jdbc;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PreparedStatementTestUI extends JFrame {
	//driver and database info
	static final String DRIVER = "oracle.jdbc.OracleDriver";             
	static final String DATABASE_URL = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";
	static final String USERNAME = "user";
	static final String PASSWORD = "password";
	//GUI objects
	private JButton insert, show;
	private JTextArea display;
	private JPanel pEntry;
	private JLabel lblDescription,lblProductId,lblPrice;
	private JTextField txtDescription,txtProductId, txtPrice;
	
	//JDBC objects
	Connection c ;
	Statement st;
	PreparedStatement pst;

	//
	public PreparedStatementTestUI()
	{
		//create and add the text area to south area of the frame
		display=new JTextArea(10,10);
		display.setFont(new Font("Arial", Font.BOLD, 24));
		
		JScrollPane scrollPane = new JScrollPane(display);
		add(scrollPane,BorderLayout.SOUTH);
		//
		pEntry =new JPanel();
		GridBagLayout grid = new GridBagLayout();
		pEntry.setLayout (grid);
		//
		lblProductId=new JLabel("ProductId:");
		lblProductId.setFont(new Font("Arial", Font.BOLD, 24));
 		//
		txtProductId = new JTextField(20);
		txtProductId.setFont(new Font("Arial", Font.BOLD, 24));
		//
		lblDescription=new JLabel("Description:");
		lblDescription.setFont(new Font("Arial", Font.BOLD, 24));
		//
		txtDescription=new JTextField(20);
		txtDescription.setFont(new Font("Arial", Font.BOLD, 24));
		//
		lblPrice=new JLabel("Price:");
		lblPrice.setFont(new Font("Arial", Font.BOLD, 24));
		//
		txtPrice=new JTextField(20);
		txtPrice.setFont(new Font("Arial", Font.BOLD, 24));

		//
		insert=new JButton("Insert");
		insert.setFont(new Font("Arial", Font.BOLD, 24));
		//
		show = new JButton("Show");
		show.setFont(new Font("Arial", Font.BOLD, 24));
  
		//add components to the grid
		//gridx - column, gridy - row
		addComponent(pEntry, grid, lblProductId, 0,0,1,1);
		addComponent(pEntry, grid, txtProductId, 1,0,1,1);
		
		addComponent(pEntry, grid, lblDescription, 0,1,1,1);
		addComponent(pEntry, grid, txtDescription, 1,1,1,1);
		
		addComponent(pEntry, grid, insert, 2,0,1,1);
		
		addComponent(pEntry, grid, lblPrice, 0,2,1,1);
		addComponent(pEntry, grid, txtPrice, 1,2,1,1);
		
		addComponent(pEntry, grid, show, 2,1,1,1);
		  	
		add(pEntry,BorderLayout.WEST);
		
		//
		ButtonHandler bHandler= new ButtonHandler();
		insert.addActionListener(bHandler);
		show.addActionListener(bHandler);
		//
		connect();
		
	}
	public void addComponent(JPanel p, GridBagLayout grid, Component c, int gridx, int gridy,
			int gridwidth, int gridheight)
	{
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = gridx; //column
		constr.gridy = gridy; //row
		constr.gridwidth = gridwidth; //number of cells in the row that will be covered
		constr.gridheight = gridheight; //number of cells in the column that will be covered
		constr.fill = GridBagConstraints.HORIZONTAL; //resize the component horizontally
		// add the component 
	    grid.setConstraints(c, constr); //apply the constraints to the grid
	    p.add(c);
	}
	
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==insert)
			{
				String productId = txtProductId.getText();
				String description = txtDescription.getText();
				double price = Double.parseDouble(txtPrice.getText());
				insertRow(productId,description, price);
			}
				else if(e.getSource()==show)
				displayResults();
		}
		
	}
	//
	public void connect()
	{
		try
		{			
			Class.forName( DRIVER );
		      // establish connection to database                              
			c = DriverManager.getConnection( DATABASE_URL,USERNAME, PASSWORD);
				
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			//e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			//e.printStackTrace();
		}

	}
	//
	public void displayResults()
	{
		try
		{
			st = c.createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM product");
			ResultSetMetaData md = rs.getMetaData();
			int row=0;
			String info="";
			while(rs.next())
			{
				for( int i=1;i <= md.getColumnCount();i++)
				{
					info+=md.getColumnName(i)+"\t: "+rs.getObject(i)+"\t"; 
				}
				row+=1;
				info+="\n";
			}
			display.setText(info);
			rs.close();
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			//e.printStackTrace();
		}
	}
	//
	public void insertRow(String productId, String description, double price)
	{
		try {
			
			pst = c.prepareStatement("Insert into product (productId, description, price) VALUES(?,?,?)");
			pst.setString(1, productId);
			pst.setString(2, description); 
			pst.setDouble(3, price);
			
			//Execute the prepared statement using executeUpdate method:  
			int val = pst.executeUpdate(); //returns the row count
			pst.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			System.out.println("Done!");
		}
	}
	public static void main(String[] args) {
		JFrame frame = new PreparedStatementTestUI();
		frame.setSize(800,300);
		frame.pack();
		frame.setVisible(true);
		
	}
	
}

