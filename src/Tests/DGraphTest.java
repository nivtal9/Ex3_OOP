package Tests;
import Server.Game_Server;
import Server.game_service;
import dataStructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Point3D;
import static org.junit.jupiter.api.Assertions.*;
class DGraphTest {
    static DGraph graph = new DGraph();
    @BeforeEach
    void init2(){
        graph = new DGraph();
    }
    @Test
    void getNode() {
        graph.addNode(new node(13,new Point3D(2,2,2),0));
        graph.getNode(13);
    }

    @org.junit.jupiter.api.Test
    void getEdge() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(13,new Point3D(2,2,2),0));
        graph.connect(10,13,0);
        graph.getEdge(10,13);
    }

    @org.junit.jupiter.api.Test
    void addNode() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(11,new Point3D(2,2,2),0));
        graph.addNode(new node(12,new Point3D(4,4,4),0));
        graph.addNode(new node(13,new Point3D(3,3,3),0));
        graph.addNode(new node(14,new Point3D(1,1,1),0));
        System.out.println(graph.getNode(13));
        assertEquals(5,graph.nodeSize());
    }

    @org.junit.jupiter.api.Test
    void connect() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(11,new Point3D(2,2,2),0));
        graph.addNode(new node(12,new Point3D(4,4,4),0));
        graph.addNode(new node(13,new Point3D(3,3,3),0));
        graph.addNode(new node(14,new Point3D(1,1,1),0));
        graph.connect(10,13,0);
        System.out.println(graph.edgeSize());
        graph.connect(10,14,0);
        System.out.println(graph.edgeSize());
        graph.connect(11,10,0);
        System.out.println(graph.edgeSize());
        graph.connect(11,13,0);
        System.out.println(graph.edgeSize());
        graph.connect(12,11,0);
        System.out.println(graph.edgeSize());
        graph.connect(13,14,1);
        System.out.println(graph.edgeSize());
        graph.connect(13,12,1.5);
        System.out.println(graph.edgeSize());
        graph.connect(14,13,0);
        System.out.println(graph.edgeSize());
        System.out.println(graph.getEdge(13,14));
        System.out.println(graph.getEdge(13,12));
        assertEquals(8,graph.edgeSize());
    }

    @Test
    void getV() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(11,new Point3D(2,2,2),0));
        graph.addNode(new node(12,new Point3D(4,4,4),0));
        graph.addNode(new node(13,new Point3D(3,3,3),0));
        graph.addNode(new node(14,new Point3D(1,1,1),0));
        System.out.println(graph.getV());
    }

    @Test
    void getE() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(11,new Point3D(2,2,2),0));
        graph.addNode(new node(12,new Point3D(4,4,4),0));
        graph.addNode(new node(13,new Point3D(3,3,3),0));
        graph.addNode(new node(14,new Point3D(1,1,1),0));
        graph.connect(10,13,0);
        graph.connect(10,14,0);
        graph.connect(11,10,0);
        graph.connect(11,13,0);
        graph.connect(12,11,0);
        graph.connect(13,14,1);
        graph.connect(13,12,1.5);
        graph.connect(14,13,0);
        System.out.println (graph.getE(11));
    }

    @Test
    void removeNode() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(11,new Point3D(2,2,2),0));
        graph.addNode(new node(12,new Point3D(4,4,4),0));
        graph.addNode(new node(13,new Point3D(3,3,3),0));
        graph.addNode(new node(14,new Point3D(1,1,1),0));
        graph.connect(10,13,0);
        graph.connect(10,14,0);
        graph.connect(11,10,0);
        graph.connect(11,13,0);
        graph.connect(12,11,0);
        graph.connect(13,14,1);
        graph.connect(13,12,1.5);
        graph.connect(14,13,0);
        System.out.println (graph.removeNode(12));
        System.out.println(graph.getE(13));
        System.out.println(graph.getV());
    }

    @Test
    void removeEdge() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(11,new Point3D(2,2,2),0));
        graph.connect(10,11,0);
        graph.removeEdge(10,11);
        graph.getE(10);
        assertEquals(0,graph.getE(10).size());
    }

    @Test
    void nodeSize() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(11,new Point3D(2,2,2),0));
        graph.addNode(new node(12,new Point3D(4,4,4),0));
        graph.addNode(new node(13,new Point3D(3,3,3),0));
        graph.addNode(new node(14,new Point3D(1,1,1),0));
        graph.connect(10,13,0);
        graph.connect(10,14,0);
        graph.connect(11,10,0);
        graph.connect(11,13,0);
        graph.connect(12,11,0);
        graph.connect(13,14,1);
        graph.connect(13,12,1.5);
        graph.connect(14,13,0);
        graph.removeNode(12);
        assertEquals(graph.nodeSize(),4);
    }

    @Test
    void edgeSize() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(11,new Point3D(2,2,2),0));
        graph.addNode(new node(12,new Point3D(4,4,4),0));
        graph.addNode(new node(13,new Point3D(3,3,3),0));
        graph.addNode(new node(14,new Point3D(1,1,1),0));
        graph.connect(10,13,0);
        graph.connect(10,14,0);
        graph.connect(11,10,0);
        graph.connect(11,13,0);
        graph.connect(12,11,0);
        graph.connect(13,14,1);
        graph.connect(13,12,1.5);
        graph.connect(14,13,0);
        graph.removeNode(12);
        assertEquals(6,graph.edgeSize());
    }

    @Test
    void getMC() {
        graph.addNode(new node(10,new Point3D(0,0,0),0));
        graph.addNode(new node(11,new Point3D(2,2,2),0));
        graph.addNode(new node(12,new Point3D(4,4,4),0));
        graph.addNode(new node(13,new Point3D(3,3,3),0));
        graph.addNode(new node(14,new Point3D(1,1,1),0));
        graph.connect(10,13,0);
        graph.connect(10,14,0);
        graph.connect(11,10,0);
        graph.connect(11,13,0);
        graph.connect(12,11,0);
        graph.connect(13,14,1);
        graph.connect(13,12,1.5);
        graph.connect(14,13,0);
        assertEquals(13,graph.getMC());
    }
    @Test
    void setInfo(){
        graph.addNode(new node(11,new Point3D(2,2,2),3.5));
        Point3D p =new Point3D(3,6,9);
        node_data temp=new node(3, p,8);
        System.out.println(temp);
        temp.setInfo(graph.getNode(11).toString());
        System.out.println(graph.getNode(11).toString());
        System.out.println(temp);
    }
    @Test
    void init(){
        game_service game= Game_Server.getServer(0);
        String graph_str = game.getGraph();
        DGraph level_graph = new DGraph();
        level_graph.init(graph_str);
        assertEquals(level_graph.nodeSize(),11);
        assertEquals(level_graph.edgeSize(),22);
        level_graph.removeNode(0);
        assertEquals(level_graph.nodeSize(),10);
        assertEquals(level_graph.edgeSize(),18);
    }
    @Test
    void TheTest(){
       graph= (DGraph) graph.TheTest();
       assertEquals(graph.edgeSize(),1000000);
       assertEquals(graph.nodeSize(),100000);
    }

}

