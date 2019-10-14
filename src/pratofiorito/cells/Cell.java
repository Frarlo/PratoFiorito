package pratofiorito.cells;

import pratofiorito.utils.Pos;

public interface Cell {
    
    Pos getPos();
    
    void uncover();
    
    boolean isVisible();
    
    void setFlag(boolean flag);
    
    boolean isFlagged();
}
