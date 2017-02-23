
package twitchbot.filehandler;

import org.json.simple.JSONObject;

/**
 * Things that implement this should be able to return a JSONObject containing their information.
 * @author Darius
 */
public interface JSONConversion {
    
    /**
     * Store information in a JSONObject
     * @return JSONObject containing information for this object.
     */
    public abstract JSONObject toJSONObject();
            
}
