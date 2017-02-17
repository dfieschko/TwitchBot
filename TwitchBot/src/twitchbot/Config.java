
package twitchbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Darius
 */
public class Config {
    
    private static ArrayList<Viewer> viewers;
    
    private static TwitchBot bot;
    
    private static final String DIRECTORY_PATH = "";
    private static final String SETTINGS_PATH = DIRECTORY_PATH + "Settings.json";
    private static final String VIEWERS_PATH = DIRECTORY_PATH + "Viewers.json";
    
    private static FileHandler ViewerHandler;
    private static FileHandler SettingsHandler;
    
    private static String[] messages;
    
    public static void main(String args[])
    {
        try {
            ViewerHandler = new FileHandler(VIEWERS_PATH);
            SettingsHandler = new FileHandler(SETTINGS_PATH);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        viewers = ViewerHandler.getViewerList();
    }
    
    public static void stop()
    {
        ViewerHandler.set(viewers);
        System.exit(0);
    }
    
    public static ArrayList<Viewer> getViewerArray()
    {
        return viewers;
    }
    
    public static TwitchBot getBot()
    {
        return bot;
    }
}
