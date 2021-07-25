/*
 * The programs are designed for PDC paper
 */
package pdcassignment2;

import java.awt.Dimension;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

//Database.
//Stores user preferences. (Note duplicate preferences won't be saved
public class DatabaseModel {

    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    private static final String URL = "jdbc:derby:gameDB;create=true";  //derby.jar
    private String currentUsername;
    private Connection conn;

    public boolean reset() {
        try {
            ResultSet rs;
            DatabaseMetaData meta = conn.getMetaData();
            Statement stmt = conn.createStatement();

            rs = meta.getTables(null, null, null, new String[]{"TABLE"});
            int expected = 2;
            int i = 0;
            while (rs.next()) {
                i++;
                out.println(rs.getString("TABLE_NAME"));

                stmt.executeUpdate("DROP TABLE " + rs.getString("TABLE_NAME"));

            }

            this.initialiseTables();

            return i == expected;

        } catch (SQLException ex) {
        }

        return false;
    }

    public DatabaseModel() {

        establishConnection();
        if (!this.checkTableExisting("USERS")) {
            initialiseTables();

        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        if (this.conn == null) {
            try {

                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            } catch (SQLException ex) {
                //this can happen when multiple instances of the
                //program is open, in which case it makes sense to exit altogether.

                System.exit(0);
            }
        }
    }

    public void savePreferences(Preferences pref) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO SETTINGS VALUES ( ?, ?, ?, ?, ?)");
            ps.setInt(1, pref.screenDim.width);
            ps.setInt(2, pref.screenDim.height);

            ps.setString(3, pref.diffLevel);
            ps.setString(4, pref.bgImage + "");
            ps.setString(5, this.currentUsername);

            out.println("pref inserted");

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {

                conn.close();
            } catch (SQLException ex) {

            }
        }
    }

    public boolean checkForDuplicate(String username) {

        try {
            ResultSet rs = myQuery("SELECT * FROM USERS WHERE USERNAME= " + "'" + username + "'");

            return rs.next();
        } catch (SQLException ex) {

        }

        return true;

    }

    public ResultSet myQuery(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException e) {
        }
        return resultSet;
    }

    public void myUpdate(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinkedHashSet<String> loadPreviousPrefsOf() {
        LinkedHashSet<String> prefs = new LinkedHashSet();

        try {
            ResultSet rs;

            int i = 0;

            rs = myQuery("SELECT * FROM SETTINGS WHERE USERNAME = '" + currentUsername + "'");
            while (rs.next()) {

                int screenWidth = rs.getInt("SCREENWIDTH");
                int screenHeight = rs.getInt("SCREENHEIGHT");
                String diffLevel = rs.getString("GAME_DIFFICULTY");
                String imgName = rs.getString("IMG_NAME");

                prefs.add("" + screenWidth + " x " + screenHeight + "|" + diffLevel + "|" + imgName + "|" + currentUsername);

            }
        } catch (SQLException ex) {
            out.println("Error getting prefs!");
        }

        return prefs;

    }

    public boolean createUser(String name, String pswd) {

        try {
            currentUsername = name;

            Statement stmt = conn.createStatement();
            ResultSet rs = myQuery("SELECT * FROM USERS");

            if (!myQuery("SELECT * FROM USERS WHERE USERNAME= '" + name + "'").next()) {
                stmt.executeUpdate("INSERT INTO USERS VALUES ('" + name + "', '" + pswd + "')");
                out.println("inserted");
            } else {

                return false;
            }

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean checkTableExisting(String newTableName) {
        try {

            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();

            try (ResultSet rsDBMeta = dbmd.getTables(null, null, null, types)) {

                //Statement dropStatement=null;
                while (rsDBMeta.next()) {
                    String tableName = rsDBMeta.getString("TABLE_NAME");

                    if (tableName.compareToIgnoreCase(newTableName) == 0) {
                        return true;

                    }

                }
            }
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }

    public void initialiseTables() {
        try {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("CREATE TABLE USERS (USERNAME VARCHAR(10), PSWD VARCHAR(10))");
            stmt.executeUpdate("CREATE TABLE SETTINGS (SCREENWIDTH INT, SCREENHEIGHT INT, GAME_DIFFICULTY VARCHAR(10), IMG_NAME VARCHAR(10), USERNAME VARCHAR(10))");

            //need a time played date, high score, coins collected, highestScore
            //stmt.executeUpdate("CREATE TABLE SCORE_INFO (HIGHESTSCORE INT, CURRENT_SCORE INT, COINS_COLLECTED INT, TIME_STAMP VARCHAR(20),  USERNAME VARCHAR(20))");
        } catch (SQLException e) {

            e.printStackTrace();
            System.exit(0);

        }
    }

    public boolean verifyCredentials(String name, String password) throws SQLException {
        ResultSet rs;
        String s = null;
        try {
            rs = myQuery("SELECT * FROM USERS");

            //rs.beforeFirst();
            while (rs.next()) {
                out.println(rs.getString("USERNAME"));
                out.println(rs.getString("PSWD"));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        if ((rs = myQuery("SELECT * FROM USERS WHERE USERNAME = '" + name + "' AND PSWD = '" + password + "'")).next()) {

            s = rs.getString("USERNAME") + rs.getString("PSWD");

            return true;
        }
        return false;

    }

    public boolean login(String name, String password) {

        try {

            conn = DriverManager.getConnection("jdbc:derby:GameDB;create=true", "pdc", "pdc");
            if (this.verifyCredentials(name, password)) {
                currentUsername = name;

                return true;
            }

        } catch (SQLException e) {

            return false;
        }

        return false;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUserName(String name) {
        currentUsername = name;
    }

    public boolean checkTableEmpty(String name) {
        try {
            return !myQuery("SELECT * FROM " + name).next();
        } catch (SQLException ex) {
            return true;
        }

    }

}

//a class that stores user in-game perferences such as background image,
//screen dimensions etc.
class Preferences {

    public Dimension screenDim;
    public ImageIcon bgImage;
    public String diffLevel;

    Preferences(int dimWidth, int dimHeight, String imgName, String diffLevel) {
        screenDim = new Dimension(dimWidth, dimHeight);
        bgImage = new ImageIcon(imgName);
        this.diffLevel = diffLevel;
    }

    Preferences(String dimString, String imgName, String diffLevel) {
        String dimensions[] = dimString.split("x");

        screenDim = new Dimension(Integer.parseInt(dimensions[0].trim()), Integer.parseInt(dimensions[1].trim()));
        bgImage = new ImageIcon("src/BackgroundsFolder/" + imgName);
        this.diffLevel = diffLevel;
    }

    Preferences(int dimWidth, int dimHeight, ImageIcon imgName, String diffLevel) {
        screenDim = new Dimension(dimWidth, dimHeight);
        bgImage = imgName;
        this.diffLevel = diffLevel;
    }

    public String unpack() {
        String displayString = screenDim.width + ", " + screenDim.height + "|" + bgImage + "|" + diffLevel;
        return displayString;

    }

    //given a prefString (preference string), construct a new Preference Object.
    public static Preferences pack(String prefString) {

        StringTokenizer tokenizer = new StringTokenizer(prefString, "\\||x");

        int width = Integer.parseInt(tokenizer.nextToken().trim());
        int height = Integer.parseInt(tokenizer.nextToken().trim());

        String diffLevel = tokenizer.nextToken();

        String imgName = tokenizer.nextToken();

        return new Preferences(width, height, imgName, diffLevel);

    }

    public String toString() {
        return "" + screenDim;

    }
}
