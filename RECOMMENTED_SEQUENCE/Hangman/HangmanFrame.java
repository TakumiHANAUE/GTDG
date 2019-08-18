import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HangmanFrame extends Frame {

    private static final int FRAME_WIDTH = 720;
    private static final int FRAME_HEIGHT = 480;
    private WinAdapter winadapter;
    private boolean isDisposed = false;

    public void init()
    {
        winadapter = new WinAdapter();
        this.addWindowListener(winadapter);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Hangman");
        this.setResizable(false);
        this.setVisible(true);
    }

    public boolean isFrameOpen()
    {
        return !(this.isDisposed);
    }

    public class WinAdapter extends WindowAdapter {
        public void windowClosing(WindowEvent close)
        {
            isDisposed = true;
            dispose();
        }
    }
}