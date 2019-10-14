package pratofiorito.renderer.swing.state;

import java.awt.Graphics;
import pratofiorito.Board;

public class FlagsDisplay extends BaseIntegerDisplay {
    private final Board board;
    
    public FlagsDisplay(Board board) {
        this.board = board;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        drawInteger(g, board.getRemainingFlags(), 3, 0, 0, getWidth(), getHeight());
    }
}
