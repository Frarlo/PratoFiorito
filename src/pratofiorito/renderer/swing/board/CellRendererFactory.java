package pratofiorito.renderer.swing.board;

import java.awt.Component;
import pratofiorito.cells.Cell;

public interface CellRendererFactory {
    Component makeRenderer(Cell cell);
}
