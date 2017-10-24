import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class UDEDesktop extends Application {
    @Override
    public void start(final Stage stage) throws Exception {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        Scene scene = new Scene(FXMLLoader.load(UDEDesktop.class.getResource("resources/UDEDesktopWindow.fxml")), width, height);
        scene.setFill(null);
        scene.getStylesheets().add("resources/stylesheet.css");
        stage.setScene(scene);
        // stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("UDEDesktop");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.show();
        stage.toBack();
    }
    // needed because you know... it's java.
    public static void main(String[] args) { launch(args); }
}
