package twitchbot.filehandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * SettingsHandler is a FileHandler that is specifically made (usually) to handle one
 * settings file, which contains, as you might guess, settings, including one that determines
 * whether the TwitchBot should quit.
 * @author Darius
 */
public class SettingsHandler extends FileHandler {

    public static final int QUIT = 1;
    public static final int DONT_QUIT = 0;

    public static final String QUIT_TAG = "quit";
    public static final String PRIV_TAG = "priviliged";
    
    private String[] privileged;


    public SettingsHandler(String filepath) throws IOException, ParseException {
        super(filepath);
        object.replace(QUIT_TAG, QUIT, DONT_QUIT);
        privileged = getPrivileged();
    }

    public boolean shouldQuit() throws IOException {
        readInfo();
        if (Integer.parseInt(object.get(QUIT_TAG).toString()) == QUIT) {
            object.replace(QUIT_TAG, DONT_QUIT);
            return true;
        }
        return false;
    }
    
    public String[] getPrivileged()
    {
        try{
            return privileged;
        } catch(Exception e)
        {
            privileged = getPrivilegedList();
        }
        return privileged;
    }
    
    
    public String[] getPrivilegedList()
    {
        JSONArray jArr = (JSONArray) object.get(PRIV_TAG);
        String[] privs = new String[jArr.size()];
        for(int i = 0; i < jArr.size(); i++)
        {
            privs[i] = jArr.get(i).toString();
        }
        return privs;
    }

    @Override
    public void update()
    {
        
    }
}
