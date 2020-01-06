package Tests;
import algorithms.Graph_Algo;
import dataStructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Point3D;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Graph_AlgoTest {
    private DGraph ga=null;
    private Graph_Algo TestGraph = null;

    @BeforeEach
    void init(){
        ga=new DGraph();
        TestGraph=new Graph_Algo();
        ga.addNode(new node(10, new Point3D(100, 100, 150), 0));
        ga.addNode(new node(11, new Point3D(135, 125, 130), 0));
        ga.addNode(new node(12, new Point3D(120, 300, 200), 0));
        ga.addNode(new node(13, new Point3D(150, 200, 100), 0));
        ga.addNode(new node(14, new Point3D(75, 250, 250), 0));
        ga.connect(10, 11, 4.5);
        ga.connect(10, 14, 1);
        ga.connect(11, 13, 5);
        ga.connect(12, 11, 17);
        ga.connect(13, 14, 1);
        ga.connect(13, 11, 20.5);
        ga.connect(13, 12, 1.5);
        ga.connect(14, 13, 2);
        TestGraph.init(ga);
    }
    @Test
    void initByString() {

    }

    @Test
    void isConnected() {
        System.out.println("There are no Dest edges to the Node 10, and isConnected is: "+TestGraph.isConnected());
        ga.connect(11, 10, 0);
        TestGraph.init(ga);
        assertEquals(true,TestGraph.isConnected());
    }

    @Test
    void shortestPathDist() {
        assertEquals(3,TestGraph.shortestPathDist(10,13));
    }

    @Test
    void shortestPath() {
        assertEquals(TestGraph.shortestPath(13,10),null);
    }

    @Test
    void TSP() {
        ga.connect(11, 10, 4.5);
        ga.connect(13, 10, 8);
        TestGraph.init(ga);
        List<Integer>lst=new LinkedList<>();
        lst.add(10);
        lst.add(13);
        lst.add(10);
        lst.add(14);
        lst.add(11);
        System.out.println(TestGraph.TSP(lst));
    }

    @Test
    void copy() {
        graph g=new DGraph();
        g=TestGraph.copy();
        assertEquals(g.nodeSize(),5);
        assertEquals(g.edgeSize(),8);
    }
}