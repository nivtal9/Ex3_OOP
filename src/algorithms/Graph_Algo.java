package algorithms;
import java.io.*;
import java.util.*;

import dataStructure.*;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms,Serializable{
	/**
	 * private data types of the class
	 * DGraph graph
	 */
	private graph graph;

	/**
	 * a simple Constructor that Initialized a graph into Graph_algo graph
	 * @param g simple graph
	 */
	public Graph_Algo(graph g) {
		graph=new DGraph();
		for(node_data nodes : g.getV()){
			graph.addNode(nodes);
			try {
				for (edge_data edges : g.getE(nodes.getKey())) {
					graph.connect(edges.getSrc(), edges.getDest(), edges.getWeight());
				}
			}
			catch (Exception e){
				//this node has no edges, the graph is still being initialized...
			}
		}
	}

	/**
	 * Empty Constructor
	 */
	public Graph_Algo(){}

	/**
	 * Compute a deep copy of this graph.
	 * @return
	 */
	@Override
	public void init(graph g) {
		graph=new DGraph();
		for(node_data nodes : g.getV()){
			graph.addNode(nodes);
			try {
				for (edge_data edges : g.getE(nodes.getKey())) {
					graph.connect(edges.getSrc(), edges.getDest(), edges.getWeight());
				}
			}
			catch (Exception e){
				//this node has no edges, the graph is still being initialized...
			}
		}
	}
	/**
	 * Init a graph from file
	 * @param file_name
	 */
	@Override
	public void init(String file_name) {
		try
		{
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream in = new ObjectInputStream(file);
			graph GA = (graph) in.readObject();
			graph=GA;
			in.close();
			file.close();
			System.out.println("Graph has been deserialized");
		}
		catch(IOException ex)
		{
			System.out.println("IOException is caught");
		}
		catch(ClassNotFoundException ex)
		{
			System.out.println("ClassNotFoundException is caught");
		}

	}
	/** Saves the graph to a file.
	 *
	 * @param file_name
	 */
	@Override
	public void save(String file_name) {
		try
		{
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(this.graph);
			out.close();
			file.close();
			System.out.println("Graph has been serialized");
		}
		catch(IOException ex)
		{
			System.out.println("IOException is caught");
		}
	}
	/**
	 * Returns true if and only if (iff) there is a valid path from EVREY node to each
	 * other node.
	 * NOTE: assume directional graph - a valid path (a-->b) does NOT imply a valid path (b-->a).
	 * the algorithm the function is based on is https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
	 * take the start node and enters him and his neighbor to a Linked List.
	 * remove the peek node and enters the neighbor of the first in the list.
	 * the purpose is to close a circle with the first node (we save his data).
	 * if a circle i complete the graph is connected.
	 * else not.
	 * @return
	 *
	 */
	@Override
	public boolean isConnected() {
		if(graph.getV().size()==0){
			return true;
		}
		int conutTag = 0;
		boolean NullSrcNode = true;
		for (node_data nd : graph.getV()) {
			nd.setTag(0);
		}
		Queue<Integer> q = new LinkedList<>();
		node_data first = graph.getV().iterator().next();
		q.add(first.getKey());
		while (!q.isEmpty()) {
			int peek = q.peek();
			try {
				for (edge_data e : graph.getE(peek)) {
					if (graph.getNode(e.getDest()).getTag() == 0) {
						graph.getNode(e.getDest()).setTag(1);
						conutTag++;
						q.add(e.getDest());
					}
				}
			} catch (Exception e) {
				//this node has no edges
				NullSrcNode = false;
			}
			q.poll();
		}
		if (conutTag == graph.nodeSize() && NullSrcNode) {
			return true;
		}
		return false;
	}
	/**
	 * returns the length of the shortest path between src to dest
	 * @param src - start node
	 * @param dest - end (target) node
	 * use the shortestPath function and compute the weight.
	 * if not found return -1.
	 * @return
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		List<node_data> ans=shortestPath(src,dest);
		if(ans==null){
			return Integer.MAX_VALUE;
		}
		if(ans.size()==0){
			return 0;
		}
		return ans.get(ans.size()-1).getWeight();
	}
	/**
	 * returns the the shortest path between src to dest - as an ordered List of nodes:
	 * src--> n1-->n2-->...dest
	 * see: https://en.wikipedia.org/wiki/Shortest_path_problem
	 * @param src - start node
	 * @param dest - end (target) node
	 * we use the same idea of the isConnected() function for visiting all nodes in the graph.
	 * while visiting each node we update the weight and save which node gave us the weight.
	 * after visiting all the nodes we go to the dest, enter the arr who gave him the weight.
	 * go to this node and enter the arr who gave him the weight and so on until the src.
	 * we revers the order to return the list in the right order.
	 * if way is not found return null.
	 * @return
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		List<node_data> Path=new LinkedList<>();
		if(src==dest) return Path;
		Queue<Integer> q=new LinkedList<>();
		for (node_data nd:graph.getV()) {
			nd.setTag(0);
			nd.setWeight(Integer.MAX_VALUE);
		}
		graph.getNode(src).setTag(src);
		graph.getNode(src).setWeight(0);
		q.add(src);
		while(!q.isEmpty()){
			int peek=q.peek();
			try {
				for (edge_data e : graph.getE(peek)) {
					if (graph.getNode(e.getDest()).getWeight() > e.getWeight() + graph.getNode(e.getSrc()).getWeight()) { //check if edge+node we came from weight is less then our node
						graph.getNode(e.getDest()).setTag(peek); //changing tag to the node we came from
						double RecentWeight = graph.getNode(e.getSrc()).getWeight(); //recent node weight
						graph.getNode(e.getDest()).setWeight(RecentWeight + e.getWeight()); //changing node weight to recent node weight+edge weight
						q.add(e.getDest());
					}
				}
				q.poll();
			}
			catch(Exception e) { q.poll(); }
		}
		if(graph.getNode(dest).getTag()==0&&graph.getNode(dest).getWeight()==Integer.MAX_VALUE){
			return null;
		}
		Path.add(graph.getNode(dest));
		int tempKey=graph.getNode(dest).getTag();
		while(tempKey!=src) {
			Path.add(graph.getNode(tempKey));
			tempKey=graph.getNode(tempKey).getTag();
		}
		Path.add(graph.getNode(src));
		LinkedList<node_data> ans=new LinkedList<>();
		for (int i = Path.size()-1; i >= 0; i--) {
			ans.add(Path.get(i));
		}
		return ans;
	}

	/**
	 * this TSP Method Would check if the List of Targets given is "isConnected" and then Would return approximately
	 * the shortest Path Between all targets list, if there is duplicates in targets list it would count all the duplicates as one
	 * because we need to pass all the dots of the list min one time and it doesnt matter if some node key is given 10 times or just one
	 * because we already pass on him once.
	 * if there is no Exist short path or the targets List in not connected, the Method return null.
	 * @param targets List of targets we Should return an approximately the shortest path between.
	 * @return an approximately the shortest path between given targets list
	 */
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		List<Integer> targets_no_duplicates=new LinkedList<>();
		for (int i = 0; i <targets.size() ; i++) {
			if(!targets_no_duplicates.contains(graph.getNode(targets.get(i)).getKey())){
				targets_no_duplicates.add(graph.getNode(targets.get(i)).getKey());
			}
		}
		targets=targets_no_duplicates;
		List<node_data>ans=new LinkedList<>();
		if(targets.size()==1){
			ans.add(graph.getNode(targets.get(0)));
			return ans;
		}
		int i=0;
		node_data temp=graph.getNode(targets.get(0));
		ans.add(temp);
		if(ListIsConnected(targets)||isConnected()){
			while(i!=targets.size()-1&&temp.getKey()!=targets.get(targets.size()-1)){
				double MinLengthNodeWeight=Integer.MAX_VALUE;
				int MinLengthNode=0;
				for (node_data nd:graph.getV()) {
					if (shortestPathDist(temp.getKey(), nd.getKey()) < MinLengthNodeWeight&&nd.getKey()!=temp.getKey()&&targets.contains(nd.getKey())) {
						if (!CheckForTightDuplicates(ans,nd)) {
							MinLengthNode = nd.getKey();
							MinLengthNodeWeight = nd.getWeight();
						}
					}
				}
				List<node_data>ShortestPath=new LinkedList<>();
				ShortestPath=shortestPath(temp.getKey(),MinLengthNode);
				for (int j = 0; j <ShortestPath.size() ; j++) {
					ans.add(ShortestPath.get(j));
				}
				temp=graph.getNode(MinLengthNode);
				i++;
			}
			ans.add(temp);
			for (int k = 0; k < ans.size()-1; k++) {
				if (ans.get(k).getKey() == ans.get(k+1).getKey()) {
					ans.remove(k + 1);
				}
			}
		}
		else{
			return null;
		}
		return ans;
	}

	/**
	 * This method return a deep copy of our Graph
	 * @return a deep copy of our Graph
	 */
	@Override
	public graph copy() {
		DGraph graphcopy=new DGraph();
		Collection<node_data> nodescopy=graph.getV();
		for (node_data ND:nodescopy) {
			node_data temp=new node((node)ND);
			graphcopy.addNode(temp);
		}
		for (node_data ND:nodescopy) {
			Collection<edge_data> edgescopy = graph.getE(ND.getKey());
			try {
				for (edge_data ED : edgescopy) {
					graphcopy.connect(ED.getSrc(), ED.getDest(), ED.getWeight());
				}
			}
			catch (Exception e){
				//this node has no edges, the graph is still being initialized...
			}
		}
		return graphcopy;
	}

	//**************PRIVATE METHODS**************//

	private boolean ListIsConnected(List<Integer> target){
		graph newgraph=new DGraph();
		for (int i = 0; i <target.size() ; i++) {
				newgraph.addNode(graph.getNode(target.get(i)));
			try{
					for (edge_data ed : graph.getE(target.get(i))) {
						if (target.contains(ed.getDest())) {
							newgraph.connect(ed.getSrc(), ed.getDest(), ed.getWeight());
						}
					}
			}
			catch (Exception e){
				return false;
			}
		}
		int conutTag = 0;
		boolean NullSrcNode = true;
		for (node_data nd : newgraph.getV()) {
			nd.setTag(0);
		}
		Queue<Integer> q = new LinkedList<>();
		node_data first = newgraph.getV().iterator().next();
		q.add(first.getKey());
		while (!q.isEmpty()) {
			int peek = q.peek();
			try {
				for (edge_data e : newgraph.getE(peek)) {
					if (newgraph.getNode(e.getDest()).getTag() == 0) {
						newgraph.getNode(e.getDest()).setTag(1);
						conutTag++;
						q.add(e.getDest());
					}
				}
			} catch (Exception e) {
				//this node has no edges
				NullSrcNode = false;
			}
			q.poll();
		}
		if (conutTag == newgraph.nodeSize() && NullSrcNode) {
			return true;
		}
		return false;
	}
	private boolean CheckForTightDuplicates(List<node_data>ans,node_data nd){
		for (int k = 0; k < ans.size()-1; k++) {
			if (ans.get(k).getKey() == ans.get(k+1).getKey()) {
				if(ans.get(k).getKey()==nd.getKey()) {
					return true;
				}
			}
		}
		return false;
	}
}