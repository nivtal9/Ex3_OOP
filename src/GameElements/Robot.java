package GameElements;
import utils.Point3D;
import java.util.List;

public class Robot implements robot_data {
    private String[] robot_arr;

    /**
     * simple constructor for robot
     * @param lst game.getRobots() List of Strings
     * @param id the specific String that Listed in the id place of lst
     */
    public Robot(List<String> lst, int id) {
        robot_arr=lst.get(id).split("[:\\,]");
    }
    @Override
    public int getValue() {
        return Integer.parseInt(robot_arr[5]);
    }
    @Override
    public int getDest() {
        return Integer.parseInt(robot_arr[9]);
    }
    @Override
    public int getSrc() {
        return Integer.parseInt(robot_arr[7]);
    }
    @Override
    public Point3D getLocation() {
        return new Point3D(Double.parseDouble(robot_arr[12].substring(1)),Double.parseDouble(robot_arr[13]),0);
    }
}
