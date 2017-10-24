package it.dominator.ude.desktop;


import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class BackgroundManager  {
    protected final Stage backgroundStage = new Stage();
    protected Scene scene;

    // Private class to generate singleton
    private static BackgroundManager backgroundManager = new BackgroundManager( );
    // init
    private BackgroundManager() {
        // Setup window
        try {
         scene = new Scene(FXMLLoader.load(BackgroundManager.class.getResource("resources/BackgroundManagerWindow.fxml")), 800, 600);
        } catch (Exception e) {
          // If it fails, write the error message to screen
          e.printStackTrace();
        }
        backgroundStage.setScene(scene);
        backgroundStage.setTitle("Background");
        backgroundStage.setMinWidth(300);
        backgroundStage.setMinHeight(300);
    }

    // Functions
    // Geters
    public static BackgroundManager getInstance( ) { return backgroundManager; }
    // Setters

    // Others
    protected void showWindow() { backgroundStage.show(); }
}
