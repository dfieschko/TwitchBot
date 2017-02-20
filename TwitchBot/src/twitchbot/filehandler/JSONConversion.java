
package twitchbot.filehandler;

import org.json.simple.JSONObject;

/**
 * Things that implement this should be able to return a JSONObject containing their information.
 * @author Darius
 */
public interface JSONConversion {
    
    public abstract JSONObject toJSONObject();
            
}
