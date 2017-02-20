package twitchbot.filehandler;

import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import twitchbot.Viewer;

/**
 *
 * @author Darius
 */
public class ViewerHandler extends FileHandler implements Iterable{
    
    private static final String VIEWER_TAG = "viewer";
    private static final String NICK_TAG = "nick";
    private static final String TIME_TAG = "timeWatched";

    /**
     * Constructor, creates ViewerHandler based on a filepath.
     *
     * @param filepath
     * @throws IOException
     * @throws ParseException
     */
    public ViewerHandler(String filepath) throws IOException, ParseException {
        super(filepath);
    }

    public final ArrayList<Viewer> toViewerList() {
        JSONArray jArr = (JSONArray) object.get(VIEWER_TAG);
        ArrayList arr = new ArrayList();
        for (int i = 0; i < jArr.size(); i++) {
            arr.add(new Viewer((JSONObject) jArr.get(i)));
        }
        return arr;
    }

    public JSONArray getJSONArray(ArrayList<Viewer> viewerArray) {
        JSONArray temp = new JSONArray();
        for (int i = 0; i < viewerArray.size(); i++) {
            temp.add(viewerArray.get(i).toJSONObject());
        }
        return temp;
    }

    public final void set(ArrayList<Viewer> viewerArray) {
        JSONObject obj = new JSONObject();
        for(int i = 0; i < viewerArray.size(); i++)
        {
            JSONObject vObj = new JSONObject();
            vObj.put(NICK_TAG, viewerArray.get(i).getNick());
            vObj.put(TIME_TAG, viewerArray.get(i).getTimeWatched());
            obj.put(VIEWER_TAG, vObj);
        }
        object = obj;
    }
}
