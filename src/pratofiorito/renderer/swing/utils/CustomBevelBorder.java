package pratofiorito.renderer.swing.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.BevelBorder;

public class CustomBevelBorder extends BevelBorder {
    private final int innerSize;
    private final int outerSize;
    
    public CustomBevelBorder(int bevelType) {
        this(bevelType, 1, 1);
    }

    public CustomBevelBorder(int bevelType, 
                             Color highlight, 
                             Color shadow) {
        this(bevelType, 1, 1, highlight, shadow);
    }

    public CustomBevelBorder(int bevelType, 
                             Color highlightOuterColor, 
                             Color highlightInnerColor, 
                             Color shadowOuterColor, 
                             Color shadowInnerColor) {
        this(bevelType, 1, 1, highlightOuterColor, highlightInnerColor, 
                shadowOuterColor, shadowInnerColor);
    }
    
    public CustomBevelBorder(int bevelType, int innerSize) {
        this(bevelType, innerSize, innerSize - 1);
    }

    public CustomBevelBorder(int bevelType,
                             int outerSize,
                             Color highlight, 
                             Color shadow) {
        this(bevelType, outerSize, outerSize - 1, highlight, shadow);
    }

    public CustomBevelBorder(int bevelType,
                             int outerSize,
                             Color highlightOuterColor, 
                             Color highlightInnerColor, 
                             Color shadowOuterColor, 
                             Color shadowInnerColor) {
        this(bevelType, outerSize, outerSize - 1, highlightOuterColor, 
                highlightInnerColor, shadowOuterColor, shadowInnerColor);
    }
    
    public CustomBevelBorder(int bevelType, int outerSize, int innerSize) {
        super(bevelType);
        this.outerSize = outerSize;
        this.innerSize = innerSize;
    }

    public CustomBevelBorder(int bevelType,
                             int outerSize,
                             int innerSize,
                             Color highlight, 
                             Color shadow) {
        super(bevelType, highlight, shadow);
        this.outerSize = outerSize;
        this.innerSize = innerSize;
    }

    public CustomBevelBorder(int bevelType,
                             int outerSize,
                             int innerSize,
                             Color highlightOuterColor, 
                             Color highlightInnerColor, 
                             Color shadowOuterColor, 
                             Color shadowInnerColor) {
        super(bevelType, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor);
        this.outerSize = outerSize;
        this.innerSize = innerSize;
    }
    
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        final int size = innerSize + outerSize;
        insets.set(size, size, size, size);
        return insets;
    }
    
    @Override
    protected void paintRaisedBevel(Component c, Graphics g, int x, int y,
                                    int width, int height)  {
        Color oldColor = g.getColor();
        int h = height;
        int w = width;

        g.translate(x, y);

        paintBorder(g,
                    getHighlightOuterColor(c),
                    getShadowOuterColor(c),
                    w, h, outerSize);
        
        g.translate(outerSize, outerSize);
        
        paintBorder(g,
                    getHighlightInnerColor(c),
                    getShadowInnerColor(c),
                    w - outerSize * 2, h - outerSize * 2, innerSize);
        
        g.translate(-outerSize, -outerSize);

        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    @Override
    protected void paintLoweredBevel(Component c, Graphics g, int x, int y,
                                        int width, int height)  {
        Color oldColor = g.getColor();
        int h = height;
        int w = width;

        g.translate(x, y);

        paintBorder(g,
                    getShadowOuterColor(c),
                    getHighlightOuterColor(c),
                    w, h, outerSize);
        
        g.translate(outerSize, outerSize);
        
        paintBorder(g,
                    getShadowInnerColor(c),
                    getHighlightInnerColor(c),
                    w - outerSize * 2, h - outerSize * 2, innerSize);
        
        g.translate(-outerSize, -outerSize);

        g.translate(-x, -y);
        
        g.translate(-x, -y);
        g.setColor(oldColor);
    }
    
    private void paintBorder(Graphics g, 
                                   Color highlightColor, 
                                   Color shadowColor,
                                   int w, int h, int size) {
        g.setColor(highlightColor);
        g.fillRect(0, 0, size, h - size);
        g.fillRect(size, 0, w - size * 2, size);

        g.fillPolygon(
                new int[] { w - size, w - size, w },
                new int[] { 0, size, 0 },
                3
        );
        
        g.fillPolygon(
                new int[] { 0, 0, size },
                new int[] { h - size, h, h - size },
                3
        );

        g.setColor(shadowColor);
        g.fillRect(size, h - size, w - size, size);
        g.fillRect(w - size, size, size, h - size * 2);
        
        g.fillPolygon(
                new int[] { w, w, w - size },
                new int[] { 0, size, size },
                3
        );
        
        g.fillPolygon(
                new int[] { 0, size, size },
                new int[] { h, h, h - size },
                3
        );
    }
}
