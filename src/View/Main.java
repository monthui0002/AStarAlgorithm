package View;

import AStarAlgorithm.AStarAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Main extends JPanel {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;

    public static final int COLS = 30;
    public static final int ROWS = 30;

    public static final int TILE_SIZE = HEIGHT/COLS;

    public Node[][] node = new Node[COLS][ROWS];

    public Node startNode;
    public Node targetNode;

    public Node result;

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
        this.startNode = new Node(0, 0);
        this.targetNode = new Node(10, 10);
        this.node = new Node[COLS][ROWS];

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                this.node[i][j] = new Node(i, j);
                this.node[i][j].setH(targetNode);
            }
        }
        this.startNode = this.node[0][0];
        this.targetNode = this.node[10][10];

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        if (i + m >= 0 && i + m < Main.COLS && j + n >= 0 && j + n < Main.ROWS && !(m == 0 && n == 0)) {
                            this.node[i][j].list.add(this.node[i + m][j + n]);
                        }
                    }
                }
            }
        }
    }


    public void initialize() {
        Main main = new Main();
        main.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame = new JFrame("A Star Algorithm");
        frame.getContentPane().add(main);
        frame.setResizable(false);
        frame.pack();
        frame.getContentPane().add(main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        startMouseHandler(main);

        main.start();

        frame.setVisible(true);
    }


    public void start(){
        resetState();
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(this);
        aStarAlgorithm.visited.add(this.startNode);
        aStarAlgorithm.AStar(this.startNode);
        repaint();
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
            case UNVISITED:
                color = Color.WHITE;
                break;
            case VISITED:
                color = Color.ORANGE;
                break;
            case OPEN:
                color = Color.CYAN;
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


    public void drawWay(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2f));
        while (result != null && result.parent != null){
            g.draw(new Line2D.Double((result.pos_x + 0.5) * TILE_SIZE, (result.pos_y + 0.5) * TILE_SIZE, (result.parent.pos_x + 0.5) * TILE_SIZE, (result.parent.pos_y + 0.5) * TILE_SIZE));
            result = result.parent;
        }
    }


    public void drawMaze(Graphics2D g) {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                drawNode(g, this.node[i][j]);
            }
        }
        drawWay(g);
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


    private void resetState(){
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                this.node[i][j].setH(targetNode);
                this.node[i][j].setG(0);
                this.node[i][j].parent = null;
                if(this.node[i][j].getState() == Node.State.VISITED || this.node[i][j].getState() == Node.State.OPEN)
                    this.node[i][j].setState(Node.State.UNVISITED);
            }
        }
    }
}
