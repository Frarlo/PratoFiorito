package pratofiorito.renderer;

import pratofiorito.Board;

public interface RenderManager {
    void onBoardChanged(Board newBoard);
    
    void onGameEnd();
}
