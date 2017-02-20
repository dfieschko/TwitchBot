package TwitchBot;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Configuration file that contains useful information in one convenient place.
 * Also contains the main method. And now contains a lot more stuff, too.
 * Honestly I probably should have called this Main or Instance or something
 *
 * @author Darius Fieschko
 */
public class Config {

    public static final String DEFAULT_CHANNEL = "#thetyrant_lol";               //target channel
    public static final String DEFAULT_NAME = "thetyrant_bot";                   //username
    public static final String PASSWORD = "oauth:e606tmnom560yng1y7q8jyt54sb9fe";//password; found at:
                                                                                 //http://twitchapps.com/tmi/
										 //putting this on github was definitely a good idea
    //TODO: add the option to toggle these
    private static final boolean showRandomMessages = true;
    private static final boolean showStartupMessage = false;

    public static Timer timer = new Timer();
    
    public static Bag<Viewer> viewers = new Bag();

    private static TwitchBot bot;
    
    public static Hangman hangman;
    
    private static String lastRandMessage;
    
    //Messages that have a low chance to be displayed every minute
    //See method randomMessage() and TwitchBot.nextMinute()
    private static final String[] lowChanceMsg =
    {
        "if you don't play magic pen mundo you're pretty bad tbh",
        "#Girl Power!",
        "Bathroom Tip: Going number 2? Discreetly cover up your poop sounds by continually shrieking at the top of your lungs",
        "Parenting Tip: Naming your son Moist Nuggets is probably a subpar move.",
        "Magic Pen Mundo is sleeper OP - try it in a ranked game today!",
        "Watching Erick CS like OSsloth",
        "Fact: Bees like flowers. This means if you give a girl flowers and she likes them she is a swarm of bees in disguise.",
	"League Pro Tip: If you don't own all the skins for your champion you have a 75% higher chance to lose the game."
    };
    //Messages that have a medium chance to be displayed every minute
    private static final String[] medChanceMsg = 
    {
        "League Pro Tip: You can reach your base faster if you recall instead of walking to it!",
        "League Pro Tip: If you are looking to win the game, try destroying the enemy team's nexus!",
        "League Pro Tip: Having trouble climbing in ranked? It must be your team's fault!",
        "League Pro Tip: Blame it on your ping.",
        "League Pro Tip: Your skillshots generally deal more damage if they hit an enemy.",
        "League Pro Tip: Help your minions grow stronger by letting them have all your CS.",
        "League Pro Tip: It's always your support's fault.",
        "League Pro Tip: It's always your jungler's fault.",
        "League Pro Tip: You can definitely duel that 4/0 Riven.",
        "League Pro Tip: If you lose all of your health, you will die."
    };
    //Messages that have a medium chance to be displayed every minute
    private static final String[] highChanceMsg = 
    {
        "Use !info and !commandlist to learn more about me!",
        "PM me \"hangman WORD\" to start a Hangman game!",
        "Tune in every weekday 6PM-9PM PST!",
        "Like what you're seeing? Follow the channel!",
        "You earn 1 exp and 1 gold per minute you watch - use !stats to see your current score!",
	"Use \"!hm LETTER\" to guess a letter in Hangman!"
    };
    
    /**
     * Main method. Instantiates the bot and sets it up to do its work.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        hangman = new Hangman("");
        hangman.instaLose();
        
	GUI.main(args);
	
        connect();
	try{
	    viewers = new FileHandler().getViewerList(FileHandler.DEFAULT_VIEWER_FILEPATH);
	}catch(Exception e)
	{}

        
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bot.nextMinute();
                if(showRandomMessages)
                    randomMessage();
            }
        }, 0, 10 * 6000);
    }

    /**
     * Connects bot to server.
     *
     * @throws Exception when something goofs
     */
    public static void connect() throws Exception {
        bot = new TwitchBot();
        bot.setVerbose(true);
        bot.connect("irc.twitch.tv", 6667, PASSWORD);
        bot.joinChannel(DEFAULT_CHANNEL);
        bot.getName();
        bot.sendRawLine("CAP REQ :twitch.tv/membership twitch.tv/commands");
        if (showStartupMessage) {
            bot.sendMessage(DEFAULT_CHANNEL, "Tyrant Bot Online!");
        }
    }

    /**
     * Like Random.nextInt(), but starting at 1.
     *
     * @param high is what it can roll up to.
     * @return random integer from 1 to the input integer.
     */
    public static int randInt(int high) {
        if (high == 1) //bootleg
        {
            return 1;
        }
        return new Random().nextInt(high - 1) + 1;
    }

    /**
     * Finds a random integer between two input integers.
     *
     * @param low is the minimum output.
     * @param high is the maximum output.
     * @return random integer between two input integers.
     */
    public static int randInt(int low, int high) {
        if (low == high) {
            return low;
        }
        return new Random().nextInt(high - low) + low;
    }

    /**
     * Contains a small chance to say some random message. Configurable; change
     * numPossible to adjust chances of messages being said. I'm probably going
     * to change this system into one or two arrays of Strings and ints so it's
     * easier to add to and modify in the future.
     */
    public static void randomMessage() {
        int randy = randInt(1, 20); // 1/20 chance to be displayed
        if(randy == 1)              // please forgive the magic numbers
        {
            String word;
            randy = randInt(1, 100);
            if(randy <= 20)     //low chance messages
            {
                word = lowChanceMsg[randInt(lowChanceMsg.length - 1)];
                if(word.equals(lastRandMessage))
                    return;
                bot.say(word);
                lastRandMessage = word;
            }
            else if(randy <= 55) //medium chance messages
            {
                word = medChanceMsg[randInt(medChanceMsg.length - 1)];
                if(word.equals(lastRandMessage))
                    return;
                bot.say(word);
                lastRandMessage = word;
            }
            else                 //high chance messages
            {
                word = highChanceMsg[randInt(highChanceMsg.length - 1)];
                if(word.equals(lastRandMessage))
                    return;
                bot.say(word);
                lastRandMessage = word;
            }
        }
    }
}
