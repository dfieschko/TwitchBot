package twitchbot;

import java.io.IOException;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

/**
 * TwitchBot class that has most of the controls for interacting with the
 * audience.
 *
 * @author Darius Fieschko
 */
public class TwitchBot extends PircBot {

    private static final String DEFAULT_CHANNEL = "#thetyrant_lol";
    private static final String DEFAULT_NAME = "thetyrant_bot";
    private static final String DEFAULT_PASSWORD = "oauth:e606tmnom560yng1y7q8jyt54sb9fe";

    private String channel;
    private String password;

    public TwitchBot() {
        this(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_CHANNEL);
    }

    public TwitchBot(String name, String password, String channel) {
        setName(name);
        this.channel = channel;
        this.password = password;
    }

    public void setUp() throws IOException, IrcException {
        setVerbose(true);
        connect("irc.twitch.tv", 6667, password);
        joinChannel(channel);
        getName();
        sendRawLine("CAP REQ :twitch.tv/membership twitch.tv/commands");
        say("Online");
    }

    /**
     * This deals with incoming messages.
     */
    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        
    }

    /**
     * Reacts to private messages. Not using PircBot's method because it no
     * longer works.
     *
     * @param sender the sender of the private message.
     * @param message the message
     */
    public void onPrivateMessage(String sender, String message) {

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
     * sendMessage(channel, text); every time.
     *
     * @param text the text being said.
     */
    public void say(String text) {
        sendMessage(channel, text);
    }

    public String getChannel() {
        return channel;
    }

}
