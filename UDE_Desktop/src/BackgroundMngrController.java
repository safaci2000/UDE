import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.DirectoryChooser;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import java.io.File;
import javafx.concurrent.Task;


public class BackgroundMngrController {
    // Class objects
    private Settings settings = Settings.getInstance(); // Singleton Class settings

    // Fxml objects
    @FXML private Label dir;
    @FXML private TextField txtDirPath;
    @FXML private Button clear;
    @FXML private TilePane tilePane;

    // Generics
	 		private DirectoryChooser folderChooser = new DirectoryChooser();
    private Image pth = new Image(".");
    private Image previewPth = new Image(".");
    private ImageView imgView = new ImageView(pth);
    private String textAreaPth = "";
    private File directory;
    private File[] fileList;


    @FXML void initialize() {
        // Initialize any logic here: all @FXML variables will have been injected
    }

    @FXML void onEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            textAreaPth = txtDirPath.getText();
            System.out.println(textAreaPth);
            newDir();
        }
    }

    @FXML void setNewDir(MouseEvent event) {
        newDir();
    }

    // Scan selected dir
    public void newDir() {
        tilePane.getChildren().clear();
        Stage stage = new Stage();
        if (textAreaPth != "")
            directory = new File(textAreaPth);
        else {
            directory = folderChooser.showDialog(stage);
            if (directory != null) {
                System.out.println("Directory: " + directory);
             }
        }

        fileList = directory.listFiles();
        txtDirPath.setText("" + directory);
			     for (int i=0; i<fileList.length; i++) {
        					imgView = new ImageView();
 					       imgView.setFitWidth(300); // Need these here to get grid properly.
						       imgView.setFitHeight(200);
					 		     tilePane.getChildren().add(imgView);
        }

        Task getDir = new Task<Void>() {
            @Override public Void call() {
              newDir2();
              return null;
            }};
        new Thread(getDir).start();
    }
    public void newDir2() {
        int j = 0; // Used to properly insert to grid when there are files other than an image
			     for (int i=0; i<fileList.length; i++) {
            String path = "" + fileList[i];
            if (fileList[i].getName().contains(".png") || fileList[i].getName().contains(".jpg")||
                fileList[i].getName().contains(".gif") || fileList[i].getName().contains(".jpeg")) {
			             String title = "" + fileList[i];
                pth = new Image("file://" + fileList[i]);
                ImageView view = (ImageView) (tilePane.getChildren().get(j));

                view.setImage(pth);
                view.setCache(true);
												    final ImageView imgViewPoped =  new ImageView("file://" + fileList[i]);
			             // image click actions
                view.setOnMouseClicked(mouse -> {
                    MouseButton button = mouse.getButton();
								            if (mouse.getClickCount() == 2 && button == MouseButton.PRIMARY && !mouse.isConsumed()) {
								                mouse.consume();
                        txtDirPath.setText(path);
                        settings.setBackgroundInfo(path);
       								     } else if (button == MouseButton.SECONDARY) {
    			                 displayImg(imgViewPoped, title);
                    }
			             });
                j++;
								    } else {
                   System.out.println("Not an image file.");
            }
        }
    }

    public void displayImg(ImageView imgViewPoped, String title) {
        Stage popOut = new Stage();
        Pane pane = new Pane();
        imgViewPoped.setLayoutX(0);
        imgViewPoped.setLayoutY(0);
        imgViewPoped.fitWidthProperty().bind(pane.widthProperty());
        imgViewPoped.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(imgViewPoped);
        Scene scene = new Scene(pane, 1280, 900);
        popOut.setTitle(title);
        popOut.setScene(scene);
        popOut.show();
    }

    @FXML void clearBttnClick(ActionEvent event) {
        tilePane.getChildren().clear();
        tilePane.getChildren().addAll(dir);
        txtDirPath.setText("");
    }
}
