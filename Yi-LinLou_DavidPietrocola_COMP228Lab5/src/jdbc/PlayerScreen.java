package jdbc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class PlayerScreen extends JFrame {
    //
    private Student player;
    JLabel lbl[] = new JLabel[6];
    String lblNames[] = {"First Name:", "Last Name:", "Address:",
            "Province:", "Postal Code:", "Phone Number:"};
    JLabel gamelbl[] = new JLabel[3];
    String gamelblNames[] = {"Game Title:", "Played on:", "Score:"};
    int txtWidths[] = {10, 10, 15, 4, 10, 10, 6};

    //data access buttons
    JButton btn[] = new JButton[6];
    String btnNames[] = {"Add New", "Save", "Update", "Delete", "Add game", "Display games"};

    //navigator buttons
    JButton nav[] = new JButton[4];
    String navNames[] = {"First", "Previous", "Next", "Last"};
    JTextField txt[] = new JTextField[7];
    Font font;
    //
    GridBagLayout gridBag;
    private GridBagConstraints constraints; // constraints of this layout
    JPanel screen;

    //Construct the frame
    public PlayerScreen() {
        //
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        this.setSize(500, 300);
        this.setTitle("Player Information");
        //	Set the GridBagLayout for the frame
        screen = new JPanel();
        gridBag = new GridBagLayout();
        screen.setLayout(gridBag);
        //
        //create data entry screen
        for (int i = 0; i < lbl.length; i++) {
            lbl[i] = new JLabel(lblNames[i]);
            lbl[i].setFont(new Font("Arial", Font.BOLD, 24));
            txt[i] = new JTextField(txtWidths[i]);
            txt[i].setFont(new Font("Arial", Font.BOLD, 24));
            //add labels
            constraints = new GridBagConstraints();
            addComponent(lbl[i], i, 0, 1, 1,
                    GridBagConstraints.BOTH,
                    GridBagConstraints.EAST, new Insets(3, 3, 3, 3));

            //add text fields
            addComponent(txt[i], i, 1, 1, 1, GridBagConstraints.NONE,
                    GridBagConstraints.WEST, new Insets(3, 3, 3, 3));
        }
        //
        for (int i = 0; i < btn.length; i++) {
            btn[i] = new JButton(btnNames[i]);
            btn[i].setFont(new Font("Arial", Font.BOLD, 24));

            addComponent(btn[i], i, 2, 1, 1,
                    GridBagConstraints.HORIZONTAL,
                    GridBagConstraints.CENTER, new Insets(3, 3, 3, 3));

            btn[i].addActionListener(new ButtonHandler());
        }
        //navigators
        JPanel pSouth = new JPanel();
        for (int i = 0; i < nav.length; i++) {
            nav[i] = new JButton(navNames[i]);
            nav[i].setFont(new Font("Arial", Font.BOLD, 24));

            pSouth.add(nav[i]);
            nav[i].addActionListener(new NavigatorHandler());
        }
        c.add(screen, BorderLayout.CENTER);
        c.add(pSouth, BorderLayout.SOUTH);
        //load and show the first record
        try {
            //player = new Player();
            player = new Student();
            showRow();
        } catch (Exception e) {
            //
        }
        btn[1].setEnabled(false);
        //set the focus to txtName when frame is opened
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                txt[0].requestFocus();
            }
        });
        pack();//size window to preferred size
    } //end of constructor

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    //
    // method to set constraints on
    private void addComponent(Component component,
                              int row, int column, int width, int height, int fill,
                              int anchor, Insets insets) {
        constraints.gridx = column; // set gridx
        constraints.gridy = row; // set gridy
        constraints.gridwidth = width; // set gridwidth
        constraints.gridheight = height; // set gridheight
        constraints.fill = fill; //specify the fill
        constraints.anchor = anchor; //set the anchor
        constraints.insets = insets; //set the insets
        // set constraints
        gridBag.setConstraints(component, constraints);
        screen.add(component); // add component
    } // end method addComponent

    //
    //read values from text fields
    public void readRow(String row[]) {
        for (int i = 0; i < txt.length; i++) {
            row[i] = txt[i].getText();
        }

    }

    //show the row in text fields
    public void showRow() {
        txt[0].setText(player.getStudentID());
        txt[1].setText(player.getFirstName());
        txt[2].setText(player.getLastName());

        txt[3].setText(player.getAddress());
        txt[4].setText(player.getCity());
        txt[5].setText(player.getProvince());
        txt[6].setText(player.getPostalCode());
    }

    //
    class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();
            if (name.equals(btnNames[0])) //add new
            {
                for (int i = 0; i < txt.length; i++) {
                    txt[i].setText("");
                }
                txt[0].requestFocus();
                btn[0].setEnabled(false);
                btn[2].setEnabled(false);
                btn[3].setEnabled(false);
                btn[1].setEnabled(true);
            } else if (name.equals(btnNames[1])) //save
            {
                String row[] = new String[7];
                readRow(row);
                try {
                    player = new Student();
                    player.saveStudent(row);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                btn[1].setEnabled(false);
                btn[0].setEnabled(true);
                btn[2].setEnabled(true);
                btn[3].setEnabled(true);
            } else if (name.equals(btnNames[2])) //update
            {
                String row[] = new String[7];
                readRow(row);
                player.updateStudent(row);
            } else if (name.equals(btnNames[3])) //delete
            {
                player.deleteStudent();
            }
            else if(name.equals(btnNames[5])) //search
            {
                DisplayGamesTable dgt = new DisplayGamesTable(txt[0].getText());
                dgt.setVisible(true);
            }



        } //actionPerformed
    } //end of ButtonHandler

    //
    class NavigatorHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();
            if (name.equals(navNames[0])) //add new
            {
                String row[] = new String[7];
                row = player.moveToFirst();
                showRow();
            } else if (name.equals(navNames[1])) //save
            {

                String row[] = new String[7];
                row = player.moveToPrevious();
                showRow();
            } else if (name.equals(navNames[2])) //update
            {
                String row[] = new String[7];
                row = player.moveToNext();
                showRow();
            } else if (name.equals(navNames[3])) //delete
            {
                String row[] = new String[7];
                row = player.moveToLast();
                showRow();
            }

        } //actionPerformed
    } //NavigatorHandler

    //
    public static void main(String[] args) {
        PlayerScreen frame = new PlayerScreen();
        frame.setFont(new Font("Arial", Font.BOLD, 24));
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout

        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);

    }

} //end of StudentScreen class
