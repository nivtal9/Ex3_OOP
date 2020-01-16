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
    public Robot(String s){robot_arr=s.split("[:\\,]");}
    @Override
    public int getValue() {
        return Integer.parseInt(robot_arr[4]);
    }
    @Override
    public int getDest() {
        return Integer.parseInt(robot_arr[8]);
    }
    @Override
    public int getSrc() { return Integer.parseInt(robot_arr[6]); }
    @Override
    public int getId(){return Integer.parseInt(robot_arr[2]);}
    @Override
    public Point3D getLocation() { return new Point3D(Double.parseDouble(robot_arr[12].substring(1)),Double.parseDouble(robot_arr[13]),0); }
    public int TotalScore(){return Integer.parseInt(robot_arr[6]);}
}
