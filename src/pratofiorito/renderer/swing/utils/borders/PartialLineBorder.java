package pratofiorito.renderer.swing.utils.borders;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import javax.swing.border.AbstractBorder;

public class PartialLineBorder extends AbstractBorder {
    public static final int TOP_LINE = 0x1;
    public static final int RIGHT_LINE = 0x2;
    public static final int BOTTOM_LINE = 0x4;
    public static final int LEFT_LINE = 0x8;
    
    private static final int DEFAULT_BITMASK = 
            TOP_LINE | RIGHT_LINE | BOTTOM_LINE | LEFT_LINE;
    
    protected final int thickness;
    protected final Color lineColor;
    protected final int bitmask;
    
    public PartialLineBorder(Color color) {
        this(color, DEFAULT_BITMASK);
    }
    
    public PartialLineBorder(Color color, int bitmask) {
        this(color, 1, bitmask);
    }
    
    public PartialLineBorder(Color color, int thickness, Void unused)  {
        this(color, thickness, DEFAULT_BITMASK);
    }
    
    public PartialLineBorder(Color color, int thickness, int bitmask)  {
        this.lineColor = color;
        this.thickness = thickness;
        this.bitmask = bitmask;
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (thickness <= 0 || !(g instanceof Graphics2D))
            return;
        
        final Color oldColor = g.getColor();
        
        g.setColor(lineColor);
        
        g.translate(x, y);
        
        if((bitmask & TOP_LINE) != 0)
            g.fillRect(0, 0, width, thickness);
        if((bitmask & RIGHT_LINE) != 0)
            g.fillRect(width - thickness, 0, width, height);
        if((bitmask & BOTTOM_LINE) != 0)
            g.fillRect(0, height - thickness, width, height);
        if((bitmask & LEFT_LINE) != 0)
            g.fillRect(0, 0, thickness, height);
        
        g.translate(-x, -y);
        
        g.setColor(oldColor);
    }
    
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(thickness, thickness, thickness, thickness);
        return insets;
    }

    public Color getLineColor() {
        return lineColor;
    }
    
    public int getThickness() {
        return thickness;
    }
}
