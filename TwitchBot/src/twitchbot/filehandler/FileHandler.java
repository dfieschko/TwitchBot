package twitchbot.filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Parses JSON into stuff. 
 * Uses json-simple https://github.com/fangyidong/json-simple
 
 * @author Darius Fieschko
 */
public class FileHandler {

    protected File file;
    protected JSONArray array;
    protected JSONObject object;
    
    /**
     * Constructor.
     * Loads information from a JSON file, if it exists. If the file does not
     * exist, an empty .json is created.
     * 
     * @param filepath the location of the file, as a String
     * @throws FileNotFoundException the file was not found despite createNewFile() saying it's there
     * @throws IOException error reading the file
     * @throws ParseException file is not formatted for JSON correctly
     */
    public FileHandler(String filepath) throws FileNotFoundException, IOException, ParseException
    {
        JSONParser parser = new JSONParser();
        file = new File(filepath);
        if(file.createNewFile())
            return;
        Object obj = parser.parse(new FileReader(file));
        object = (JSONObject) obj;
        array = (JSONArray) obj;
    }
    
    /**
     * @return filepath
     */
    public String getFilepath()
    {
        return file.getAbsolutePath();
    }
    
    /**
     * Wipes the file, making it empty/blank.
     * @throws FileNotFoundException file not found
     */
    protected void wipeFile() throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(file);
        pw.close();
    }
    
    /**
     * Updates file to match current data stored in the handler.
     * It does this by wiping the file and rewriting it based on the JSONArray
     * stored in this object.
     * @throws IOException 
     */
    public void update() throws IOException
    {
        wipeFile();
        FileWriter writer = new FileWriter(getFilepath());
        writer.write(array.toJSONString());
        writer.flush();
    }

    /**
     * Overwrites the JSONArray with a new one.
     * @param array the new array
     */
    public void setArray(JSONArray array)
    {
        this.array = array;
    }
    
    /**
     * Overwrites the JSONObject with a new one.
     * @param object the new object
     */
    public void setObject(JSONObject object)
    {
        this.object = object;
    }
    
    /**
     * @return the File object associated with this FileHandler.
     */
    public File getFile()
    {
        return file;
    }
    
    /**
     * @return the JSONArray object associated with this FileHandler.
     */
    public JSONArray getJSONArray()
    {
        return array;
    }
    
    /**
     * @return the JSONObject associated with this FileHandler.
     */
    public JSONObject getJSONObject()
    {
        return object;
    }

}
