package it.dominator.ude.desktop;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import java.util.Scanner;
import java.io.File;


public class Icon {
    // Class objects
    private Thumbnail thumbnail = new Thumbnail();

    // Generics
    private VBox icon = new VBox();
    private Image img;
    private ImageView imgView = new ImageView();
    private Label title = new Label("");
    private Tooltip tooltip;
    private Process pb;    // Process runner
    private Scanner scanner;
    private File file;

    private String containsStr = System.getProperty("user.home")+ "/Desktop/",
                                 toLaunch = "", runCommand = "", thumbImg = "",
                                 toShellOut = "", toShellExec = "", line = "",
                                                         path = "", name = "";
    private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
    private double iconWidth = 150.0, iconHeight = 100.0;

    public VBox getIcon() {
        return icon;
    }

    public void createIcon(AnchorPane desktopArea, String pth, String nme,
                                                    int i, int x, int y) {
        icon.getStyleClass().add("vbox");
        path = pth;
        name = nme;

        // Creates it.dominator.ude.desktop.Icon parts for desktop and sets click actions
        getIconImg();
        mergeIconParts();
        setExecuteModel();
        setIconEvents(desktopArea);
    }

    private void getIconImg() {
        thumbnail.setIconImg(path, name);
        thumbImg = thumbnail.getIconImg();
        System.out.println("b4 insert " + thumbImg);

        img = new Image("file:" + thumbImg);
        imgView.setImage(img);
	       imgView.setFitWidth(iconWidth);  // Need these here to get grid properly.
        imgView.setFitHeight(iconHeight);
        imgView.setCache(true);  // Improves responsiveness when there are many images
    }

    private void mergeIconParts() {
        title.setMaxWidth(iconWidth);    // Based On Character Count??
        title.setText(name);
        tooltip = new Tooltip(name);
        tooltip.minHeight(800);
        tooltip.minWidth(800);
        Tooltip.install(icon, tooltip);
        icon.getChildren().addAll(imgView, title);  // Insert image and name to icon VBox container
    }

    private void setExecuteModel() {
        // Set file execution for .desktop files else use xdg-open
        toLaunch = path;
        if (path.contains(".desktop")) {
            file = new File(path);
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
        }
        else {
            runCommand = "xdg-open " + toLaunch;
        }
    }

    private void setIconEvents(AnchorPane desktopArea) {
        icon.addEventFilter(MouseEvent.MOUSE_PRESSED,
            new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    click.consume();
                    try {
                        System.out.println(runCommand);
                        pb = Runtime.getRuntime().exec(runCommand);
                    } catch(Throwable imgIOErr) {
                        System.out.println(imgIOErr);
                    }
                } else {
                    orgSceneX = click.getSceneX();
                    orgSceneY = click.getSceneY();
                    orgTranslateX = desktopArea.getLeftAnchor(icon);
                    orgTranslateY = desktopArea.getTopAnchor(icon);
                }
            }
        });

        icon.addEventFilter(MouseEvent.MOUSE_DRAGGED,
            new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent clck) {
                double offsetX = clck.getSceneX() - orgSceneX;
                double offsetY = clck.getSceneY() - orgSceneY;
                double newTranslateX = orgTranslateX + offsetX;
                double newTranslateY = orgTranslateY + offsetY;
                desktopArea.setLeftAnchor(icon, newTranslateX);
                desktopArea.setTopAnchor(icon, newTranslateY);
            }
        });
    }
}
