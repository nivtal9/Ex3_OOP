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

/**
 * This class contain number of algorithms used in automatic mode
 * @author sarah-han
 *
 */
public class Game_Algo  implements auto_game {
    public static final double EPSILON = 0.0000001;


    /**
     * https://www.mathsisfun.com/algebra/distance-2-points.html
     * Calculate the distance between src and dest of an edge. "x"
     * Calculate the distance between src and fruit "y"
     * Calculate the distance between dest and fruit "z"
     * if x=y+z meaning the fruit is on this edge.
     * check for type:
     * -1 ~> fruit located on  the edge id src bigger then id dest
     * 1 ~> fruit located on  the edge id src smaller then id dest
     * @param i- integer for fruit number
     * @param Game
     * @param level_graph
     * @return edge -a specific fruit is on.
     */

    //*********************graph level_graph          game_service Game  **********************//
    @Override
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
    /**
     * The function implements algorithm for first location fo the robots in the level
     * by amount of robot in the level choose the same amount of fruit
     * The selected fruits are worth the highest score (value)
     * locate each robot in the src of each fruit
     * @param game
     * @param level_graph
     * need to be protected from Exception
     * @return
     */

//*********************graph level_graph     game_service game  **********************//

    @Override
    public void AutoSetRobot(game_service game,graph level_graph) throws JSONException {
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
    /**
     * The function implements algorithm to direct the robot to his next destination
     * analyze the jason given by the game_service
     * then extract the information of the robots:id, src, dest
     * for a specific robot if his dest=-1
     * calls privet function which return what will be the next node the robot will move to.
     * the function named "nextNode"
     * @param game
     * @param levelgraph
     * need to be protected from Exception
     * @return
     */

    //*********************graph levelgraph**********************//


    @Override
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
    /**
     * the function "MoveRobots" calls this private function to help direct a robot to his next destination
     * for on all fruits in the level
     * call function "shortestPathDist" which receive  src  and dest (of the fruit edge)
     * compere all double "shortestPathDist" and save the min double and the dest relevant
     *call function "shortestPath" which receive  src  and dest relevant
     * send the robot to key locate in [1] place of the list "shortestPath" returns
     * @param game
     * @param src on which the robot located on
     * @param levelgraph
     * @return key -id of node dest.
     */

    //*********************graph levelgraph**********************//


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
