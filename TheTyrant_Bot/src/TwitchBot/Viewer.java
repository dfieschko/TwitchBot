package TwitchBot;


/**
 *
 * @author Darius
 */
public class Viewer {
    
    private String nick;
    
    public Stat attack = new Stat("Attack");
    public Stat magic = new Stat("Magic");
    public Stat tank = new Stat("Tank");
    public Stat exp = new Stat("Experience");
    public Stat gold = new Stat("Gold");
    
    /**
     * Constructor.
     * Empty right now. I was going to do something but then decided not to.
     */
    public Viewer()
    {
        //am i gonna do anything with this
    }
    
    /**
     * Constructor with name.
     * @param nick the Viewer's name
     */
    public Viewer(String nick)
    {
        this.nick = nick;
    }
    
    /**
     * Returns the Viewer contained in the Viewer Bag in Config.java.
     * Yeah, it's really janky and I should probably get rid of this.
     * @param nick the Viewer's name
     * @return the Viewer with matching name
     * @throws IllegalArgumentException if you try to search for something wrong
     */
    public static Viewer get(String nick) throws IllegalArgumentException
    {
        return Config.viewers.get(Config.viewers.findViewer(nick));
    }
    
    /**
     * Changes name/nick.
     * @param nick the name it changes to
     */
    public void setNick(String nick)
    {
        this.nick = nick;
    }
    
    /**
     * Just a different way of spelling "setNick".
     * Stop judging me
     * @param name the name it changes to
     */
    public void setName(String name)
    {
        setNick(name);
    }
    
    /**
     * @return Viewer's name
     */
    public String getNick()
    {
        return nick;
    }
    
    /**
     * @return Viewer's name
     */
    public String getName()
    {
        return getNick();
    }
    
    /**
     * Gives default reward to the Viewer.
     * Meant to be called when TwitchBot.nextMinute() happens.
     */
    public void giveReward()
    {
        exp.levelUp();
        gold.levelUp();
    }
    
    /**
     * @return the Viewer's information as a String.
     */
    @Override
    public String toString()
    {
        return exp.getName() + ": " + exp.getLevel() + '|' + gold.getName() + ": " + gold.getLevel() + '|' +
                attack.getName() + ": " + attack.getLevel() + '|' + magic.getName() + ": " + magic.getLevel() + '|' + 
                tank.getName() + ": " + tank.getLevel();
    }
}
