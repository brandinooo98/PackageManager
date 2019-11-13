import java.lang.reflect.Array;
import java.util.*;


/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    Brandon Erickson
 * <p>
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {

    ArrayList[] graph;

    /*
     * Default no-argument constructor
     */
    public Graph() {
        graph = new ArrayList[10];
        for (int i = 0; i < graph.length; i++)
            graph[i] = new ArrayList();
    }

    /**
     * Add new vertex to the graph.
     * <p>
     * If vertex is null or already exists,
     * method ends without adding a vertex or
     * throwing an exception.
     * <p>
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph
     */
    public void addVertex(String vertex) {
        if (vertex == null) {
        } else if (vertexSearch(vertex) != -1) {
        } else {
            // Searches for empty LinkedList
            for (ArrayList list : graph) {
                if (list.size() == 0)
                    list.add(vertex);
            }
            // Resizes array if no empty LinkedLists
            ArrayList[] tempGraph = graph; // Stores current vertices in graph
            graph = new ArrayList[tempGraph.length * 2]; // Resizes graph
            // Reinserts vertices into graph
            for (int i = 0; i < tempGraph.length; i++)
                graph[i] = tempGraph[i];
            // Adds ArrayLists to the new indices
            for (int i = tempGraph.length - 1; i < graph.length; i++){
                graph[i] = new ArrayList();
            }
        }
    }

    /**
     * Remove a vertex and all associated
     * edges from the graph.
     * <p>
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges,
     * or throwing an exception.
     * <p>
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph
     */
    public void removeVertex(String vertex) {
        // Checks if given vertex is null
        if (vertex == null) {
        } else {
            int vertIndex = vertexSearch(vertex); // Stores vertex index
            // If index was found, clears element
            if (vertIndex != -1)
                graph[vertIndex].clear();
            // Removes edges to given vertex
            for (ArrayList<String> edgeList : graph){
                int edgeIndex = edgeSearch(vertex, edgeList); // Stores index to edge
                // If found, removes edge
                if(edgeIndex != -1)
                    edgeList.remove(edgeIndex);
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
     * <p>
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph
     * 3. the edge is not in the graph
     */
    public void addEdge(String vertex1, String vertex2) {
        // Checks if given vertex is null
        if (vertex1 == null || vertex2 == null) {
        } else {
            int vert1Index = vertexSearch(vertex1); // Stores vertex1 index
            int vert2Index = vertexSearch(vertex2); // Stores vertex2 index
            // If vertices were found
            if (vert1Index != -1 && vert2Index != -1) {
                int index = edgeSearch(vertex2, graph[vert1Index]); // Stores index of specified edge
                // If edge was not found, creates edge
                if (index == -1)
                    graph[vert1Index].add(vertex2);
            }
        }
    }

    /**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * <p>
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph
     * 3. the edge from vertex1 to vertex2 is in the graph
     */
    public void removeEdge(String vertex1, String vertex2) {
        // Checks if given vertex is null
        if (vertex1 == null || vertex2 == null) {
        } else {
            int vertIndex = vertexSearch(vertex1);
            if (vertIndex != -1) {
                int index = edgeSearch(vertex2, graph[vertIndex]); // Stores index of specified edge
                // If edge was not found, removes edge
                if (index != -1)
                    graph[vertIndex].remove(index);
            }
        }
    }

    /**
     * Finds the index of a given vertex within a graph
     *
     * @param vertex - Vertex to find index of
     * @return - Returns index of vertex if found, otherwise returns -1
     */
    private int vertexSearch(String vertex) {
        int counter = 0;
        // Iterates through graph looking for a specific vertex
        for (ArrayList<String> vert : graph) {
            // If vertex is found returns the index
            if (vert != null && vert.size() != 0 && vert.get(0).equals(vertex))
                return counter;
            else
                counter++;
        }
        return -1;
    }

    /**
     * Finds the index of a given edge within a ArrayList
     *
     * @param vertex   - Edge to find
     * @param edgeList - ArrayList to search in
     * @return - Returns index of edge if found, otherwise returns -1
     */
    private int edgeSearch(String vertex, ArrayList<String> edgeList) {
        int counter = 0;
        // Iterates through ArrayList of vertex looking for a specific edge
        for (String edge : edgeList) {
            // If found returns index
            if (edge != null && edge.equals(vertex))
                return counter;
            else
                counter++;
        }
        return -1;
    }

    /**
     * Returns a Set that contains all the vertices
     */
    public Set<String> getAllVertices() {
        Set<String> vertices = new HashSet<>(); // Stores verticies
        // Iterates through graph storing each vertex
        for (ArrayList<String> vertex : graph) {
            // If vertex is found stores in set
            if (vertex.size() != 0)
                vertices.add(vertex.get(0));
        }
        return vertices;
    }

    /**
     * Get all the neighbor (adjacent) vertices of a vertex
     */
    public List<String> getAdjacentVerticesOf(String vertex) {
        List<String> adjVertices = new ArrayList<>(); // Stores all adjacent vertices
		// Iterates through graph searching for non null lists
        for (ArrayList<String> edgeList : graph) {
            if (edgeList.size() == 0)
                continue;
        	String first = edgeList.get(0); // Stores vertex
			// If list of edges for selected vertex is found, adds all edges to list
            if (first != null && first.equals(vertex)) {
                for (String edge : edgeList)
                    adjVertices.add(edge);
            } else if (first != null && !adjVertices.contains(first)){
            	// Searches through list looking to see if edge to given vertex exists
                for (String edge : edgeList) {
                    if (edge.equals(vertex))
                        adjVertices.add(first);
                }
            }
        }
        return adjVertices;
    }

    /**
     * Returns the number of edges in this graph.
     */
    public int size() {
        int count = 0;
        // Iterates through graph taking every vertex list
        for (ArrayList<String> vertex : graph) {
            count--; // Subtracts vertex from count
            // Iterates through vertex graph counting edges in vertex
            for (String vert : vertex)
                count++;
        }
        return count;
    }

    /**
     * Returns the number of vertices in this graph.
     */
    public int order() {
        int count = 0;
        // Iterates through graph searching for non null lists
        for (ArrayList<String> vertex : graph) {
            // If list is not null, counts a vertex
            if (vertex.size() != 0)
                count++;
        }
        return count;
    }
}
