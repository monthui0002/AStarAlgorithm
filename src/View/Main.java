package View;

import AStarAlgorithm.AStarAlgorithm;
import AStarAlgorithm.Dijkstra;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JPanel implements Runnable{
    static Main main;

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;

    public static final int COLS = 30;
    public static final int ROWS = 30;

    public static final int TILE_SIZE = HEIGHT/COLS;

    public Node[][] node = new Node[COLS][ROWS];

    public Node startNode;
    public Node targetNode;

    public Node result;

    public MouseHandler mouseHandler;

    /* main framework */
    public static JFrame frame;

    public int speed = 20;

    public double time;

    public Main() {
        createNode();
    }


    public static void main(String[] args) {
        main = new Main();
        main.setBackground(Color.gray);
        Thread thread = new Thread(main);
        thread.start();
    }


    @Override
    public void run() {
        main.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame = new JFrame("A Star Algorithm");
        frame.getContentPane().add(main);
        frame.setResizable(false);
        frame.pack();
        frame.getContentPane().add(main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);

        createButton(main);

        startMouseHandler(main);

        frame.setVisible(true);
    }


    public void createNode() {
        this.startNode = new Node(10, 10);
        this.targetNode = new Node(20, 20);
        this.node = new Node[COLS][ROWS];

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                this.node[i][j] = new Node(i, j);
                this.node[i][j].calculateH(targetNode);
            }
        }
        this.startNode = this.node[10][10];
        this.targetNode = this.node[20][20];


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


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze((Graphics2D) g);
        drawExplanations((Graphics2D) g);
        drawInformation((Graphics2D) g);
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
        int posY = 2 * HEIGHT / 3 + 50;

        g.setFont(new Font("TimesRoman", Font.BOLD, 12));
        g.setStroke(new BasicStroke(1));

        g.drawString("LEFT MOUSE BUTTON = START", posX, posY );
        g.drawString("RIGHT MOUSE BUTTON = TARGET", posX, posY + 40);
        g.drawString("MIDDLE MOUSE BUTTON = WALL", posX, posY + 80);

        g.setColor(Color.BLUE);
        g.fillRect(posX, posY + 120, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.RED);
        g.fillRect(posX, posY + 160, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.BLACK);
        g.fillRect(posX, posY + 200, TILE_SIZE, TILE_SIZE);

        g.setColor(Color.ORANGE);
        g.fillRect(posX + 100, posY + 120, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.CYAN);
        g.fillRect(posX + 100, posY + 160, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.WHITE);
        g.fillRect(posX + 100, posY + 200, TILE_SIZE, TILE_SIZE);

        g.setColor(Color.BLACK);
        g.drawRect(posX, posY + 120, TILE_SIZE, TILE_SIZE);
        g.drawRect(posX, posY + 160, TILE_SIZE, TILE_SIZE);
        g.drawRect(posX, posY + 200, TILE_SIZE, TILE_SIZE);
        g.drawRect(posX + 100, posY + 120, TILE_SIZE, TILE_SIZE);
        g.drawRect(posX + 100, posY + 160, TILE_SIZE, TILE_SIZE);
        g.drawRect(posX + 100, posY + 200, TILE_SIZE, TILE_SIZE);

        g.drawString("START", posX + 40, posY + 120 + TILE_SIZE / 2 + g.getFont().getSize() / 2);
        g.drawString("TARGET", posX + 40, posY + 160 + TILE_SIZE / 2 + g.getFont().getSize() / 2);
        g.drawString("WALL", posX + 40, posY + 200 + TILE_SIZE / 2 + g.getFont().getSize() / 2);
        g.drawString("CLOSED", posX + 100 + 40, posY + 120 + TILE_SIZE / 2 + g.getFont().getSize() / 2);
        g.drawString("OPEN", posX + 100 + 40, posY + 160 + TILE_SIZE / 2 + g.getFont().getSize() / 2);
        g.drawString("UNVISITED", posX + 100 + 40, posY + 200 + TILE_SIZE / 2 + g.getFont().getSize() / 2);

        try {
            BufferedImage logo = ImageIO.read(new File("src/img/HUS.jpg"));
            g.drawImage(logo, HEIGHT + 1, 0,WIDTH - HEIGHT,150, null);
            g.drawLine(HEIGHT + 1, 150,WIDTH,150);
            g.drawLine(HEIGHT + 1, 0,WIDTH,0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedImage logo = ImageIO.read(new File("src/img/info.png"));
            g.drawImage(logo, HEIGHT + 75, HEIGHT/2,150,150, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void drawInformation(Graphics2D g){
        int posX = HEIGHT + 50;
        int posY = 200;

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesNewRomans", Font.BOLD, 15));
        g.drawString("time : \t \t" + + this.time + "s", posX, posY);
        g.drawString("walk length: \t " + targetNode.getG() ,posX, posY + 50 );
    }


    public void drawWay(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2f));
        while (result != null && result.parent != null){
            g.draw(new Line2D.Double((result.pos_x + 0.5) * TILE_SIZE, (result.pos_y + 0.5) * TILE_SIZE, (result.parent.pos_x + 0.5) * TILE_SIZE, (result.parent.pos_y + 0.5) * TILE_SIZE));
            result = result.parent;
        }
    }


    private void createButton(JPanel panel) {
        panel.setLayout(null);

        int posAStarX = HEIGHT + TILE_SIZE;
        int posAStarY = 300;
        int posDSX = HEIGHT + TILE_SIZE;
        int posDSY = 375;

        final Thread[] threadAStar = new Thread[1];
        final Thread[] threadDijkstra = new Thread[1];


        JButton aStar = new JButton("A* algorithm");
        JButton ds = new JButton("Dijkstra algorithm");

        aStar.setBackground(new Color(255, 255, 255));
        ds.setBackground(new Color(255, 255, 255));

        aStar.setBounds(posAStarX,posAStarY,240,50);
        aStar.setFocusPainted(false);
        aStar.setFont(new Font("Serif", Font.BOLD, 15));

        aStar.addActionListener(e -> {
            ds.setBackground(new Color(255, 255, 255));
            aStar.setBackground(new Color(59, 89, 182));
            resetState();
            if(threadDijkstra[0] != null) threadDijkstra[0].stop();
            if(threadAStar[0] != null) threadAStar[0].stop();
            this.removeMouseListener(mouseHandler);
            AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(this);
            threadAStar[0] = new Thread(aStarAlgorithm);
            threadAStar[0].start();
            repaint();
        });


        ds.setBounds(posDSX,posDSY,240,50);
        ds.setFocusPainted(false);
        ds.setFont(new Font("Serif", Font.BOLD, 15));

        ds.addActionListener(e -> {
            aStar.setBackground(new Color(255, 255, 255));
            ds.setBackground(new Color(59, 89, 182));
            resetState();
            if(threadDijkstra[0] != null) threadDijkstra[0].stop();
            if(threadAStar[0] != null) threadAStar[0].stop();
            this.removeMouseListener(mouseHandler);
            Dijkstra dijkstra = new Dijkstra(this);
            threadDijkstra[0] = new Thread(dijkstra);
            threadDijkstra[0].start();
            repaint();
        });

        panel.add(aStar);
        panel.add(ds);
    }


    public void resetState(){
        this.time = 0;
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                this.node[i][j].calculateH(targetNode);
                this.node[i][j].setG(0);
                this.node[i][j].parent = null;
                if(this.node[i][j].getState() == Node.State.VISITED || this.node[i][j].getState() == Node.State.OPEN)
                    this.node[i][j].setState(Node.State.UNVISITED);
            }
        }
    }


    public void startMouseHandler(Main main) {
        mouseHandler = new MouseHandler(main);
        main.addMouseListener(mouseHandler);
    }
}
