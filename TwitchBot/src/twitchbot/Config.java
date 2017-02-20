package twitchbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import twitchbot.filehandler.*;

/**
 *
 * @author Darius
 */
public class Config {

    private static ArrayList<Viewer> viewers;

    private static TwitchBot bot;
    
    private static final int SECONDS = 1000; //in ms

    private static final String DIRECTORY_PATH = "";
    private static final String SETTINGS_PATH = DIRECTORY_PATH + "Settings.json";
    private static final String VIEWERS_PATH = DIRECTORY_PATH + "Viewers.json";
    private static final String COMMANDS_PATH = DIRECTORY_PATH + "Commands.json";
    
    private static ViewerHandler viewerHandler;
    private static SettingsHandler settingsHandler;
    
    public static Timer timer = new Timer();
    public static TimerTask checkQuit = new TimerTask() {
            @Override
            public void run() {
                try {
                    if(settingsHandler.shouldQuit())
                        stop();
                } catch (IOException ex) {
                    Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
                    stop();
                }
            }
        };

    public static void main(String args[]) {
        try {
            viewerHandler = new ViewerHandler(VIEWERS_PATH);
            viewers = viewerHandler.toViewerList();
            
            
            settingsHandler = new SettingsHandler(SETTINGS_PATH);
            timer.schedule(checkQuit, 5 * SECONDS); //checks to see if someone decided to stop the bot every 5 seconds
            
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            stop(true);
        }
    }

    public static void stop() {
        try {
            viewerHandler.quit();
            settingsHandler.quit();
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
    public static void stop(boolean error)
    {
        if(!error)
            stop();
        else
            System.exit(1);
    }

    public static ArrayList<Viewer> getViewerArray() {
        return viewers;
    }

    public static TwitchBot getBot() {
        return bot;
    }
}
