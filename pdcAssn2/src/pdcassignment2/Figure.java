package pdcassignment2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Interfaces to be implemented by concrete decorators such as Dinosaur, Plant
 * etc. For the meanings of methods of CollideableFigure and MoveableFigure
 * examine the class MoveableObject.
 *
 * @author Roy
 */
public interface Figure {

    public int getRightMostCoordX();

    public int getCoordX();

    public void setCoordX(int c);

    public int getCoordY();

    public void setCoordY(int c);

    public void setCollisionHandler(CollisionHandler handler);

    public void doRun();

}

interface CollideableFigure extends Figure {

    public void setRightMostCoordX(int c);

}

interface MoveableFigure extends Figure {

    public int getHeight();

    public void setHeight(int h);

    public int getWidth();

    public void setWidth(int h);

    public boolean stillWithinFrame();

    public int getNumPixels();

    public void setNumPixels(int numPixels);

    public int getVelocityX();

    public void setVelocityX(int v);

    public int getVelocityY();

    public void setActive(boolean active);

    public void setVelocityY(int v);

    public MoveableFigure getNewFigure();

    public Image getAltForm();

    public void setAltForm(Image img);

    public Image getOriginalForm();

    public void setOriginalForm(Image form);

    public void drawSelf(Graphics g);

    public boolean isActive();

    public Color getColour();

    public String getid();

    public void setColour(Color clr);

}
