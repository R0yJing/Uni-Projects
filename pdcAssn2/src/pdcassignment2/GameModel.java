/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * The model for the Game GUI conforming to the MVC paradigm. extends Runnable
 * so to make painting the GameGUI possible.
 *
 * @author Roy
 */
public class GameModel extends Observable implements Runnable {

    private static int currentScore = 0;
    private static int numCoinsCollected = 0;
    private static volatile boolean interrupted = false;
    private static volatile boolean gameOver = false;
    private Preferences preferences;
    private static volatile boolean drawing = false;
    private static int frameHeight;
    private Dinosaur dino = null;
    private int sleepTime = 150;
    private DatabaseModel dbM;
    private static List<MoveableFigure> figs = Collections.synchronizedList(new ArrayList());

    public static int getScore() {
        return currentScore;
    }

    public static int getCoinCount() {
        return numCoinsCollected;
    }

    public static boolean isInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public static void addCoin() {
        numCoinsCollected++;
    }

    public static int getNumCoinsCollected() {
        return numCoinsCollected;
    }

    public static void setNumCoinsCollected(int numCoinsCollected) {
        GameModel.numCoinsCollected = numCoinsCollected;
    }

    public static boolean isDrawing() {
        return drawing;
    }

    public static void setDrawing(boolean flag) {
        drawing = flag;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public static void addCoins() {
        numCoinsCollected += 1;

    }

    public void init() {

        new Thread(this).start();
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
    private final static int pixelSize = 5;
    private static int frameWidth;
    private static Random rand = new Random();
    private volatile int numCurrentFigs;
    private int MAX_NUM_FIGS;

    public int getNumCurrentFigs() {
        return numCurrentFigs;
    }

    public synchronized void setNumCurrentFigs(int numCurrentFigs) {
        this.numCurrentFigs = numCurrentFigs;
    }

    public static boolean pixelWithFrame(int x, int y) {
        if (x > -1 && x < frameWidth) {
            if (y > -1 && y < frameHeight) {
                return true;
            }
        }

        return false;
    }

    public static int getFrameWidth() {
        return frameWidth;
    }

    public static void setFrameWidth(int frameWidth) {
        GameModel.frameWidth = frameWidth;
    }

    public static int getFrameHeight() {
        return frameHeight;
    }

    public static void setFrameHeight(int frameHeight) {
        GameModel.frameHeight = frameHeight;
    }

    public static int getPixelSize() {
        return pixelSize;
    }

    public void addDatabse(DatabaseModel m) {
        dbM = m;
    }

    //Test that some "pixel" of a figure is within the game frame.
    public static boolean pixelInFrame(int coordX, int coordY) {
        if (coordX > -1 && coordY > -1) {
            if (coordX < getFrameWidth() && coordY < getFrameHeight()) {
                return true;
            }
        }

        return false;
    }

    public Preferences getPrevUserPrefs() {
        return preferences;
    }

    public void setDim(Dimension d) {

        setFrameHeight(d.height);
        setFrameWidth(d.width);

    }

    public int getNumCurrentFig() {
        return figs.size();
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static List<MoveableFigure> getFigs() {
        return figs;
    }

    public static void setFigs(ArrayList<MoveableFigure> figs) {
        GameModel.figs = figs;
    }

    public Dinosaur initDino() {

        dino = new Dinosaur(new MoveableObject("Dino"));
        figs.add(dino);
        CollisionHandler.addDino(dino);

        return dino;
    }

    public void setDimensions(Dimension d) {
        frameHeight = d.height;
        frameWidth = d.width;

    }

    public MoveableFigure generateNewFigures() {
        int numValues = MoveableTypes.values().length;

        MoveableFigure fig = MoveableTypes.values()[rand.nextInt(numValues)].getNewFigure();

        figs.add(fig);
        return fig;

    }

    public void run() {

        while (true) {

            if (!gameOver) {

                if (!interrupted) {

                    if (figs.size() < MAX_NUM_FIGS) {

                        this.generateNewFigures();
                    }

                    //roughly 2 minutes
                    if (currentScore++ % 1000 == 0 && currentScore < 10000) {
                        sleepTime -= 5;

                    }

                }

            }
            this.setChanged();
            this.notifyObservers(figs);

            //game over, so this is to notify game gui of game over
            try {

                Thread.sleep(sleepTime);

            } catch (InterruptedException ex) {

            }
        }

    }

    public void closeSession() {

        if (preferences != null) {

            dbM.savePreferences(preferences);

        }
        dbM.closeConnections();
        System.exit(0);
    }

    public static void setGameOver(boolean b) {
        gameOver = b;

    }

    public void setDiffLevel(String diffLevel) {

        switch (diffLevel) {
            case "Easy":
                this.MAX_NUM_FIGS = 4;
                sleepTime = 150;
                break;
            case "Medium":
                this.MAX_NUM_FIGS = 6;
                sleepTime = 130;
                break;
            case "Hard":
                this.MAX_NUM_FIGS = 8;
                sleepTime = 120;
                break;

        }
    }

}
