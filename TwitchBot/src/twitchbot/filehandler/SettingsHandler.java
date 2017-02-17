package twitchbot.filehandler;

import java.io.IOException;
import org.json.simple.JSONArray;
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


    public SettingsHandler(String filepath) throws IOException, ParseException {
        super(filepath);
        object.replace(QUIT_TAG, QUIT, DONT_QUIT);
    }

    public boolean shouldQuit() throws IOException {
        updateInfo();
        if (Integer.parseInt(object.get(QUIT_TAG).toString()) == QUIT) {
            object.replace(QUIT_TAG, DONT_QUIT);
            update();
            return true;
        }
        return false;
    }

}
