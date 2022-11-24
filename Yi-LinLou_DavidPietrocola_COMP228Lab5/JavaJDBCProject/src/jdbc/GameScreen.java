/*-----------------------------------------------
|   Package: jdbc
|   Project: JavaJDBCProject
|  Creation Date: 2022-11-23
|   Language: Java
|
|   Author: David
|   Email: pietrocoladavid@gmail.com
|
|   Description:
|
*-----------------------------------------------*/
package jdbc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameScreen extends JFrame {

    //variables

    private Player player;

    JLabel lbl[] = new JLabel[4];
    String lblNames[] = {"Player id:","Game id:","Game Title:", "Score:"};
    int txtWidths[] = {10,10,10, 10};

    //data access buttons
    JButton btn[] = new JButton[1];
    String btnNames[] = {"Add game"};


    //textfields
    JTextField txt[] = new JTextField[4];
    Font font;
    //
    GridBagLayout gridBag;
    private GridBagConstraints constraints; // constraints of this layout
    JPanel screen;

    public GameScreen(String playerId) {
        //
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        this.setSize(500, 400);
        this.setTitle("Add game");
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
            if(i==0){
                txt[i].setText(playerId);
                txt[i].setEditable(false);
            }
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

        c.add(screen, BorderLayout.CENTER);


        //btn[1].setEnabled(true);
        //set the focus to txtName when frame is opened
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                txt[0].requestFocus();
            }
        });
        pack();//size window to preferred size

    }

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

    class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();

            if (name.equals(btnNames[0])) //add new
            {
                // retrieve texts
                String playerId = txt[0].getText();
                String gameId = txt[1].getText();
                String gameTitle = txt[2].getText();
                String score = txt[3].getText();

                Game game = new Game(playerId, gameId, gameTitle, score);
                try {
                    game.saveGame(playerId, gameId, gameTitle, score);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }


            } else if (name.equals(btnNames[1])) //save
            {
                String row[] = new String[7];
//                readRow(row);
                try {
                    //assigning private variables according to the textfields
                	/*
                	playerId=txt[0].getText();
                	playerFirstName=txt[1].getText();
                	playerLastName=txt[2].getText();
                	address=txt[3].getText();
                	postCode=txt[4].getText();
                	province=txt[5].getText();
                	phoneNum=txt[6].getText();
                	*/

                    player = new Player();
                    player.savePlayer(row);
					/*
                    player = new Player(playerId, playerFirstName, playerLastName, address, postCode,
                			 province, phoneNum);
                			 */
                    //player.savePlayer(row);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                btn[1].setEnabled(false);
                btn[0].setEnabled(true);
                btn[2].setEnabled(true);
                btn[3].setEnabled(true);
                btn[4].setEnabled(true);
                btn[5].setEnabled(true);


            } else if (name.equals(btnNames[2])) //update
            {
                String row[] = new String[7];
//                readRow(row);
                player.updatePlayer(row);
            } else if (name.equals(btnNames[3])) //delete
            {
                player.deletePlayer();
                for (int i = 0; i < txt.length; i++) {
                    txt[i].setText(""); //emptying the text fields
                }
            }
            else if(name.equals(btnNames[5])) //search
            {
                DisplayGamesTable dgt = new DisplayGamesTable(txt[0].getText());
                dgt.setVisible(true);
            }



        } //actionPerformed
    } //end of ButtonHandler
}
