/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.*;
import javax.swing.ImageIcon;

/**
 * The SettingsSelectionModel is the model for the SettingSelectionWindow,
 * conforming to the MVC paradigm.
 *
 * @author Roy
 */
public class SettingSelectionModel extends Observable {

    private DatabaseModel dbM;
    private LinkedHashSet<String> prefSet;
    private boolean usePrevious = false;
    private boolean loadDefaultClicked = false,
            loadPreviouClicked = false;
    private Boolean goBack = true;

    public void setDbM(DatabaseModel dbM) {
        this.dbM = dbM;
    }

    public void setUsePrevious(boolean usePrevious) {
        this.usePrevious = usePrevious;
    }

    public void loadDefaultSetting() {
        if (this.loadDefaultClicked == false) {
            loadDefaultClicked = true;
            usePrevious = false;
            setChanged();

            notifyObservers(new DefaultSelections());
            this.loadDefaultClicked = true;
            setChanged();
            notifyObservers(goBack);

        } else {
            setChanged();
            this.notifyObservers();
        }

    }

    public boolean getUsePrevious() {
        return usePrevious;
    }

    public void setModel(DatabaseModel dbM) {
        this.dbM = dbM;

    }

    public boolean checkPreviousSettingsExist() {
        return !dbM.loadPreviousPrefsOf().isEmpty();
    }

    public void loadPreviousSetting() {
        if (!this.loadPreviouClicked) {
            usePrevious = true;

            loadPreviouClicked = true;

            //prefSet is set of prestrings
            prefSet = dbM.loadPreviousPrefsOf();

            //inform setting window of the pref string!
            this.setChanged();
            notifyObservers(prefSet);
            this.setChanged();
            notifyObservers(goBack);

        } else {
            setChanged();
            this.notifyObservers();
        }

    }

    public void disableGoBack() {
        goBack = false;
    }

}

class DefaultSelections {

    private static int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    private static String screenDims[] = {"" + (SCREEN_WIDTH + " x " + (SCREEN_HEIGHT - 40)),
        "" + ((int) (SCREEN_WIDTH * 0.7) + " x " + (int) (SCREEN_HEIGHT * 0.7)),
        "" + (int) ((SCREEN_WIDTH) * 0.5) + " x " + (int) (SCREEN_HEIGHT * 0.5)};

    LinkedHashSet<String> diffLevel;
    LinkedHashSet<String> dimensions;

    List<String> fileNames;
    LinkedHashSet<ImageIcon> imagesIcons;
    ImageIcon[] scaledImages;

    public ImageIcon[] getScaledImage() {
        int i = 0;
        scaledImages = new ImageIcon[imagesIcons.size()];

        for (ImageIcon icon : imagesIcons) {
            scaledImages[i++] = new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

        }

        return scaledImages;

    }

    public ImageIcon getImageIconAt(int i) {
        int j = 0;
        for (ImageIcon icon : imagesIcons) {
            if (j++ == i) {
                return icon;
            }
        }

        return null;
    }

    //retrieving all images from the backgrounds folder
    private LinkedHashSet<ImageIcon> getImages() {
        fileNames = new ArrayList<>();

        File[] defaultFolder = new File("src/BackgroundsFolder").listFiles();
        imagesIcons = new LinkedHashSet();

        for (File f : defaultFolder) {
            if (f.getName().toLowerCase().endsWith(".png")) {
                ImageIcon icon = new ImageIcon("src/BackgroundsFolder/" + f.getName());
                imagesIcons.add(icon);

                icon.setDescription(f.getName());
            }

        }

        return imagesIcons;
    }

    DefaultSelections() {
        diffLevel = new LinkedHashSet();
        dimensions = new LinkedHashSet();
        imagesIcons = this.getImages();
        for (String level : new String[]{"Easy", "Medium", "Hard"}) {
            diffLevel.add(level);
        }

        for (String dimension : screenDims) {
            dimensions.add(dimension);
        }

    }

}
