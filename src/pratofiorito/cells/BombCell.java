package pratofiorito.cells;

import pratofiorito.Board;
import pratofiorito.utils.Pos;

public class BombCell extends BaseCell {
    private boolean isLosingBomb;
    
    public BombCell(Board gameBoard, Pos pos) {
        super(gameBoard, pos);
        
        this.isLosingBomb = false;
    }
    
    @Override
    public void uncover() {
        isLosingBomb = true;
        gameBoard.lose();
    }
    
    @Override
    public boolean isVisible() {
        return !isFlagged && gameBoard.hasLost();
    }
    
    public boolean isLosingBomb() {
        return isLosingBomb;
    }
}
