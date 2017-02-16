
package twitchbot;

import java.util.ArrayList;

/**
 *
 * @author Darius
 */
public class Config {
    
    private static ArrayList<Viewer> viewers;
    
    private static TwitchBot bot;
    
    public static void main(String args[])
    {
        //use args to completely initialize bot
        //load settings from file
    }
    public static ArrayList<Viewer> getViewerArray()
    {
        return viewers;
    }
    
    public TwitchBot getBot()
    {
        return bot;
    }
}
