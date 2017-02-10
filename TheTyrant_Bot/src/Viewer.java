
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
    
    public Viewer()
    {
        //am i gonna do anything with this
    }
    
    public Viewer(String nick)
    {
        this.nick = nick;
    }
    
    public static Viewer get(String nick) throws IllegalArgumentException
    {
        return Config.viewers.get(Config.viewers.findViewer(nick));
    }
    
    public void setNick(String nick)
    {
        this.nick = nick;
    }
    
    public void setName(String name)
    {
        setNick(name);
    }
    
    public String getNick()
    {
        return nick;
    }
    
    public String getName()
    {
        return getNick();
    }
    
    public void giveReward()
    {
        exp.levelUp();
        gold.levelUp();
    }
    
    @Override
    public String toString()
    {
        return exp.getName() + ": " + exp.getLevel() + '|' + gold.getName() + ": " + gold.getLevel() + '|' +
                attack.getName() + ": " + attack.getLevel() + '|' + magic.getName() + ": " + magic.getLevel() + '|' + 
                tank.getName() + ": " + tank.getLevel();
    }
}
