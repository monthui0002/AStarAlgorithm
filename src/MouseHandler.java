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
        System.out.println(x + " " + y);
        getClicked(e);
    }

    private boolean outOfRange(int x, int y) {
        return (x < 0 || y < 0 || x >= Main.TILE_SIZE || y >= Main.TILE_SIZE);
    }

    public void getClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            System.out.println("left clicked");
        } else if (SwingUtilities.isRightMouseButton(e)) {
            System.out.println("right clicked");
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            System.out.println("middle clicked");
        }
    }
}