package pratofiorito;

import pratofiorito.cells.Cell;
import pratofiorito.cells.EmptyCell;
import pratofiorito.utils.GameConstants;
import pratofiorito.renderer.swing.SwingRenderManager;
import pratofiorito.renderer.RenderManager;

public class PratoFiorito implements GameConstants {
    private final RenderManager renderer;
    
    private Board board;
    
    public PratoFiorito() {
        this.renderer = new SwingRenderManager(this);
        
        loadNewBoard(EXPERT_ROWS, EXPERT_COLS, EXPERT_BOMBS);
    }
    
    public void loadNewBoard(int rows, int cols, int bombs) {
        if(this.board != null)
            this.board.endGame();
        
        this.board = new Board(this, rows, cols, bombs);
        this.renderer.onBoardChanged(board);
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void clickCell(final Cell clicked, boolean doubleClick) {
        if(board.isGameOver())
            return;
                            
        if (!clicked.isVisible()) {
            if (!clicked.isFlagged())
                clicked.uncover();
        } else if (doubleClick /*&& clicked.isVisible()*/) {
            if(clicked instanceof EmptyCell)
                ((EmptyCell)clicked).uncoverNearby();
        }
        
        if(board.isGameOver())
            renderer.onGameEnd();
    }
    
    public void rightClickCell(final Cell clicked) {
        if(board.isGameOver())
            return;
        
        if (!clicked.isVisible() && board.getRemainingFlags() > 0)
            clicked.setFlag(!clicked.isFlagged());
        
        if(board.isGameOver())
            renderer.onGameEnd();
    }
}
