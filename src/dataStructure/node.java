package dataStructure;
import java.io.Serializable;
import java.util.*;
import utils.Point3D;
/**
 * this class implements the interface node_data.
 * represents the set of operations applicable on a
 * node (vertex) in a (directional) weighted graph.
 * @author : nivtal9
 * @author : Sarah-han
 */
public class node implements node_data, Serializable {
    /**
     * private data types of the class
     * int node id(id=key).
     * Point3D location of the node.(class Point3D in utils).
     * double weight of node.
     * int tag of node.
     */
    private int key;
    private Point3D location;
    private double weight;
    private int tag;
    /**
     * copy constructor.
     * @param n - the node which we copy the data from.
     */
    public node(node n) {
        this.key = n.key;
        this.location = n.location;
        this.tag = n.tag;
        this.weight = n.weight;
    }
    /**
     * Default constructor
     * require int key(id), Point3D location, double w.
     */
    public node(int key, Point3D location,double weight) {
        this.key = key;
        this.location = location;
        this.tag = 0;
        this.weight = weight;
    }
    /**
     * Empty constructor
     */
    public node(){}

    /**
     * Return the key (id) associated with this node.
     * @return
     */
    @Override
    public int getKey() {
        return this.key;
    }
    /** Return the location (of applicable) of this node, if
     * none return null.
     *
     * @return
     */
    @Override
    public Point3D getLocation() {
        if (this.location != null) return this.location;
        else return null;
    }
    /** Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(Point3D p) {
        this.location = new Point3D(p);
    }
    /**
     * Return the weight associated with this node.
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }
    /**
     * Allows changing this node's weight.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight = w;
    }
    /**
     * return the remark (meta data) associated with this node.
     * @return
     */
    @Override
    public String getInfo() {
        return "key:" + this.key + " location:" + this.location + " weight:" + this.weight + " tag:" + this.tag;
    }
    /**
     * return description of the data associated with this edge.
     * @return
     */
    @Override
    public String toString() {
        return "key:" + this.key + " location:" + this.location + " weight:" + this.weight + " tag:" + this.tag;
    }
    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        String str = s;
        String[] splitData = str.split("[:\\ ]");
        key = Integer.parseInt(splitData[1]);
        location = new Point3D(splitData[3]);
        weight = Double.parseDouble(splitData[5]);
        tag = Integer.parseInt(splitData[7]);
    }
    /**
     * Temporal data (aka 0 for not visited,1 for visited)
     * which can be used be algorithms
     * @return
     */

    @Override
    public int getTag() {
        return this.tag;
    }
    /**
     * Allow setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) { this.tag = t; }
}