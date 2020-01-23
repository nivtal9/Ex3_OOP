package GameElements;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * This class contain number of algorithms used in automatic mode
 * @author sarah-han
 *
 */

public class Game_Algo  implements game_algorithms {
    private int dt=40;
    private static final double EPSILON = 0.0000001;
    private edge_data robot0=null;
    private edge_data robot1=null;
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

    @Override
    public void AutoSetRobot(game_service game,graph level_graph) throws JSONException {
        List<String> Temp_Fruit = game.getFruits();
        JSONObject line = new JSONObject(game.toString());
        JSONObject ttt = line.getJSONObject("GameServer");
        int rs = ttt.getInt("robots");
        int level=ttt.getInt("game_level");
        for (int i = 0; i < rs; i++) {
            if(i==1&&level==16){
                Temp_Fruit.remove(3);
            }
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

    @Override
    public void MoveRobots(game_service game,graph levelgraph,int level){
        List<String> log = game.move();
        if(log!=null) {
            for (String robot_json : log) {
                boolean b=true;
                try {
                    JSONObject line = new JSONObject(robot_json);
                    JSONObject ttt = line.getJSONObject("Robot");
                    int rid = ttt.getInt("id");
                    int src = ttt.getInt("src");
                    int dest = ttt.getInt("dest");
                    if (dest == -1) {
                        dest = nextNode(levelgraph, src, game);
                    }
                    if (log.size() == 2) {
                        if (rid == 0) {
                            robot0 = levelgraph.getEdge(src, dest);
                        }
                        if (rid == 1) {
                            edge_data temp = levelgraph.getEdge(src, dest);
                            if (temp.getDest() == robot0.getDest() || temp.getDest() == robot0.getSrc()) {
                                try {
                                    if (src != dest + 1) {
                                        game.chooseNextEdge(rid, dest + 1);
                                    } else {
                                        game.chooseNextEdge(rid, dest - 1);
                                    }
                                    b=false;
                                } catch (Exception e) {
                                    game.chooseNextEdge(rid, dest - 1);
                                    b=false;
                                }
                            }
                        }
                    }
                    if (log.size() == 3) {
                        if (rid == 0) {
                            robot0 = levelgraph.getEdge(src, dest);
                        }
                        if (rid == 1) {
                            robot1 = levelgraph.getEdge(src, dest);
                            edge_data temp = levelgraph.getEdge(src, dest);
                            if (temp.getDest() == robot0.getDest() || temp.getDest() == robot0.getSrc()) {
                                try {
                                    if (src != dest + 1) {
                                        game.chooseNextEdge(rid, dest + 1);
                                    } else {
                                        game.chooseNextEdge(rid, dest - 1);
                                    }
                                    b=false;
                                } catch (Exception e) {
                                    game.chooseNextEdge(rid, dest - 1);
                                    b=false;
                                }
                            }
                        }
                        if (rid==2){
                            edge_data temp = levelgraph.getEdge(src, dest);
                            if (temp.getDest() == robot0.getDest() || temp.getDest() == robot0.getSrc()||temp.getDest()==robot1.getDest()||temp.getDest()==robot1.getSrc()) {
                                try {
                                    if (src != dest + 1) {
                                        game.chooseNextEdge(rid, dest + 1);
                                    } else {
                                        game.chooseNextEdge(rid, dest - 1);
                                    }
                                    b=false;
                                } catch (Exception e) {
                                    game.chooseNextEdge(rid, dest - 1);
                                    b=false;
                                }
                            }
                        }
                    }
                    if(b) {
                        game.chooseNextEdge(rid, dest);
                    }
                    boolean dtchange=false;
                    if(log.size()==1){
                        if (fruitonedge(src, dest, levelgraph, game) || fruitonedge(dest, src, levelgraph, game)){
                            dtchange=true;
                        }
                    }
                    if(log.size()==2){
                        if (fruitonedge(src, dest, levelgraph, game) || fruitonedge(dest, src, levelgraph, game) ||
                                fruitonedge(robot0.getSrc(),robot0.getDest(),levelgraph,game)||fruitonedge(robot0.getDest(),robot0.getSrc(),levelgraph,game)){
                            dtchange=true;
                        }
                    }
                    if(log.size()==3){
                        if (fruitonedge(src, dest, levelgraph, game) || fruitonedge(dest, src, levelgraph, game) ||
                                fruitonedge(robot0.getSrc(),robot0.getDest(),levelgraph,game)||fruitonedge(robot0.getDest(),robot0.getSrc(),levelgraph,game) ||
                                fruitonedge(robot1.getSrc(),robot1.getDest(),levelgraph,game)||(fruitonedge(robot1.getDest(),robot1.getSrc(),levelgraph,game))) {
                            dtchange=true;
                        }
                    }
                    if (dtchange) {
                        switch (level) {
                            case 9:
                            case 20:
                            case 21:
                            case 23:
                                dt = 40;
                                break;
                            case 16:
                                dt = 65;
                                break;
                            default:
                                dt = 70;
                        }
                    } else {
                        switch (level) {
                            case 21:
                            case 23:
                                dt=75;
                                break;
                            case 20:
                                dt=175;
                                break;
                            case 5:
                                dt = 190;
                                break;
                            case 9:
                            case 19:
                            case 16:
                            case 13:
                                dt = 120;
                                break;
                            default:
                                dt = 170;
                        }
                    }
                } catch (JSONException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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

    private int nextNode(graph levelgraph, int src,game_service game) throws InterruptedException {
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

    /**
     * gets the refresh rate of the game thread
     * @return
     */
    @Override
    public int getdt() {
        return this.dt;
    }

    /**
     * gets a placement of a robot and the method checks if there is a fruit on the robot placement(edge)
     * @param src src of the robot
     * @param dest dest of the robot
     * @param level_graph the graph of the game
     * @param game the game itself
     * @return true if there is a fruit on the robot edge, false if there is not a fruit on the robot edge
     */
    @Override
    public boolean fruitonedge(int src, int dest, graph level_graph, game_service game){
        edge_data ed=level_graph.getEdge(src,dest);
        for (int i = 0; i <game.getFruits().size() ; i++) {
            Fruit f=new Fruit(game.getFruits(),i);
            double distance = Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x()) - (level_graph.getNode(ed.getDest()).getLocation().x())), 2) + Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y()) - (level_graph.getNode(ed.getDest()).getLocation().y())), 2));
            double fruit_from_dest = Math.sqrt(Math.pow(((f.getLocation().x() - (level_graph.getNode(ed.getDest()).getLocation().x()))), 2) + Math.pow(((f.getLocation().y() - (level_graph.getNode(ed.getDest()).getLocation().y()))), 2));
            double fruit_to_src = Math.sqrt(Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().x()) - (f.getLocation().x())), 2) + Math.pow(((level_graph.getNode(ed.getSrc()).getLocation().y()) - (f.getLocation().y())), 2));
            if (fruit_from_dest + fruit_to_src - distance <= EPSILON) {
                return true;
            }
        }
        return false;
    }
}
