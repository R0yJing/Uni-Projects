/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

/**
 * This class is needed to change the behaviour of how game figures handle
 * collision, at run time. For example, although the coin class is a
 * MoveableFigure, the way it handles collisions with the Dinosaur is a little
 * bit different. So the Coin class can be composed of a CoinCollisionHandler
 * which inherits from CollideableCollisionHandler, which inherits from
 * CollisionHandler.
 *
 * You might be thinking, why can't the Coin class simply override the
 * handleCollision class instead of going through all this fuss? Well it's
 * because Coin is (Just like any other MoveableFigures) COMPOSED OF a
 * MoveableObject, but does not INHERIT FROM MoveableObject, only MoveableObject
 * can directly use the CollisionHandler's methods such as handleCollision (in
 * other words, these methods are not visible from the concrete decorator Coin,
 * so it is pointless to override them). Why not make those methods visible is
 * due to a decision made to delegate the responsibilities of moving the figure
 * as well as checking for collisions to the wrappee MoveableObject.
 *
 * @author Roy
 */
public abstract class CollisionHandler {

    protected static Dinosaur dino;
    protected MoveableFigure self;

    CollisionHandler(MoveableFigure fig) {
        self = fig;

    }

    public abstract void handleCollision();

    public static void addDino(Dinosaur d) {
        dino = d;
    }

    public abstract boolean checkForCollision();

    public abstract boolean checkStillWithinFrame();

}
