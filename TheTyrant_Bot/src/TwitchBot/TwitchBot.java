package TwitchBot;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jibble.pircbot.*;
import org.json.simple.JSONObject;

/**
 * TwitchBot class that has most of the controls for interacting with the audience.
 * I'm using PircBot to help me avoid suicidal thoughts.
 * @author Erect Gandalf
 */

public class TwitchBot extends PircBot
{	
    /** A solid bar that is one line long in Twitch chat. */
    private static final String BAR = "__________________________________________________";
    
    private Timer timer = new Timer();
    
	/**
	 * Constructor. Uses default name. 
         * Won't work with any other name, so don't try.
	 */
	public TwitchBot()
	{
            this.setName(Config.DEFAULT_NAME);
	}
        
        /**
         * Checks for white male privilege.
         * @param name the name of the viewer being checked
         * @return true if the viewer is privileged, false if not.
         */
        private boolean isPrivileged(String name)
        {
            return "erect_gandalf".equals(name) || "thetyrant_lol".equals(name);
        }
	
	
	/**
	 * This deals with incoming messages.
	 */
        @Override
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{   
		if(message.equalsIgnoreCase("this channel sucks") || message.equalsIgnoreCase("this stream sucks")) 
                                                                      //if someone says this in chat
			sendMessage(channel, "no u " + sender);       //this happens
                
                /*
                Stops the bot.
                */
                else if(message.equalsIgnoreCase("!stopbot") && isPrivileged(sender))
                {
                    whisper(sender, "You have shut me down");
                    quitBot();
                }
                
                /*
                Gives someone gold. Requires privilege.
                */
                else if(message.contains("!givegold ") && isPrivileged(sender))
                {
                    
                    String[] str = message.split(" ");
                    Viewer view;
                    try{
                        view = Viewer.get(str[1]);
                    }catch(Exception e)
                    {
                        sendMessage(channel, "That viewer doesn't exist you dumb idiot");
                        return;
                    } try{
                        view.gold.levelUp(Integer.parseInt(str[2]));
                    }catch(Exception e)
                    {
                        sendMessage(channel, "That's not a valid number you dumb idiot");
                    }
                }
                
                /*
                Gives someone exp. Requires privilege.
                */
                else if(message.contains("!giveexp ") || message.contains("!givexp ") && isPrivileged(sender))
                {
                    
                    String[] str = message.split(" ");
                    Viewer view;
                    try{
                        view = Viewer.get(str[1]);
                    }catch(Exception e)
                    {
                        sendMessage(channel, "That viewer doesn't exist you dumb idiot");
                        return;
                    } try{
                        view.exp.levelUp(Integer.parseInt(str[2]));
                    }catch(Exception e)
                    {
                        sendMessage(channel, "That's not a valid number you dumb idiot");
                    }
                }
                
                /*
                Guesses a letter for Hangman.
                */
                else if(message.toLowerCase().contains("!hm ") || message.toLowerCase().contains("!hangman "))
                {
                    if(Config.hangman.isFinished())
                    {
                        say("There isn't a hangman game currently in progress - message me \"hangman WORD\" to start a new game!");
                        return;
                    }
                    
                    String guess = message.split(" ")[1].split("")[0].toUpperCase();
                    
                    if(Config.hangman.wasGuessed(guess))
                    {
                        say(guess + " was already guessed! The following have been guessed: " + Config.hangman.getGuesses());
                        return;
                    }
                    
                    if(Config.hangman.guessLetter(guess))
                    {
                            say(guess + " was correct! " + Config.hangman.toString());
                    }
                    else
                    {
                        say("Incorrect guess! You have " + Config.hangman.getLives() + " lives left!");
                    }
                    if(Config.hangman.isFinished())
                    {
                        if(Config.hangman.getLives() == 0)
                            say("You lost! The word: " + Config.hangman.getWord());
                        else
                            say("You guessed the word! Good job!");
			timer.cancel();
                    }
                }
                
                /*
                Displays the requester's stats.
                TODO: make this look less ugly in Twitch chat.
                */
                else if(message.equalsIgnoreCase("!stats"))
                {
                    try{
                    int viewerID = Config.viewers.findViewer(sender);
                    say(BAR + " " + Config.viewers.get(viewerID).toString() +  " @" + sender);
                    }catch(Exception e) //don't judge me
                    {
                        say("I need a minute to load viewer data - please ask again later " + sender + ".");
                    }
                }
                
                /*
                Wishes someone luck if they ask for it.
                */
                else if(message.toLowerCase().contains("wish me luck"))
                {
                    say("Good luck, " + sender + '!');
                }
                
                
		/*
		 * Lists every basic command.
		 */
                else if(message.equalsIgnoreCase("!commandlist"))
			sendMessage(channel, "Commands: !info, !stats, !classes, !skills, !levelup, !upgrades, !roll. I can receive PMs, too: /w me \"hangman WORD\" to start a Hangman game!");
		
                /*
                 * Lists upgradeables.
                 */ 
                else if(message.equalsIgnoreCase("!upgrades") || message.equalsIgnoreCase("!upgradeables"))
			sendMessage(channel, "Upgradeables: !SuperLoot, !UltraGuard, !Poison. Armour: !FireShield, !IceShield, !LightningShield. http://imgur.com/a/GGUin for more detailed information.");
                
                /*
                 * Lists classes. (Class as in class of warrior, not in the programming sense)
                 */
                else if(message.equalsIgnoreCase("!classes"))
			sendMessage(channel, "!DPS: add to the Attack Damage of the Twitch Warrior. "
                                + "!Mage: add to the Magic Power of the Twitch Warrior. !Tank: add Health to the Twitch Warrior.");
                
                /*
                Gives information for the DPS class.
                */
                else if(message.equalsIgnoreCase("!DPS"))
			sendMessage(channel, "!DPS adds to the Attack Power of the Twitch Warrior, which boosts DPS abilities. "
                                + "DPS skills are !Slash (70 gold) and !CrossSlash (160 gold). !levelup dps to level up in this class.");
                
                /*
                Gives information for the Tank class.
                */
                else if(message.equalsIgnoreCase("!Tank"))
			sendMessage(channel, "!Tank adds to the Health of the Twitch Warrior. "
                                + "There are currently no Tank skills. !levelup tank to level up in this class."); 
                
                /*
                Gives information for the Mage class.
                */
                else if(message.equalsIgnoreCase("!Mage") || message.equalsIgnoreCase("!Magic"))
			sendMessage(channel, "!Mage adds to the Magic Power of the Twitch Warrior, which boosts Mage abilities."
                                + "Mages support the Twitch Warrior with shields and heals, and deal some damage with spells."
                                + "Mage skills are !HolyShield (100 gold), !Heal (60 gold), !Fire, !Ice, and !Lightning (80 gold). !levelup mage to level up in this class.");
                
                /*
                Gives information for the supportive Mage subclass.
                */
                else if(message.equalsIgnoreCase("!Mage support") || message.equalsIgnoreCase("!Mage supp"))
                    sendMessage(channel, "Support spells don't deal damage to the enemy, but help the Twitch Warrior in other ways. "
                            + "!HolyShield (100 gold) provides a shield of 5 health and can only be used once per night. "
                            + "!Heal (60 gold) heals the Twitch Warrior by a value based on the Mage levels of both the Twitch Warrior and the caster.");
                
                /*
                Gives information for the offensive Mage subclass.
                */
                else if(message.equalsIgnoreCase("!Mage attack") || message.equalsIgnoreCase("!Mage damage"))
                    sendMessage(channel, "Damage spells deal damage to the enemy. !Fire, !Ice, and !Lightning (80 gold) "
                            + "unleash a blast of flame, ice, and lightning, respectively.");
                
		/*
		 * Gives a short intro about the bot.
		 */
                else if(message.equalsIgnoreCase("!info"))
			sendMessage(channel, "Hi, I'm your friendly neighborhood bot! I make sure that everyone gets cool rewards just for watching!"
					+ " I'm still in development, so expect some bugs and missing features. Contact @erect_gandalf "
					+ "if you have any ideas for how I could improve!");
		
		/*
		 * Lists all the skills the Twitch Warrior can use
		 */
                else if(message.equalsIgnoreCase("!skills") || message.equalsIgnoreCase("!abilities"))
				sendMessage(channel, "!Classes: !Mage, !DPS, !Tank. !Skills: !Slash, !CrossSlash, !Fire, !Ice, !Lightning, !Poison, !Heal, !Holyshield.");
		
		/*
		 * Rolls any sided die, with the output being either between two integer inputs, or between one and an integer,
		 * depending on the number of inputs.
		 */
                else if(message.contains("!roll"))
		{
			try{
                            String str[] = message.split(" ");
                            switch (str.length) {
                                case 1:
                                    sendMessage(channel, "You rolled a " + Config.randInt(20) + ", " + sender + "!");
                                    break;
                                case 2:
                                    sendMessage(channel, "You rolled a " + Config.randInt(Integer.parseInt(str[1])) + ", " + sender + "!");
                                    break;
                                case 3:
                                    sendMessage(channel, "You rolled a " + Config.randInt(Integer.parseInt(str[1]), Integer.parseInt(str[2]))
                                            + ", " + sender + "!");
                                    break;
                                default:
                                    throw new Exception();
                            }
			}catch(Exception e)
			{
				sendMessage(channel, "Incorrect syntax, " + sender + ". !roll for 1-20, !roll x for 1-x, !roll x y for x-y. Try again!");
			}
		}
		
	}
        
