package GameElements;

import utils.Point3D;

import java.util.List;


/**
 * This class represents the set of operations applicable on a
 * fruit used in the game.
 * @author sarah-han
 *
 */
public class Fruit implements fruit_data{
    /**
     * private data types of the class
     * String[] fruit_arr
     */
    private String[] fruit_arr;

    /**
     * simple constructor
     * @param i- the fruit location in the list the
     * @param lst- list of string representing the fruit data
     * @return
     */
    public Fruit (List<String> lst, int i) {
        fruit_arr = lst.get(i).split("[:\\,]");
    }
    /**
     * Return the value (score) associated with this fruit
     * @return
     */
    @Override
    public int getValue() {
        return (int)Double.parseDouble(fruit_arr[2]);

    }
    /**
     * Return the type associated with this fruit.
     * -1 ~> fruit located on edge which id src bigger then id dest
     * 1 ~> fruit located on edge which id src smaller then id dest
     *
     * @return
     */
    @Override
    public int getType() {
        return Integer.parseInt(fruit_arr[4]);
    }
    /**
    * Return the location (of applicable) of this fruit
    *
    * @return
    */
    @Override
    public Point3D getLocation() {
        double x=Double.parseDouble(fruit_arr[6].substring(1));
        double y=Double.parseDouble(fruit_arr[7]);
        return new Point3D( x, y, 0.0);
    }
}
