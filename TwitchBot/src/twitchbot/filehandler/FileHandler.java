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

    File file;
    JSONArray array;
    JSONObject object;
    
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
    
    public String getFilepath()
    {
        return file.getAbsolutePath();
    }
    
    protected void wipeFile() throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(file);
        pw.close();
    }
    
    public void update() throws IOException
    {
        wipeFile();
        FileWriter writer = new FileWriter(getFilepath());
        writer.write(array.toJSONString());
        writer.flush();
    }

    public void setArray(JSONArray array)
    {
        this.array = array;
    }
    
    public File getFile()
    {
        return file;
    }
    
    public JSONArray getJSONArray()
    {
        return array;
    }
    
    public JSONObject getJSONObject()
    {
        return object;
    }

}
