package pdcassignment2;

import java.awt.Image;
import javax.swing.ImageIcon;

/*
 * The Dinosaur class that is to be controlled by the player.
 */
/**
 *
 * @author Roy
 */
public class Dinosaur extends Animal {

    private boolean previouslyHunched = false;
    private boolean hunched = false;
    private volatile boolean isJumping = false;
    private boolean accelerated = false;
    private boolean landed = true;
    private boolean shouldMovePart = true;

    private static Image normalForm = new ImageIcon("src/dinosaur.png").getImage();
    private static Image hunchedForm = new ImageIcon("src/dinosaurHunched.png").getImage();
    private static Image altForm = new ImageIcon("src/dinosaurAltForm.png").getImage();

    public boolean isJumping() {
        return isJumping;
    }
    //the indices in the coordinate matrix (altForm and 
    //originalForm) of Dinosaur, where the feet pixels can be found.

    public boolean isLanded() {
        return landed;
    }

    public void setLanded(boolean landed) {
        this.landed = landed;
    }

    public boolean isAccelerated() {
        return accelerated;
    }

    public void setAccelerated(boolean accelerated) {
        this.accelerated = accelerated;
    }

    public boolean isPreviouslyHunched() {
        return previouslyHunched;
    }

    public void setPreviouslyHunched(boolean previouslyHunched) {
        this.previouslyHunched = previouslyHunched;
    }

    public boolean isHunched() {
        return hunched;
    }

    public void setHunched(boolean hunched) {
        this.hunched = hunched;
    }

    /**
     * getting the Y coord of Dinosaur's feet.
     *
     * @return
     */
    public int getFeetLocationY() {

        return this.getCoordY() + this.getHeight();
    }

    //the dinosaur's movepart method should take into consideration the input 
    //coming from the keyboard, therefore we need to perform additional checks to
    //see whether dinosaur is currently "hunched" or not, and whether it should
    //movepart.
    public void movePart() {
        //out.println("moving part");

        if (hunched && !previouslyHunched) {
            previouslyHunched = true;

            this.setOriginalForm(hunchedForm);
            this.setAltForm(hunchedForm);
            this.setCoordY(this.getCoordY() + 10);

            this.setHeight(40);
            this.setWidth(70);

        } else if (!hunched && previouslyHunched) {
            this.setCoordY(this.getCoordY() - 10);
            this.setHeight(50);
            this.setWidth(50);
            previouslyHunched = false;
            this.setOriginalForm(normalForm);
            this.setAltForm(altForm);
        }

        if (this.shouldMovePart) {

            super.movePart();

        }
    }

    public void jump() {

        this.setVelocityY(this.getVelocityY() + 1 * GameModel.getPixelSize());
    }

    @Override
    public void doRun() {

        super.doRun();
        if (isJumping) {

            this.jump();

        }
    }

    Dinosaur(MoveableFigure fig) {

        super(fig);
        this.setWidth(50);
        this.setHeight(50);

        setCoordY(GameModel.getFrameHeight() - this.getHeight() - 2); //coord Y is body line
        setCoordX(5);
        setAltForm(altForm);
        setOriginalForm(normalForm);

    }

    //Get the x coordinate of Dinosaur's foot, on the Game frame.
    public int getLeftFootCoordX() {
        return this.getCoordX();
    }

    /**
     * The stillWithinFrame method will adjust a MoveableFigure's next
     * horizontal velocity. The velocityX value will be used by method
     * refreshFrameMatrix to shift a MoveableFigure across the screen
     *
     * @return
     */
    @Override
    public boolean stillWithinFrame() {

        //we don't want the dinosaur to shift outside the frame,
        //so the wise thing to do is to readjust its velocityX 
        //so that it doesn't
        int frameHeight = GameModel.getFrameHeight();
        int frameWidth = GameModel.getFrameWidth();
        //check left edge of frame.
        if (this.getCoordX() + this.getVelocityX() < 0) {
            this.setVelocityX(-this.getCoordX());
            this.setAccelerated(false);
        } //check right edge of frame.
        else if (getRightMostCoordX() + this.getVelocityX() >= frameWidth) {
            this.setVelocityX(frameWidth - this.getRightMostCoordX() - 1);
            this.setAccelerated(false);

        }

        //check if will land on the floor.
        if (getFeetLocationY() + this.getVelocityY() >= frameHeight) {
            this.setVelocityY(frameHeight - this.getFeetLocationY() - 2);
            this.setVelocityX(0);
            this.shouldMovePart = true;
            this.isJumping(false);
        }

        return true;

    }

    public int getRightFootCoordX() {

        return this.getCoordX() + this.getWidth();

    }

    public void sprintForward() {
        setVelocityX(this.getVelocityX() + 1 * GameModel.getPixelSize());
    }

    public void sprintBackward() {
        setVelocityX(this.getVelocityX() - 1 * GameModel.getPixelSize());
    }

    public void setShouldMovePart(boolean b) {
        this.shouldMovePart = b;
    }

    public void isJumping(boolean b) {
        isJumping = b;

    }

}
