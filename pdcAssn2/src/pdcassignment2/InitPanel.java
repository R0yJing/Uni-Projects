/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Initial configuration panel. To let user select in-game settings.
 *
 * @author Roy
 */
public class InitPanel extends JPanel implements Observer {

    private JButton loginButton;
    private JFrame parentFrame;
    private JButton goBack = new JButton("Go back");
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton nextButton;
    private JButton createNewUserBtn = new JButton("create user");
    private JButton confirmBtn;
    private JButton usePrevious = new JButton("Yes");
    private JButton dontUsePrevious = new JButton("No, use default settings");
    private GridBagConstraints gbc = new GridBagConstraints();
    private JScrollPane messagePane;
    private InitPanelController controller;
    private JButton confirmSelectionBtn = new JButton("Confirm!");
    private boolean askedForPrevious = false;
    private ResetAndCreateUserController resetAndCreateController;

    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public GridBagConstraints getGbc() {
        return gbc;
    }

    public JScrollPane getMessagePane() {
        return messagePane;
    }

    public InitPanelController getController() {
        return controller;
    }

    public JButton getConfirmBtn() {

        return confirmBtn;
    }

    public void setConfirmBtn(JButton confirmBtn) {
        this.confirmBtn = confirmBtn;
    }

    public JButton usePrevious() {
        return usePrevious;
    }

    public JButton dontUsePrevious() {
        return dontUsePrevious;
    }

    public JButton getCreateNewUserBtn() {
        return createNewUserBtn;
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }

    private JButton resetBtn;

    //GUI code for asking the user if they want to load previous settings
    //saved in the database.
    public void askForUsingPreviousSetting() {
        //GameModel's preferences at this point is not null.
        if (!askedForPrevious) {
            askedForPrevious = true;
            this.removeAll();

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JLabel message = new JLabel("Previous settings exist, would you like to use them?");

            JPanel buttonPanel = new JPanel();

            buttonPanel.add(usePrevious);
            buttonPanel.add(dontUsePrevious);

            usePrevious.addActionListener(controller);
            dontUsePrevious.addActionListener(controller);

            add(message);
            add(buttonPanel);

            parentFrame.setVisible(true);
            // this.setVisible(true);
            parentFrame.pack();
            this.revalidate();
            this.repaint();

        } else {

            this.parentFrame.setVisible(true);
            this.setVisible(true);
        }
    }

    public InitPanel() {

        this.setLayout(new GridBagLayout());

        usernameField = new JTextField();
        passwordField = new JTextField();

        usernameField.setColumns(15);
        passwordField.setColumns(15);

        parentFrame = new JFrame("Initial config...");
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton = new JButton("Login");
        loginButton.addActionListener(controller);

        resetBtn = new JButton("Delete all history");
        resetBtn.addActionListener(controller);
    }

    public void addController(ActionListener listener) {
        controller = (InitPanelController) listener;
    }

    public void dispTips() {

        JTextArea text
                = new JTextArea("Welcome to Dinosaur The Game.\nThis game is very similar to\n"
                        + "the offline game Google T-Rex.\n Use the keys WASD to control"
                        + "the movement of the Dinosaur.\n"
                        + "Use 'w' to jump, 'a' to move backward,\n"
                        + "d to move forward, and finally s to \"hunch\" the Dinosaur. \n"
                        + "Avoid any randomly generated incoming obstacles.\n"
                        + "as you go, you'll see your current score increase.\n "
                        + "There's a special moving figure in the shape of a cloud\n"
                        + "You will see them as you progress.\n"
                        + "This figure is special in that you need not avoid this\n"
                        + "obstacle and you can choose to\n"
                        + "\"land\" your Dinosaur on top of it, by first pressing\n"
                        + "'w' key, Dinosaur will \"jump\" onto the Cloud if the positions\n"
                        + "of the Dinosaur align with that of the Cloud \n"
                        + "That's everything, have fun!");

        text.setEditable(false);

        messagePane = new JScrollPane(text);
        nextButton = new JButton("next");
        add(this.messagePane);

        nextButton.addActionListener(controller);

        addAt(2, 2, nextButton);

    }

    public JButton getConfirmSelectionBtn() {
        return confirmSelectionBtn;
    }

    public void displayError(String errMessage) {

        JOptionPane.showMessageDialog(this, errMessage, "ERROR",
                JOptionPane.ERROR_MESSAGE);

    }

    @Override
    public void update(Observable o, Object arg) {

        //the boolean flag is used to alert the initPanel
        //whether this is a first time set up,
        //in which case the game will first display tips
        //before bringing the user to the login panel.
        if (arg instanceof Boolean) {

            parentFrame.add(this);

            boolean prevExist = (boolean) arg;
            if (!prevExist) {

                dispTips();
            } else {
                this.bringToLogin(true);

                //this.askForUsingPreviousSetting();
            }
            parentFrame.pack();
            parentFrame.setVisible(true);

            parentFrame.setResizable(false);

        } else if (arg instanceof Error) {

            this.displayError(((Error) arg).errMsg);
        }

    }

    //the addAt method assumes a layout of GridBagLayout.
    //it adds a JComponent at x, y and optional varargs widtAndHeight.
    private void addAt(int x, int y, JComponent comp, int... widthAndHeight) {

        gbc.gridx = x;
        gbc.gridy = y;
        if (widthAndHeight.length != 0) {
            gbc.gridwidth = widthAndHeight[0];
            if (widthAndHeight.length > 1) {
                gbc.gridheight = widthAndHeight[1];
            }
        } else {
            gbc.gridwidth = 5;
        }

        this.add(comp, gbc);

    }

    //Bring user to the login page...
    public void bringToLogin(boolean canLogin) {

        this.removeAll();
        this.setLayout(new GridBagLayout());

        addAt(1, 1, new JLabel("Username"));

        addAt(1, 2, usernameField);

        addAt(10, 1, new JLabel("Password"));

        addAt(10, 2, passwordField);

        addAt(10, 3, loginButton);

        addAt(15, 3, this.resetBtn);

        if (canLogin) {
            loginButton.addActionListener(controller);
            resetBtn.addActionListener(resetAndCreateController);

        } else {
            loginButton.setEnabled(false);
            this.resetBtn.setEnabled(false);
        }

        addAt(3, 3, this.createNewUserBtn);
        createNewUserBtn.addActionListener(resetAndCreateController);
        parentFrame.setResizable(true);
        parentFrame.pack();

        repaint();
        revalidate();
    }

    public void addResetAndCreateController(ResetAndCreateUserController controller) {
        this.resetAndCreateController = controller;
    }

    public JButton getResetBtn() {
        return resetBtn;
    }

    public void displayMessage(String s) {
        JOptionPane.showMessageDialog(this, s, "INFO",
                JOptionPane.INFORMATION_MESSAGE);

    }

}
