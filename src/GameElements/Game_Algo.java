package GameElements;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
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
    public void AutosetRobot(game_service game,graph level_graph) throws JSONException {
        List<String> Temp_Fruit = game.getFruits();
        String info = game.toString();
        JSONObject line;
        line = new JSONObject(info);
        JSONObject ttt = line.getJSONObject("GameServer");
        int rs = ttt.getInt("robots");
        for (int i = 0; i < rs; i++) {
            int maxFruit = Integer.MIN_VALUE;
            int MaxFruitID = 0;
            for (int j = 0; j < Temp_Fruit.size(); j++) {
                Fruit f = new Fruit(Temp_Fruit, j);
                if (f.getValue() > maxFruit) {
                    maxFruit = f.getValue();
                    MaxFruitID = j;
                }
            }
            game.addRobot(new Game_Algo().getFruitEdge(MaxFruitID,game,level_graph).getSrc());
            Temp_Fruit.remove(MaxFruitID);
        }
    }
    public void MoveRobots(game_service game,graph levelgraph) throws JSONException {
        List<String> log = game.move();
        if(log!=null) {
            for(int i=0;i<log.size();i++) {
                String robot_json = log.get(i);
                try {
                    JSONObject line = new JSONObject(robot_json);
                    JSONObject ttt = line.getJSONObject("Robot");
                    int rid = ttt.getInt("id");
                    int src = ttt.getInt("src");
                    int dest = ttt.getInt("dest");
                    if(dest==-1) {
                        dest = nextNode(levelgraph, src,game);
                        game.chooseNextEdge(rid, dest);
                    }
                }
                catch (JSONException e) {e.printStackTrace();}
            }
        }
        /*Robot robot=null;
        Fruit fruit=null;
        List<node_data> lst=new LinkedList<>();
        Graph_Algo ga=new Graph_Algo();
        ga.init(levelgraph);
        for(int i=0;i<game.getRobots().size();i++){
            robot =new Robot(game.getRobots(),i);
            if(robot.getDest()!=-1) {
                double shortestpathdist = Integer.MAX_VALUE;
                for (int j = 0; j < game.getFruits().size(); j++) {
                    fruit = new Fruit(game.getFruits(), j);
                    if (ga.shortestPathDist(robot.getSrc(), getFruitEdge(j, game, levelgraph).getDest()) < shortestpathdist) {
                        shortestpathdist = ga.shortestPathDist(robot.getSrc(), getFruitEdge(j, game, levelgraph).getDest());
                        lst = ga.shortestPath(robot.getSrc(), getFruitEdge(j, game, levelgraph).getDest());
                    }
                    if (j == game.getFruits().size() - 1) {
                        fruit.set_is_Avilable(false);
                    }
                }
                for (int k = 1; k < lst.size(); k++) {
                    while (robot.getDest()!=-1){game.move();}
                    game.chooseNextEdge(i, lst.get(k).getKey());
                }
            }
        }*/
    }

    private int nextNode(graph levelgraph, int src,game_service game) {
        Graph_Algo ga=new Graph_Algo();
        int key =-1;
        double shortestpathdist=Integer.MAX_VALUE;
        ga.init(levelgraph);
        for (int j = 0; j <game.getFruits().size() ; j++){
            edge_data ed=getFruitEdge(j, game, levelgraph);
            if (ga.shortestPathDist(src, ed.getDest()) < shortestpathdist) {
                try {
                    shortestpathdist = ga.shortestPathDist(src, ed.getDest());
                    key = ga.shortestPath(src, ed.getDest()).get(1).getKey();
                }
                catch (Exception e){
                    key=ed.getSrc();
                }
            }
        }
        return key;
    }
}
