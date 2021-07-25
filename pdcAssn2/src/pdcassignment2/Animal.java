package pdcassignment2;

import java.awt.Image;

//Animal is an abstract decorator that wraps a MoveableObject.
public abstract class Animal extends MoveableFigureBaseDecorator {

    Animal(MoveableFigure f) {

        super(f);

    }

    //change the animal's appearance (by moving its body part)
    //for example, a Dinosaur is an Animal. It has an altForm
    //(alternative form) where its feet's x values in the
    //altForm is different from the corresponding x value
    //in the orginalForm. The method Game.refreshFrameMatrix(MoveableFigure)
    //will set all screen pixels indexed by originalForm to blank,
    //and set all characters (on screen) indexed by altForm to Animal's 'symbol'
    //(For definition of symbol, see the class MoveableObject.
    public void movePart() {

        Image tempForm = this.getOriginalForm();
        this.setOriginalForm(super.getAltForm());
        this.setAltForm(tempForm);

    }

    public void doRun() {

        super.doRun();
        movePart();

    }

}
