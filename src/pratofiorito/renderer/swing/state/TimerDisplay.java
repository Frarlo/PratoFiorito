package pratofiorito.renderer.swing.state;

import java.awt.Graphics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import pratofiorito.Board;

public class TimerDisplay extends BaseIntegerDisplay {
    private final Board board;
    
    private ScheduledFuture<?> future;
    
    public TimerDisplay(Board board) {
        this.board = board;
        this.future = null;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        drawInteger(g, (int) (board.elapsedMillis() / 1000), 
                3, 0, 0, getWidth(), getHeight());
        
        // After the first action, the repaint method is going to get called,
        // allowing us to schedule at fixed rate with an exact measure
        if(future == null && board.hasDoneFirstMove())
            future = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                    () ->  {
                        if(board.isGameOver())
                            future.cancel(false);
                        else
                            repaint();
                    }, 
                    1000 - board.elapsedMillis() % 1000, 1000, TimeUnit.MILLISECONDS);
    }
}