        /**
         * Reacts to any and all incoming messages from the Twitch chat.
         * This is necessary because PircBot was last updated in 2009 and Twitch chat changed up
         * their private messaging system in around 2014, which ruined PircBot's ability to
         * receive whispers. Now I have to parse them on my own.
         * @param line the incoming message from Twitch chat
         */
        @Override
        public void onUnknown(String line)
        {

            String[] scooby = line.split(":");
            String[] dooby = scooby[1].split("!");
            String sender = dooby[0];
            String message = scooby[scooby.length - 1];

            //Reacts to private messages
            if(line.toUpperCase().contains("WHISPER"))
            {
                onPrivateMessage(sender, message);
            }
        }
        
        /**
         * Reacts to private messages.
         * Not using PircBot's method because it no longer works.
         * @param sender the sender of the private message.
         * @param message the message
         */
	public void onPrivateMessage(String sender, String message)
        {
            if(message.contains("hangman") || message.contains("!hangman"))
                startHangMan(sender, message);
            else if(message.contains("!stopbot") && isPrivileged(sender))
            {
                whisper(sender, "You have shut me down");
                quitBot();
            }
            else if(message.contains("!say ") && isPrivileged(sender))
            {
                say(message.split("!say ")[1]);
            }
        }
        
        /**
         * Sends someone a whisper (PM).
         * @param name the name of the person you're sending the message to
         * @param message the message
         */
        public void whisper(String name, String message)
        {
            sendMessage(Config.DEFAULT_CHANNEL, "/w " + name + " " + message);
        }
        
