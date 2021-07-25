/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import static java.lang.System.out;
import java.util.Observable;

/**
 *
 * @author Roy
 */
public class InitPanelModel extends Observable {

    private boolean firstTime = false;
    private DatabaseModel dbM;

    public void setDbM(DatabaseModel dbM) {
        this.dbM = dbM;
    }

    public void init() {

        //preferences can be null
        //notifies initpanel whether this is a first-time set up
        this.setChanged();
        this.notifyObservers(!dbM.checkTableEmpty("USERS"));

    }

    public boolean login(String username, String password) {

        if (dbM.login(username, password)) {

            return true;
        } else {
            this.setChanged();
            notifyObservers(new Error("Login unsuccessful!"));
            return false;
        }

    }

    public boolean createUser(String user, String pswd) {
        user = user.trim();
        pswd = pswd.trim();

        if (!user.trim().isEmpty() && !pswd.trim().isEmpty()) {
            if (!(dbM.checkForDuplicate(user))) {
                dbM.createUser(user, pswd);
                return true;
            } else {
                //notify InitPanel of the error.
                this.setChanged();
                notifyObservers(new Error("Cannot create this duplicate user"));
            }
        } else {
            //notify InitPanel of the error.
            out.println("failed");
            this.setChanged();
            notifyObservers(new Error("Invalid input!"));
        }

        return false;
    }

}

class Error {

    public String errMsg;

    Error(String msg) {
        errMsg = msg;

    }
}
