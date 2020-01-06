package gui;
import algorithms.Graph_Algo;
import dataStructure.*;
import utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class GUI extends JFrame implements ActionListener {
    private static graph graph;
    private JButton save;
    private JButton isconnected;
    private JButton tsp;
    private JButton shortestpathdist;
    private JButton shortestpath;
    private JButton load;
    private boolean paintActionPerformed;
    private List<node_data> ans;
    private int MC;



    /*public static void main(String[] args) {
        graph = new DGraph();
        *//*graph.addNode(new node(1, new Point3D(350, 400, 350), 0));
        graph.addNode(new node(2, new Point3D(35, 325, 50), 0));
        graph.addNode(new node(3, new Point3D(250, 70, 10), 0));
        graph.addNode(new node(4, new Point3D(550, 400, 140), 0));
        graph.addNode(new node(5, new Point3D(135, 600, 250), 0));
        graph.addNode(new node(6, new Point3D(730, 250, 350), 0));
        graph.addNode(new node(7, new Point3D(630, 650, 350), 0));
        graph.addNode(new node(8, new Point3D(530, 100, 350), 0));
        graph.addNode(new node(9, new Point3D(470, 570, 370), 0));
        graph.addNode(new node(10, new Point3D(100, 100, 250), 0));
        graph.connect(1,2,8);
        graph.connect(2,1,10);
        graph.connect(2,3,1);
        graph.connect(3,2,2);
        graph.connect(3,4,2);
        graph.connect(4,5,2);
        graph.connect(5,3,15);
        graph.connect(2,6,20);
        graph.connect(1,10,30);
        graph.connect(6,7,22);
        graph.connect(6,8,60);
        graph.connect(7,3,7);
        graph.connect(8,9,7);
        graph.connect(9,2,19);
        graph.connect(9,8,8);
        graph.connect(10,8,7);
        graph.connect(10,6,2);
        graph.connect(9,5,3);
        graph.connect(2,5,21);*//*
        graph.addNode(new node(10, new Point3D(350, 400, 350), 0));
        graph.addNode(new node(11, new Point3D(35, 325, 50), 0));
        graph.addNode(new node(12, new Point3D(550, 400, 140), 0));
        graph.addNode(new node(13, new Point3D(630, 650, 350), 0));
        graph.addNode(new node(14, new Point3D(800, 100, 250), 0));
        graph.addNode(new node(15, new Point3D(300, 300, 350), 0));
        graph.connect(10, 13, 4);
        graph.connect(10, 11, 4.5);
        graph.connect(10, 14, 1);
        graph.connect(11, 13, 5);
        graph.connect(12, 11, 17);
        graph.connect(13, 14, 1);
        graph.connect(13, 11, 20.5);
        graph.connect(13, 12, 1.5);
        graph.connect(14, 13, 2);
        graph.connect(11, 10, 4.5);
        graph.connect(13, 10, 8);
        GUI gui = new GUI();
        gui.setVisible(true);
        MC=graph.getMC();
    }*/
    public void Init(graph g){
        graph=g;
        this.MC=g.getMC();
        GUI gui = new GUI();
        gui.setVisible(true);
    }

    public GUI() {
        paintActionPerformed=false;
        ans=new LinkedList<>();
        //this.MC=graph.getMC();
        INITGUI();
    }

    private void INITGUI() {
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MenuBar MB = new MenuBar();
        this.setMenuBar(MB);
        Menu File = new Menu("File");
        MB.add(File);
        Menu Algo = new Menu("Algo");
        MB.add(Algo);
        MenuItem Save = new MenuItem("Save");
        MenuItem Load = new MenuItem("Load");
        MenuItem TSP = new MenuItem("TSP");
        MenuItem ShortestPath = new MenuItem("ShortestPath");
        MenuItem isConnected = new MenuItem("isConnected");
        MenuItem ShortestPathDist = new MenuItem("ShortestPathDist");
        save=new JButton("save");
        load=new JButton("load");
        isconnected=new JButton("isconnected");
        tsp=new JButton("tsp");
        shortestpathdist=new JButton("shortestpathdist");
        shortestpath=new JButton("shortestpath");
        File.add(Save);
        File.add(Load);
        Algo.add(TSP);
        Algo.add(ShortestPath);
        Algo.add(isConnected);
        Algo.add(ShortestPathDist);
        Save.addActionListener(this);
        Load.addActionListener(this);
        TSP.addActionListener(this);
        ShortestPath.addActionListener(this);
        isConnected.addActionListener(this);
        ShortestPathDist.addActionListener(this);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        if (graph.getMC() != MC) {
                            MC = graph.getMC();
                            repaint();
                        }
                    }
                }
            }
        });
        th.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (node_data nodes : graph.getV()) {
            Point3D nodes_src = nodes.getLocation();
            g.setColor(Color.BLUE);
            g.fillOval((int) nodes_src.x() - 7, (int) nodes_src.y() - 7, 15, 15);
            g.drawString("" + nodes.getKey(), (int) nodes_src.x(), (int) (nodes_src.y() + 20));
            try {
                for (edge_data edges : graph.getE(nodes.getKey())) {
                    g.setColor(Color.RED);
                    Point3D nodes_dest = graph.getNode(edges.getDest()).getLocation();
                    g.drawLine((int) nodes_src.x(), (int) nodes_src.y(), (int) nodes_dest.x(), (int) nodes_dest.y());

                    g.setColor(Color.BLACK);
                    int directed_x = (int) (nodes_src.x() * 0.15 + nodes_dest.x() * 0.85);
                    int directed_y = (int) (nodes_src.y() * 0.15 + nodes_dest.y() * 0.85);
                    g.fillOval(directed_x - 4, directed_y - 2, 7, 7);
                    g.setColor(Color.PINK);
                    g.drawString("" + edges.getWeight(), directed_x, directed_y);
                }
            }
            catch(Exception e){
                //this node has no edges, the graph is still being initialized...
            }
            if(paintActionPerformed){
                g.setColor(Color.BLUE);
                for (int i=0;i<ans.size()-1;i++){
                    Point3D Path_Start = ans.get(i).getLocation();
                    Point3D Path_End= ans.get(i+1).getLocation();
                    g.drawLine((int) Path_Start.x(), (int) Path_Start.y(), (int) Path_End.x(), (int) Path_End.y());
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("Save")) {
            paintActionPerformed=false;
            repaint();
            JFileChooser fileChooser = new JFileChooser();
            Graph_Algo ga = new Graph_Algo();
            ga.init(graph);
            int retval = fileChooser.showSaveDialog(save);
            if (retval == JFileChooser.APPROVE_OPTION) {
                try {
                    ga.save(fileChooser.getSelectedFile()+".txt");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (str.equals("Load")) {
            paintActionPerformed=false;
            repaint();
            JFileChooser fileChooser = new JFileChooser();
            Graph_Algo ga = new Graph_Algo();
            int retval = fileChooser.showOpenDialog(load);
            if (retval == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = fileChooser.getSelectedFile();
                    ga.init(selectedFile.getAbsolutePath());
                    graph = ga.copy();
                    repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (str.equals("TSP")) {
            paintActionPerformed=false;
            repaint();
            boolean InvalidNode=false;boolean ContinueTSP=true;
            JFrame tsp = new JFrame();
            List<Integer>lst=new LinkedList<>();
            try {
                String src = (JOptionPane.showInputDialog(tsp, "Enter Nodes key right Below Separated by ',' to finish the list put '.'"));
                src=src.replaceAll(" ","");
                int j=0;
                if(src.contains(",")&&src.contains(".")) {
                    for (int i = 0; i < src.length(); i++) {
                        if (src.charAt(i) == ',') {
                            if (graph.getNode(Integer.parseInt(src.substring(j, i))) != null) {
                                lst.add(Integer.parseInt(src.substring(j, i)));
                                j = i + 1;
                            } else {
                                InvalidNode = true;
                                throw new RuntimeException("Node Entered are nor in the graph");
                            }
                        }
                        if (src.charAt(i) == '.') {
                            if (graph.getNode(Integer.parseInt(src.substring(j, i))) != null) {
                                lst.add(Integer.parseInt(src.substring(j, i)));
                            } else {
                                InvalidNode = true;
                                throw new RuntimeException("Node Entered are nor in the graph");
                            }
                            break;
                        }
                    }
                }
                else throw new RuntimeException("String Doesn't have ',' and '.' ");
                if(lst.size()==0){
                    throw new RuntimeException("lst is Empty");
                }
            } catch (Exception exception) {
                ContinueTSP=false;
                if(!InvalidNode){
                    JOptionPane.showMessageDialog(tsp, "Invalid Pattern/Not entered any Number");
                    exception.printStackTrace();
                }
                else{
                    JOptionPane.showMessageDialog(tsp, "some of the Nodes that you Entered are not in the graph!");
                }
            }
            try {
                if (ContinueTSP) {
                    Graph_Algo ga = new Graph_Algo();
                    ga.init(graph);
                    ans = ga.TSP(lst);
                    if (ans != null) {
                        if (ans.size() == 1) {
                            JOptionPane.showMessageDialog(tsp, "List Should be at least 2 targets!");
                        } else {
                            String string="";
                            for(int i=0;i<ans.size();i++){
                                string+=ans.get(i).getKey();
                                if(i!=ans.size()-1){
                                    string+="-->";
                                }
                            }
                            paintActionPerformed = true;
                            repaint();
                            JOptionPane.showMessageDialog(tsp, "The TSP Path is: "+string);
                        }
                    } else {
                        JOptionPane.showMessageDialog(tsp, "targets entered are not isConnected!");
                    }
                }
            } catch (Exception exception) {
                System.out.println("Cannot Operate TSP!");
                exception.printStackTrace();
            }
        }
        if (str.equals("isConnected")) {
            paintActionPerformed=false;
            repaint();
            try {
                JFrame isconnected = new JFrame();
                Graph_Algo ga = new Graph_Algo();
                ga.init(graph);
                if (ga.isConnected()) {
                    JOptionPane.showMessageDialog(isconnected, "Current Graph isConnected!");
                } else {
                    JOptionPane.showMessageDialog(isconnected, "Current Graph is Not Connected!");
                }
            }
            catch(Exception exception){
                System.out.println("Cannot Operate isConnected!");
                exception.printStackTrace();
            }
        }
        if (str.equals("ShortestPath")) {
            boolean b=false;
            paintActionPerformed = false;
            repaint();
            JFrame shortestpath = new JFrame();
            int Source = Integer.MIN_VALUE + 1;
            int Destination = Integer.MIN_VALUE + 1;
            try {
                Source = Integer.parseInt(JOptionPane.showInputDialog(shortestpath, "Enter src from current graph"));
                Destination = Integer.parseInt(JOptionPane.showInputDialog(shortestpath, "Enter dest from current graph"));
                if (graph.getNode(Source) == null || graph.getNode(Destination) == null) {
                    JOptionPane.showMessageDialog(shortestpath, "The Source or Destination keys you Entered are not in the Current Graph!");
                    throw new RuntimeException();
                }
            } catch (NumberFormatException exception) {
                b=true;
                JOptionPane.showMessageDialog(shortestpath, "Invalid Number/Not entered any Number");
                exception.printStackTrace();
            }
            try {
                Graph_Algo ga = new Graph_Algo();
                ga.init(graph);
                ans = ga.shortestPath(Source, Destination);
                if (ans != null) {
                    if(ans.size()>0) {
                        String string = "";
                        for (int i = 0; i < ans.size(); i++) {
                            string += ans.get(i).getKey();
                            if (i != ans.size() - 1) {
                                string += "-->";
                            }
                        }
                        paintActionPerformed = true;
                        repaint();
                        JOptionPane.showMessageDialog(tsp, "The Shortest Path is: " + string);
                    }
                    else{
                        if(!b)
                        JOptionPane.showMessageDialog(shortestpath, "Source = Destination and The Path is 0");
                    }
                } else {
                    JOptionPane.showMessageDialog(shortestpath, "There is no Path Between the Source and Destination");
                }
            } catch (Exception exception) {
                System.out.println("Cannot Operate ShortestPath!");
                exception.printStackTrace();
            }
        }
        if (str.equals("ShortestPathDist")) {
            boolean b=false;
            paintActionPerformed=false;
            repaint();
            JFrame shortestpathdist = new JFrame();
            int Source=Integer.MIN_VALUE+1;
            int Destination=Integer.MIN_VALUE+1;
            try {
                Source = Integer.parseInt(JOptionPane.showInputDialog(shortestpathdist, "Enter src from current graph"));
                Destination = Integer.parseInt(JOptionPane.showInputDialog(shortestpathdist, "Enter dest from current graph"));
                if(graph.getNode(Source)==null||graph.getNode(Destination)==null){
                    JOptionPane.showMessageDialog(shortestpathdist,"The Source or Destination keys you Entered are not in the Current Graph!");
                    throw new RuntimeException();
                }
            }
            catch (NumberFormatException exception){
                b=true;
                JOptionPane.showMessageDialog(shortestpathdist,"Invalid Number/Not entered any Number");
                exception.printStackTrace();
            }
            try{
                Graph_Algo ga=new Graph_Algo();
                ga.init(graph);
                Double answer=ga.shortestPathDist(Source,Destination);
                if(answer!=Integer.MAX_VALUE) {
                    if(!b)
                    JOptionPane.showMessageDialog(shortestpathdist,"The Weight of the Path you entered is: "+answer);
                }
                else{
                    JOptionPane.showMessageDialog(shortestpathdist,"There is no Path Between the Source and Destination you Entered!");
                }
            }
            catch(Exception exception){
                System.out.println("Cannot Operate ShortestPathDist!");
                exception.printStackTrace();
            }
        }
    }
}