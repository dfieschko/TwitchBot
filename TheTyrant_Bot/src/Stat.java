
/**
 * Contains info for a stat.
 * I just made this so I wouldn't have to write a bajillion methods in Combatant
 * @author Darius
 */
public class Stat {
    int level;
    String name;
    
    /**
     * Constructor without level/value.
     * @param name the name of the particular stat.
     */
    public Stat(String name)
    {
        this.name = name;
        this.level = 0;
    }
    
    /**
     * Constructor. Should be used in most cases.
     * @param name the name of the particular stat.
     * @param level the value/level of the particular stat.
     */
    public Stat(String name, int level)
    {
        this.name = name;
        this.level = level;
    }
    
    /**
     * @return the name of the stat.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @param name the new name
     * @return the new name
     */
    public String setName(String name)
    {
        this.name = name;
        return this.name;
    }
    
    /**
     * @return the level of the stat.
     */
    public int getLevel()
    {
        return level;
    }
    
    /**
     * Levels up by one.
     * @return the new level
     */
    public int levelUp()
    {
        level++;
        return level;
    }
    
    /**
     * Level up by a specific amount.
     * @param levels the amount being leveled up by
     * @return the new level
     */
    public int levelUp(int levels)
    {
        this.level += levels;
        return this.level;
    }
    
    /**
     * Sets level to a specific amount.
     * @param level the new level
     * @return the new level
     */
    public int setLevel(int level)
    {
        this.level = level;
        return this.level;
    }
}
