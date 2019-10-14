package pratofiorito.renderer.swing;

import pratofiorito.renderer.swing.board.BoardPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import pratofiorito.Board;
import pratofiorito.PratoFiorito;
import pratofiorito.renderer.swing.state.StatePanel;
import pratofiorito.renderer.swing.utils.borders.PartialLineBorder;

public class GamePanel extends JPanel {
    private static final int MIN_DIM = 1000;
    private static final int MAX_GAP_SIZE = 5;
    
    private final SwingRenderManager manager;
    
    private final StatePanel statePanel;
    private final BoardPanel boardPanel;
    
    private final JPanel glassPane;
    
    public GamePanel(final PratoFiorito game, 
                     final Board board,
                     final SwingRenderManager manager) {
        this.manager = manager;
        
        setBorder(new PartialLineBorder(Color.WHITE, 4, 
                PartialLineBorder.TOP_LINE | PartialLineBorder.LEFT_LINE));
        setBackground(Color.LIGHT_GRAY);
        
        // Glass Pane
        
        glassPane = new JPanel();
        glassPane.setVisible(false);
        glassPane.setOpaque(false);
        // Add a mouse listener so it can block any mouse events
        glassPane.addMouseListener(new MouseAdapter() {});
        
        add(glassPane);
        
        // Make components
        
        this.statePanel = new StatePanel(game, board, manager);
        add(statePanel);
        
        this.boardPanel = new BoardPanel(game, board, manager);
        add(boardPanel);
        
        // Set default size
        
        final int w, h;
        if(board.getRows() > board.getCols()) {
            h = MIN_DIM;
            w = h / board.getRows() * board.getCols();
        } else {
            w = MIN_DIM;
            h = w / board.getCols() * board.getRows();
        }
        setPreferredSize(new Dimension(w, h));
        
        // AbsoluteLayout
        
        setLayout(null);
    }
    
    void blockMouseEvents(boolean block) {
        glassPane.setVisible(block);
        repaint();
    }
    
    @Override
    public void setBounds(final int x, final int y, final int width, final int height) {
        // This function is full of magic numbers rip ;-;
                
        // Max Dimension that the board could cover
        // if the borders and the state panel weren't rendered
        final Dimension maxBoardDim = boardPanel.fitPanel(width, height);
        // Minimum size of the gaps between the 2 panels and of the external borders
        // It goes from 1 to MAX_GAP_SIZE
        final int gapSize = Math.max(1, Math.min(MAX_GAP_SIZE, maxBoardDim.height / 50));
        // Max width the board should use
        final int maxBoardWidth = width 
                - getInsets().left - getInsets().right 
                - gapSize * 2;
        // Height of the state panel
        final int statePaneHeight = height / 9;
        // Max height the board should use
        final int maxBoardHeight = height 
                // State panel size
                - statePaneHeight
                - gapSize
                // Start and end gap
                - getInsets().top - getInsets().bottom 
                - gapSize * 2;
        // Dimension covered by the board
        final Dimension boardDim = boardPanel.fitPanel(maxBoardWidth, maxBoardHeight);
        // The stuff is all at the center of the pane
        int boardX = (width - boardDim.width) / 2;
        int boardY = getInsets().top + gapSize + (maxBoardHeight - boardDim.height) / 2;
                
        statePanel.setBounds(boardX, boardY, boardDim.width, statePaneHeight);
                
        boardY += statePaneHeight + gapSize;
        boardPanel.setBounds(boardX, boardY, boardDim.width, boardDim.height);
        glassPane.setBounds(boardX, boardY, boardDim.width, boardDim.height);
                
        super.setBounds(x, y, width, height);
    }
}
