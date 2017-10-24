package it.dominator.ude.desktop;// Clear file of special characters and spaces
import java.io.File;

public class CleanPath {
    private String preLine = "";

    public String cleanThumbPth(String tmp) {
        File f = new File(tmp);

        // if it's a directory, don't remove the extention
        if (f.isDirectory()) System.out.println("This is a a directory.");
            String name = f.getName();
            final int lastPeriodPos = name.lastIndexOf('.');
            if (lastPeriodPos <= 0) {
                preLine = "" + name;
        } else {
            // Remove the last period and everything after it
            File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
            preLine = "" + renamed;
            preLine = preLine.replaceAll("[^A-Za-z]+", "");
            preLine = preLine.replaceAll("\\s+","");
        }
    return preLine;
    }
}
