package pdcassignment2;

/**
 * This enum class will nicely generate a newly instantiated MoveableFigure on
 * screen by calling the method getNewFigure. (As otherwise, if simply f is
 * returned then the next time we call getNewFigure(), it will just return the
 * reference to the old figure.
 *
 * @author Roy
 */
public enum MoveableTypes {

    PLANT(new Plant(new MoveableObject("PLANT"))),
    BIRD(new Bird(new MoveableObject("BIRD"))),
    CLOUD(new Cloud(new MoveableObject("CLOUD"))),
    COIN(new Coin(new MoveableObject("Coin")));

    private MoveableFigure f;

    private MoveableTypes(MoveableFigure f) {
        this.f = f;

    }

    public MoveableFigure getNewFigure() {

        return f.getNewFigure();
    }

}
