package pratofiorito.renderer.swing;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import pratofiorito.renderer.swing.board.CellRendererFactory;
import pratofiorito.renderer.swing.board.EmptyCellRenderer;
import pratofiorito.renderer.swing.board.BombCellRenderer;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pratofiorito.Board;
import pratofiorito.PratoFiorito;
import pratofiorito.cells.BombCell;
import pratofiorito.cells.Cell;
import pratofiorito.cells.EmptyCell;
import pratofiorito.renderer.RenderManager;
import pratofiorito.utils.GameConstants;

public class SwingRenderManager extends JFrame implements RenderManager, GameConstants {
    private final PratoFiorito game;
    private final Map<Class<? extends Cell>, CellRendererFactory> cellToRenderer;
    
    private final JDialog customDifficultyDialog;
    
    private GamePanel currGamePanel;
    
    public SwingRenderManager(final PratoFiorito game) {
        super("Minesweeper");
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }
        
        this.game = game;
        this.cellToRenderer = new HashMap<>();
        this.cellToRenderer.put(EmptyCell.class, cell -> 
                new EmptyCellRenderer(game, (EmptyCell)cell, this));
        this.cellToRenderer.put(BombCell.class, cell -> 
                new BombCellRenderer(game, (BombCell)cell, this));
        
        this.customDifficultyDialog = new CustomDifficultyDialog(this.game, this);
        
        final JMenuBar menuBar = new JMenuBar();
        final JMenu gameMenu = new JMenu("Game");
        
        final JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        newMenuItem.addActionListener((ActionEvent e) -> {
            final Board board = this.game.getBoard();
            this.game.loadNewBoard(board.getRows(), board.getCols(), board.getBombs());
        });
        gameMenu.add(newMenuItem);
        
        gameMenu.addSeparator();
        
        final ButtonGroup difficulty = new ButtonGroup();
        
        final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Beginner");
        beginner.addActionListener((ActionEvent e) ->
            this.game.loadNewBoard(BEGINNER_ROWS, BEGINNER_COLS, BEGINNER_BOMBS)
        );
        difficulty.add(beginner);
        gameMenu.add(beginner);
        
        final JCheckBoxMenuItem intermediate = new JCheckBoxMenuItem("Intermediate");
        intermediate.addActionListener((ActionEvent e) ->
            this.game.loadNewBoard(INTERMEDIATE_ROWS, INTERMEDIATE_COLS, INTERMEDIATE_BOMBS)
        );
        difficulty.add(intermediate);
        gameMenu.add(intermediate);
        
        final JCheckBoxMenuItem expert = new JCheckBoxMenuItem("Expert", true);
        expert.addActionListener((ActionEvent e) ->
            this.game.loadNewBoard(EXPERT_ROWS, EXPERT_COLS, EXPERT_BOMBS)
        );
        difficulty.add(expert);
        gameMenu.add(expert);
        
        final JCheckBoxMenuItem custom = new JCheckBoxMenuItem("Custom...");
        custom.addActionListener((ActionEvent e) -> {
            customDifficultyDialog.setLocationRelativeTo(SwingRenderManager.this);
            customDifficultyDialog.setVisible(true);
        });
        difficulty.add(custom);
        gameMenu.add(custom);
        
        gameMenu.addSeparator();
        
        final JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((ActionEvent e) -> System.exit(0));
        gameMenu.add(exit);
        
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
        
        
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void onBoardChanged(Board newBoard) {
        boolean shouldPack = currGamePanel == null;
        
        if(this.currGamePanel != null)
            remove(currGamePanel);
        
        currGamePanel = new GamePanel(game, newBoard, this);
        add(currGamePanel);
        
        // Re-enable mouse events
        getGlassPane().setVisible(false);
        
        revalidate();
        repaint();
        
        if(shouldPack) {
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    @Override
    public void onGameEnd() {
        currGamePanel.blockMouseEvents(true);
    }
    
    public CellRendererFactory cellToRendererFactory(Class<? extends Cell> claz) {
        return cellToRenderer.get(claz);
    }
}
