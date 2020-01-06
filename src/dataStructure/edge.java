package dataStructure;

import java.io.Serializable;
/**
 * this class implements the interface edge data.
 * represents the set of operations applicable on a
 * directional edge(src,dest) in a (directional) weighted graph.
 * @author : nivtal9
 * @author :Sarah-han
 *
 */
public class edge implements edge_data, Serializable {
    /**
     * private data types of the class
     * int node src id.
     * int node dest id.
     * double weight of edge.
     * int tag of edge.
     */
    private int src;
    private int dest;
    private double weight;
    private int tag;
    /**
     * copy constructor.
     * @param ed - the edge which we copy the data from.
     */
    public edge(edge ed) {
        this.src=ed.src;
        this.dest=ed.dest;
        this.tag =ed.tag;
        this.weight =ed.weight;
    }
    /**
     * Default constructor
     * require int src, int dest, double w.
     */
    public edge(int src, int dest, double w) {
        this.src=src;
        this.dest=dest;
        this.weight=w;
        this.tag=0;
    }
    /**
     * Empty constructor
     */
    public edge(){}

    /**
     * The id of the source node of this edge.
     * @return
     */
    @Override
    public int getSrc() { return this.src; }
    /**
     * The id of the destination node of this edge
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }
    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.weight;
    }
    /**
     * return the remark (meta data) associated with this edge.
     * @return
     */
    @Override
    public String getInfo() {
        return "src:"+this.src+" dest:"+this.dest+" weight:"+this.weight+" tag:"+this.tag;
    }
    /**
     * return description of the data associated with this edge.
     * @return
     */
    public String toString() {
        return "src:"+this.src+" dest:"+this.dest+" weight:"+this.weight+" tag:"+this.tag;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        String str = s;
        String[] splitData = str.split("[:\\ ]");
        src = Integer.parseInt(splitData[1]);
        dest = Integer.parseInt(splitData[3]);
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
     * Allow setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag=t;

    }
}
