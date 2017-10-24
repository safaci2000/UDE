package it.dominator.ufm;// Generates the actual image of the icon.
import java.util.Scanner;
import java.io.File;


public class Thumbnail {
    // Class objects
    private CleanPath cleaner = new CleanPath();  // Cleans thumbnail names
    private ThumbnailGenerator vidThumbnailGen = new ThumbnailGenerator();

    // Generics
    private static File highColorDir = new File(System.getProperty("user.home") +
                                         "/.local/share/icons/hicolor/256x256/apps/"),
                 localApplicationsDir = new File(System.getProperty("user.home") +
                                                 "/.local/share/applications/");
    private static File[] highColorDirList = highColorDir.listFiles(),
                   localApplicationsDirList = localApplicationsDir.listFiles();

    // Generics
    private static Scanner scanner;
    private static File file;
    private static String thumbImg = "", line = "", temp = "";


    public String getIconImg() {
        return thumbImg;
    }

    public void setIconImg(String path, String name) {
        temp = path.toLowerCase();

        if (temp.matches("^.*?(avi|webm|mpeg|mpg|mkv|flv|wmv|mp4).*$")) {
            thumbImg = vidThumbnailGen.generateThumb(path, cleaner.cleanThumbPth(name));
        } else if (temp.matches("^.*?(png|svg|jpg|jpeg|tiff|gif).*$")) {
            thumbImg = path;
        } else if (temp.contains(".desktop")) {
            file = new File(path);
            try {
                scanner = new Scanner(file);
                while(scanner.hasNext()) {
                    line = scanner.nextLine();
                    if(line.contains("it.dominator.ufm.Icon=")) {
                        thumbImg = line;
                        break;
                    }
                }
            } catch(Throwable lineErr) {
                 System.out.println("Failed To Get it.dominator.ufm.Icon: " + lineErr);
            }
            thumbImg = thumbImg.replaceAll("Icon=","");  // Strip it.dominator.ufm.Icon= from the icon info

            // Steam Icons
            if (thumbImg.contains("steam_icon")) {
                for (int i=0; i<highColorDirList.length; i++) {
                    if (highColorDirList[i].toString().contains(thumbImg)) {
                        thumbImg = "" + highColorDirList[i];
                    }
                }
                for (int i=0; i<localApplicationsDirList.length; i++) {
                    if (localApplicationsDirList[i].toString().contains(thumbImg)) {
                        thumbImg = "" + localApplicationsDirList[i];
                    }
                }
            }
        } else {
            thumbImg = "resources/generic-theme/systemFile.png";
        }
    }
}
