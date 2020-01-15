package GameElements;

import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Game_Algo {
    public static final double EPSILON = 0.0000001;
    public void AutoMode(graph level_graph, game_service game) {
        List<edge_data> edges_with_fruits=new LinkedList<>();
        while(edges_with_fruits.size()==game.getFruits().size()) {
            Iterator<String> f_iter = game.getFruits().iterator();
            boolean b = false;
            String[] splitData = f_iter.next().split("[:\\,]");
            edge_data temp = null;
            for (node_data nd : level_graph.getV()) {
                for (edge_data ed : level_graph.getE(nd.getKey())) {
                    double distance = Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x()) - (level_graph.getNode(ed.getDest()).getLocation().x())), 2) + Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y()) - (level_graph.getNode(ed.getDest()).getLocation().y())), 2));
                    double fruit_from_dest = Math.sqrt(Math.pow(((Double.parseDouble(splitData[6]) - (level_graph.getNode(ed.getDest()).getLocation().x()))), 2) + Math.pow(((Double.parseDouble(splitData[7]) - (level_graph.getNode(ed.getDest()).getLocation().y()))), 2));
                    double fruit_to_src = Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x()) - (Double.parseDouble(splitData[6]))), 2) + Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y()) - (Double.parseDouble(splitData[7]))), 2));
                    if (fruit_from_dest + fruit_to_src - distance <= EPSILON) {
                        temp = ed;
                        b = true;
                    }
                    if (b) break;
                }
                if (b) break;
            }
            if (splitData[4].equals("1")) {
                if (Math.min(temp.getSrc(), temp.getDest()) == temp.getDest()) {
                    edges_with_fruits.add(level_graph.getEdge(temp.getDest(), temp.getSrc()));
                } else {
                    edges_with_fruits.add(temp);
                }
            } else {
                if (Math.max(temp.getSrc(), temp.getDest()) == temp.getDest()) {
                    edges_with_fruits.add(level_graph.getEdge(temp.getDest(), temp.getSrc()));
                } else {
                    edges_with_fruits.add(temp);
                }
            }
        }
        //**************continue CODE HERE******************//
    }

}
