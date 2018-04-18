package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.insa.algo.ArcInspector;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShortestPathTest {
	
    protected ArcInspector arcInspector;
     
    // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc x1x2, x1x3, x2x4, x2x5, x2x6, x3x1, x3x2, x3x6, x5x3, x5x4, x5x6, x6x5;
	
    @BeforeClass
	public static void initAll() {
		// 10 and 20 meters per seconds
        RoadInformation speed = new RoadInformation(RoadType.MOTORWAY, null, true, 36, "");

        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        x1x2 = Node.linkNodes(nodes[0], nodes[1], 7, speed, null);
        x1x3 = Node.linkNodes(nodes[0], nodes[2], 8, speed, null);
        x2x4 = Node.linkNodes(nodes[1], nodes[3], 4, speed, null);
        x2x5 = Node.linkNodes(nodes[1], nodes[4], 1, speed, null);
        x2x6 = Node.linkNodes(nodes[1], nodes[5], 5, speed, null);
        x3x1 = Node.linkNodes(nodes[2], nodes[0], 7, speed, null);
        x3x2 = Node.linkNodes(nodes[2], nodes[1], 2, speed, null);
        x3x6 = Node.linkNodes(nodes[2], nodes[5], 2, speed, null);
        x5x3 = Node.linkNodes(nodes[4], nodes[2], 2, speed, null);
        x5x4 = Node.linkNodes(nodes[4], nodes[3], 2, speed, null);
        x5x6 = Node.linkNodes(nodes[4], nodes[5], 3, speed, null);
        x6x5 = Node.linkNodes(nodes[5], nodes[4], 3, speed, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);
		
	}
	
    @Test
	public void test1() {
		ShortestPathData data1 = new ShortestPathData(graph, nodes[0], nodes[5], arcInspector);
		
		DijkstraAlgorithm algo1 = new DijkstraAlgorithm(data1);
		
		BellmanFordAlgorithm algo2 = new BellmanFordAlgorithm(data1);
		
		assertEquals((int)algo1.run().getPath().getLength(), (int)algo2.run().getPath().getLength());
	}
	
	
	
}
