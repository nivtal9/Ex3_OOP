package GameElements;

import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import org.json.JSONException;

/**
 * This interface represents the set of function needed for auto version of the game
 * you may need to use EPSILON = 0.0000001
 * @author sarah-han
 */

public interface game_algorithms {
    /**
     * The function need to be an algorithm which direct the robot to his next destination
     * @param game_service
     * @param graph of the level
     * @param level level
     * need to be protected from Exception
     * @return
     */

    void MoveRobots(game_service game_service,graph graph,int level) throws JSONException ;
    /**
     * The function need to be an algorithm for first location fo the robots in the level
     * @param game_service
     * @param graph of the level
     * need to be protected from Exception
     * @return
     */

    void AutoSetRobot(game_service game_service,graph graph) throws JSONException;
    /**
     * The function return the edge a specific fruit is on.
     * @param i- integer for fruit number
     * @param game_service
     * @param graph of the level
     * @return edge
     */
    edge_data getFruitEdge(int i, game_service game_service, graph graph);
    /**
    * gets the refresh rate of the game thread
    */
    int getdt();

    /**
     * gets a placement of a robot and the method checks if there is a fruit on the robot placement(edge)
     * @param src src of the robot
     * @param dest dest of the robot
     * @param level_graph the graph of the game
     * @param game the game itself
     * @return true if there is a fruit on the robot edge, false if there is not a fruit on the robot edge
     */
    boolean fruitonedge(int src,int dest,graph level_graph,game_service game);
}
