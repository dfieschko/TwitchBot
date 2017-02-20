package twitchbot;

import org.json.simple.JSONObject;
import twitchbot.filehandler.JSONConversion;

/**
 *
 * @author Darius
 */
public class Viewer implements JSONConversion{

    private String nick;
    private int timeWatched;

    /**
     * Constructor
     * Creates new Viewer from JSONObject
     * @param jObject
     */
    public Viewer(JSONObject jObject) {
        nick = jObject.get("nick").toString();
        timeWatched = Integer.parseInt(jObject.get("timeWatched").toString());
    }

    /**
     * Constructor
     * Creates new Viewer from nick (name)
     * @param nick 
     */
    public Viewer(String nick) {
        this.nick = nick;
        timeWatched = 0;
    }

    /**
     * Sets time watched to new value
     * @param timeWatched the new value
     */
    public void setTimeWatched(int timeWatched) {
        this.timeWatched = timeWatched;
    }

    /**
     * Adds value to time watched
     * @param time the value being added
     */
    public void addTimeWatched(int time) {
        timeWatched += time;
    }
    
    /**
     * @return Viewer as JSONObject for storage
     */
    @Override
    public JSONObject toJSONObject()
    {
        JSONObject jObj = new JSONObject();
        jObj.put("nick", nick);
        jObj.put("timeWatched", timeWatched);
        return jObj;
    }

    /**
     * @return Viewer's nick (name)
     */
    public String getNick() {
        return nick;
    }

    /**
     * @return Viewer's time watched
     */
    public int getTimeWatched() {
        return timeWatched;
    }
}
