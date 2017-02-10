
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
 * Parses JSON into stuff. Using a simple, open source JSON parser that I
 * already forgot the name of. It's pretty dank tho
 *
 * @author Darius
 */
public class FileHandler {
    
    public static final String DEFAULT_VIEWER_FILEPATH = "Viewers.json";

    /**
     * Makes a Bag of Viewers out of a file.
     * @param filepath the location of the file
     * @return Bag of Viewers
     * @throws IOException if you goof
     * @throws ParseException if I goof
     */
    public Bag<Viewer> getViewerList(String filepath) throws IOException, ParseException 
    {
        Bag<Viewer> b = new Bag();
        
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filepath));
        JSONArray jsonarray = (JSONArray) obj;

        for (int i = 0; i < jsonarray.size(); i++) 
        {
            JSONObject jsonObject = (JSONObject) jsonarray.get(i);
            b.add(toViewer(jsonObject));
        }
        return b;
    }
    
    /**
     * Converts a Bag of Viewers into a JSON file for storage.
     * @param filepath filepath of the file
     * @param bag the Bag of viewers (see Bag.class)
     * @return the File created
     * @throws IOException if there's an issue finding the file.
     */
    public File toFile(String filepath, Bag<Viewer> bag) throws IOException
    {
        wipeFile(filepath);
        File file = new File(filepath);
        FileWriter writer = new FileWriter(filepath);
        
        JSONArray viewerArray = getJSONArray(bag);

        writer.write(viewerArray.toJSONString());
        writer.flush();
        return file;
    }
    
    /**
     * Makes a JSONArray from a Bag of Viewers.
     * @param b the Bag of Viewers
     * @return JSONArray of JSONObjects containing Viewer information
     */
    public JSONArray getJSONArray(Bag<Viewer> b)
    {
        JSONArray array = new JSONArray();
        for(int i = 0; i < b.getCount(); i++)
            array.add(toJSON(b.get(i)));
        return array;
    }

    /**
     *
     * @param filepath
     * @throws FileNotFoundException
     */
    public void wipeFile(String filepath) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(filepath);
        pw.close();
    }

    /**
     * Turns a JSONObject with only one Viewer into a Viewer.
     * @param j the JSONObject being parsed.
     * @return Viewer based on information in JSONObject
     * @throws IllegalArgumentException 
     */
    public Viewer toViewer(JSONObject j) throws IllegalArgumentException {
        Viewer v = new Viewer();
        v.setName(j.get("nick").toString());
        v.exp.setLevel(Integer.parseInt(j.get("exp").toString()));
        v.gold.setLevel(Integer.parseInt(j.get("gold").toString()));
        v.attack.setLevel(Integer.parseInt(j.get("attack").toString()));
        v.magic.setLevel(Integer.parseInt(j.get("magic").toString()));
        v.tank.setLevel(Integer.parseInt(j.get("tank").toString()));
        return v;
    }

    /**
     * Creates a new JSONObject out of a Viewer.
     * @param v the Viewer being turned into a JSONObject
     * @return JSONObject containing Viewer information
     * @throws IllegalArgumentException if you goof it up
     */
    public JSONObject toJSON(Viewer v) throws IllegalArgumentException {
        JSONObject j = new JSONObject();
        j.put("nick", v.getNick());
        j.put("exp", v.exp.getLevel());
        j.put("gold", v.gold.getLevel());
        j.put("attack", v.attack.getLevel());
        j.put("magic", v.magic.getLevel());
        j.put("tank", v.tank.getLevel());
        return j;
    }
}
