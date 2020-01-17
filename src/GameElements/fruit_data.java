package GameElements;

import utils.Point3D;

import java.util.List;


/**
     * This interface represents the set of operations applicable on a
     * fruit used in the game.
     * @author sarah-han
     *
     */
    public interface fruit_data {
        /**
         * Return the value (score) associated with this fruit
         *
         * @return
         */
        public int getValue();

        /**
         * Return the type associated with this fruit.
         * -1 ~> fruit located on edge which id src bigger then id dest
         * 1 ~> fruit located on edge which id src smaller then id dest
         *
         * @return
         */
        public int getType();

        /**
         * Return the location (of applicable) of this fruit
         *
         * @return
         */
        public Point3D getLocation();
    /**
     * Return the boolean value for availability of the fruit
     *
     * @return
     */
    public boolean get_is_Avilable();
    /**
     * set the boolean value for availability of the fruit
     *
     * @return
     */
    public void set_is_Avilable(boolean b);
    }