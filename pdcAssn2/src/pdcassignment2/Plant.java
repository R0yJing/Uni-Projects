package pdcassignment2;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * The Plant class represents a Plant (or a Cactus) if you will. Its form is
 * displayed onto the screen. Composed of a MoveableFigure Object f.
 *
 * @author Roy
 */
public class Plant extends MoveableFigureBaseDecorator {

    private static Image originalForm = new ImageIcon("src/cactus.png").getImage();
    private static Image altForm = new ImageIcon("src/cactus.png").getImage();

    Plant(MoveableFigure f) {
        super(f);
        this.setWidth(30);
        this.setHeight(60);
        setVelocityX(-1 * GameModel.getPixelSize());
        this.setOriginalForm(originalForm);
        this.setAltForm(altForm);
        setCoordX(GameModel.getFrameWidth() + 5);
        setCoordY(GameModel.getFrameHeight() - this.getHeight() - 1); //centre is the mid point

    }

    @Override
    public MoveableFigure getNewFigure() {
        return new Plant(new MoveableObject("Plant"));
    }

}
