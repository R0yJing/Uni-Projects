/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

/**
 *
 * @author Roy
 */
class InitPanelController implements ActionListener {

    private InitPanel initPanel;
    private InitPanelModel initModel;
    private SettingSelectionModel settingsModel;

    //private SettingSelectionWindow settingsWind;
    //game model is unnecessary
    public void setInitModel(InitPanelModel initModel) {
        this.initModel = initModel;
    }

    public void setSettingsModel(SettingSelectionModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    public void addView(InitPanel p) {
        this.initPanel = p;
    }

    public void addModel(InitPanelModel m) {
        this.initModel = m;
    }

    public void addModel(SettingSelectionModel m) {
        this.settingsModel = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent src = (JComponent) e.getSource();
        if (src == initPanel.getNextButton()) {

            initPanel.bringToLogin(false);

        } else if (src == initPanel.getLoginButton()) {
            if (initModel.login(initPanel.getUsernameField().getText().trim(), initPanel.getPasswordField().getText().trim())) {
                if (settingsModel.checkPreviousSettingsExist()) {

                    initPanel.askForUsingPreviousSetting();

                } else {

                    //there is no game records for this user yet...so
                    //load default setting
                    settingsModel.disableGoBack();
                    settingsModel.loadDefaultSetting();
                    initPanel.getParentFrame().setVisible(false);

                    //SwingUtilities.getWindowAncestor(p).dispose();
                }
            }

        } else if (src == initPanel.usePrevious()) {

            settingsModel.loadPreviousSetting();
            initPanel.getParentFrame().setVisible(false);

        } //ask for using previous setting
        else if (src == initPanel.dontUsePrevious()) {
            settingsModel.loadDefaultSetting();
            initPanel.getParentFrame().setVisible(false);

            //settingsModel.loadPreviousSetting();
            //.bringToNewPreferenceSelection();
        }

    }

}
