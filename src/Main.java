import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JPanel implements ActionListener {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;

    private static final int COLS = 30;
    private static final int ROWS = 30;

    public static final int TILE_SIZE = 30;

    private Node[][] node = new Node[COLS][ROWS];

    /* main framework */
    private JFrame frame;

    public Main(){}


    public static void main(String[] args) {
        Main window = new Main();
        window.initialize();
    }


    public void createNode(){
        for(int i = 0; i < COLS; i++){
            for(int j = 0; j < ROWS; j++){
                node[i][j] = new Node(i, j);
            }
        }
        node[10][10].setState(Node.State.CLOSED);
        node[20][20].setState(Node.State.OPEN);
    }


    public void drawNode(Graphics2D g, Node node){
        Color color = Color.BLACK;

        switch (node.getState()) {
            case OPEN:
                color = Color.CYAN;
                break;
            case CLOSED:
                color = Color.ORANGE;
                break;
            case UNVISITED:
                color = Color.WHITE;
                break;
        }
        g.setColor(color);
        g.fillRect(node.pos_x * TILE_SIZE, node.pos_y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(node.pos_x * TILE_SIZE, node.pos_y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }


    public void drawMaze(Graphics2D g) {
        createNode();
        for(int i = 0; i < COLS; i++){
            for(int j = 0; j < ROWS; j++){
                drawNode(g, node[i][j]);
            }
        }
    }


    public void initialize(){
        Main main = new Main();
        main.setPreferredSize(new Dimension(600,600));
        main.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));


        frame = new JFrame("A Star Algorithm");
        frame.setBounds(0, 0, WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().add(main);

        startMouseHandler(main);

        frame.setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze((Graphics2D) g);
    }


    public void startMouseHandler(Main main){
        System.out.println("starting mouse listener...");
        main.addMouseListener(new MouseHandler(this));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
    }
}
