
package twitchbot.filehandler;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Darius
 */
public class SettingsHandler extends FileHandler{
    
    public static final int QUIT = 1;
    
    
    private String[] commands;
    
    public SettingsHandler(String filepath) throws IOException, ParseException
    {
        super(filepath);
        JSONArray commandArray = (JSONArray) object.get("commands");
        for(int i = 0; i < array.size(); i++)
        {
            commands[i] = commandArray.get(i).toString();
        }
    }
    
    public String[] getCommands()
    {
        return commands;
    }
    
}
