package twitchbot;


/**
 *
 * @author Darius
 */
public class Config {


    private static TwitchBot bot;
    
    private static final int SECONDS = 1000; //in ms
    
    //toggleable settings use this convention
    public static boolean Hangman_Enabled = true;
    
    
    
    //these are all temporary
    public static final String DEFAULT_CHANNEL = "#erect_gandalf";               //target channel
    public static final String DEFAULT_NAME = "thetyrant_bot";                   //username
    public static final String PASSWORD = "oauth:e606tmnom560yng1y7q8jyt54sb9fe";

    public static void main(String args[]) {
        try{
            bot = new TwitchBot(DEFAULT_NAME, PASSWORD, DEFAULT_CHANNEL);
        }catch(Exception e)
        {
            System.out.println("you're fucked");
        }
    }
    
    public static void quit()
    {
        bot.quit();
        System.exit(0);
    }
}
