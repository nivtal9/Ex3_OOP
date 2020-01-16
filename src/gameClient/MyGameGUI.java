package gameClient;

import GameElements.Fruit;
import GameElements.Robot;
import Server.*;
import dataStructure.*;
import org.json.JSONObject;
import utils.Point3D;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import GameElements.*;

public class MyGameGUI extends JFrame implements ActionListener, MouseListener,Runnable {
    private JButton start;
    private JButton start2;
    private static graph level_graph;
    private Boolean PaintRobots;
    private double max_node_x;
    private double max_node_y;
    private double min_node_x;
    private double min_node_y;
    private game_service game;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private Thread clientThread;
    private boolean ManuelMode;
    private boolean firstpress=false;
    private Robot choosenrobot;

    public static void main(String[] args) {
        MyGameGUI g = new MyGameGUI();
        g.setVisible(true);
    }

    public MyGameGUI()
    {
        INITGUI();
    }

    private void INITGUI() {
        PaintRobots = false;
        this.setSize(1300, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(this);
        start = new JButton("Manuel Game");
        start2 = new JButton("Auto Game");
        start.addActionListener(this);
        start2.addActionListener(this);
        this.getContentPane().setLayout(new GridLayout());
        this.getContentPane().add(start);
        this.getContentPane().add(start2);
        clientThread = new Thread(this);
        /*MenuBar MB = new MenuBar();
        this.setMenuBar(MB);
        Menu Game = new Menu("Game");
        MB.add(Game);
        MenuItem New_Game = new MenuItem("New Game");
        Game.add(New_Game);
        New_Game.addActionListener(this);*/
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String str = actionEvent.getActionCommand();
        if (str.equals("Manuel Game")) {
            ManuelMode=true;
            JFrame start = new JFrame();
            try {
                int level = Integer.parseInt(JOptionPane.showInputDialog(start, "Enter level between 0-23"));
                if (level < 0 || level > 23) {
                    JOptionPane.showMessageDialog(start, "Invalid level");
                } else {
                    game = Game_Server.getServer(level);
                    String Graph_str = game.getGraph();
                    level_graph = new DGraph();
                    ((DGraph) level_graph).init(Graph_str);
                    this.remove(this.start);
                    this.remove(this.start2);
                    repaint();
                    ManuelsetRobots();
                    game.startGame();
                    clientThread.start();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(start, "Invalid Pattern/Not entered any Number");
                e.printStackTrace();
            }
        }
        if (str.equals("Auto Game")) {
            ManuelMode=false;
            JFrame start = new JFrame();
            try {
                int level = Integer.parseInt(JOptionPane.showInputDialog(start, "Enter level between 0-23"));
                if (level < 0 || level > 23) {
                    JOptionPane.showMessageDialog(start, "Invalid level");
                } else {
                    game = Game_Server.getServer(level);
                    String Graph_str = game.getGraph();
                    level_graph = new DGraph();
                    ((DGraph) level_graph).init(Graph_str);
                    this.remove(this.start);
                    this.remove(this.start2);
                    repaint();
                    game.startGame();
                    new Game_Algo().AutoMode(level_graph, game);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(start, "Invalid Pattern/Not entered any Number");
                e.printStackTrace();
            }
        }
    }

    private void ManuelsetRobots() {
        try {
            String info = game.toString();
            JSONObject line;
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("robots");
            int robot_src = -1;
            int i = 0;
            while (i < rs) {
                while (!level_graph.getV().contains(level_graph.getNode(robot_src))) {
                    try {
                        robot_src = Integer.parseInt(JOptionPane.showInputDialog(start, "Enter intersection (in the map) number to place the Robot. you have: " + (rs - i) + " robots left to place"));
                        if (!level_graph.getV().contains(level_graph.getNode(robot_src))) {
                            JOptionPane.showMessageDialog(start, "intersection not in the map!");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(start, "Invalid Pattern/Not entered any Number");
                    }
                }
                game.addRobot(robot_src);
                robot_src = -1;
                i++;
                PaintRobots = true;
            }
            repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(start, "Invalid Pattern/Not entered any Number");
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponents(Graphics g){
        super.paint(g);
        if (level_graph != null) {
            max_node_x = getMaxMinNode(level_graph.getV(), true, true);
            max_node_y = getMaxMinNode(level_graph.getV(), true, false);
            min_node_x = getMaxMinNode(level_graph.getV(), false, true);
            min_node_y = getMaxMinNode(level_graph.getV(), false, false);
            try {
                String[] splitData = game.toString().split("[:\\}]");
                splitData[6] = splitData[6].substring(1, 8);
                BufferedImage graph_image = ImageIO.read(new File(splitData[6] + ".png"));
                g.drawImage(graph_image, 30, 60, null);
            } catch (Exception e) {
                System.out.println("graph photo not found");
                e.printStackTrace();
            }
            for (node_data nodes : level_graph.getV()) {
                Point3D nodes_src = nodes.getLocation();
                g.setColor(Color.GREEN);
                double nodes_src_x = scale(nodes_src.x(), min_node_x, max_node_x, 50, 1250);
                double nodes_src_y = scale(nodes_src.y(), min_node_y, max_node_y, 50, 650);
                g.fillOval((int) nodes_src_x - 7, (int) nodes_src_y - 7, 15, 15);
                g.drawString("" + nodes.getKey(), (int) nodes_src_x, (int) (nodes_src_y + 20));
                try {
                    for (edge_data edges : level_graph.getE(nodes.getKey())) {
                        g.setColor(Color.RED);
                        Point3D nodes_dest = level_graph.getNode(edges.getDest()).getLocation();
                        double nodes_dest_x = scale(nodes_dest.x(), min_node_x, max_node_x, 50, 1250);
                        double nodes_dest_y = scale(nodes_dest.y(), min_node_y, max_node_y, 50, 650);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(2));
                        g2.drawLine((int) nodes_src_x, (int) nodes_src_y, (int) nodes_dest_x, (int) nodes_dest_y);

                        g.setColor(Color.BLACK);
                        int directed_x = (int) (nodes_src_x * 0.15 + nodes_dest_x * 0.85);
                        int directed_y = (int) (nodes_src_y * 0.15 + nodes_dest_y * 0.85);
                        g.fillOval(directed_x - 4, directed_y - 2, 7, 7);
                        g.setColor(Color.DARK_GRAY);
                        g.drawString("" + df2.format(edges.getWeight()), directed_x, directed_y);
                    }
                } catch (Exception e) {
                    //this node has no edges, the graph is still being initialized...
                }
            }
            for (int i = 0; i < game.getFruits().size(); i++) {
                try {
                    BufferedImage fruit_image = ImageIO.read(new File("data/apple.jpeg"));
                    Fruit f = new Fruit(game.getFruits(), i);
                    double fruit_src_x = scale(f.getLocation().x(), min_node_x, max_node_x, 50, 1250);
                    double fruit_src_y = scale(f.getLocation().y(), min_node_y, max_node_y, 50, 650);
                    g.drawImage(fruit_image, (int) fruit_src_x - 15, (int) fruit_src_y - 10, null);
                    g.setColor(Color.BLACK);
                    if (f.getType() == 1) {
                        g.drawString(f.getValue() + " ↑", (int) fruit_src_x - 9, (int) fruit_src_y + 11);
                    }
                    if (f.getType() == -1) {
                        g.drawString(f.getValue() + " ↓", (int) fruit_src_x - 9, (int) fruit_src_y + 11);
                    }
                } catch (Exception e) {
                    System.out.println("404-file not found!");
                    e.printStackTrace();
                }
            }
            if (PaintRobots) {
                for (int i = 0; i < game.getRobots().size(); i++) {
                    try {
                        BufferedImage Robot_image = ImageIO.read(new File("data/robot3.png"));
                        Robot Robot_src = new Robot(game.getRobots(), i);
                        double robot_src_x = scale(Robot_src.getLocation().x(), min_node_x, max_node_x, 50, 1250);
                        double robot_src_y = scale(Robot_src.getLocation().y(), min_node_y, max_node_y, 50, 650);
                        g.drawImage(Robot_image, (int) robot_src_x - 15, (int) robot_src_y - 10, null);
                    } catch (Exception e) {
                        System.out.println("404-file not found!");
                        e.printStackTrace();
                    }
                }
            }
            g.drawString("00:" + (game.timeToEnd() / 1000)+" Total Score: "+new Robot(game.toString()).TotalScore(), 150, 50);
        }
    }
    @Override
    public void paint(Graphics g) {
        Image IBI = createImage(1300, 700);
        Graphics gdb = IBI.getGraphics();
        paintComponents(gdb);
        g.drawImage(IBI,0,0,this);
    }

    /**
     * @param data  denote some data to be scaled
     * @param r_min the minimum of the range of your data
     * @param r_max the maximum of the range of your data
     * @param t_min the minimum of the range of your desired target scaling
     * @param t_max the maximum of the range of your desired target scaling
     * @return
     */
    private double scale(double data, double r_min, double r_max, double t_min, double t_max) {
        double res = ((data - r_min) / (r_max - r_min)) * (t_max - t_min) + t_min;
        return res;
    }

    /**
     * @param col Collection of node_data in level_graph
     * @param b   true to get MaxNode false to get MinNode
     * @param c   true to get x false to get y
     * @return
     */
    private double getMaxMinNode(Collection<node_data> col, boolean b, boolean c) {
        double MaxNode = 0, MinNode = 0;
        if (b) {
            MaxNode = Integer.MIN_VALUE;
            for (node_data nd : col) {
                if (c) {
                    if (MaxNode < nd.getLocation().x()) {
                        MaxNode = nd.getLocation().x();
                    }
                } else {
                    if (MaxNode < nd.getLocation().y()) {
                        MaxNode = nd.getLocation().y();
                    }
                }
            }
        } else {
            MinNode = Integer.MAX_VALUE;
            for (node_data nd : col) {
                if (c) {
                    if (MinNode > nd.getLocation().x()) {
                        MinNode = nd.getLocation().x();
                    }
                } else {
                    if (MinNode > nd.getLocation().y()) {
                        MinNode = nd.getLocation().y();
                    }
                }
            }
        }
        if (b) return MaxNode;
        else return MinNode;
    }

    @Override
    public void mouseClicked(MouseEvent e1) {
        if(ManuelMode) {
            if(!firstpress) {
                for (int i=0;i<game.getRobots().size();i++){
                    choosenrobot=new Robot(game.getRobots(),i);
                    double RobotlocationX = scale(level_graph.getNode(choosenrobot.getSrc()).getLocation().x(), min_node_x, max_node_x, 50, 1250);
                    double RobotlocationY = scale(level_graph.getNode(choosenrobot.getSrc()).getLocation().y(), min_node_y, max_node_y, 50, 650);
                    double e1_get_y = scale(e1.getY(), 0, 700, 0, 700);
                    double e1_get_x = scale(e1.getX(), 0, 1300, 0, 1300);
                    if(Math.abs(RobotlocationX-e1_get_x)<15&&Math.abs(RobotlocationY-e1_get_y)<15){
                        firstpress=true;
                        break;
                    }
                }
            }
            if(firstpress) {
                double e1_get_y = scale(e1.getY(), 0, 700, 0, 700);
                double e1_get_x = scale(e1.getX(), 0, 1300, 0, 1300);
                for (edge_data ed : level_graph.getE(choosenrobot.getSrc())) {
                    double ndlocationX = scale(level_graph.getNode(ed.getDest()).getLocation().x(), min_node_x, max_node_x, 50, 1250);
                    double ndlocationY = scale(level_graph.getNode(ed.getDest()).getLocation().y(), min_node_y, max_node_y, 50, 650);
                    if (Math.abs(e1_get_x - ndlocationX) < 15 && Math.abs(e1_get_y - ndlocationY) < 15) {
                        game.chooseNextEdge(choosenrobot.getId(), ed.getDest());
                        firstpress=false;
                    }
                }
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) { /*not used*/}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) { /*not used*/}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) { /*not used*/}

    @Override
    public void mouseExited(MouseEvent mouseEvent) { /*not used*/}

    @Override
    public void run() {
        int dt = 50;
        while (game.isRunning()) {
            try {
                game.move();
                repaint();
                Thread.sleep(dt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(null, "GameOver, Final Score is: "+new Robot(game.toString()).TotalScore());
    }
}