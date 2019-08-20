// https://techdevguide.withgoogle.com/paths/foundational/hangman-challenge-archetypal/#!

import java.util.Scanner;
import java.util.Random;
import java.awt.Insets;
import java.awt.Dimension;


public class Hangman {
    private HangmanLexicon hangmanLexicon;
    private HangmanCanvas hangmanCanvas;
    private HangmanFrame hangmanFrame;
    private HangmanConsole hangmanConsole;
    private Scanner scannedLine;
    private Random random;
    private int guessNumLeft;
    private boolean isFrameOpen = false;

    public static void main(String args[])
    {
        Hangman hangman = new Hangman();
        hangman.init();
        hangman.run();
    }

    public void init()
    {
        this.hangmanLexicon = new HangmanLexicon();
        this.hangmanCanvas = new HangmanCanvas();
        this.hangmanFrame = new HangmanFrame();
        this.hangmanConsole= new HangmanConsole();
        this.scannedLine = new Scanner(System.in);
        this.random = new Random();

        this.hangmanFrame.init();
        this.hangmanConsole.init();
        this.hangmanCanvas.init();
        
        hangmanFrame.add(this.hangmanConsole);
        hangmanFrame.add(this.hangmanCanvas);

        Insets frameInsets = this.hangmanFrame.getInsets();
        Dimension frameDimension = hangmanFrame.getSize();

        hangmanConsole.setLocation(frameInsets.left, frameInsets.top);
        hangmanConsole.setSize( (frameDimension.width) / 2 - frameInsets.left,
                                    (frameDimension.height) - frameInsets.top - frameInsets.bottom);

        hangmanCanvas.setLocation((frameDimension.width) / 2, frameInsets.top);
        hangmanCanvas.setSize( (frameDimension.width) / 2 - frameInsets.right,
                                    (frameDimension.height) - frameInsets.top - frameInsets.bottom);
        
        hangmanCanvas.repaint();
    }

    public void resetCounter()
    {
        this.guessNumLeft = 8;
    }

    public void run()
    {
        boolean quitted = false;
        while (quitted == false)
        {
            // Reset Counter
            this.resetCounter();
            // Reset Canvas
            this.hangmanCanvas.reset();
            // Get a word
            String word = new String();
            word = this.getAWord();
            // Set all letters to not opened
            boolean[] isOpenedChar = this.setWordFalse(word);

            // GUESS THE WORD
            boolean finishedAGame = false;
            while (finishedAGame == false)
            {
                // Show the word
                this.showTheWord(word, isOpenedChar);
                // Show the left guess number
                this.hangmanConsole.outputString(String.format("You have %d guess left.", this.guessNumLeft));
                // Ask a user to guess a letter
                char guessedLetter = '\0';
                guessedLetter = this.askUserToGuessALetter();
                if (this.isFrameOpen == true)
                {
                    // Check the letter if it's correct
                    this.checkALetter(word, isOpenedChar, guessedLetter);
                    // Check if Hangman game finished
                    finishedAGame = this.checkTheGameFinished(word, isOpenedChar);
                }
                else
                {
                    break;
                }
            }
            if (this.isFrameOpen == true)
            {
                // Continue the game or not
                quitted = this.quitTheGame();
            }
            else
            {
                quitted = true;
            }
        }
        this.scannedLine.close();
    }

