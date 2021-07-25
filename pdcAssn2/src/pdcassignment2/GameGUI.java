/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import static java.lang.System.out;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Roy
 */
public class GameGUI extends JPanel implements Observer {

    private boolean running = false;
    private DinoController dinoControl;
    private GameModel model;
    private JFrame frame = new JFrame("Main game");
    private Image img;
    private List<MoveableFigure> tempFigs;
    private volatile boolean busy = false;

    public void setModel(GameModel model) {
        this.model = model;
    }

    public DinoController getDinoControl() {
        return dinoControl;
    }

    public void addController(DinoController dinoCon) {
        this.addKeyListener(dinoCon);

    }

    //I know the MVC paradigm dictates that the view should have minimum
    //logic associeated with it.
    //But the advantage of doing the iteration inside paintComponent
    //is that it saves some time. For contrast,
    //if say I do the logic inside the GameModel,
    //I'd have to iterate through them again in paintComponent,
    //in order to draw each one of them in turn.
    //so that's a tradeoff.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (GameModel.isGameOver()) {
            this.displayGameOver(g);
            return;
        }
        out.println("coin count: " + GameModel.getCoinCount());

        g.drawImage(img, 0, 0, GameModel.getFrameWidth(), GameModel.getFrameHeight(), null);
        g.setFont(new Font("Consolas", Font.BOLD, 16));
        g.setColor(Color.WHITE);

        g.drawString("Your current score: " + GameModel.getScore(), 0, 10);
        g.drawString("Number of coins collected :" + GameModel.getCoinCount(), 0, 30);

        //this is just being careful, to make sure that there's no NullPointerException.
        if (tempFigs == null) {

            busy = false;
            return;
        }

        ListIterator<MoveableFigure> it = tempFigs.listIterator(tempFigs.size());

        while (it.hasPrevious()) {
            MoveableFigure fig;
            fig = it.previous();

            if (fig.isActive()) {

                fig.drawSelf(g);
                fig.doRun();
            } else {
                it.remove();
            }
        }

        busy = false;
    }
//    

    public void loadPreferences(Preferences p) {

        this.setImage(p.bgImage);
        this.setPreferredSize(p.screenDim);

        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public void addController(GameController control) {
        frame.addWindowListener(control);
    }

    public void addDinoController(Dinosaur dino) {

        DinoController controller = new DinoController(dino);
        this.addKeyListener(controller);
        this.setFocusable(true);
        this.requestFocus();

    }

    @Override
    public void update(Observable o, Object arg) {

        //List is presumed to be the list of current in-frame figures.
        if (!busy && arg instanceof List) {

            busy = true;
            tempFigs = (List) arg;
        }

        repaint();

    }

    public void displayGameOver(Graphics g) {

        g.setFont(new Font("Consolas", Font.BOLD, 18));
        g.drawString("Game Over (:", this.getWidth() / 2 - 30, this.getHeight() / 2);
        g.drawString("Your final score: " + GameModel.getScore(), this.getWidth() / 2 - 30, this.getHeight() / 2 + 20);
        g.drawString("Number of coins collected: " + GameModel.getCoinCount(), this.getWidth() / 2 - 30, this.getHeight() / 2 + 40);

    }

    public void setImage(ImageIcon img) {

        this.img = img.getImage();

    }

    public void promptQuit() {
        int dialogResult;

        dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Warning", JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION) {
            // Saving code here

            model.setGameOver(true);
            model.closeSession();

        } else {

            model.setInterrupted(false);

        }

    }

}
