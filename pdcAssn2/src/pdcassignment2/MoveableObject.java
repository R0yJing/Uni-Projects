package pdcassignment2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roy
 */
/**
 *
 * The class MoveableObject provides implementations for MoveableFigure. Any
 * concrete decorators that implement MoveableFigure should compose of a
 * MoveableObject, conforming to the decorator design pattern.
 *
 * @author Roy
 */
public class MoveableObject implements MoveableFigure {

    private volatile boolean active = true;
    private boolean changeToAltForm = true;
    private int velocityX;
    private int velocityY = 0;
    private Color selfColour;
    private int numPixels;
    private int coordX;
    private int coordY;
    private int width = 10, height = 10;

    //id is there for debugging purposes.
    private String id;
    private CollisionHandler handler;
    /*
    Alternative form of a MoveableObject on screen.
     */
    private Image altForm;

    public Color getColour() {
        return selfColour;
    }

    public void setSelfColour(Color selfColour) {
        this.selfColour = selfColour;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public MoveableObject(String id) {
        this.setName(id);
    }

    //Definition of a symbol:
    /*A group of them together represents the on-screen appearance of a 
    particular MoveableFigure. How the group is arranged is defined by the figure's altForm
    and originalForm. */
    public void drawSelf(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(selfColour);
        //out.println("drawing");

        g2d.drawImage(originalForm, coordX, coordY, width, height, null);

    }
    /*
    Original form of a MoveableObject on screen.
     */
    private Image originalForm;

    @Override
    public Image getOriginalForm() {
        return originalForm;
    }

    @Override
    public void setOriginalForm(Image originalForm) {
        this.originalForm = originalForm;
    }

    @Override
    public int getCoordY() {
        return coordY;
    }

    @Override
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    @Override
    public int getNumPixels() {
        return numPixels;
    }

    @Override
    public void setNumPixels(int n) {
        this.numPixels = n;
    }

    @Override
    public int getCoordX() {
        return coordX;
    }

    @Override
    public void setCoordX(int x) {
        coordX = x;
    }

    @Override
    public void setVelocityY(int v) {
        velocityY = v;
    }

    @Override
    public int getVelocityY() {
        return velocityY;
    }

    //Check that tis object is still visible within the frame.
    @Override
    public boolean stillWithinFrame() {
        return coordX + width >= 0;

    }

    @Override
    public int getVelocityX() {
        return velocityX;
    }

    @Override
    public void setVelocityX(int v) {
        this.velocityX = v;
    }

    public void move() {

        coordX += getVelocityX();
        coordY += this.getVelocityY();

    }

    @Override
    public int getRightMostCoordX() {
        //the coord furthest to the right in the matrix will be the right most coord.
        return this.getWidth() + getCoordX();
    }

    //This method should only be overriden. What it does is that it instantiates
    //a class that overrides this method. For example,
    //the concrete decorator class Cloud overrides this method, and calling it
    //will instantiate a new Cloud. (This method is used for the Game.generateMoveableFigs
    //method).
    @Override
    public MoveableFigure getNewFigure() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Image getAltForm() {
        return altForm;
    }

    @Override
    public void setAltForm(Image altForm) {
        this.altForm = altForm;
    }

    public int[][] getCoordMatrix() {
        return null;
    }

    public String getid() {
        return id;
    }

    public void setName(String name) {
        id = name;

    }

    public void setColour(Color clr) {
        this.selfColour = clr;
    }

    public boolean isActive() {

        return active;
    }

    public void setCollisionHandler(CollisionHandler handler) {
        this.handler = handler;

    }

    //This method defines all that should be performed on the moveable figure
    //such as moving, checking for collision with Dinosaur, etc.
    public void doRun() {
        if (!GameModel.isInterrupted()) {

            if (handler.checkStillWithinFrame()) {
                if (handler.checkForCollision()) {
                    handler.handleCollision();

                }

                move();

            } else {

                active = false;
            }
        }

    }

    @Override
    public void setActive(boolean flag) {
        active = flag;
    }

}