    private String getAWord()
    {
        String word = new String();
        int wordNum = 0;
        wordNum = this.random.nextInt(hangmanLexicon.getWordCount());
        try
        {
            word = hangmanLexicon.getWord(/*wordNum*/0);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return word;
    }

    private boolean[] setWordFalse(String word)
    {
        boolean[] isOpenedChar = new boolean[word.length()];
        for (int i = 0; i < isOpenedChar.length; i++)
        {
            isOpenedChar[i] = false;
        }
        return isOpenedChar;
    }

    private void showTheWord(String word, boolean[] isOpenedChar)
    {
        this.hangmanConsole.outputString(String.format("The word now looks like this: "));
        StringBuffer wordBuf = new StringBuffer();
        for (int i = 0; i < word.length(); i++)
        {
            if ( isOpenedChar[i] == false )
            {
                wordBuf.append("-");
                this.hangmanConsole.outputString(String.format("-"));
            }
            else
            {
                wordBuf.append(word.charAt(i));
                this.hangmanConsole.outputString(String.format("%c", word.charAt(i)));
            }
        }
        wordBuf.append("\n");
        // System.out.print(wordBuf);
        hangmanCanvas.displayWord(wordBuf.toString());
        this.hangmanConsole.outputString("\n");
    }

    private char askUserToGuessALetter()
    {
        char guessedLetter = '\0';
        boolean validGuess = false;
        while (validGuess == false)
        {
            this.hangmanConsole.outputString(String.format("Your guess: "));
            String guess = this.getInputString();
            if (this.isFrameOpen == false)
            {
                break;
            }

            if (guess.length() == 1)
            {
                guessedLetter = guess.charAt(0);
                validGuess = true;
            }
            else
            {
                this.hangmanConsole.outputString(String.format("You should guess one letter. Guess again.\n"));
            }
        }
        return guessedLetter;
    }

    private String getInputString()
    {
        String s = new String();
        this.isFrameOpen = false;
        this.hangmanConsole.setWaitingNewStringTrue(true);
        while (this.hangmanFrame.isFrameOpen() == true)
        {
            if (this.hangmanConsole.isAbleToGetString() == true)
            {
                s = hangmanConsole.getInputString();
                this.isFrameOpen = true;
                break;
            }
        }
        this.hangmanConsole.setWaitingNewStringTrue(false);
        return s;
    }

    private void checkALetter(String word, boolean[] isOpenedChar, char guessedLetter)
    {
        int searchIndex = 0;
        boolean letterFound = false;
        while (searchIndex != -1)
        {
            searchIndex = word.indexOf(guessedLetter, searchIndex);
            if (searchIndex == -1)
            {
                // not correct letter
                break;
            }
            else
            {
                // the letter is found in the word
                isOpenedChar[searchIndex] = true;
                letterFound = true;
            }
            searchIndex++;
        }
        if (letterFound == false)
        {
            this.hangmanConsole.outputString(String.format("There are no %c's in the word.\n", guessedLetter));
            this.guessNumLeft--;
            hangmanCanvas.nextHangmanStep();
            hangmanCanvas.noteIncorrectGuess(guessedLetter);
        }
        else
        {
            this.hangmanConsole.outputString(String.format("That guess is correct.\n"));
        }
    }

    private boolean checkTheGameFinished(String word, boolean[] isOpenedChar)
    {
        boolean finishedAGame = false;
        boolean guessSuccess = true;
        for (int i = 0; i < isOpenedChar.length; i++)
        {
            if (isOpenedChar[i] == false)
            {
                guessSuccess = false;
                break;
            }
        }

        if (guessSuccess == true)
        {
            this.hangmanConsole.outputString(String.format("You guessed the word: %s\n", word));
            this.hangmanConsole.outputString(String.format("You win.\n"));
            finishedAGame = true;
        }
        else if (guessNumLeft == 0)
        {
            this.hangmanConsole.outputString(String.format("You're completely hung.\n"));
            this.hangmanConsole.outputString(String.format("The word was: %s\n", word));
            this.hangmanConsole.outputString(String.format("You lose.\n"));
            finishedAGame = true;
        }
        return finishedAGame;
    }

    private boolean quitTheGame()
    {
        boolean quitted = false;
        this.hangmanConsole.outputString(String.format("\n"));
        this.hangmanConsole.outputString(String.format("Do you continue the game? (y/n): "));
        String yesOrNo = this.getInputString();
        if (this.isFrameOpen == true)
        {
            if (yesOrNo.charAt(0) == 'N')
            {
                quitted = true;
                hangmanFrame.dispose();
            }
            this.hangmanConsole.outputString(String.format("\n"));
        }
        else
        {
            quitted = true;
        }
        return quitted;
    }

}