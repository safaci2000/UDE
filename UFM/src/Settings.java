 import java.util.prefs.Preferences;


public class Settings {
    private static Settings settings = new Settings();

    private Settings() {

    }

    public static Settings getInstance() { return settings; }

    


}
