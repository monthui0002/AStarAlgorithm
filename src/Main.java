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

    public Node[][] node = new Node[COLS][ROWS];

    public Node startNode;
    public Node targetNode;

    /* main framework */
    public static JFrame frame;

    public Main() {
        createNode();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main window = new Main();
            window.initialize();
        });
    }


    public void createNode() {
        this.startNode = new Node(10, 10);
        this.targetNode = new Node(20, 20);

        this.node = new Node[COLS][ROWS];

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                this.node[i][j] = new Node(i, j);
            }
        }
    }


    public void initialize() {
        Main main = new Main();
        main.setPreferredSize(new Dimension(600, 600));
        main.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));


        frame = new JFrame("A Star Algorithm");
        frame.setBounds(0, 0, WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.getContentPane().add(main);

        startMouseHandler(main);

        frame.setVisible(true);
    }


    public void startMouseHandler(Main main) {
        main.addMouseListener(new MouseHandler(main));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze((Graphics2D) g);
        drawExplanations((Graphics2D) g);
    }


    public void drawNode(Graphics2D g, Node node) {
        Color color = Color.BLACK;

        switch (node.getState()) {
            case OPEN:
                color = Color.WHITE;
                break;
            case CLOSED:
//                color = Color.BLACK;
                break;
        }
        if (node.compare(this.startNode)) {
            color = Color.BLUE;
        } else if (node.compare(this.targetNode)) {
            color = Color.RED;
        }
        g.setColor(color);
        g.fillRect(node.pos_x * TILE_SIZE, node.pos_y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(node.pos_x * TILE_SIZE, node.pos_y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }


    public void drawMaze(Graphics2D g) {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                drawNode(g, this.node[i][j]);
            }
        }
    }


    public void drawExplanations(Graphics2D g) {
        int posX = HEIGHT + 50;
        int posY = HEIGHT / 3;

        g.setColor(Color.BLUE);
        g.fillRect(posX, posY, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.RED);
        g.fillRect(posX, posY + 40, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.BLACK);
        g.fillRect(posX, posY + 80, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.CYAN);

        g.setColor(Color.BLACK);
        g.drawRect(posX, posY, TILE_SIZE, TILE_SIZE);
        g.drawRect(posX, posY + 40, TILE_SIZE, TILE_SIZE);
        g.drawRect(posX, posY + 80, TILE_SIZE, TILE_SIZE);

        int d = 40;
        g.setFont(new Font("TimesRoman", Font.BOLD, 12));

        g.drawString("START", posX + d, posY + TILE_SIZE / 2 + g.getFont().getSize() / 2);
        g.drawString("TARGET", posX + d, posY + 40 + TILE_SIZE / 2 + g.getFont().getSize() / 2);
        g.drawString("WALL", posX + d, posY + 80 + TILE_SIZE / 2 + g.getFont().getSize() / 2);

        g.drawString("LEFT MOUSE BUTTON = START", posX, posY + 200);
        g.drawString("RIGHT MOUSE BUTTON = TARGET", posX, posY + 240);
        g.drawString("MIDDLE MOUSE BUTTON = WALL", posX, posY + 280);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
