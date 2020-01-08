package dataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * this class implements the interface graph.
 * represents a directional weighted graph.
 * road-system or communication network in mind - and should support a large number of nodes (over 100,000).
 * implementation is based on an efficient compact representation (NOT be based on a n*n matrix).
 */
public class DGraph implements graph, Serializable {
    /**
     * private data types of the class
     * first hashMap contains the nodes(vertex).
     * second and Third contains the edges. the data of the the hashMap is a hashMap.
     * int MC-counter for version number of the graph.
     * int NodeSize-counter for node(vertex) numbers in the graph
     * int EdgeSize-counter for edge numbers in the graph
     */
    private HashMap<Integer, node_data> HashNode = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, edge_data>> HashEdge = new HashMap<>();
    private int MC = 0;
    private int NodeSize = 0;
    private int EdgeSize = 0;
    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if (HashNode.get(key) == null) {
            return null;
        }
        return this.HashNode.get(key);
    }
    /**
     * return the data of the edge (src,dest), null if none.
     *run in O(1) time.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (HashEdge.get(src).get(dest) == null) {
            return null;
        }
        return this.HashEdge.get(src).get(dest);
    }
    /**
     * add a new node to the graph with the given node_data.
     *run in O(1) time.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if (!(HashNode.containsKey(n.getKey()))) {
            HashNode.put(n.getKey(), n);
            MC++;
            NodeSize++;
        } else {
            throw new RuntimeException("The Edge Id is already exist!");
        }

    }
    /**
     * Connect an edge with weight w between node src to node dest.
     * run in O(1) time.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     *check if the src == dest, if so no need to connect.
     *check if the src exist,check if the dest exist,if not return Runtime Exception.
     *else create a new edge.
     *update edge size
     */
    @Override
    public void connect(int src, int dest, double w) {
        boolean b=true;
        if (src == dest) {
            System.out.println("src and dest are the same");
            b=false;
        }
        if (b||(!(HashNode.get(src) == null) || !(HashNode.get(dest) == null))) {
            if (HashEdge.containsKey(src)) {
                if (HashEdge.get(src).get(dest) != null) {
                    throw new RuntimeException("The edge is already exist!");
                } else {
                    edge_data edge = new edge(src, dest, w);
                    this.HashEdge.get(src).put(dest, edge);
                    MC++;
                    EdgeSize++;
                }
            } else {
                edge_data edge = new edge(src, dest, w);
                HashEdge.put(src, new HashMap<>());
                this.HashEdge.get(src).put(dest, edge);
                MC++;
                EdgeSize++;
            }
        } else if(b){throw new RuntimeException("src/dest does not exist!");}
    }
    /**
     * This method return a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * run in O(1) time.
     * @return Collection<node_data>
     * using a HashMap function that return collection.
     */
    @Override
    public Collection<node_data> getV() {
        return this.HashNode.values();
    }
    /**
     * This method return a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     *run in O(1) time.
     * @return Collection<edge_data>
     * using a HashMap function that return collection.
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return HashEdge.get(node_id).values();
    }
    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     *run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     * if the node exist delete all edge the node is src or dest.
     * update edge size.
     * delete the node .
     * update node size.
     */
    @Override
    public node_data removeNode(int key) {
        node_data temp;
        if(HashNode.containsKey(key)){
            temp = this.HashNode.get(key);
        }
        else{return null;}
        if (HashNode.get(key) != null) {
            int x = HashEdge.get(key).size();
            HashEdge.remove(key);
            EdgeSize -= x;
        }
        for (int it: HashEdge.keySet()) {
            if (HashEdge.get(it).containsKey(key)) {
                HashEdge.get(it).remove(key);
                EdgeSize--;
            }
        }
        HashNode.remove(key);// remove the node of the key
        MC++;
        NodeSize--;
        return temp;
    }
    /**
     * Delete the edge from the graph,
     *run in O(1) time.
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     * remove the edge if exist from the HashMap. update edge size
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        edge_data e;
        if(getEdge(src,dest)!=null){
            e=getEdge(src,dest);
            HashEdge.get(src).remove(dest);
            EdgeSize--;
        }
        else{
            return  null;
        }
        return e;
    }
    /** return the number of vertices (nodes) in the graph.
     *run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {

        return this.NodeSize;
    }
    /**
     * return the number of edges (assume directional graph).
     *run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {

        return this.EdgeSize;
    }
    /**
     * return the Mode Count - for testing changes in the graph.
     * @return
     */
    @Override
    public int getMC() {

        return this.MC;
    }
}
