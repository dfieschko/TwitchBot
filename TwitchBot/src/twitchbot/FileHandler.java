package twitchbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        Object obj = parser.parse(new FileReader(file));
        object = (JSONObject) obj;
        array = (JSONArray) obj;
    }

}
