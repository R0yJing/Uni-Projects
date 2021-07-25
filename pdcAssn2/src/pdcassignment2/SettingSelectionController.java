/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 *
 * Controller for SettingSelectionPanel. handles user selection of in-game
 * preferences.
 */
public class SettingSelectionController implements ActionListener {

    private GameGUI GUI;
    private SettingSelectionWindow settingsWindow;
    private GameModel gameModel;
    private InitPanel panel;
    private SettingSelectionModel settingsModel;

    public void setGUI(GameGUI GUI) {
        this.GUI = GUI;
    }

    public void setView(SettingSelectionWindow wind) {
        settingsWindow = wind;
    }

    public void addModel(GameModel m) {
        this.gameModel = m;
    }

    public void addModel(SettingSelectionModel m) {
        this.settingsModel = m;
    }

    public void configureGame(Preferences settings) {

        gameModel.setDimensions(settings.screenDim);
        gameModel.setDiffLevel(settings.diffLevel);
        GUI.addDinoController(gameModel.initDino());
        GUI.loadPreferences(settings);

    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        //this will only be triggered if confirmSelectionBtn is clicked
        if (src == settingsWindow.getConfirmSelectionBtn()) {

            //if the useePrevious flag is set then we know the user is trying to 
            //confirm a selection from the History of Settings.
            if (settingsModel.getUsePrevious()) {

                Preferences settings = settingsWindow.getSelection();
                this.configureGame(settings);

                //else if flag is not set then the user is trying to confirm a selection from the
                //default settings.
            } else if (!settingsModel.getUsePrevious()) {

                Preferences customisedSettings = settingsWindow.getCustomisedSelection();
                this.configureGame(customisedSettings);

                //only set if user does not choose to use previous -- meaning new preferences created
                gameModel.setPreferences(customisedSettings);

            }
            SwingUtilities.getWindowAncestor(settingsWindow).dispose();;

            gameModel.init();

        } else if (src == settingsWindow.getGoBack()) {

            settingsWindow.getParentFrame().setVisible(false);
            panel.askForUsingPreviousSetting();

        }

    }

    public void setInitPanel(InitPanel p) {

        this.panel = p;
    }

    public void addView(SettingSelectionWindow settingsWindow) {
        this.settingsWindow = settingsWindow;
    }
}
