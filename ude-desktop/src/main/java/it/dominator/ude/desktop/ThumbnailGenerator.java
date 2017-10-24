package it.dominator.ude.desktop;// Generate Thumbnails for videos with ffmpegthumbnailer

public class ThumbnailGenerator {
    private Process pb;    // Process runner
    private String thumbName, genCommand;

    public String generateThumb(String file, String thumb) {
        thumbName = thumb;
        genCommand = "ffmpegthumbnailer -w -t='00:30:00' -c png -i " +
                     file + " -s 300 -o " + "thumbs/" + thumbName + ".png";
        try {
            pb = Runtime.getRuntime().exec(genCommand);
            pb.waitFor();
        } catch(Throwable imgIOErr) {
            System.out.println(imgIOErr);
        }
        thumbName = "./thumbs/" + thumbName + ".png";
    return thumbName;
    }
}
