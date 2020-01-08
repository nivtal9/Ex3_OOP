package gameClient;



import Server.*;
import dataStructure.DGraph;
import org.json.JSONWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class MyGameGUI extends JFrame implements ActionListener {
    private JButton start;
    public static void main(String[] args){
        MyGameGUI g=new MyGameGUI();
        g.setVisible(true);
    }
    public MyGameGUI() {
        //this.MC=graph.getMC();
        INITGUI();
    }

    private void INITGUI() {
     //  MenuBar MB = new MenuBar();
        //this.setMenuBar(MB);
        //start=new JButton("start");
        //start.setBounds(100,100,220, 123);
        //this.add(start);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start = new JButton("Click here!");
        start.addActionListener(this);
        JPanel panel = new JPanel();

        // Add button to JPanel
        panel.add(start);
        // And JPanel needs to be added to the JFrame itself!
        this.getContentPane().add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String str = actionEvent.getActionCommand();
        if(str.equals("Click here!")){
            JFrame start = new JFrame();
            try {
                int level = Integer.parseInt(JOptionPane.showInputDialog(start, "Enter level between 0-23"));
                if(level<0||level>23){
                    JOptionPane.showMessageDialog(start, "Invalid level");
                }
                else{
                    game_service game= Game_Server.getServer(level);
                    String Graph_str=game.getGraph();
                    DGraph level_graph=new DGraph();
                    level_graph.init(Graph_str);
                    //*****************Draw Graph*************
                }
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(start, "Invalid Pattern/Not entered any Number");
                e.printStackTrace();
            }
        }
    }
}
