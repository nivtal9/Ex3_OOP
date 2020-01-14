package GameElements;

import utils.Point3D;

import java.util.List;

public class Fruit implements fruit_data{

    private String[] fruit_arr;
    /**
     * simple constructor
     * @return
     */
    public Fruit (List<String> lst, int i) {
        fruit_arr = lst.get(i).split("[:\\,]");
    }

    @Override
    public int getValue() {
        return  Integer.parseInt(fruit_arr[2]);
     
    }

    @Override
    public int getType() {
        return Integer.parseInt(fruit_arr[4]);

    }

    @Override
    public Point3D getLocation() {

        fruit_arr[6]=fruit_arr[6].substring(1);
        double x=Double.parseDouble(fruit_arr[6]);
        double y=Double.parseDouble(fruit_arr[7]);
        double z=0.0;
        Point3D location= new Point3D( x, y, z);
        return location;
    }




}
