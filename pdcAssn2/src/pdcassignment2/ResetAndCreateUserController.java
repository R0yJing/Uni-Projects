/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Roy
 */
public class ResetAndCreateUserController implements ActionListener {

    private DatabaseModel dbM;
    private InitPanel initPanel;
    private InitPanelModel initModel;
    private SettingSelectionModel settingsModel;

    public void setView(InitPanel initPanel) {
        this.initPanel = initPanel;
    }

    public void setModel(InitPanelModel initModel) {
        this.initModel = initModel;
    }

    public void setSettingsModel(SettingSelectionModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    public DatabaseModel getDbM() {
        return dbM;
    }

    public void setDbM(DatabaseModel dbM) {
        this.dbM = dbM;
    }

    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        if (src == initPanel.getCreateNewUserBtn()) {

            if (initModel.createUser(initPanel.getUsernameField().getText(), initPanel.getPasswordField().getText())) {
                this.settingsModel.disableGoBack();

                settingsModel.loadDefaultSetting();

                initPanel.getParentFrame().setVisible(false);
            }

        } else if (src == initPanel.getResetBtn()) {
            if (dbM.reset()) {

                initPanel.getLoginButton().setEnabled(false);
                initPanel.getResetBtn().setEnabled(false);

                initPanel.displayMessage("Settings erased!");

            } else {
                initPanel.displayError("Cannot reset!");
            }

        }
    }
}
