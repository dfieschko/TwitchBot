
package twitchbot.games;

/**
 *
 * @author Darius
 */
public class Hangman {
    
    private static final char[] validChars = {
        'A','B','C','D','E','F','G','H',
        'I','J','K','L','M','N','O','P',
        'Q','R','S','T','U','V','W','X',
        'Y','Z'
    };
    public static final int MAX_LENGTH = 10;
    private static final int DEFAULT_LIVES = 10;
    
    private final char[] word;
    private char[] guesses = new char[26];
    private int numGuesses = 0;
    private int lives = DEFAULT_LIVES;
    private char[] guessedWord;
    
    private boolean inProgress = true;
    
    /**
     * Constructor.
     * Starts a game of Hangman based on an input word.
     * Throws IllegalArgumentExceptions if the word is too long (greater than MAX_LENGTH)
     * and if the word contains an invalid character.
     * @param gameWord the word used to start the game.
     */
    public Hangman(String gameWord)
    {
        word = gameWord.toUpperCase().toCharArray();
        if(word.length > MAX_LENGTH)
            throw new IllegalArgumentException("Word is too long! Maximum of " + MAX_LENGTH + " characters.");
        for(int i = 0; i < word.length; i++)
            if(!isValidChar(word[i]))
                throw new IllegalArgumentException("Invalid character!");
        guessedWord = new char[word.length];
        for(int i = 0; i < guessedWord.length; i++)
            guessedWord[i] = '_'; //fills guessedWord with _'s
    }
    
    /**
     * Constructor.
     * Same as previous constructor, but also lets you choose how many lives you start with.
     * @param gameWord the word used to start the game
     * @param lives the number of lives
     */
    public Hangman(String gameWord, int lives)
    {
        this(gameWord);
        this.lives = lives;
    }
    
    /**
     * Guesses a String. 
     * If the String is of length 1, it treats it as guessing a char.
     * If the String is as long as the word this game is based on, it treats it 
     * as guessing the whole word.
     * @param x the String being guessed
     * @return true if the guess was correct, false if it was not.
     */
    public boolean guess(String x)
    {
        x = x.toUpperCase();
        if(x.toCharArray() == word)
        {
            numGuesses++;
            guessedWord = word;
            inProgress = false;
            return true;
        }
        else if(x.length() == word.length)
        {
            numGuesses++;
            lives--;
            return false;
        }
        else if(x.length() == 1)
            return guess(x.charAt(0));
        else 
            throw new IllegalArgumentException("Invalid length");
    }
    
    /**
     * Guesses a char.
     * If it was already guessed, throw an IllegalArgumentException.
     * If it was correct, update the guessed word.
     * If it was incorrect, decrement lives.
     * Adds char to guessed list.
     * @param x the char being guessed
     * @return true if correct, false if incorrect
     */
    public boolean guess(char x)
    {
        if(wasGuessed(x))
            throw new IllegalArgumentException("Already guessed!");
        boolean correct = false;
        for(int i = 0; i < word.length; i++)
        {
            if(x == word[i])
            {
                guessedWord[i] = x;
                correct = true;
            }
        }
        if(!correct)
            lives--;
        guesses[numGuesses] = x;
        numGuesses++;
        return correct;
    }
    
    /**
     * @return true if there is a game in progress, false if there is not.
     */
    public boolean inProgress()
    {
        return inProgress;
    }
    
    
    /**
     * Instantly loses the game.
     * Sets lives to 0 and inProgress to false.
     */
    public void instaLose()
    {
        lives = 0;
        inProgress = false;
    }
    
    /**
     * Checks if a char is valid.
     * (one of the chars in the array at the top of this class)
     * @param x the char being checked.
     * @return true if the char is valid, false if it is not.
     */
    private boolean isValidChar(char x)
    {
        for(int i = 0; i < validChars.length; i++)
            if(x == validChars[i])
                return true;
        return false;
    }
    
    /**
     * Checks if a char has already been guessed.
     * @param x the char being checked
     * @return true if the char has already been guessed, false if it has not.
     */
    private boolean wasGuessed(char x)
    {
        for(int i = 0; i <= numGuesses; i++)
            if(x == guesses[i])
                return true;
        return false;
    }
    
    /**
     * @return number of lives left
     */
    public int getLives()
    {
        return lives;
    }
    
    /**
     * Makes a String out of the guessed chars.
     * Meant to be displayed.
     * Example output: "A, B, X, Y, M."
     * @return String containing list of guessed chars.
     */
    public String getGuessedString()
    {
        String str = "";
        for(int i = 0; i < numGuesses; i++)
        {
            if(i > 0)
                str += ", ";
            str += guesses[i];
        }
        str += ".";
        return str;
    }
    
    /**
     * @return the complete word
     */
    public String getWord()
    {
        String str = "";
        for(int i = 0; i < word.length; i++)
        {
            str += word[i];
        }
        return str;
    }
    
    /**
     * @return Non-complete word as String with non-guessed letters displayed as "_" with spaces between them.
     */
    @Override
    public String toString()
    {
        String str = "";
        for(int i = 0; i < guessedWord.length; i++)
        {
            str += guessedWord[i];
            str += " ";
        }
        return str.trim();
    }
    
    /**
     * Checks if the game has been won.
     * Does this by comparing the full word with the guessed word.
     * @return true if the full word matches the guessed word, false if it does not.
     */
    public boolean hasWon()
    {
        return guessedWord == word;
    }
    
}
