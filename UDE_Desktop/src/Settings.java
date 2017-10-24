// ::  NOTES  ::
//  Read file for Icon= to get the desktop's icon. Then look for it in:
//  Steam --> ~/.local/share/icons/hicolor/256x256/apps/.
//        --> ~/.local/share/applications/
//  Other Icons --> /usr/share/icons/<themeName>/<size>
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Scanner;


public class Settings {
    // Class objects
    protected final Stage settingsStage = new Stage();
    private BackgroundManager backgroundManager = BackgroundManager.getInstance(); // Singleton Class backgroundManager

    // Generics
    protected Scene scene;
    private File thumbDirPth = new File("thumbs/"),
                 desktopDir = new File(System.getProperty("user.home") + "/Desktop/"),
                 highColorDir = new File(System.getProperty("user.home") +
                                         "/.local/share/icons/hicolor/256x256/apps/"),
                 localApplicationsDir = new File(System.getProperty("user.home") +
                                                 "/.local/share/applications/");
    private File[] desktopItems = desktopDir.listFiles(),  // Gets files in desktop folder
                   thumbsList = thumbDirPth.listFiles(),   // Gets video thumbnail list
                   highColorDirList = highColorDir.listFiles(),
                   localApplicationsDirList = localApplicationsDir.listFiles();

    private String imgPath, line, defaultBG = "resources/generic-theme/background.png";
    private Image backgroundImg;
    private ImageView bgView;
    private Scanner scanner;
    private File file = new File("resources/config.txt");

    // Private class to generate singleton
    private static Settings settings = new Settings( );
    // init
    private Settings() {
        // Setup window
        try {
         scene = new Scene(FXMLLoader.load(Settings.class.getResource("resources/SettingsManagerWindow.fxml")), 800, 600);
        } catch (Exception e) {
          // If it fails, write the error message to screen
          e.printStackTrace();
        }
        settingsStage.setScene(scene);
        settingsStage.setTitle("Settings");
        settingsStage.setMinWidth(300);
        settingsStage.setMinHeight(300);

        // load settings info from file by reading line by line
        try {
            scanner = new Scanner(file);
            while(scanner.hasNext()) {
                line = scanner.nextLine();
                if(line.contains("Background=")) {
                    line = line.replaceAll("Background=","");
                     if (line.isEmpty()) {
                        backgroundImg = new Image(defaultBG);
                    } else {
                        backgroundImg = new Image("file://" + line);
                        break;
                    }
                }
            }
        } catch(Throwable lineErr) {
             backgroundImg = new Image(defaultBG);
             System.out.println("Failed To Get Config Info: " + lineErr);
        }
    }

    // Functions
    // Geters
    public static Settings getInstance( ) { return settings; }
    protected Image getBackgroundInfo() { return backgroundImg; }
    protected String getImgPath() { return imgPath; }
    protected File getThumbDirPth() { return thumbDirPth; }
    protected File[] getDesktopItems() { return desktopItems; }
    protected File[] getThumbsList() { return thumbsList; }
    protected File[] getHighColorDirList() { return highColorDirList; }
    protected File[] getLocalApplicationsDirList() { return localApplicationsDirList; }

    // Setters
    protected void setsettingsImgView(ImageView imgVw) { bgView = imgVw; }
    protected void setBackgroundInfo(String file) {
        imgPath = file;
        bgView.setImage(new Image("file://" + file));
    }

    // Others
    protected void callBackgroundMngr() {
        showWindow();
        backgroundManager.showWindow();
    }

    protected void showWindow() { settingsStage.show(); }
}
