package pratofiorito.renderer.swing.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Map;
import pratofiorito.PratoFiorito;
import pratofiorito.cells.EmptyCell;
import pratofiorito.renderer.swing.SwingRenderManager;

public class EmptyCellRenderer extends BaseCellRenderer {
    private final EmptyCell cell;
    
    public EmptyCellRenderer(final PratoFiorito game, 
                             final EmptyCell cell,
                             final SwingRenderManager manager) {
        super(game, cell, manager);
        
        this.cell = cell;
    }
    
    @Override
    public void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
                
        if(game.getBoard().hasLost() && cell.isFlagged()) {
            setBorderDuringPaint(getLineBorder());
            
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            
            drawBomb(g2d);
            
            g.setColor(Color.RED);
            final int smallerDim = getSmallerDimension();
            g2d.setStroke(new BasicStroke(Math.max(smallerDim / 14, 2)));
            
            final int xBorder = smallerDim / 7;
            g.drawLine(xBorder, xBorder, getWidth() - xBorder, getHeight() - xBorder);
            g.drawLine(xBorder, getHeight() - xBorder, getWidth() - xBorder, xBorder);
        } else if(!cell.isVisible()) {
            super.paintComponent(g);
        } else {
            setBorderDuringPaint(getLineBorder());
            
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            
            final int bombs = cell.getNearbyBombs();
            if(bombs != 0) {
                g.setColor(getColorForBombs(bombs));
                g.setFont(new Font("Arial", Font.BOLD, getHeight() - getHeight() / 4));
                
                final Map<?, ?> desktopHints = (Map<?, ?>) 
                        Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
                if (desktopHints != null)
                    g2d.setRenderingHints(desktopHints);
                
                drawCenteredString(g, String.valueOf(bombs), getWidth() / 2, getHeight() / 2);
            }
        }
    }
    
    private Color getColorForBombs(int bombs) {
        switch(bombs) {
            case 1:
                return Color.BLUE;
            case 2:
                return new Color(0, 128, 0);
            case 3:
                return Color.RED;
            case 4:
                return new Color(0, 0, 128);
            case 5:
                return new Color(128, 0, 0);
            case 6:
                return new Color(0, 128, 128);
            case 7:
                return Color.BLACK;
            case 8:
                return Color.GRAY;
            default:
                return Color.BLACK;
        }
    }
    
    private void drawCenteredString(Graphics g, String text, int x, int y) {
        final Font font = g.getFont();
        final FontMetrics metrics = g.getFontMetrics(font);
        
        x = (int) (x - (metrics.stringWidth(text)) / 2);
        y = (int) (y - (metrics.getHeight() / 2) + metrics.getAscent());
        g.drawString(text, x, y);
    }
}
