package pratofiorito.renderer.swing.board;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import pratofiorito.Board;
import pratofiorito.PratoFiorito;
import pratofiorito.cells.Cell;
import pratofiorito.renderer.swing.utils.SquareGridLayout;
import pratofiorito.renderer.swing.SwingRenderManager;
import pratofiorito.renderer.swing.utils.borders.CustomBevelBorder;

public class BoardPanel extends JPanel {
    private final Board board;

    public BoardPanel(final PratoFiorito game, 
                      final Board board,
                      final SwingRenderManager manager) {
        this.board = board;
        
        setLayout(new SquareGridLayout(board.getRows(), board.getCols()));
        setBorder(new CustomBevelBorder(BevelBorder.LOWERED, 4, 0, Color.WHITE, Color.GRAY));
        
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                final Cell cell = board.getCell(row, col);
                final Component component = manager
                        .cellToRendererFactory(cell.getClass())
                        .makeRenderer(cell);
                
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1)
                            game.clickCell(cell, e.getClickCount() == 2);
                        else if (e.getButton() == MouseEvent.BUTTON3)
                            game.rightClickCell(cell);
                        manager.repaint();
                    }
                });
                add(component);
            }
        }
    }

    public Dimension fitPanel(int width, int height) {
        final Insets insets = getInsets();
        final int actualWidth = width - insets.left - insets.right;
        final int actualHeight = height - insets.top - insets.bottom;
        
        final int fitWidth = fitSize(actualWidth, board.getCols());
        final int possibleHeight = fitWidth / board.getCols() * board.getRows();
        
        int w, h;
        if (fitWidth <= actualWidth && possibleHeight <= actualHeight) {
            w = fitWidth;
            h = possibleHeight;
        } else {
            h = fitSize(actualHeight, board.getRows());
            w = h / board.getRows() * board.getCols();
        }
        
        w += insets.left + insets.right;
        h += insets.top + insets.bottom;
        
        return new Dimension(w, h);
    }

    private static int fitSize(int maxSize, int nComponents) {
        return maxSize - (maxSize % nComponents);
    }
}
