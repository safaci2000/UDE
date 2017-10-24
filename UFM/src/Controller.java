import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.SplitPane;
import javafx.scene.Node;

import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.util.Timer;
import java.util.TimerTask;


public class Controller {
    // Classes
    private Settings settings = Settings.getInstance();

    // FXML stuff
    @FXML private HBox masterMenu;
    @FXML private Button tgglPane2Bttn, tgglPane3Bttn, tgglPane4Bttn;
    @FXML private AnchorPane masterBase, base1, base2, base3, base4, splitPaneBottomAnchor;
    @FXML private SplitPane masterSplitPane, splitPaneTop, splitPaneBottom;
    @FXML private TabPane tabPane1, tabPane2, tabPane3, tabPane4;
    private static Node pane2Node, pane3Node, pane4Node, masterTopNode, masterBottomNode;
    private static boolean stateOfCol2 = true, stateOfCol3 = true,
                      stateOfCol4 = true, botomRowInserted = true;
    private static int bottomCount = 2, selectedTabView;
    private static final String homeDir = System.getProperty("user.home");;
    private static final Timer garbageTimer = new Timer();

    private TabView tabView;
    private Tab tab;


    @FXML void initialize() {
        assert masterBase != null : "fx:id=\"masterBase\" was not injected: check your FXML file 'window.fxml'.";
        assert masterMenu != null : "fx:id=\"masterMenu\" was not injected: check your FXML file 'window.fxml'.";
        assert tgglPane2Bttn != null : "fx:id=\"tgglPane2Bttn\" was not injected: check your FXML file 'window.fxml'.";
        assert tgglPane3Bttn != null : "fx:id=\"tgglPane3Bttn\" was not injected: check your FXML file 'window.fxml'.";
        assert tgglPane4Bttn != null : "fx:id=\"tgglPane4Bttn\" was not injected: check your FXML file 'window.fxml'.";
        assert splitPaneTop != null : "fx:id=\"splitPaneTop\" was not injected: check your FXML file 'window.fxml'.";
        assert base1 != null : "fx:id=\"base1\" was not injected: check your FXML file 'window.fxml'.";
        assert tabPane1 != null : "fx:id=\"tabPane1\" was not injected: check your FXML file 'window.fxml'.";
        assert base2 != null : "fx:id=\"base2\" was not injected: check your FXML file 'window.fxml'.";
        assert tabPane2 != null : "fx:id=\"tabPane2\" was not injected: check your FXML file 'window.fxml'.";
        assert splitPaneBottom != null : "fx:id=\"splitPaneBottom\" was not injected: check your FXML file 'window.fxml'.";
        assert base3 != null : "fx:id=\"base3\" was not injected: check your FXML file 'window.fxml'.";
        assert tabPane3 != null : "fx:id=\"tabPane3\" was not injected: check your FXML file 'window.fxml'.";
        assert base4 != null : "fx:id=\"base4\" was not injected: check your FXML file 'window.fxml'.";
        assert tabPane4 != null : "fx:id=\"tabPane4\" was not injected: check your FXML file 'window.fxml'.";

        masterTopNode = masterSplitPane.getItems().get(0);     // In slot "1" of masterSplitPane
        masterBottomNode = masterSplitPane.getItems().get(1);  //  In slot "2" of masterSplitPane
        pane2Node = splitPaneTop.getItems().get(1);            // In slot 2 of splitPaneTop
        pane3Node = splitPaneBottom.getItems().get(0);         // In slot "1" of splitPaneBottom
        pane4Node = splitPaneBottom.getItems().get(1);         //  In slot "2" of splitPaneBottom

        setClickEvents();
        setTimeTasks();
    }

