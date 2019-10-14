package pratofiorito.renderer.swing.board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import pratofiorito.PratoFiorito;
import pratofiorito.cells.Cell;
import pratofiorito.renderer.swing.SwingRenderManager;
import pratofiorito.renderer.swing.utils.BaseButtonRenderer;

public class BaseCellRenderer extends BaseButtonRenderer implements CellRenderer {
    protected final PratoFiorito game;
    private final Cell cell;
    
    public BaseCellRenderer(final PratoFiorito game, 
                            final Cell cell,
                            final SwingRenderManager manager) {
        super(manager);
        
        this.game = game;
        this.cell = cell;
    }
    
    @Override
    public void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        if(!cell.isFlagged() && isPressed()) {
            setBorderDuringPaint(getLineBorder());
        } else {
            setBorderDuringPaint(getBevelBorder());
        
            if(cell.isFlagged())
                drawFlag(g2d);
        }
    }
    
    @Override
    protected void setPressed(boolean isPressed) {
        final boolean old = this.isPressed();
        super.setPressed(isPressed);
           
        if(old != isPressed())
            repaint();
    }
    
    private class Triangle extends Path2D.Double {
        
        public Triangle(double x1, double y1, 
                        double x2, double y2, 
                        double x3, double y3) {
            moveTo(x1, y1);
            lineTo(x2, y2);
            lineTo(x3, y3);
            closePath();
        }
    }
    
    private class Rectangle extends Path2D.Double {
        
        public Rectangle(double x, double y, double width, double height) {
            moveTo(x, y);
            lineTo(x + width, y);
            lineTo(x + width, y + height);
            lineTo(x, y + height);
            closePath();
        }
    }
    
    private void drawFlag(Graphics2D g2d) {
        final Flag flag = new Flag(getSmallerDimension());
        
        double toTranslate = 0;
        if(getWidth() < getHeight()) {
            toTranslate = (getHeight() - getWidth()) / 2;
            g2d.translate(0, toTranslate);
        } else if(getWidth() > getHeight()) {
            toTranslate = (getWidth() - getHeight()) / 2;
            g2d.translate(toTranslate, 0);
        }
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.fill(flag.bastone);
        g2d.fill(flag.smallBase);
        g2d.fill(flag.base);
        
        g2d.setColor(Color.red);
        g2d.fill(flag.flag);
        
        if(getWidth() < getHeight()) {
            g2d.translate(0, -toTranslate);
        } else if(getWidth() > getHeight()) {
            g2d.translate(-toTranslate, 0);
        }
    }
    
    private class Flag {
        private final Path2D.Double bastone;
        private final Path2D.Double flag;
        private final Path2D.Double smallBase;
        private final Path2D.Double base;
        
        public Flag(double size) {
            bastone = new Path2D.Double();
            bastone.moveTo(size / 2.111169754715409D, size / 1.369369369369369D);
            bastone.lineTo(size / 2.111169754715409D, size / 4.606060606060606D);
            bastone.lineTo(size / 1.9D, size / 4.606060606060606D);
            bastone.lineTo(size / 1.9D, size / 1.369369369369369D);
            bastone.closePath();
            
            flag = new Triangle(
                    size / 1.9D, size / 5.477477477477477D,
                    size / 3.92258064516129D, size / 2.814814814814815D,
                    size / 1.9D, size / 1.894080996884735D
            );
            
            smallBase = new Rectangle(
                    size / 2.660039900598509D, size / 1.472154963680387D, 
                    size / 4.030333563133054D, size / 13.81818181818182D
            );
            
            base = new Rectangle(
                    size / 3.758283058055583D, size / 1.346109566233904D,
                    size / 2.137533398959359D, size / 10.7939213179946D
            );
            
        }
    }
    
    private static final Image bombImage;
    
    static {
        Image temp;
        try {
            temp = ImageIO.read(ClassLoader.getSystemResource("assets/nabombafu.png"));
        } catch (IOException ignored) {
            temp = null;
        }
        bombImage = temp;
    }
    
    protected void drawBomb(Graphics2D g2d) {
        drawCenteredImage(g2d, bombImage);
    }
}
