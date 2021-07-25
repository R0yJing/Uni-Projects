/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

/**
 *
 * @author Roy
 */
public class MoveableCollisionHandler extends CollisionHandler {

    MoveableCollisionHandler(MoveableFigure fig) {
        super(fig);

    }

    public boolean checkStillWithinFrame() {
        return self.stillWithinFrame();
    }

    public boolean checkForCollision() {
        if (!(self instanceof Dinosaur)) {

            int dinoHeight = dino.getHeight();
            int dinoWidth = dino.getWidth();

            int dinoCoordY = dino.getCoordY();
            int dinoCoordX = dino.getCoordX();
            int selfCoordY = self.getCoordY();
            int selfCoordX = self.getCoordX();
            int selfWidth = self.getWidth();
            int selfHeight = self.getHeight();

            //if none of the conditions are true, then it means dinosaur must intersect
            //(or overlap) with this object
            return (!(dinoCoordY + dinoHeight < selfCoordY || selfCoordY + selfHeight < dinoCoordY
                    || selfCoordX + selfWidth < dinoCoordX || dinoCoordX + dinoWidth < selfCoordX));

        } else {
            return false;
        }
    }

    @Override
    public void handleCollision() {

        GameModel.setGameOver(true);
        // GameModel.setGameOver(true);

    }

}
