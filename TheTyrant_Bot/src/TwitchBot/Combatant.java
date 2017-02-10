package TwitchBot;


/**
 * Parent to any combatant.
 * The only combatants are probably going to be Enemy and TwitchWarrior,
 * but there can be others.
 * @author Darius
 */
public class Combatant {
    
    public static enum DamageType {BASIC, FIRE, ICE, LIGHTNING};
    
    
    //these are public for a reason
    //the reason is that I'm lazy
    public Stat attack = new Stat("Attack");
    public Stat magic = new Stat("Magic");
    
    public Stat maxHP = new Stat("Max HP");
    public Stat currentHP = new Stat("HP");
    public Stat shield = new Stat("Shield");
    
    public Stat basicResist = new Stat("Armor");
    public Stat fireResist = new Stat("Fire Resistance");
    public Stat iceResist = new Stat("Ice Resistance");
    public Stat lightningResist = new Stat("Lightning Resistance");
    
    /**
     * @param stat the Stat you're requesting info for
     * @return the level of the stat (int)
     * @throws IllegalArgumentException if you cock it up
     */
    public int getStatLevel(Stat stat) throws IllegalArgumentException
    {
        return stat.getLevel();
    }
    
    /**
     * @param stat the Stat you're requesting info for
     * @return the name of the stat (String)
     * @throws IllegalArgumentException if you cock it up
     */
    public String getStatName(Stat stat) throws IllegalArgumentException
    {
        return stat.getName();
    }
}
