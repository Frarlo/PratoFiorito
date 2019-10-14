package pratofiorito.renderer.swing.state;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import pratofiorito.Board;
import pratofiorito.PratoFiorito;
import pratofiorito.renderer.swing.SwingRenderManager;
import pratofiorito.renderer.swing.utils.borders.CustomBevelBorder;

public class StatePanel extends JPanel {
    private final FlagsDisplay flagsDisplay;
    private final RestartButton restartBtn;
    private final TimerDisplay timerDisplay;
    
    public StatePanel(final PratoFiorito game, 
                      final Board board,
                      final SwingRenderManager manager) {
        
        setBorder(new CustomBevelBorder(BevelBorder.LOWERED, 4, 0, Color.WHITE, Color.GRAY));
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        
        this.flagsDisplay = new FlagsDisplay(board);
        add(flagsDisplay);
        
        this.restartBtn = new RestartButton(game, board, manager); 
        add(restartBtn);
        
        this.timerDisplay = new TimerDisplay(board);
        add(timerDisplay);
    }
    
    @Override
    public void setBounds(int x, int y, int width, int height) {
        final int heightWOInsets = height - getInsets().top - getInsets().bottom;
        final int widthWOInsets = width - getInsets().left - getInsets().right;
        
        final int size = heightWOInsets - (heightWOInsets / 6);
        final int numberDisplaysWidth = (size * timerDisplay.getDigitWidth() / timerDisplay.getDigitHeight()) * 3;
        
        flagsDisplay.setBounds(
                getInsets().left + getInsets().right, 
                (height - size) / 2,
                numberDisplaysWidth, size);
        
        restartBtn.setBounds(
                (width - size) / 2, 
                (height - size) / 2, 
                size, size);
        
        timerDisplay.setBounds(
                widthWOInsets - numberDisplaysWidth,
                (height - size) / 2,
                numberDisplaysWidth, size);
        
        super.setBounds(x, y, width, height);
    }
}
