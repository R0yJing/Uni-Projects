package pdcassignment2;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A CollideableObject is one which can collide with the dinosaur and not cause the
 * game to exit. Essentially it means Dinosaur can land (or jump) on top of the  object.
 * @author Roy
 */
public class CollideableObject implements CollideableFigure{
    
    private int coordX, rightMostCoordX;
    private int coordY;
    private boolean active = true;
    
    private CollisionHandler handler;
    
    public void setCollisionHandler(CollisionHandler handler){
        this.handler = handler;
    }
    
    public void setRightMostCoordX(int rightMostCoordX) {
        this.rightMostCoordX = rightMostCoordX;
    }
    
    @Override
    public int getCoordY() {
        return coordY;
    }
    
      
    public void doRun(){
        
        if (handler.checkForCollision()){
            handler.handleCollision();
        }
    }

    @Override
    public int getRightMostCoordX() {
        return rightMostCoordX;
    }

    @Override
    public void setCoordY(int c) {
        coordY = c;
    }

    @Override
    public int getCoordX() {
        return coordX;
    }

    @Override
    public void setCoordX(int c) {
        coordX = c;
    }
    
}
