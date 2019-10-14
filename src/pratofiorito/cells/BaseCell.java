package pratofiorito.cells;

import pratofiorito.Board;
import pratofiorito.utils.Pos;

public abstract class BaseCell implements Cell {
    protected final Board gameBoard;
    protected final Pos pos;
    
    protected boolean isFlagged;
    
    protected BaseCell(Board gameBoard, Pos pos) {
        this.gameBoard = gameBoard;
        this.pos = pos;
        
        this.isFlagged = false;
    }
    
    @Override
    public Pos getPos() {
        return pos;
    }
    
    @Override
    public void setFlag(boolean isFlagged) {
        if(this.isFlagged == isFlagged)
            return;
        
        this.isFlagged = isFlagged;
        this.gameBoard.flag(isFlagged());
    }
    
    @Override
    public boolean isFlagged() {
        return isFlagged;
    }
}
