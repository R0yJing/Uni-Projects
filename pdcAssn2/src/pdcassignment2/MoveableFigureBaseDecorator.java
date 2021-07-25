package pdcassignment2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/*
 * The MoveableFigureBaseDecorator serves as a abstract class that is to 
 * extended by concrete decorators that add extra functionalities to a MoveableObject.
 * 
 */
/**
 *
 * @author Roy
 */
public abstract class MoveableFigureBaseDecorator implements MoveableFigure {

    protected MoveableFigure figure;

    public void setCollisionHandler(CollisionHandler handler) {
        figure.setCollisionHandler(handler);
    }

    public void setColour(Color clr) {
        figure.setColour(clr);

    }

    public Color getColour() {
        return figure.getColour();

    }

    public boolean isActive() {

        return figure.isActive();
    }

    MoveableFigureBaseDecorator(MoveableFigure f) {
        figure = f;
        setCollisionHandler(new MoveableCollisionHandler(this));
    }

    public void doRun() {

        figure.doRun();
    }

    public void setActive(boolean flag) {
        figure.setActive(flag);
    }

    @Override
    public MoveableFigure getNewFigure() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean stillWithinFrame() {

        return figure.stillWithinFrame();
    }

    @Override
    public int getCoordY() {
        return figure.getCoordY();
    }

    @Override
    public void setCoordY(int coordY) {
        figure.setCoordY(coordY);
    }

    @Override
    public int getCoordX() {
        return figure.getCoordX();
    }

    public String getid() {
        return figure.getid();
    }

    @Override
    public void setCoordX(int coordX) {
        figure.setCoordX(coordX);
    }

    @Override
    public int getVelocityY() {
        return figure.getVelocityY();
    }

    @Override
    public void setVelocityY(int v) {
        figure.setVelocityY(v);
    }

    @Override
    public int getVelocityX() {
        return figure.getVelocityX();
    }

    @Override
    public void setVelocityX(int velocityX) {
        figure.setVelocityX(velocityX);
    }

    @Override
    public int getNumPixels() {
        return figure.getNumPixels();
    }

    public void setNumPixels(int numPixels) {
        figure.setNumPixels(numPixels);
    }

    @Override
    public int getRightMostCoordX() {
        return figure.getRightMostCoordX();
    }

    @Override
    public Image getAltForm() {
        return figure.getAltForm();
    }

    @Override
    public void setAltForm(Image img) {
        figure.setAltForm(img);
    }

    @Override
    public Image getOriginalForm() {
        return figure.getOriginalForm();
    }

    @Override
    public void setOriginalForm(Image form) {
        figure.setOriginalForm(form);
    }

    public MoveableObject getObject() {
        return (MoveableObject) figure;

    }

    public void drawSelf(Graphics g) {
        figure.drawSelf(g);
    }

    @Override
    public int getHeight() {
        return figure.getHeight();
    }

    @Override
    public void setHeight(int h) {
        figure.setHeight(h);
    }

    @Override
    public int getWidth() {
        return figure.getWidth();

    }

    @Override
    public void setWidth(int h) {
        figure.setWidth(h);
    }

}
