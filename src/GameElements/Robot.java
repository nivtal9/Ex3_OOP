package GameElements;
import utils.Point3D;
import java.util.List;
/**
 * This class represents the set of robot's that shown
 * in a given game to play with
 * @author nivtal9
 */
public class Robot implements robot_data {

    /**
     * private data types of the class
     * String[] robot_arr;
     */
    private String[] robot_arr;

    /**
     * simple constructor for robot
     * @param lst game.getRobots() List of Strings
     * @param id the specific String that Listed in the id place of lst
     */
    public Robot(List<String> lst, int id) {
        robot_arr=lst.get(id).split("[:\\,]");
    }
    /**
     * constructor *JUST* to get game info, the method that use that constructor is TotalScore().
     * @param s game.toString()
     */
    public Robot(String s){robot_arr=s.split("[:\\,]");}

    /**
     * @return the Value (Score) of this Robot.
     */
    @Override
    public int getValue() {
        return (int)Double.parseDouble(robot_arr[4]);
    }

    /**
     * @return the remark node id associated with this Robot Dest.
     * if the user wont set a Dest, Default is -1
     */
    @Override
    public int getDest() {
        return Integer.parseInt(robot_arr[8]);
    }

    /**
     * return the node id associated with this Robot Src
     * @return
     */
    @Override
    public int getSrc()
    { return Integer.parseInt(robot_arr[6]); }

    /**
     * @return the Id of this Robot.
     */
    @Override
    public int getId(){return Integer.parseInt(robot_arr[2]);}

    /**
     * return the remark (meta data) associated with this Robot Location.
     * @return
     */
    @Override
    public Point3D getLocation() { return new Point3D(Double.parseDouble(robot_arr[12].substring(1)),Double.parseDouble(robot_arr[13]),0); }

    /**
     * this Method returns the TotalScore at the end of the game
     * @return
     */
    @Override
    public int TotalScore(){return Integer.parseInt(robot_arr[8]);}
    /**
     * this Method returns the Total Moves of all the robots
     * @return
     */
    @Override
    public int TotalMoves(){return Integer.parseInt(robot_arr[6]);}
}
