import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.control.MenuItem;
import javafx.event.EventHandler;


public class Controller {
    // Class objects
    private Settings settings = Settings.getInstance(); // Singleton Class settings

    // Fxml objects
    @FXML private AnchorPane desktopArea;
    @FXML private ImageView backgroundImgView;

    // Generics
    private int x = 0, y = 0, found = 0;  // x & y for grid generation
    private ContextMenu contextMenu = new ContextMenu();
    final private MenuItem settingsMenuItm = new MenuItem("Settings"),
                     chngBckgrndMenuItem = new MenuItem("Desktop Background");

    @FXML void initialize() {
        // Create ContextMenu
        settingsMenuItm.setOnAction(e -> { settings.showWindow(); });
        chngBckgrndMenuItem.setOnAction(e -> { settings.callBackgroundMngr(); });

        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(settingsMenuItm, chngBckgrndMenuItem);

        // When user right-click on desktop
        desktopArea.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
           @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(desktopArea, event.getScreenX(), event.getScreenY());
            }
        });

        settings.setsettingsImgView(backgroundImgView);
        backgroundImgView.setImage(settings.getBackgroundInfo());
	       backgroundImgView.setFitWidth(1920); // Scale to view Note: need to do based on system parameters
        backgroundImgView.setFitHeight(1080);

        addIconTiles();
    }

    // set icons to desktop view
    // Possibly too many calls....
    void addIconTiles() {
			     for (int i=0; i<settings.getDesktopItems().length; i++) {
            Icon icon = new Icon();
            final String fileName = "" + settings.getDesktopItems()[i].getName(),
                         file = "" + settings.getDesktopItems()[i];

            // Create Icon and position icon on grid according to parameters
            // passes desktop, path, name, index, and x and y coridinits
            icon.createIcon(desktopArea, file, fileName, i, x, y);
            desktopArea.getChildren().add(icon.getIcon());
            desktopArea.setLeftAnchor(icon.getIcon(), (double) x);
            desktopArea.setTopAnchor(icon.getIcon(), (double) y);

            // Grid management
            x += 160;
            if (x >= 1600) {
               x = 0;
               y += 150;
            }
        }
    }
}
