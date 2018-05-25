package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Graph;


import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class PerformanceTest {
	protected static ArcInspector arcInspector;
    
    // Small graph use for tests
    private static Graph graph;
    
	public void tester (String mapName) throws IOException {
		
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
        arcInspector = ArcInspectorFactory.getAllFilters().get(0);
        for (int i=0; i<200; i++) {
        	int a = (int)(Math.random() * graph.size()); 
        	int b = (int)(Math.random() * graph.size()); 
            ShortestPathData data = new ShortestPathData(graph, graph.get(a), graph.get(b), arcInspector);	
            DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);
        	algoD.doRun();
        }
	}

	
	@Test
	public void test1() throws IOException {
		String mapName = "Maps\\carre.mapgr";
		tester (mapName);
	}
	
	@Test
	public void test2() throws IOException {
		String mapName = "Maps\\insa.mapgr";
		tester (mapName);
	}
	
	@Test
	public void test3() throws IOException {
		String mapName = "Maps\\midi-pyrenees.mapgr";
		tester (mapName);
	}
	
	@Test
	public void test4() throws IOException {
		String mapName = "Maps\\carre-dense.mapgr";
		tester (mapName);
	}
	
}

	
