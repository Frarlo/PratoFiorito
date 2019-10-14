package pratofiorito.renderer.swing.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import pratofiorito.Board;
import pratofiorito.PratoFiorito;
import pratofiorito.renderer.swing.SwingRenderManager;
import pratofiorito.renderer.swing.utils.BaseButtonRenderer;

public class RestartButton extends BaseButtonRenderer {
    private static final Image ALIVE_IMAGE = loadImage("assets/notyet.png");
    private static final Image SURPRISE_IMAGE = loadImage("assets/ooohoo.png");
    private static final Image WIN_IMAGE = loadImage("assets/yay.png");
    private static final Image LOSS_IMAGE = loadImage("assets/uwot.png");
    
    private static Image loadImage(String path) {
        try {
            return ImageIO.read(ClassLoader.getSystemResource(path));
        } catch (IOException ignored) {
            return null;
        }
    }
    
    private final PratoFiorito game;
    private final Board board;
    
    private Border normalBorder;
    private int oldLineSize;
    
    private Border pressedBorder;
    private int oldPressedLineSize;
    
    public RestartButton(final PratoFiorito game, 
                         final Board board,
                         final SwingRenderManager manager) {
        super(manager);
        
        this.game = game;
        this.board = board;
        
        setBackground(Color.LIGHT_GRAY);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1)
                    game.loadNewBoard(board.getRows(), board.getCols(), board.getBombs());
            }
        });
    }
    
    @Override
    public void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        
        super.paintComponent(g);
        
        if(isPressed()) {
            final Insets ins = getBevelBorder().getBorderInsets(this);
            
            // Set the border to the normal one
            // so the image is centered based on its insets
            // and not the new ones
            setBorderDuringPaint(getNormalBorder());
            
            g.translate(ins.left / 2, ins.top / 2);
            drawCenteredImage(g2d, ALIVE_IMAGE);
            g.translate(-ins.left / 2, -ins.top / 2);
            
            setBorderDuringPaint(getPressedBorder());
        } else {
            setBorderDuringPaint(getNormalBorder());
            
            final Image image;
            if(board.hasWon())
                image = WIN_IMAGE;
            else if(board.hasLost())
                image = LOSS_IMAGE;
            else if(isLeftClickDown())
                image = SURPRISE_IMAGE;
            else
                image = ALIVE_IMAGE;
            
            drawCenteredImage(g2d, image);
        }
    }
    
    protected Border getNormalBorder() {
        final int lineBorderSize = Math.max(1, getSmallerDimension() / 16);
        if(oldLineSize != lineBorderSize) {
            oldLineSize = lineBorderSize;
            normalBorder = new CompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, lineBorderSize), 
                    getBevelBorder());
        }
        return normalBorder;
    }
    
    protected Border getPressedBorder() {
        final int lineBorderSize = Math.max(1, getSmallerDimension() / 16);
        if(oldPressedLineSize != lineBorderSize) {
            oldPressedLineSize = lineBorderSize;
            pressedBorder = new CompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, lineBorderSize), 
                    getLineBorder());
        }
        return pressedBorder;
    }
    
    @Override
    protected void setPressed(boolean isPressed) {
        final boolean old = this.isPressed();
        super.setPressed(isPressed);
           
        if(old != isPressed())
            repaint();
    }
    
    @Override
    protected void setLeftClickDown(boolean isLeftClickDown) {
        final boolean old = this.isLeftClickDown();
        super.setLeftClickDown(isLeftClickDown);
           
        if(old != isLeftClickDown())
            repaint();
    }
}
