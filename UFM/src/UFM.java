import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.StandardWatchEventKinds;

import javafx.concurrent.Task;


public class UFM extends Application {
    private static Scene scene;
    private static Task fileWatcherThread = new Task<Void>() {
                    @Override public Void call() {
                        setWatcherOnThemeFile();
                    return null;
                    }};
    private static final Path path = FileSystems.getDefault().getPath("resources/");

    @Override public void start(Stage stage) {
        try {
            scene = new Scene(FXMLLoader.load(UFM.class.getResource("resources/window.fxml")));
            scene.getStylesheets().add("resources/Theme.css");
            stage.setTitle("UFM");
            stage.setScene(scene);
        } catch (Throwable startException) {
            System.out.println("\n\n\t\t:: UFM Start Failure  ::\n\n" +
                                                       startException);
        }
        new Thread(fileWatcherThread).start();
        stage.show();
    }

    private static void setWatcherOnThemeFile() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                final WatchKey wk = watchService.take();
                for (WatchEvent<?> event : wk.pollEvents()) {
                    //we only register "ENTRY_MODIFY" so the context is always a Path.
                    final Path changed = (Path) event.context();
                    System.out.println(changed);
                    if (changed.endsWith("Theme.css")) {
                        System.out.println("Theme.css has changed...reloading stylesheet.");
                        scene.getStylesheets().clear();
                        scene.getStylesheets().add("resources/Theme.css");
                    }
                }
                boolean valid = wk.reset();
                if (!valid)
                    System.out.println("Watch Key has been reset...");
            }
        } catch (Exception e) { /*Thrown to void*/ }
    }
    public static void main(String[] args) { launch(args); }
}
