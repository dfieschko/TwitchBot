package twitchbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public ArrayList<Viewer> getViewerList()
    {
        ArrayList arr = new ArrayList();
        for(int i = 0; i < array.size(); i++)
        {
            arr.add(new Viewer((JSONObject) array.get(i)));
        }
        return arr;
    }
    
    public String getFilepath()
    {
        return file.getAbsolutePath();
    }
    
    private void wipeFile() throws FileNotFoundException
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
    
    public void set(ArrayList <Viewer> viewerArray)
    {
        try {
            setArray(getJSONArray(viewerArray));
            update();
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setArray(JSONArray array)
    {
        this.array = array;
    }
    
    public JSONArray getJSONArray(ArrayList<Viewer> viewerArray)
    {
        JSONArray temp = new JSONArray();
        for(int i = 0; i < viewerArray.size(); i++)
        {
            temp.add(viewerArray.get(i).toJSON());
        }
        return temp;
    }

}
