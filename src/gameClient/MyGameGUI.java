package gameClient;

import Server.*;
import dataStructure.*;
import utils.Point3D;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class MyGameGUI extends JFrame implements ActionListener {
    private JButton start;
    private JButton start2;
    private static graph level_graph;
    public static void main(String[] args){
        MyGameGUI g=new MyGameGUI();
        g.setVisible(true);
    }
    public MyGameGUI() {
        //this.MC=graph.getMC();
        INITGUI();
    }

    private void INITGUI() {
        this.setSize(1300, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start = new JButton("Manuel Game");
        start2=new JButton("Auto Game");
        start.addActionListener(this);
        start2.addActionListener(this);
        this.getContentPane().setLayout(new GridLayout());
        this.getContentPane().add(start);
        this.getContentPane().add(start2);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String str = actionEvent.getActionCommand();
        if(str.equals("Manuel Game")){
            JFrame start = new JFrame();
            try {
                int level = Integer.parseInt(JOptionPane.showInputDialog(start, "Enter level between 0-23"));
                if(level<0||level>23){
                    JOptionPane.showMessageDialog(start, "Invalid level");
                }
                else{
                    game_service game= Game_Server.getServer(level);
                    String Graph_str=game.getGraph();
                    level_graph=new DGraph();
                    ((DGraph) level_graph).init(Graph_str);
                    this.remove(this.start);
                    this.remove(this.start2);
                    repaint();
                }
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(start, "Invalid Pattern/Not entered any Number");
                e.printStackTrace();
            }
        }
        if(str.equals("Auto Game")){
            JOptionPane.showMessageDialog(start, "Not Ready yet !");
        }
    }
    public void paint(Graphics g) {
        if(level_graph!=null) {
            super.paint(g);
            double max_node_x = getMaxMinNode(level_graph.getV(), true, true);
            double max_node_y = getMaxMinNode(level_graph.getV(), true, false);
            double min_node_x = getMaxMinNode(level_graph.getV(), false, true);
            double min_node_y = getMaxMinNode(level_graph.getV(), false, false);
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
                        g.drawLine((int) nodes_src_x, (int) nodes_src_y, (int) nodes_dest_x, (int) nodes_dest_y);

                        g.setColor(Color.BLACK);
                        int directed_x = (int) (nodes_src_x * 0.15 + nodes_dest_x * 0.85);
                        int directed_y = (int) (nodes_src_y * 0.15 + nodes_dest_y * 0.85);
                        g.fillOval(directed_x - 4, directed_y - 2, 7, 7);
                        g.setColor(Color.PINK);
                        g.drawString("" + edges.getWeight(), directed_x, directed_y);
                    }
                } catch (Exception e) {
                    //this node has no edges, the graph is still being initialized...
                }
            }
        }
    }
    /**
     *
     * @param data denote some data to be scaled
     * @param r_min the minimum of the range of your data
     * @param r_max the maximum of the range of your data
     * @param t_min the minimum of the range of your desired target scaling
     * @param t_max the maximum of the range of your desired target scaling
     * @return
     */
    private double scale(double data, double r_min, double r_max, double t_min, double t_max)
    {
        double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
        return res;
    }

    /**
     *
     * @param col Collection of node_data in level_graph
     * @param b true to get MaxNode false to get MinNode
     * @param c true to get x false to get y
     * @return
     */
    private double getMaxMinNode(Collection<node_data> col,boolean b,boolean c){
        double MaxNode=0,MinNode=0;
        if (b){
            MaxNode=Integer.MIN_VALUE;
            for(node_data nd:col){
                if(c){
                    if(MaxNode<nd.getLocation().x()){
                        MaxNode=nd.getLocation().x();
                    }
                }
                else{
                    if(MaxNode<nd.getLocation().y()){
                        MaxNode=nd.getLocation().y();
                    }
                }
            }
        }
        else {
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
        if(b) return MaxNode;
        else return MinNode;
    }
}
