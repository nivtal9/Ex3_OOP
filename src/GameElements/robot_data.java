package GameElements;
import utils.Point3D;

/**
 * This interface represents the set of robot's that shown
 * in a given game to play with
 * @author nivtal9
 */
public interface robot_data {
    /**
     * @return the Value (Score) of this Robot.
     */
    public int getValue();
    /**
     * @return the Id of this Robot.
     */
    public int getId();
    /**
     * @return the remark node id associated with this Robot Dest.
     * if the user wont set a Dest, Default is -1
     */
    public int getDest();
    /**
     * return the node id associated with this Robot Src
     * @return
     */
    public int getSrc();
    /**
     * return the remark (meta data) associated with this Robot Location.
     * @return
     */
    public Point3D getLocation();

    /**
     * This Method is working only with the constuctor that gets *JUST* a String that contains the game info
     * and returns the TotalScore added from all the robots
     * @return
     */
    public int TotalScore();
    /**
     * this Method returns the Total Moves of all the robots
     * @return
     */
    public int TotalMoves();
}
