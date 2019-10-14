package pratofiorito;

import java.util.Random;
import pratofiorito.cells.BombCell;
import pratofiorito.cells.Cell;
import pratofiorito.cells.EmptyCell;
import pratofiorito.utils.Pos;
import pratofiorito.utils.SimpleTimer;

public class Board {
    private final PratoFiorito game;
    private final Cell[][] cells;
    
    private final int rows;
    private final int cols;
    private final int bombs;
    
    private final SimpleTimer timer;
    private boolean firstMove;
    
    private int cellsToDiscover;
    private int remainingFlags;

    private GameState gameState;
    
    public Board(PratoFiorito game, int rows, int cols, int bombs) {
        this.game = game;
        this.rows = rows;
        this.cols = cols;
        this.bombs = bombs;
        
        this.timer = new SimpleTimer();
        this.firstMove = false;
        
        this.cellsToDiscover = rows * cols - this.bombs;
        this.remainingFlags = this.bombs;
        
        this.gameState = GameState.PLAYING;
        
        this.cells = new Cell[rows][cols];
        for(int row = 0; row < rows; row++)
            for(int col = 0; col < cols; col++)
                this.cells[row][col] = new EmptyCell(this, new Pos(row, col));
        
        final Random random = new Random();
        while(bombs > 0) {
            int index;
            do {
                index = random.nextInt(rows * cols);
            } while(getCell(index) instanceof BombCell);
            
            final int row = index / cols;
            final int col = index % cols;
            cells[row][col] = new BombCell(this, new Pos(row, col));
            bombs--;
        }
    }
    
    public boolean isValidPos(Pos pos) {
        return pos.getCol() >= 0 && pos.getCol() < cols &&
                pos.getRow() >= 0 && pos.getRow() < rows;
    }
    
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }
    
    public Cell getCell(Pos pos) {
        return getCell(pos.getRow(), pos.getCol());
    }
    
    public Cell getCell(int index) {
        final int row = index / cols;
        final int col = index % cols;
        return cells[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getBombs() {
        return bombs;
    }
    
    public void flag(boolean flagged) {
        // In case it hasn't done it yet
        doFirstMove();
        
        remainingFlags += flagged ? -1 : 1;
    }

    public int getRemainingFlags() {
        return remainingFlags;
    }
    
    private void doFirstMove() {
        if(hasDoneFirstMove() || hasLost())
            return;
        
        firstMove = true;
        timer.reset();
    }
    
    public boolean hasDoneFirstMove() {
        return firstMove;
    }
    
    public long elapsedMillis() {
        if(!firstMove)
            return 0;
        return timer.elapsedMillis();
    }
    
    public void lose() {
        timer.stop();
        gameState = GameState.LOST;
    }
    
    public boolean hasLost() {
        return gameState == GameState.LOST;
    }
    
    public void cellDiscovered() {
        // In case it hasn't done it yet
        doFirstMove();
        
        if(cellsToDiscover == 0)
            return;
        
        cellsToDiscover--;
        if(cellsToDiscover <= 0)
            win();
    }
    
    private void win() {
        timer.stop();
        gameState = GameState.WON;
    }
    
    public boolean hasWon() {
        return gameState == GameState.WON;
    }
    
    public void endGame() {
        timer.stop();
        gameState = GameState.RESTARTED;
    }
    
    public boolean isGameOver() {
        return gameState != GameState.PLAYING;
    }
    
    public enum GameState {
        PLAYING, RESTARTED, WON, LOST;
    }
}
