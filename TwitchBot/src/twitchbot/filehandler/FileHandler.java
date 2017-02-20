package twitchbot.filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Parses JSON into stuff. Uses json-simple
 * https://github.com/fangyidong/json-simple
 *
 * @author Darius Fieschko
 */
public abstract class FileHandler {

    protected File file;
    protected JSONArray jArray; //contains everything in the file.
                                //edit jArray to change things, and quit() and
                                //update() will write jArray to the file.

    /**
     * Constructor. Loads information from a JSON file, if it exists. If the
     * file does not exist, an empty .json is created.
     *
     * @param filepath the location of the file, as a String
     * @throws FileNotFoundException the file was not found despite
     * createNewFile() saying it's there
     * @throws IOException error reading the file
     * @throws ParseException file is not formatted for JSON correctly
     */
    protected FileHandler(String filepath) throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        file = new File(filepath);
        if (file.createNewFile()) {
            return;
        }
        Object obj = parser.parse(new FileReader(file));
        jArray = (JSONArray) obj;
    }
    
    protected abstract void update();

    /**
     * @return file path
     */
    public String getFilepath() {
        return file.getAbsolutePath();
    }

    /**
     * Wipes the file, making it empty/blank.
     *
     * @throws FileNotFoundException file not found
     */
    protected void wipeFile() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(file);
        pw.close();
    }

    /**
     * Updates file to match current data stored in the handler. It does this by
     * wiping the file and rewriting it based on the JSONArray stored in this
     * object.
     *
     * @throws IOException
     */
    public void quit() throws IOException {
        update();
        wipeFile();
        FileWriter writer = new FileWriter(getFilepath());
        writer.write(jArray.toJSONString());
        writer.flush();
    }

    public void readInfo() {
        try {
            JSONParser parser = new JSONParser();
            file = new File(getFilepath());
            Object obj = parser.parse(new FileReader(file));
            jArray = (JSONArray) obj;
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SettingsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the File object associated with this FileHandler.
     */
    public File getFile() {
        return file;
    }

    /**
     * @return the JSONObject associated with this FileHandler.
     */
    public JSONArray getJSONArray() {
        return jArray;
    }

}
