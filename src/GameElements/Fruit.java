package GameElements;

import utils.Point3D;

import java.util.List;

public class Fruit implements fruit_data{

    private String[] fruit_arr;
    private boolean is_Avilable;
    /**
     * simple constructor
     * @return
     */
    public Fruit (List<String> lst, int i) {
        fruit_arr = lst.get(i).split("[:\\,]");
        this.is_Avilable=true;
    }

    @Override
    public int getValue() {
        return (int)Double.parseDouble(fruit_arr[2]);
     
    }

    @Override
    public int getType() {
        return Integer.parseInt(fruit_arr[4]);

    }
    @Override
    public Point3D getLocation() {
        double x=Double.parseDouble(fruit_arr[6].substring(1));
        double y=Double.parseDouble(fruit_arr[7]);
        return new Point3D( x, y, 0.0);
    }

    @Override
    public boolean get_is_Avilable() {
        return this.is_Avilable;
    }

    @Override
    public void set_is_Avilable(boolean b) {
        this.is_Avilable=b;
    }
}
