
package twitchbot;

import twitchbot.filehandler.FileHandler;
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
    private static final String COMMANDS_PATH = DIRECTORY_PATH + "Commands.json";

    
    public static void main(String args[])
    {

        
    }
    
    public static void stop()
    {
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
