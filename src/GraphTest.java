import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GraphTest {

    private Graph graph;

    /**
     * Initialize empty graph to be used in each test
     */
    @BeforeEach
    public void setUp() throws Exception {
        graph = new Graph();
    }

    /**
     * Not much to do, just make sure that variables are reset
     */
    @AfterEach
    public void tearDown() throws Exception {
        graph = null;
    }


    /**
     * Initializes the graph full of vertices and edges to be tested on
     */
    private void initGraph(){
        graph.addVertex("Yellow");
        graph.addVertex("Blue");
        graph.addVertex("Banana");
        graph.addVertex("Sun");
        graph.addVertex("Pencil");
        graph.addVertex("Water");
        graph.addVertex("Sky");
        graph.addVertex("Blue Man");
        graph.addEdge("Banana", "Yellow");
        graph.addEdge("Sun", "Yellow");
        graph.addEdge("Pencil", "Yellow");
        graph.addEdge("Water", "Blue");
        graph.addEdge("Sky", "Blue");
        graph.addEdge("Blue Man", "Blue");
    }

    /**
     * Tests to see if the graph can correctly add and remove 10 vertices
     */
    @Test
    public void test00_Add_And_Remove_Many() {
        for (Integer i = 0; i < 10; i++)
            graph.addVertex(i.toString());
        for (Integer i = 0; i < 10; i++){
            if (graph.graph[i].size() != 0 && !graph.graph[i].get(0).equals(i.toString()))
                fail("A vertex was not inserted at the right place");
        }
        for(Integer i = 0; i < 10; i++)
            graph.removeVertex(i.toString());
        for(Integer i = 0; i < 10; i++){
            Set<String> vertices = graph.getAllVertices();
            graph.removeVertex(i.toString());
            if (vertices.contains(i.toString()))
                fail("Vertex was not removed correctly");
        }
    }

    /**
     * Tests to see if the getAdjacentVertices() method functions correctly
     */
    @Test
    public void test01_Get_Adjacent_Vertices(){
        initGraph();
        List<String> adjBlue = graph.getAdjacentVerticesOf("Blue");
        List<String> adjYellow =graph.getAdjacentVerticesOf("Yellow");
        List<String> blue = new ArrayList<>();
        List<String> yellow = new ArrayList<>();
        blue.add("Water");
        blue.add("Sky");
        blue.add("Blue Man");
        yellow.add("Banana");
        yellow.add("Sun");
        yellow.add("Pencil");
        if(!blue.equals(adjBlue) || !yellow.equals(adjYellow))
            fail("GetAdjacentVerticesOf did not get all values");
    }

    /**
     * Tests to see if the getAllVertices() method works correctly
     */
    @Test
    public void test02_Get_All_Vertices(){
        Set<String> expected = new HashSet<>();
        for (Integer i = 0; i < 10; i++) {
            graph.addVertex(i.toString());
            expected.add(i.toString());
        }
        Set<String> actual = graph.getAllVertices();
        System.out.println(expected);
        System.out.println(actual);
        if(!actual.equals(expected) && !expected.equals(actual))
            fail("GetAllVertices() does not have all the correct elements");
    }

    /**
     * Tests to see if the graph can correctly remove an edge
     */
    @Test
    public void test03_Edge_Removal(){
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");
        graph.addEdge("c", "a");
        graph.removeEdge("c", "o");
        if (graph.graph[0].size() != 1 && graph.graph[4].size() != 1)
            fail("Edge was not correctly removed");
    }

    /**
     * Tests if order() and size() return correct values even if a vertex that had an edge was removed
     */
    @Test
    public void test04_Order_And_Size(){
        initGraph();
        graph.removeVertex("Blue Man");
        if(graph.order() != 7)
            fail("Order is incorrect, order is " + graph.order() + " instead of 7");
        if(graph.size() != 5)
            fail("Size is incorrect, size is " + graph.size() + " instead of 5");
    }
}
