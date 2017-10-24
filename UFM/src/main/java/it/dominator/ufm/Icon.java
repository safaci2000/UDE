package it.dominator.ufm;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import java.util.Scanner;
import java.io.File;

import javafx.scene.layout.TilePane;
import javafx.scene.control.TextField;


public class Icon extends TabView {
    // Class objects
    private Thumbnail thumbnail = new Thumbnail();

    // Generics
    private TilePane tilePane = new TilePane();
    private TextField txtDirPath = new TextField();

    private VBox icon = new VBox();
    private Image img;
    private ImageView imgView = new ImageView();
    private Label title = new Label("");
    private Tooltip tooltip;
    private Process pb;    // Process runner
    private Scanner scanner;
    private File file;
    private boolean isDir = false, isImage = false;

    private String containsStr = System.getProperty("user.home")+ "/Desktop/",
                                 toLaunch = "", runCommand = "", thumbImg = "",
                                 toShellOut = "", toShellExec = "", line = "",
                                                         path = "", name = "";
    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;

    public VBox getIcon() {
        return icon;
    }

    public void createIcon(String pth, String nme) {
        icon.getStyleClass().add("icon");

        path = pth;
        name = nme;

        // Creates it.dominator.ufm.Icon parts for desktop and sets click actions
        getIconImg();
        mergeIconParts();
        setExecuteModel();
        setIconEvents();
    }

    private void getIconImg() {
        thumbnail.setIconImg(path, name);
        thumbImg = thumbnail.getIconImg();
        if (thumbImg.contains("systemFile.png")) {
           if (name.matches("^.*?(doc|docx|odf).*$")) {
               imgView.getStyleClass().add("icon-document");
           } else if (name.matches("^.*?(pps|ppt|pptx).*$")) {
               imgView.getStyleClass().add("icon-presentation");
           } else if (name.matches("^.*?(xls|xlsx|csv).*$")) {
               imgView.getStyleClass().add("icon-spreadsheet");
           } else if (name.matches("^.*?(mp2|mp3|ogg).*$")) {
               imgView.getStyleClass().add("icon-music");
           } else if (name.matches("^.*?(txt|sh|link).*$")) {
               imgView.getStyleClass().add("icon-text");
           } else if (name.matches("^.*?(run|bin|jar).*$")) {
               imgView.getStyleClass().add("icon-bin");
           } else if (name.matches("^.*?(zip|7zip|rar|tar|tar.gz|gz).*$")) {
               imgView.getStyleClass().add("icon-compressed");
           } else if (name.matches("^.*?(html|xml|htm|css).*$")) {
               imgView.getStyleClass().add("icon-web");
           } else {
               imgView.getStyleClass().add("icon-folder");
           }
        } else if (name.matches("^.*?(png|svg|jpg|jpeg|tiff|gif).*$")) {
            placeImage();
            isImage = true;
        } else { placeImage(); }
        imgView.setCache(true);         // Improves responsiveness when there are many images
    }

    private void placeImage() {
            img = new Image("file:" + thumbImg);
            imgView.setImage(img);
	           imgView.setFitWidth(96.0);
            imgView.setFitHeight(96.0);
    }

    private void mergeIconParts() {
        title.setMaxWidth(150);         // Based On Character Count??
        title.setText(name);
        tooltip = new Tooltip(name);
        tooltip.minHeight(800);
        tooltip.minWidth(800);
        Tooltip.install(icon, tooltip);
        icon.getChildren().addAll(imgView, title);  // Insert image and name to icon VBox container
    }

    private void setExecuteModel() {
        // Set file execution for .desktop files and directories else use xdg-open
        toLaunch = path;
        file = new File(path);
        if (path.contains(".desktop")) {
            try {
                scanner = new Scanner(file);
                while(scanner.hasNext()) {
                    line = scanner.nextLine();
                    if(line.contains("Exec=")) {
                        toShellOut = line;
                        break;
                    }
                }
            } catch(Throwable lineErr) {
                 System.out.println("Failed To Get Exec Info: " + lineErr);
            }

            if (toShellOut.contains("TryExec="))
                toShellOut = toShellOut.replaceAll("TryExec=","");
            else if (toShellOut.contains("Exec="))
                toShellOut = toShellOut.replaceAll("Exec=","");
            runCommand = toShellOut;
        } else if (!file.isFile()) {
            isDir = true;
        }
        else {
            runCommand = "xdg-open " + toLaunch;
        }
    }

    private void setIconEvents() {
        icon.addEventFilter(MouseEvent.MOUSE_PRESSED,
            new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    click.consume();
                    if (isDir == true) {
                        System.out.println(path);
                        setTabView(path, tilePane, txtDirPath);
                    } else if (isImage == true) {
                        openImage();
                    } else {
                        try {
                            System.out.println(runCommand);
                            pb = Runtime.getRuntime().exec(runCommand);
                        } catch(Throwable imgIOErr) {
                            System.out.println(imgIOErr);
                        }
                    }
                }
            }
        });
    }

    private void openImage() {
        Stage imagStage = new Stage();
        Pane pane = new Pane();
        ImageView iView = new ImageView(img);
        pane.getChildren().add(iView);
        iView.setLayoutX(0);
        iView.setLayoutY(0);
        iView.fitWidthProperty().bind(pane.widthProperty());
        iView.fitHeightProperty().bind(pane.heightProperty());
        iView.setPreserveRatio(true);
        Scene scene = new Scene(pane, 800, 800);
        imagStage.setTitle("" + img);
        imagStage.setScene(scene);
        imagStage.show();
    }

    protected void setWorkingTab(TilePane tP, TextField tF) {
        this.tilePane = tP;
        this.txtDirPath = tF;
    }
}
