package twitchbot;

import java.io.IOException;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import org.json.simple.JSONObject;
import twitchbot.filehandler.JSONConversion;
import twitchbot.games.Hangman;

/**
 * TwitchBot class that has most of the controls for interacting with the
 * audience.
 *
 * @author Darius Fieschko
 */
public class TwitchBot extends PircBot implements JSONConversion {

    private String channel;
    private final String password;

    private Hangman hangman;

    /**
     * Constructor
     *
     * @param name name of the user being used as a bot
     * @param password oauth password found at twitchapps.com/tmi
     * @param channel the channel the bot joins
     * @throws java.io.IOException
     * @throws org.jibble.pircbot.IrcException
     */
    public TwitchBot(String name, String password, String channel) throws IOException, IrcException {
        setName(name);
        this.channel = channel;
        this.password = password;
        setUp();
    }

    /**
     * Sets up the bot. Connects to Twitch, joins the channel, and requests
     * privileges.
     *
     * @throws IOException
     * @throws IrcException
     */
    private void setUp() throws IOException, IrcException {
        setVerbose(true);
        connect("irc.twitch.tv", 6667, password);
        joinChannel(channel);
        getName();
        sendRawLine("CAP REQ :twitch.tv/membership twitch.tv/commands");
    }

    /**
     * This deals with incoming messages. Use if(message.contains() ||
     * message.equalsIgnoreCase()), etc
     */
    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        message = message.trim(); //removes leading and trailing whitespace
        if (!message.startsWith("!")) //checks if it's a command and returns if it's not
        {                             //commands start with ! currently
            return;                  
        }
        String[] splitSpaces = message.split(" ");
        String command = splitSpaces[0];
        String arg1 = splitSpaces[1];

        /* HANGMAN */
        if (command.equalsIgnoreCase("!hm") || command.equalsIgnoreCase("!hangman")) {
            handleHangman(sender, arg1);
        }
    }

    /**
     * Handles Hangman commands and responses so as to not clutter up
     * onMessage().
     *
     * @param sender sender of the hangman command
     * @param message
     */
    private void handleHangman(String sender, String message) {
        if (!Config.Hangman_Enabled) {
            return;
        }
        if (hangman == null || !hangman.inProgress()) {
            say("There is currently no hangman game in progress. /w me \"!hangman <word>\" to start a new game!");
            return;
        }
        message = message.toUpperCase();
        try {
            if (hangman.guess(message)) {
                if (hangman.hasWon()) {
                    say("Your guess was correct, " + sender + "! You have won! Good job!");
                    say("The word was " + hangman.getWord());
                } else {
                    say(message + " was correct! " + hangman.toString());
                }
            }
        } catch (IllegalArgumentException e) {
            say(e.getMessage() + " " + sender + " Guessed letters: " + hangman.getGuessedString());
            
        }
    }
    
    
    /**
     * Starts a new Hangman game.
     * If there's already a hangman 
     * @param sender
     * @param message 
     */
    private void startHangman(String sender, String message) {
        if(hangman != null && hangman.inProgress())
        {
            whisper(sender, "There is already a hangman game in progress.");
            return;
        }
        try{
            Hangman tempman = new Hangman(message);
            hangman = tempman; //don't "fix" this
            say("A new game of Hangman has started! " + hangman.toString());
        }catch(IllegalArgumentException e)
        {
            whisper(sender, e.getMessage());
            whisper(sender, "Hangman only accepts the 26 letters of the "
                    + "English alphabet, and has a maximum character limit of "
                    + Hangman.MAX_LENGTH + ".");
        }
        
    }

    /**
     * Reacts to private messages. Not using PircBot's method because it no
     * longer works.
     *
     * @param sender the sender of the private message.
     * @param message the message
     */
    public void onPrivateMessage(String sender, String message) {
        message = message.trim(); //removes leading and trailing whitespace
        if (!message.startsWith("!")) //checks if it's a command and returns if it's not
        {                             //commands start with ! currently
            return;                  
        }
        String[] splitSpaces = message.split(" ");
        String command = splitSpaces[0];
        String arg1 = null;
        if(splitSpaces.length > 0)
            arg1 = splitSpaces[1];
        
        /* START HANGMAN GAME */
        if(command.equalsIgnoreCase("!hangman"))
        {
            startHangman(sender, arg1);
        }
        else if(command.equalsIgnoreCase("stopbot"))
            Config.quit();
    }

    /**
     * Reacts to any and all incoming messages from the Twitch chat. This is
     * necessary because PircBot was last updated in 2009 and Twitch chat
     * changed up their private messaging system in around 2014, which ruined
     * PircBot's ability to receive whispers. Now I have to parse them on my
     * own.
     *
     * @param line the incoming message from Twitch chat
     */
    @Override
    public void onUnknown(String line) {
        //TODO: Improve readability
        String[] aCol = line.split(":");
        String[] aExc = aCol[1].split("!");
        String sender = aExc[0];
        String message = aCol[aCol.length - 1];

        //Reacts to private messages
        if (line.toUpperCase().contains("WHISPER")) {
            onPrivateMessage(sender, message);
        }
    }

    /**
     * Sends someone a whisper (PM).
     *
     * @param name the name of the person you're sending the message to
     * @param message the message
     */
    public void whisper(String name, String message) {
        sendMessage(channel, "/w " + name + " " + message);
    }

    /**
     * Says the input text into the channel. This was just written for my
     * convenience, since it's obnoxious to type or copy/paste
     * sendMessage(channel, text) every time.
     *
     * @param text the text being said.
     */
    public void say(String text) {
        sendMessage(channel, text);
    }

    /**
     * @return JSONObject containing the bot's information for storage.
     */
    @Override
    public JSONObject toJSONObject() {
        JSONObject jObj = new JSONObject();
        jObj.put("nick", getName());
        jObj.put("channel", channel);
        jObj.put("password", password);
        return jObj;
    }

    /**
     * @return channel name
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Leaves channel and joins new channel
     *
     * @param channel the new channel
     * @throws IOException
     * @throws IrcException
     */
    public void setChannel(String channel) throws IOException, IrcException {
        this.channel = channel;
        quitServer();
        setUp();
    }

    /**
     * Disconnects bot from server and disposes of all its resources.
     */
    public void quit() {
        disconnect();
        dispose();
    }

}