    // Add new tab to location
    @FXML void newTab(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 1 && mouseEvent.isControlDown() ) {
            generateTab();
        }
    }

    // Make new tabs
    void generateTab() {
    // dasy chane tab to icon and back to edit?
        tab = new Tab(homeDir);
        tabView = new TabView();
        tabView.setDirLoc(homeDir);
        tabView.startIconGeneration();

        tab.setContent(tabView.getContent());

        if (selectedTabView == 1) {
            tabPane1.getTabs().add(tab);
            tabPane1.getSelectionModel().select(tab);
        } else if (selectedTabView == 2) {
            tabPane2.getTabs().add(tab);
            tabPane2.getSelectionModel().select(tab);
        } else if (selectedTabView == 3) {
            tabPane3.getTabs().add(tab);
            tabPane3.getSelectionModel().select(tab);
        } else if (selectedTabView == 4) {
            tabPane4.getTabs().add(tab);
            tabPane4.getSelectionModel().select(tab);
        }
    }

    @FXML void openLocation(DragEvent event) {
        // tabs will open folder locations in selected tab if drag in is folder
    }


    private void setClickEvents() {
        tgglPane2Bttn.setOnAction(col2 -> {
            if (stateOfCol2 == true) {
                splitPaneTop.getItems().remove(pane2Node);
                splitPaneTop.setDividerPositions(0.5f, 0.0f);
                stateOfCol2 = false;
                tgglPane2Bttn.setStyle("-fx-background-color: #880000;");
            } else {
                splitPaneTop.getItems().add(pane2Node);
                splitPaneTop.setDividerPositions(0.5f, 0.10f);
                stateOfCol2 = true;
                tgglPane2Bttn.setStyle("-fx-background-color: #008800;");
            }
        });
        tgglPane3Bttn.setOnAction(col3 -> {
            if (stateOfCol3 == true) {
                splitPaneBottom.getItems().remove(pane3Node);
                splitPaneBottom.setDividerPositions(0.0f, 0.10f);
                bottomCount -= 1;
                stateOfCol3 = false;
                horizontalBarState();
                tgglPane3Bttn.setStyle("-fx-background-color: #880000;");
            } else {
                splitPaneBottom.getItems().add(pane3Node);
                splitPaneBottom.setDividerPositions(0.5f, 0.10f);
                stateOfCol3 = true;
                bottomCount += 1;
                horizontalBarState();
                tgglPane3Bttn.setStyle("-fx-background-color: #008800;");
            }
        });
        tgglPane4Bttn.setOnAction(col4 -> {
            if (stateOfCol4 == true) {
                splitPaneBottom.getItems().remove(pane4Node);
                splitPaneBottom.setDividerPositions(0.5f, 0.0f);
                bottomCount -= 1;
                stateOfCol4 = false;
                horizontalBarState();
                tgglPane4Bttn.setStyle("-fx-background-color: #880000;");
            } else {
                splitPaneBottom.getItems().add(pane4Node);
                splitPaneBottom.setDividerPositions(0.5f, 0.10f);
                stateOfCol4 = true;
                bottomCount += 1;
                horizontalBarState();
                tgglPane4Bttn.setStyle("-fx-background-color: #008800;");
            }
        });
    }

    private void setTimeTasks() {
        garbageTimer.schedule(new TimerTask() { public void run()  {
            System.gc();
        }}, 1, 10500);
        // Need to properly close this.... upon system close
    }

    private void horizontalBarState() {
        if (bottomCount == 0) {
            masterSplitPane.getItems().remove(masterBottomNode);
            botomRowInserted = false;
        } else if (bottomCount == 1 && botomRowInserted != true) {
            masterSplitPane.getItems().add(masterBottomNode);
            botomRowInserted = true;
        }
    }

    // Used so that we can call proper add option for tabinsert
    @FXML void setSelectedTo1(MouseEvent event) { selectedTabView = 1; }
    @FXML void setSelectedTo2(MouseEvent event) { selectedTabView = 2; }
    @FXML void setSelectedTo3(MouseEvent event) { selectedTabView = 3; }
    @FXML void setSelectedTo4(MouseEvent event) { selectedTabView = 4; }
}
