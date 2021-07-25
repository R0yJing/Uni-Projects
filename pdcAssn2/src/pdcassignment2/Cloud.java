package pdcassignment2;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * The Cloud class. represents a cloud on to which the dinosaur can jump.
 *
 * @author Roy
 */
public class Cloud extends MoveableFigureBaseDecorator {

    private Random generator = new Random();
    private static Image originalForm = new ImageIcon("src/cloud.png").getImage();

    private static Image altForm = originalForm;

    Cloud(MoveableFigure mF) {
        super(mF);
        this.setWidth(200);
        this.setHeight(100);
        setOriginalForm(originalForm);
        setVelocityX(-GameModel.getPixelSize());
        setCoordY(GameModel.getFrameHeight() - getHeight() * 2);//- generator.nextInt(3) * this.getHeight() - 1);
        setCoordX(GameModel.getFrameWidth());
        this.setAltForm(this.getOriginalForm());

        this.setCollisionHandler(new CollideableCollisionHandler(this));

    }

    @Override
    public Cloud getNewFigure() {

        return new Cloud(new MoveableObject("cloud"));

    }

}
