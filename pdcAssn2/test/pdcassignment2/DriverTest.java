package pdcassignment2;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Roy
 */
public class DriverTest {

    private Connection conn;
    private static DatabaseModel dbM;
    GameModel model;

    @Before
    public void setUp() {
        dbM = new DatabaseModel();
        GameModel.setFrameHeight(1000);
        GameModel.setFrameWidth(1000);
        model = new GameModel();

        try {
            conn = DriverManager.getConnection("jdbc:derby:PlayerDB;create=true", "pdc", "pdc");
        } catch (SQLException ex) {
            fail("Set up failed");
        }

    }

    @After
    public void tearDown() {
        dbM.closeConnections();

    }

    @Test
    public void testInitialisingDatabase() {
        try {
            out.println("setting up database");
            dbM.reset();

            DatabaseMetaData data = conn.getMetaData();

            Statement stmt = conn.createStatement();

            boolean usersExist = dbM.checkTableExisting("USERS");
            boolean settingsExist = dbM.checkTableExisting("SETTINGS");

            assertEquals(true, usersExist && settingsExist);
        } catch (SQLException ex) {
            fail();
        }
        // TODO review the generated test code and remove the default call to fail.
    }

    @Test
    public void testSavingPreferencesToDatabase() {

        DatabaseModel dbM = new DatabaseModel();
        dbM.setCurrentUserName("John Doe");

        dbM.savePreferences(new Preferences(100, 100, "img2.jpg", "Easy"));
        LinkedHashSet<String> prefSet = dbM.loadPreviousPrefsOf();

        LinkedHashSet<String> expectedPrefSet = new LinkedHashSet();
        expectedPrefSet.add("100 x 100|Easy|img2.jpg|John Doe");

        assertEquals(expectedPrefSet, prefSet);

    }

    @Test
    public void testCollideableCollisionHandler() {

        //test whether the collision detection mechanism is working
        //for CollideableCollsionHandler
        Dinosaur d = new Dinosaur(new MoveableObject("Dino"));

        CollisionHandler.addDino(d);
        Cloud cloud = new Cloud(new MoveableObject("Cloud"));

        CollideableCollisionHandler handler = new CollideableCollisionHandler(cloud);
        cloud.setCollisionHandler(handler);
        model.setInterrupted(false);

        cloud.setCoordX(95);
        cloud.setCoordY(100);
        d.setCoordX(100);

        //since dinosaur's height is 50
        //by setting its coord Y to 50, it will not trigger the
        //collide condition. Because a collision is defined to happen
        //only when dino's bottom most current coord Y (i.e. its "feet")
        //is above the cloud's coord Y.
        d.setCoordY(50);

        d.setVelocityY(5);
        d.isJumping(true);
        boolean willNotCollide = !handler.checkForCollision();

        cloud.doRun();
        d.doRun();

        d.setCoordY(46);
        boolean collide = handler.checkForCollision();

        assertEquals(true, willNotCollide);
        assertEquals(true, collide);
    }

    @Test
    public void testVerifyLogin() {
        String username = "test";
        String password = "test";

        dbM.createUser(username, password);
        assertEquals(dbM.login(username, password), true);

    }

    @Test
    public void testCoin() {

        Dinosaur d = new Dinosaur(new MoveableObject("Dino"));
        CollisionHandler.addDino(d);
        Coin coin = new Coin(new MoveableObject("Coin"));
        CoinCollisionHandler handler = new CoinCollisionHandler(coin);
        coin.setCollisionHandler(handler);
        GameModel model = new GameModel();
        model.setInterrupted(false);
        coin.setCoordX(100);
        coin.setCoordY(GameModel.getFrameHeight() - coin.getHeight() - 2);
        d.setCoordX(78);
        d.setCoordY(GameModel.getFrameHeight() - d.getHeight() - 2);

        //expect them to collide, thus adding 1 to coin count.
        coin.doRun();
        d.doRun();

        assertEquals(true, GameModel.getCoinCount() == 1);

    }

}
