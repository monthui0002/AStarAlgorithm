import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {

    private static final int WIDTH  = 800;
    private static final int HEIGHT = 600;

//    private static final int COLS   = 30;
//    private static final int ROWS   = 30;

    /* main framework */
    private JFrame frame;

    public Main(){}


    public static void main(String[] args) {
        Main window = new Main();
        window.initialize();
        window.createGrid();
    }


    public void createGrid() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        frame.add(panel);
    }


    public void initialize(){
        frame = new JFrame();
        frame.setBounds(0, 0, WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("A Star Algorithm");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
