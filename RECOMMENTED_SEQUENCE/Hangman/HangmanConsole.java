
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class HangmanConsole extends TextArea {

    // private static final int CONSOLE_WIDTH = 360;
    // private static final int CONSOLE_HEIGHT = 480;
    //private static final String PROMPT_STRING = "[HANGMAN]$ ";
    private HangmanTextListener textListener;
    private volatile int beforeCaretPos = 0;
    private volatile int afterCaretPos = 0;
    private String inputString;
    private volatile boolean isNewString = false;
    private boolean waitingNewString = false;

    public void init()
    {
        textListener = new HangmanTextListener();
        this.addTextListener(textListener);
        // this.setSize(CONSOLE_WIDTH, CONSOLE_HEIGHT);
        this.setBackground(Color.LIGHT_GRAY);
        this.append("Welcome to Hangman!\n\n");
        this.setCaretPosition(this.getText().length());
    }

    public String getInputString()
    {
        isNewString = false;
        this.setWaitingNewStringTrue(false);
        return this.inputString;
    }

    public boolean isAbleToGetString()
    {
        return this.isNewString;
    }

    public void setWaitingNewStringTrue(boolean b)
    {
        this.waitingNewString = b;
    }

    public void outputString(String outputStr)
    {
        //this.beforeCaretPos = this.afterCaretPos;
        this.append(outputStr);
        this.afterCaretPos = this.getText().length();
        this.setCaretPosition(this.afterCaretPos);
        //System.out.format("outputString: %d %d\n", beforeCaretPos, afterCaretPos);
    }

    public class HangmanTextListener implements TextListener {

        public void textValueChanged(TextEvent arg0)
        {
            if (waitingNewString == true)
            {
                TextArea textArea = (TextArea)arg0.getSource();
                String s = textArea.getText();
                // System.out.println(s);
                // When \n is inputted, get text
                if ( s.charAt(s.length() - 1) == '\n')
                {
                    beforeCaretPos = afterCaretPos;
                    afterCaretPos = textArea.getText().length();
                    // print out string without LF at the end.
                    // System.out.format("TextChanged: %d %d\n", beforeCaretPos, afterCaretPos);
                    inputString = s.substring(beforeCaretPos, afterCaretPos - 1).toUpperCase();
                    isNewString = true;
                    // System.out.println(inputString);
                }
            }
        }

    }

}