
package pratofiorito.cells;

import java.util.Arrays;
import pratofiorito.Board;
import pratofiorito.utils.Pos;

public class EmptyCell extends BaseCell {
    private final Pos[] nearbyPositions;
    
    private Integer nearbyBombs;
    private boolean isVisible;
    
    public EmptyCell(Board gameBoard, Pos pos) {
        super(gameBoard, pos);
        
        this.nearbyPositions = Arrays.stream(
                new Pos[] {
                    pos.clone().addRow(-1).addCol(-1),
                    pos.clone().addRow(-1),
                    pos.clone().addRow(-1).addCol(1),
                    pos.clone().addCol(1),
                    pos.clone().addRow(1).addCol(1),
                    pos.clone().addRow(1),
                    pos.clone().addRow(1).addCol(-1),
                    pos.clone().addCol(-1)
                })
                .filter(p -> gameBoard.isValidPos(p))
                .toArray(Pos[]::new);
        
        this.nearbyBombs = null;
        this.isVisible = false;
    }
    
    @Override
    public void uncover() {
        if(isVisible())
            return;
        
        isVisible = true;
        if(getNearbyBombs() == 0)
            for (Pos pos : nearbyPositions) {
                final Cell cell = gameBoard.getCell(pos);
                if (cell instanceof EmptyCell && !((EmptyCell) cell).isVisible())
                    cell.uncover();
            }
        gameBoard.cellDiscovered();
    }
    
    public void uncoverNearby() {
        if(isVisible() && getAndCalculateNearbyFlags() == getNearbyBombs())
            for (Pos pos : nearbyPositions) {
                final Cell cell = gameBoard.getCell(pos);
                if (!cell.isFlagged())
                    cell.uncover();
            }
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }
    
    
    public int getNearbyBombs() {
        if(nearbyBombs == null)
            calculateNearbyBombs();
        return nearbyBombs;
    }
    
    private void calculateNearbyBombs() {
        this.nearbyBombs = 0;
        for(Pos posToCheck : nearbyPositions)
            if(gameBoard.getCell(posToCheck) instanceof BombCell)
                this.nearbyBombs++;
    }
    
    private int getAndCalculateNearbyFlags() {
        int nearbyFlags = 0;
        for(Pos posToCheck : nearbyPositions)
            if(gameBoard.getCell(posToCheck).isFlagged())
                nearbyFlags++;
        return nearbyFlags;
    }
}
