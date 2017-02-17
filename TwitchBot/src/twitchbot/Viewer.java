
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
    
    public Viewer(JSONObject jObject)
    {
        nick = jObject.get("nick").toString();
        timeWatched = Integer.parseInt(jObject.get("timeWatched").toString());
    }
    
    public Viewer(String nick)
    {
        this.nick = nick;
        timeWatched = 0;
    }
    
    public void setNick(String nick)
    {
        this.nick = nick;
    }
    
    public void setTimeWatched(int timeWatched)
    {
        this.timeWatched = timeWatched;
    }
    
    public void addTimeWatched(int time)
    {
        timeWatched += time;
    }
    
     /**
     * Creates a new JSONObject out of a Viewer.
     * @return JSONObject containing Viewer information
     */
    @Override
    public JSONObject toJSONObject() {
        JSONObject j = new JSONObject();
        j.put("nick", nick);
        j.put("time", timeWatched);
        return j;
    }
    
    public void incrementTimeWatched()
    {
        timeWatched++;
    }
    
    public String getNick()
    {
        return nick;
    }
    
    public int getTimeWatched()
    {
        return timeWatched;
    }
}
