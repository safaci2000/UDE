// Generates the actual image of the icon.
import java.util.Scanner;
import java.io.File;
import java.lang.Runtime;


public class Thumbnail {
    // Class objects
    private Settings settings = Settings.getInstance(); // Singleton Class
    private CleanPath cleaner = new CleanPath();  // Cleans thumbnail names
    private ThumbnailGenerator vidThumbnailGen = new ThumbnailGenerator();

    // Generics
    private Scanner scanner;
    private File file;
    private String thumbImg = "", line = "", temp = "";


    public String getIconImg() {
        return thumbImg;
    }

    public void setIconImg(String path, String name) {
        File[] highColorDirList = settings.getHighColorDirList();
        File[] localApplicationsDirList = settings.getLocalApplicationsDirList();
        temp = path.toLowerCase();

        if (temp.matches("^.*?(avi|webm|mpeg|mpg|mkv|flv|wmv|mp4).*$")) {
            thumbImg = vidThumbnailGen.generateThumb(path, cleaner.cleanThumbPth(name));
        } else if (temp.matches("^.*?(png|svg|jpg|jpeg|tiff).*$")) {
            thumbImg = path;
        } else if (temp.contains(".desktop")) {
            file = new File(path);
            try {
                scanner = new Scanner(file);
                while(scanner.hasNext()) {
                    line = scanner.nextLine();
                    if(line.contains("Icon=")) {
                        thumbImg = line;
                        break;
                    }
                }
            } catch(Throwable lineErr) {
                 System.out.println("Failed To Get Icon: " + lineErr);
            }
            thumbImg = thumbImg.replaceAll("Icon=","");  // Strip Icon= from the icon info

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
            // use getGenericIcon.py to get generic icon
            // String command = "python getGenericIcon.py " + path, shellOut = "";
            // try {
            //     scanner = new Scanner(Runtime.getRuntime().exec(command).getInputStream()).useDelimiter("\\A");
            //     shellOut = (scanner.hasNext()) ? scanner.next() : "";
            //     scanner = new Scanner(Runtime.getRuntime().exec("readlink -f " + shellOut).getInputStream()).useDelimiter("\\A");
            //     shellOut = (scanner.hasNext()) ? scanner.next() : "t";
            // } catch (Throwable e) {
            //     System.out.println("Exception Caught In Thumbnail.\n" + e);
            // }
            // thumbImg = shellOut;
            thumbImg = "resources/generic-theme/genericfile.png";
        }
    }
}
