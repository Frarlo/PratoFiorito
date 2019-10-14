package pratofiorito.renderer.swing.board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import pratofiorito.PratoFiorito;
import pratofiorito.cells.BombCell;
import pratofiorito.renderer.swing.SwingRenderManager;

public class BombCellRenderer extends BaseCellRenderer {
    private final BombCell cell;
    
    public BombCellRenderer(final PratoFiorito game, 
                            final BombCell cell,
                            final SwingRenderManager manager) {
        super(game, cell, manager);
        
        this.cell = cell;
    }
    
    @Override
    public void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        
        if(!cell.isVisible()) {
            super.paintComponent(g);
            
            /*if(!cell.isFlagged()) {
                g.setColor(Color.red);
                g.fillRect(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);   
            }*/
        } else {
            setBorderDuringPaint(getLineBorder());
            
            g.setColor(cell.isLosingBomb() ? Color.RED : Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            
            drawBomb(g2d);
        }
    }
}