        /**
         * Says the input text into the default channel.
         * This was just written for my convenience, since it's obnoxious to
         * type or copy/paste sendMessage(Config.DEFAULT_CHANNEL, text); every time.
         * @param text the text being said.
         */
        public void say(String text)
        {
            sendMessage(Config.DEFAULT_CHANNEL, text);
        }
        
        /**
         * Starts a new game of Hangman.
         * @param sender is the starter of the game.
         * @param message is the message that was sent to start the game.
         */
        private void startHangMan(String sender, String message)
        {
            if(!Config.hangman.isFinished())
                {
                    whisper(sender, "There is already a Hangman game in progress.");
                    return;
                }
                String[] word = message.split(" ");
                try
                {
                    if(message.split(" ").length >= Hangman.MAX_CHARS)
                    {
                        throw new IllegalArgumentException();
                    }
                    Config.hangman = new Hangman(message.split(" ")[1].toUpperCase());
		    TimerTask timerTask = new TimerTask()
		    {
                        @Override
                        public void run() 
                        {
                            Config.hangman.instaLose();
                            sendMessage(Config.DEFAULT_CHANNEL, "You've run out of time and lost the Hangman game :( Word: " 
                                    + Config.hangman.getWord());
			    this.cancel();
                        }
                    };
		    timer.schedule(timerTask, 15 * 60000);
                    
                    whisper(sender, " You have started a new game of Hangman using the word "
                            + message.split(" ")[1].toUpperCase());
                    sendMessage(Config.DEFAULT_CHANNEL, BAR + " A new game of Hangman has started! Use !hangman or !hm to guess letters! WORD: " + Config.hangman.toString());
                }catch(IllegalArgumentException e)
                {
                    whisper(sender, "Looks like you used incorrect syntax - correct syntax is \"hangman word\", "
                            + "replacing \"word\" with the word you want to play with. The maximum size of the word is " 
                            + Hangman.MAX_CHARS + " characters.");
                }catch(Exception e)
		{
		    whisper(sender, "Something got goofed. Try again. "
			    + "If it still doesn't work, please message Erect_Gandalf "
			    + "with a description of what you told me to do.");
		}
        }
	
	/**
	 * Updates viewer list and awards each online viewer for one minute of watching time.
	 * Meant to be called once per minute.
         * Yes, the name makes it marginally harder to update.
	 */
	public void nextMinute()
	{
            int newUsers = 0;
            User[] users = getUsers(Config.DEFAULT_CHANNEL);
            for (User user : users) { //TODO: See if there's a more efficient way to do this
                String name = user.getNick();
                int index = Config.viewers.findViewer(name);
                if(index == -1)
                {
                    Config.viewers.add(new Viewer(name));
                    index = Config.viewers.getCount() - 1;
                    newUsers++;
                }
                Config.viewers.get(index).giveReward();
            }
            System.out.println("Minute rewards given. Number of viewers: " 
                    + users.length + " New viewers: " + newUsers);
            if(!isConnected())
            {
                System.out.println("Not connected. Reconnecting:");
                try {
                    Config.connect();
                    System.out.print(" Reconnected.");
                } catch (Exception ex) {
                    System.out.print(" Connection Failed.");
                }
            }
	}
        
        /**
         * Saves info, leaves channel, and shuts down the bot.
         */
        public void quitBot()
        {
            try{
                new FileHandler().toFile("Viewers.json", Config.viewers);
            }catch(Exception e)
            {
                e.printStackTrace(); //forgive me programming Jesus
            }
            quitServer();
            for(int i = 0; i < Config.viewers.getCount(); i++)
            {
                System.out.println(Config.viewers.get(i).getName() + ": " + Config.viewers.get(i).toString());
            }
            System.exit(0);
        }

}
