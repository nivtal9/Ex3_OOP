package Tests;

import GameElements.Fruit;
import GameElements.Game_Algo;
import GameElements.Robot;
import GameElements.robot_data;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Game_AlgoTest {
    private static final double EPSILON = 0.0000001;

    @Test
    void autosetRobot() throws JSONException {
        game_service game=Game_Server.getServer(3);
        String Graph_str = game.getGraph();
        DGraph level_graph = new DGraph();
        level_graph.init(Graph_str);
        new Game_Algo().AutoSetRobot(game,level_graph);
        game_service game2=Game_Server.getServer(3);
        List<String> Temp_Fruit = game2.getFruits();
        int maxFruit = Integer.MIN_VALUE;
        int MaxFruitID = 0;
        for (int j = 0; j < Temp_Fruit.size(); j++) {
            Fruit f = new Fruit(Temp_Fruit, j);
            if (f.getValue() > maxFruit) {
                maxFruit = f.getValue();
                MaxFruitID = j;
            }
        }
        Fruit f = new Fruit(game2.getFruits(), MaxFruitID);
        boolean b = false;
        edge_data temp = null;
        edge_data finaledge=null;
        for (node_data nd : level_graph.getV()) {
            for (edge_data ed : level_graph.getE(nd.getKey())) {
                double distance = Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x()) - (level_graph.getNode(ed.getDest()).getLocation().x())), 2) + Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y()) - (level_graph.getNode(ed.getDest()).getLocation().y())), 2));
                double fruit_from_dest = Math.sqrt(Math.pow(((f.getLocation().x() - (level_graph.getNode(ed.getDest()).getLocation().x()))), 2) + Math.pow(((f.getLocation().y() - (level_graph.getNode(ed.getDest()).getLocation().y()))), 2));
                double fruit_to_src = Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x()) - (f.getLocation().x())), 2) + Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y()) - (f.getLocation().y())), 2));
                if (fruit_from_dest + fruit_to_src - distance <= EPSILON) {
                    temp = ed;
                    b = true;
                }
                if (b) break;
            }
            if (b) break;
        }
        if (f.getType() == 1) {
            if (Math.min(temp.getSrc(), temp.getDest()) == temp.getDest()) {

                finaledge=level_graph.getEdge(temp.getDest(), temp.getSrc());
            } else {
                finaledge=level_graph.getEdge(temp.getSrc(), temp.getDest());
            }
        } else {
            if (Math.max(temp.getSrc(), temp.getDest()) == temp.getDest()) {
                finaledge=level_graph.getEdge(temp.getDest(), temp.getSrc());
            } else {
                finaledge=level_graph.getEdge(temp.getSrc(), temp.getDest());
            }
        }
        game2.addRobot(finaledge.getSrc());
        robot_data R1=new Robot(game2.getRobots(),0);
        robot_data R2=new Robot(game.getRobots(),0);
        System.out.println("Src: "+R1.getSrc()+" "+R2.getSrc()+" Dest: "+R1.getDest()+ " "+R2.getDest());
        assertEquals(R1.getSrc(),R2.getSrc());
        assertEquals(R1.getDest(),R2.getDest());
    }

    @Test
    void moveRobots() throws JSONException {
        game_service game=Game_Server.getServer(2);
        String Graph_str = game.getGraph();
        DGraph level_graph = new DGraph();
        level_graph.init(Graph_str);
        game.addRobot(0);
        game.startGame();
        new Game_Algo().MoveRobots(game,level_graph);
        game_service game2=Game_Server.getServer(2);
        game2.addRobot(0);
        int key=-1;
        double shortestpathdist=Integer.MAX_VALUE;
        for (int i = 0; i <game2.getFruits().size() ; i++) {
            Fruit f = new Fruit(game2.getFruits(), i);
            boolean b = false;
            edge_data temp = null;
            edge_data finaledge = null;
            for (node_data nd : level_graph.getV()) {
                for (edge_data ed : level_graph.getE(nd.getKey())) {
                    double distance = Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x()) - (level_graph.getNode(ed.getDest()).getLocation().x())), 2) + Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y()) - (level_graph.getNode(ed.getDest()).getLocation().y())), 2));
                    double fruit_from_dest = Math.sqrt(Math.pow(((f.getLocation().x() - (level_graph.getNode(ed.getDest()).getLocation().x()))), 2) + Math.pow(((f.getLocation().y() - (level_graph.getNode(ed.getDest()).getLocation().y()))), 2));
                    double fruit_to_src = Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x()) - (f.getLocation().x())), 2) + Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y()) - (f.getLocation().y())), 2));
                    if (fruit_from_dest + fruit_to_src - distance <= EPSILON) {
                        temp = ed;
                        b = true;
                    }
                    if (b) break;
                }
                if (b) break;
            }
            if (f.getType() == 1) {
                if (Math.min(temp.getSrc(), temp.getDest()) == temp.getDest()) {

                    finaledge = level_graph.getEdge(temp.getDest(), temp.getSrc());
                } else {
                    finaledge = level_graph.getEdge(temp.getSrc(), temp.getDest());
                }
            } else {
                if (Math.max(temp.getSrc(), temp.getDest()) == temp.getDest()) {
                    finaledge = level_graph.getEdge(temp.getDest(), temp.getSrc());
                } else {
                    finaledge = level_graph.getEdge(temp.getSrc(), temp.getDest());
                }
            }
            String str = game2.getGraph();
            level_graph = new DGraph();
            level_graph.init(str);
            Graph_Algo ga=new Graph_Algo();
            ga.init(level_graph);
            robot_data R1=new Robot(game2.getRobots(),0);
            if (ga.shortestPathDist(R1.getSrc(), finaledge.getDest()) < shortestpathdist) {
                try {
                    shortestpathdist = ga.shortestPathDist(R1.getSrc(), finaledge.getDest());
                    key = ga.shortestPath(R1.getSrc(), finaledge.getDest()).get(1).getKey();
                } catch (Exception e) {
                    key = finaledge.getSrc();
                }
            }
        }
        robot_data R2=new Robot(game.getRobots(),0);
        assertEquals(key,R2.getDest());
    }
}