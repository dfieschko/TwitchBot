
package twitchbot.filehandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import twitchbot.Viewer;

/**
 *
 * @author Darius
 */
public class ViewerHandler extends FileHandler{
    
    /**
     * Constructor, creates ViewerHandler based on a filepath.
     * @param filepath
     * @throws IOException
     * @throws ParseException 
     */
    public ViewerHandler(String filepath) throws IOException, ParseException
    {
        super(filepath);
    }
    
    public final ArrayList<Viewer> toViewerList()
    {
        ArrayList arr = new ArrayList();
        for(int i = 0; i < array.size(); i++)
        {
            arr.add(new Viewer((JSONObject) array.get(i)));
        }
        return arr;
    }
    
    public JSONArray getJSONArray(ArrayList<Viewer> viewerArray)
    {
        JSONArray temp = new JSONArray();
        for(int i = 0; i < viewerArray.size(); i++)
        {
            temp.add(viewerArray.get(i).toJSONObject());
        }
        return temp;
    }
    
    public final void set(ArrayList <Viewer> viewerArray)
    {
        try {
            setArray(getJSONArray(viewerArray));
            update();
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
