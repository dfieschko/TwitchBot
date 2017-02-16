
package twitchbot;

/**
 *
 * @author Darius
 */
public class Viewer {
    
    private String nick;
    private int timeWatched;
    
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
