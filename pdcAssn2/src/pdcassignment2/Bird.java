package pdcassignment2;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * Class Bird is a concrete decorator for a MoveableObject. It's altForm is
 * different from originalForm
 *
 * @author Roy
 */
public class Bird extends Animal {

    private static Image originalForm = new ImageIcon("src/birdUp.png").getImage();
    private static Image altForm = new ImageIcon("src/birdDown.png").getImage();
    private Random generator = new Random();

    Bird(MoveableFigure f) {
        super(f);

        this.setWidth(80);
        this.setHeight(40);
        setAltForm(altForm);
        setOriginalForm(originalForm);
        setCoordX(GameModel.getFrameWidth() + 5);
        setCoordY(GameModel.getFrameHeight() - getHeight() * (2 + generator.nextInt(3)) + 3);
        this.setVelocityX(-2 * GameModel.getPixelSize());

    }

    //for the meaning of the method please see the enum class MoveableTypes
    @Override
    public Bird getNewFigure() {
        return new Bird(new MoveableObject("Bird"));
    }

}
