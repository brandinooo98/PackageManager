import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    Brandon Erickson
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {

	LinkedList[] graph;
	
	/*
	 * Default no-argument constructor
	 */ 
	public Graph() {
		graph = new LinkedList[10];
	}

	/**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void addVertex(String vertex) {
		if (vertex == null){}
		else if (contains(vertex)){}
		else {
			// Searches for empty LinkedList
			for(LinkedList list : graph) {
				if (list.size() == 0)
					list.add(vertex);
			}
			// Resizes array if no empty LinkedLists
			LinkedList[] tempGraph = graph; // Stores current vertices in graph
			graph = new LinkedList[tempGraph.length * 2]; // Resizes graph
			// Reinserts verticies into graph
			for(int i = 0; i < tempGraph.length; i++)
				graph[i] = tempGraph[i];
		}
	}

	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void removeVertex(String vertex) {
		if (vertex == null){}
		else {
			// Searches for vertex's LinkedList
			for(LinkedList vert : graph) {
				// If vertex is found, clears LinkedList
				if (vert.get(0).equals(vertex)) {
					vert.clear();
					//TODO REMOVE EDGES
				}
			}
		}
	}

	/**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * add vertex, and add edge, no exception is thrown.
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
	 */
	public void addEdge(String vertex1, String vertex2) {
		
	}
	
	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     */
	public void removeEdge(String vertex1, String vertex2) {

	}

	private boolean contains(String vertex) {
		for(LinkedList vert : graph) {
			if (vert.contains(vertex))
				return true;
		}
		return false;
	}

	/**
     * Returns a Set that contains all the vertices
     * 
	 */
	public Set<String> getAllVertices() {
		return null;
	}

	/**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
	 */
	public List<String> getAdjacentVerticesOf(String vertex) {
		return null;
	}
	
	/**
     * Returns the number of edges in this graph.
     */
    public int size() {
        return -1;
    }

	/**
     * Returns the number of vertices in this graph.
     */
	public int order() {
        return -1;
    }
}
