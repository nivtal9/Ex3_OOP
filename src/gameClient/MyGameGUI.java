package gameClient;

import Server.*;
import dataStructure.*;
import org.json.JSONObject;
import utils.Point3D;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

public class MyGameGUI extends JFrame implements ActionListener {
    private JButton start;
    private JButton start2;
    private static graph level_graph;
    private Boolean PaintRobots;
    private game_service game;
    private BufferedImage Robot_image;
    private BufferedImage fruit_image;
    private BufferedImage graph_image;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static final double EPSILON = 0.0000001;

    public static void main(String[] args) {
        MyGameGUI g = new MyGameGUI();
        g.setVisible(true);
    }

    public MyGameGUI() {
        //this.MC=graph.getMC();
        INITGUI();
    }

    private void INITGUI() {
        PaintRobots = false;
        this.setSize(1300, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start = new JButton("Manuel Game");
        start2 = new JButton("Auto Game");
        start.addActionListener(this);
        start2.addActionListener(this);
        this.getContentPane().setLayout(new GridLayout());
        this.getContentPane().add(start);
        this.getContentPane().add(start2);

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String str = actionEvent.getActionCommand();
        if (str.equals("Manuel Game")) {
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
                    game.startGame();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(start, "Invalid Pattern/Not entered any Number");
                e.printStackTrace();
            }
        }
        if (str.equals("Auto Game")) {
            JOptionPane.showMessageDialog(start, "Not Ready yet !");
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (level_graph != null) {
            double max_node_x = getMaxMinNode(level_graph.getV(), true, true);
            double max_node_y = getMaxMinNode(level_graph.getV(), true, false);
            double min_node_x = getMaxMinNode(level_graph.getV(), false, true);
            double min_node_y = getMaxMinNode(level_graph.getV(), false, false);
            try {
                String[] splitData = game.toString().split("[:\\}]");
                splitData[6]=splitData[6].substring(1,8);
                graph_image=ImageIO.read(new File(splitData[6]+".png"));
                g.drawImage(graph_image,30,60,null);
            }
            catch(Exception e){
                System.out.println("graph photo not found");
                e.printStackTrace();
            }
            for (node_data nodes : level_graph.getV()) {
                Point3D nodes_src = nodes.getLocation();
                g.setColor(Color.BLUE);
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
            Iterator<String> f_iter = game.getFruits().iterator();
            while (f_iter.hasNext()) {
                try {
                    fruit_image = ImageIO.read(new File("data/apple.jpeg"));
                    boolean b=false;
                    String[] splitData = f_iter.next().toString().split("[:\\,]");
                    splitData[6]=splitData[6].substring(1);
                    double fruit_src_x = scale(Double.parseDouble(splitData[6]), min_node_x, max_node_x, 50, 1250);
                    double fruit_src_y = scale( Double.parseDouble(splitData[7]), min_node_y, max_node_y, 50, 650);
                    edge_data temp=null;
                    for(node_data nd:level_graph.getV()){
                        for(edge_data ed:level_graph.getE(nd.getKey())){
                            double distance=Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x())-(level_graph.getNode(ed.getDest()).getLocation().x())),2)+Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y())-(level_graph.getNode(ed.getDest()).getLocation().y())),2));
                            double fruit_from_dest=Math.sqrt(Math.pow(((Double.parseDouble(splitData[6])-(level_graph.getNode(ed.getDest()).getLocation().x()))),2)+Math.pow(((Double.parseDouble(splitData[7])-(level_graph.getNode(ed.getDest()).getLocation().y()))),2));
                            double fruit_to_src=Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x())-(Double.parseDouble(splitData[6]))),2)+Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y())-(Double.parseDouble(splitData[7]))),2));
                            if(fruit_from_dest+fruit_to_src-distance<=EPSILON){
                                temp=ed;
                                b=true;
                            }if(b)break;
                        }if(b)break;
                    }
                    g.drawImage(fruit_image, (int) fruit_src_x - 15, (int) fruit_src_y-10, null);
                    g.setColor(Color.BLACK);
                    splitData[2]=splitData[2].substring(0,splitData[2].length()-2);
                    if(splitData[4].equals("1")){
                        g.drawString((splitData[2])+" ↑",(int) fruit_src_x-9, (int) fruit_src_y+11);
                    }
                    if(splitData[4].equals("-1")){
                        g.drawString((splitData[2])+" ↓",(int) fruit_src_x-9, (int) fruit_src_y+11);
                    }
                } catch (Exception e) {
                    System.out.println("404-file not found!");
                    e.printStackTrace();
                }
            }
            if (PaintRobots) {
                Iterator<String> r_iter = game.getRobots().iterator();
                while (r_iter.hasNext()) {
                    try {
                        Robot_image = ImageIO.read(new File("data/robot3.png"));
                        String[] splitData = r_iter.next().toString().split("[:\\,]");
                        int Robot_src = Integer.parseInt(splitData[6]);
                        double robot_src_x = scale(level_graph.getNode(Robot_src).getLocation().x(), min_node_x, max_node_x, 50, 1250);
                        double robot_src_y = scale(level_graph.getNode(Robot_src).getLocation().y(), min_node_y, max_node_y, 50, 650);
                        g.drawImage(Robot_image, (int) robot_src_x - 15, (int) robot_src_y-10, null);
                    } catch (Exception e) {
                        System.out.println("404-file not found!");
                        e.printStackTrace();
                    }
                }
            }
        }
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
}