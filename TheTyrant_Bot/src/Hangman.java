
/**
 * Hangman in Twitch Chat!
 * @author Darius
 */
public class Hangman {
    private static final int DEFAULT_LIVES = 10;
    public static final int MAX_CHARS = 10;
    
    private String[] word;
    private String[] visible;
    private int lives;
    private char[] correct;
    private char[] incorrect;
    private int lettersLeft;
    private int numIncorrect = 0;
    private int numCorrect = 0;
    
    /**
     * Constructor.
     * @param word the word the game is based on
     */
    public Hangman(String word)
    {
        this.word = word.split("");
        visible = new String[this.word.length];
        lettersLeft = this.word.length;
        correct = new char[26];
        incorrect = new char[26];
        lives = DEFAULT_LIVES;
        
    }
    
    /**
     * Instantly loses you the game.
     */
    public void instaLose()
    {
        lives = 0;
    }
    
    /**
     * @return the number of lives left
     */
    public int getLives()
    {
        return lives;
    }
    
    /**
     * @return the number of letters left
     */
    public int getLettersLeft()
    {
        return lettersLeft;
    }
    
    /**
     * @return true if the game is over, false if it is not
     */
    public boolean isFinished()
    {
        return lettersLeft == 0 || lives == 0;
    }
    
    /**
     * @return the word being played with
     */
    public String getWord()
    {
        String s = "";
        for (int i = 0; i < word.length; i++) {
            s += word[i];
        }
        return s;
    }
    
    /**
     * See if a letter has already been guessed.
     * @param letter the letter being checked
     * @return true if the letter has been guessed already, false if not.
     */
    public boolean wasGuessed(String letter)
    {
        letter = letter.toUpperCase();
        for(int i = 0; i < correct.length; i++)
            if(correct[i] == letter.charAt(0))
                return true;
        for(int i = 0; i < incorrect.length; i++)
            if(incorrect[i] == letter.charAt(0))
                return true;
        return false;
    }
    
    /**
     * Guesses a letter.
     * @param letter the letter being guessed.
     * @return true if it is correct, false if it is not.
     */
    public boolean guessLetter(String letter)
    {
        letter = letter.toUpperCase();
        boolean isRight = false;
        for(int i = 0; i < word.length; i++)
        {
            if(letter.equals(word[i]))
            {
                isRight = true;
                correct[numCorrect] = letter.charAt(0);
                visible[i] = letter;
                lettersLeft--;
                numCorrect++;
            }
        }
        if(!isRight)
        {
            incorrect[numIncorrect] = letter.charAt(0);
            numIncorrect++;
            lives--;
        }
        return isRight;
    }
    
    /**
     * @return the word converted to a String, with unknown letters replaced with "_"
     */
    @Override
    public String toString()
    {
        String str = "";
        for(int i = 0; i < visible.length; i++)
        {
            if(visible[i] == null)
                str += "_ ";
            else
                str += visible[i].toUpperCase();
        }
        return str;
    }
    
    /**
     * Sees if an array of Strings contains a certain String, 
     * since Java apparently doesn't have a method for that.
     * @param array the array being checked against
     * @param str the String being checked for
     * @return true if the String is found, false if it is not.
     */
    private boolean contains(String[] array, String str)
    {
        for (String array1 : array) {
            if (array1.equals(str)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Makes a String containing all the letters that have been guessed already,
     * ready for outputting.
     * @return String of letters that have been guessed
     */
    public String getGuesses()
    {
        String str = "";
        String[] listed = new String[numCorrect];
        if(numCorrect != 0)
        {
            str += "Correct: ";
            for(int i = 0; i < numCorrect; i++)
            {
                if(!contains(listed, "" + correct[i])) //please forgive me
                {
                    str += correct[i];
                    listed[i] = "" + correct[i]; //father, for I have sinned
                }
                if(i != numIncorrect - 1)
                    str += ", ";
                else
                    str += ". ";
            }
        }
        if(numIncorrect != 0)
        {
            str += "Incorrect: ";
            for(int i = 0; i < numIncorrect; i++)
            {
                if(!contains(listed, "" + incorrect[i])) //please forgive me
                {
                    str += incorrect[i];
                    listed[i] = "" + incorrect[i]; //father, for I have sinned
                }
                if(i != numIncorrect - 1)
                   str += ", ";
                else
                   str += ".";
            }
        }
        return str;
    }
}
