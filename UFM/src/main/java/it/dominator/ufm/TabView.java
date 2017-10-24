package it.dominator.ufm;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Tab;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.io.File;


public class TabView extends Controller {
    // Tab Info
    private AnchorPane anchorPane = new AnchorPane();
    private ScrollPane scrollPane = new ScrollPane();
    private TilePane tilePane = new TilePane();
    private TextField txtDirPath = new TextField();
    private VBox iconVBox;

    private ImageView imgView;
    private Label dir, fileName;
    private static File[] fileList;
    private static File directory;
    private static String loadPath;
    private static final String homeDir = System.getProperty("user.home");;
    private static boolean showHidden = false;
    private Tab tab = new Tab();

    public TabView() {
        anchorPane.getChildren().addAll(txtDirPath, scrollPane);
        anchorPane.setTopAnchor(txtDirPath, 0.0);
        anchorPane.setLeftAnchor(txtDirPath, 0.0);
        anchorPane.setRightAnchor(txtDirPath, 0.0);
        anchorPane.setTopAnchor(scrollPane, 30.0);
        anchorPane.setLeftAnchor(scrollPane, 0.0);
        anchorPane.setRightAnchor(scrollPane, 0.0);
        anchorPane.setBottomAnchor(scrollPane, 0.0);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(tilePane);
        txtDirPath.setText(homeDir);
        txtDirPath.getStyleClass().add("fileURLField");
        tilePane.getStyleClass().add("tile-pane");
        tilePane.setMaxWidth(Region.USE_PREF_SIZE);
    }

    public void startIconGeneration() {
        generateIcons();
    }

    public AnchorPane getContent() { return anchorPane; }
    public void setDirLoc(String loadPath) { this.loadPath = loadPath; }

    private void generateIcons() {
        directory = new File(txtDirPath.getText());
        fileList = directory.listFiles();
			     for (int i=0; i<fileList.length; i++) {
            final Icon icon = new Icon();
             icon.setWorkingTab(tilePane, txtDirPath);
            final String fileName = "" + fileList[i].getName(),
                         file = "" + fileList[i];
            if (showHidden != true) {
                if (fileName.charAt(0) != '.') {
                    icon.createIcon(file, fileName);
         		         tilePane.getChildren().add(icon.getIcon());
                }
            } else {
                icon.createIcon(file, fileName);
     		         tilePane.getChildren().add(icon.getIcon());
            }
        }
    }

    protected void setTabView(String path, TilePane tP, TextField tF) {
        this.tilePane = tP;
        this.txtDirPath = tF;
        tilePane.getChildren().clear();
        txtDirPath.setText(path);
        generateIcons();
    }

    protected void setShowHiddenState(boolean state) {
        showHidden = state;
    }
}
