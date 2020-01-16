package GameElements;

import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import java.util.List;

public class Game_Algo {
    public static final double EPSILON = 0.0000001;

    public edge_data getFruitEdge(int i,game_service Game,graph level_graph) {
        Fruit f = new Fruit(Game.getFruits(), i);
        boolean b = false;
        edge_data temp = null;
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

                return level_graph.getEdge(temp.getDest(), temp.getSrc());
            } else {
                return level_graph.getEdge(temp.getSrc(), temp.getDest());
            }
        } else {
            if (Math.max(temp.getSrc(), temp.getDest()) == temp.getDest()) {
                return level_graph.getEdge(temp.getDest(), temp.getSrc());
            } else {
                return level_graph.getEdge(temp.getSrc(), temp.getDest());
            }
        }
    }
}
