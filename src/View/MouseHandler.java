package View;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    Main main;

    public MouseHandler(Main main) {
        this.main = main;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        int x = e.getX() / Main.TILE_SIZE;
        int y = e.getY() / Main.TILE_SIZE;

        if (outOfRange(x, y)) return;

        getClicked(e, x, y);

        this.main.repaint();

        this.main.resetState();
    }

    private boolean outOfRange(int x, int y) {
        return (x < 0 || y < 0 || x >= Main.COLS || y >= Main.ROWS);
    }

    public void getClicked(MouseEvent e, int x, int y) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            this.main.startNode = this.main.node[x][y];
            this.main.node[x][y].setState(Node.State.UNVISITED);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            this.main.targetNode = this.main.node[x][y];
            this.main.node[x][y].setState(Node.State.UNVISITED);
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            if ((this.main.node[x][y].getState() != Node.State.CLOSED)
                    && !this.main.node[x][y].compare(this.main.startNode)
                    && !this.main.node[x][y].compare(this.main.targetNode))
                this.main.node[x][y].setState(Node.State.CLOSED);
            else this.main.node[x][y].setState(Node.State.UNVISITED);
        }
    }
}