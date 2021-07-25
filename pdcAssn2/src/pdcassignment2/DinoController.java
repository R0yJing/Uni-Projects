package pdcassignment2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controls the movement of the dinosaur on screen.
 *
 * @author Roy
 */
public class DinoController extends KeyAdapter implements KeyListener {

    private static Dinosaur dino;

    DinoController(Dinosaur d) {

        dino = d;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (!dino.isJumping()) {
            char key = e.getKeyChar();

            switch (key) {
                case 'w':

                    dino.setVelocityY(-9 * GameModel.getPixelSize());
                    //dino.setVelocityX(GameModel.getPixelSize());
                    dino.isJumping(true);
                    if (dino.isHunched()) {
                        dino.setHunched(false);
                    }
                    dino.setAccelerated(false);
                    dino.setVelocityX(2);
                    dino.setShouldMovePart(false);
                    break;

                case 'a':

                    if (!dino.isAccelerated()) {
                        //on floor
                        if (dino.getFeetLocationY() == GameModel.getFrameHeight() - 2) {
                            dino.setShouldMovePart(false);

                        } else {
                            dino.setShouldMovePart(true);
                        }

                        //on a cloud, then Dinosaur should appear to be moving,
                        //so set alternative form to a tempForm (which is guaranteed
                        //to be different from originalForm.
                        dino.setAccelerated(true);
                        dino.sprintBackward();

                    }

                    break;
                case 's':
                    if (!(dino.isHunched())) {

                        dino.setHunched(true);

                    }
                    break;

                case 'd':

                    if (!dino.isAccelerated()) {
                        //if dinosaur's not on the Floor -- implying on it's sitting on
                        //a cloud.
                        if (dino.getFeetLocationY() != GameModel.getFrameHeight() - 2) {
                            dino.setShouldMovePart(true);
                        }
                        dino.sprintForward();
                        dino.setAccelerated(true);

                    }
                    break;

                default:
                    break;
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        char key = e.getKeyChar();

        if (key == 's') {
            dino.setHunched(false);
        } else if (key != 'w') {

            if (dino.getFeetLocationY() == GameModel.getFrameHeight() - 2) {

                //releasing the key 'a' when the dinosaur is on the Floor
                //must mean that dinosaur's shouldMovePart flag must have been set
                //to false previously when a is PRESSED. So should reset 
                //shouldMovePart to true.
                if (key == 'a') {
                    dino.setShouldMovePart(true);
                }

                //no need to test if 'd' is pressed
                //as pressing d will not set shouldmovepart to false
            } else {
                //original should be the same as alt because on cloud
                dino.setShouldMovePart(false);
            }

            if (dino.isAccelerated()) {

                dino.setAccelerated(false);
                if (key == 'a') {
                    dino.sprintForward();
                } else {
                    dino.sprintBackward();
                }
            }

        }

    }

}
