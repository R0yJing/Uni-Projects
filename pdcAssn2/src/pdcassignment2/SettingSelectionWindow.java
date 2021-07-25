/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.System.out;
import java.util.LinkedHashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Roy
 */
public class SettingSelectionWindow extends JPanel implements Observer {

    private JLabel promptSelectionLbl = new JLabel("Select settings ...");
    private JFrame parentFrame = new JFrame();
    private JTable settingsTable;
    private JScrollPane scrollPane;
    private JButton goBack = new JButton("Go Back");
    private JComboBox diffSelection;
    private JComboBox screenDimSelection;
    private JButton confirmSelectionBtn = new JButton("Confirm");
    private JComboBox prefSelection = new JComboBox();
    private boolean addedThis;

    public JFrame getParentFrame() {
        return parentFrame;
    }

    public JButton getConfirmSelectionBtn() {
        return confirmSelectionBtn;
    }
    DefaultSelections defaultSettings = new DefaultSelections();

    private GridBagConstraints gbc = new GridBagConstraints();
    private SettingSelectionController controller;

    public void setController(SettingSelectionController controller) {
        this.controller = controller;
    }

    private void addAt(int x, int y, JComponent comp, int... width) {

        gbc.gridx = x;
        gbc.gridy = y;
        if (width.length != 0) {
            gbc.gridwidth = width[0];
            if (width.length > 1) {
                gbc.gridheight = width[1];

            }
        }
        this.add(comp, gbc);

    }

    SettingSelectionWindow() {
        this.setLayout(new GridBagLayout());

    }

    public void unpackDefaultSelections(DefaultSelections selections) {
        diffSelection = new JComboBox();
        screenDimSelection = new JComboBox();
        defaultSettings = selections;

        for (String diffLevel : selections.diffLevel) {
            diffSelection.addItem(diffLevel);
        }

        for (String dim : selections.dimensions) {
            screenDimSelection.addItem(dim);

        }

        prefSelection.setSize(new Dimension(1000, 1000));

        prefSelection = new JComboBox(selections.getScaledImage());

    }

    public void bringToNewPreferenceSelection() {

        removeAll();

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Select settings..."), BorderLayout.NORTH);

        screenDimSelection.setSelectedIndex(0);

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        JPanel midPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        JPanel westPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());

        westPanel.add(this.screenDimSelection, BorderLayout.NORTH);
        westPanel.add(new JLabel("Select a screen dimension"), BorderLayout.SOUTH);

        midPanel.add(new JLabel("Select a difficulty"), BorderLayout.NORTH);
        midPanel.add(this.diffSelection, BorderLayout.SOUTH);

        eastPanel.add(new JLabel("Select a background image"), BorderLayout.NORTH);
        eastPanel.add(this.prefSelection, BorderLayout.SOUTH);

        this.add(westPanel, BorderLayout.WEST);
        this.add(midPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);

    }

    public void displayPrevSettings() {

        add(prefSelection);

    }

    public void setPreviousPrefs(LinkedHashSet<String> prefStrings) {
        String[] columns = {"Screen Dimension", "Game Difficulty", "Background Image"};

        String[][] data = new String[prefStrings.size()][];
        String[] rows = prefStrings.toArray(new String[prefStrings.size()]);

        for (int i = 0; i < prefStrings.size(); i++) {
            data[i] = rows[i].split("\\|");

        }
        settingsTable = new NonEditableTable(data, columns);
        settingsTable.getTableHeader().setReorderingAllowed(false);
        settingsTable.setLocation(5, 5);
        settingsTable.setSelectionModel(new NonEditableTableModel());
        scrollPane = new JScrollPane(settingsTable);
        settingsTable.addMouseListener(new MouseAdapter() {
            private JButton btn = SettingSelectionWindow.this.confirmSelectionBtn;

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                btn.setEnabled(true);
            }

        });

    }

    class NonEditableTable extends JTable {

        private static final long serialVersionUID = 1L;

        NonEditableTable(Object[][] data, String[] columns) {
            super(data, columns);
        }

        public boolean isCellEditable(int row, int column) {
            return false;
        }
    ;

    }
    
    class NonEditableTableModel extends DefaultListSelectionModel {

        public NonEditableTableModel() {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public void removeSelectionInterval(int index0, int index1) {
        }
    }

    public void update(Observable o, Object arg) {

        //if arg is null then it means we have previous displayed this
        //panel but user has decided to "go back" and then enter again.
        if (o instanceof SettingSelectionModel && arg == null) {
            parentFrame.setVisible(true);
            return;

        }

        this.setLayout(new BorderLayout());

        if (arg instanceof DefaultSelections) {
            confirmSelectionBtn.setEnabled(true);

            this.unpackDefaultSelections((DefaultSelections) arg);
            this.bringToNewPreferenceSelection();

        } else if (arg instanceof LinkedHashSet) {
            out.println("arg linkedhash");
            confirmSelectionBtn.setEnabled(false);

            setPreviousPrefs((LinkedHashSet<String>) arg);
            this.bringToOldPreferenceSelection();
        }

        //only add the go back button when user can go back...
        //User cannot go back when for instance he doesn't have
        //a previous game record saved in the database.
        if (arg instanceof Boolean && (boolean) arg) {
            JPanel confirmAndGoBack = new JPanel();

            confirmAndGoBack.add(goBack);
            confirmAndGoBack.add(this.confirmSelectionBtn);

            goBack.addActionListener(controller);
            this.add(confirmAndGoBack, BorderLayout.SOUTH);

        } else {

            this.add(this.confirmSelectionBtn, BorderLayout.SOUTH);
        }
        if (!addedThis) {
            addedThis = true;
            parentFrame.add(this);
            confirmSelectionBtn.addActionListener(controller);

        }

        parentFrame.setPreferredSize(new Dimension(1000, 250));
        parentFrame.pack();
        parentFrame.setResizable(false);
        parentFrame.setVisible(true);

    }

    private void bringToOldPreferenceSelection() {

        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(new JLabel("Select from history settings..."), BorderLayout.NORTH);

        this.add(this.scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    //construct a new Preferences object based on User's selection.
    public Preferences getSelection() {

        TableModel model = settingsTable.getModel();;

        int row = settingsTable.getSelectedRow();

        String dimString = (String) model.getValueAt(row, 0);
        String diffLevel = (String) model.getValueAt(row, 1);

        String imgName = (String) model.getValueAt(row, 2);

        return new Preferences(dimString, imgName, diffLevel);

    }

    public Preferences getCustomisedSelection() {
        ImageIcon imgName = (defaultSettings.getImageIconAt(this.prefSelection.getSelectedIndex()));
        String diffLevel = (String) this.diffSelection.getSelectedItem();
        String dimensions = (String) this.screenDimSelection.getSelectedItem();
        String[] dim = dimensions.split("x| ");
        StringTokenizer tokenizer = new StringTokenizer(dimensions, "x| ");
        String w = tokenizer.nextToken(), h = tokenizer.nextToken();

        int height = Integer.parseInt(h);
        int width = Integer.parseInt(w);

        return new Preferences(width, height, imgName, diffLevel);

    }

    public JButton getGoBack() {
        return goBack;
    }

}
