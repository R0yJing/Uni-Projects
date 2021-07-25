package pdcassignment2;

/**
 * Entry point for the game. Contains main method.
 *
 * @author Roy
 */
//Note: the github files that show version control can be found at 
public class Driver {

    public static void main(String args[]) {

        //instantiating all MVCs and setting up all relationships between the MVCs...
        InitPanel initPanel = new InitPanel();
        GameGUI gameGUI = new GameGUI();
        DatabaseModel dbM = new DatabaseModel();
        GameModel gM = new GameModel();
        InitPanelModel initModel = new InitPanelModel();
        InitPanelController initController = new InitPanelController();
        SettingSelectionWindow settingsWindow = new SettingSelectionWindow();
        SettingSelectionModel settingsModel = new SettingSelectionModel();
        GameController gameController = new GameController();
        SettingSelectionController settingController = new SettingSelectionController();

        gM.addObserver(initPanel);
        gM.addObserver(gameGUI);
        gM.addDatabse(dbM);

        ResetAndCreateUserController resetAndCreateUserController = new ResetAndCreateUserController();

        initPanel.addController(initController);
        initPanel.addResetAndCreateController(resetAndCreateUserController);

        initModel.addObserver(initPanel);
        initModel.setDbM(dbM);

        settingsModel.setDbM(dbM);
        settingsModel.addObserver(gameGUI);
        settingsModel.addObserver(settingsWindow);

        settingController.addModel(gM);
        settingController.addModel(settingsModel);
        settingController.addView(settingsWindow);
        settingController.setGUI(gameGUI);
        settingController.setInitPanel(initPanel);

        settingsWindow.setController(settingController);

        resetAndCreateUserController.setModel(initModel);
        resetAndCreateUserController.setDbM(dbM);
        resetAndCreateUserController.setView(initPanel);
        resetAndCreateUserController.setSettingsModel(settingsModel);

        initController.addModel(initModel);
        initController.addView(initPanel);
        initController.addModel(settingsModel);

        gameController.addModel(gM);
        gameController.addView(gameGUI);

        gameGUI.addController(gameController);
        gameGUI.setModel(gM);

        initModel.init();

    }

}
