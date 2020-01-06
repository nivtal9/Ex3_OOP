package gui;

import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node;
import utils.Point3D;

public class Gui_Main {
    private static graph graph;
    public static void main(String[] args) {
        graph = new DGraph();
        GUI g = new GUI();
        g.Init(graph);
        /*graph.addNode(new node(1, new Point3D(350, 400, 350), 0));
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
        graph.connect(2,5,21);*/
        graph.addNode(new node(10, new Point3D(350, 400, 350), 0));
        graph.addNode(new node(11, new Point3D(35, 325, 50), 0));
        graph.addNode(new node(12, new Point3D(550, 400, 140), 0));
        graph.addNode(new node(13, new Point3D(630, 650, 350), 0));
        graph.addNode(new node(14, new Point3D(800, 100, 250), 0));
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
/*        GUI gui = new GUI();
        gui.setVisible(true);*/
    }
}
