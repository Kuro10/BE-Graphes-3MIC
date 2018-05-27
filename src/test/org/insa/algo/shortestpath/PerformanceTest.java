package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    
	public void tester (ArrayList<String> mapList) throws IOException {
		
        arcInspector = ArcInspectorFactory.getAllFilters().get(0);
        BufferedWriter out = new BufferedWriter(new FileWriter("Output\\temps_CPU.txt"));
        for (String map : mapList) {
            // Create a graph reader.
        	String mapName = "Maps\\" + map + ".mapgr";
            GraphReader reader = new BinaryGraphReader(
                    new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
            //done Read the graph.
            graph = reader.read();
            long timeD1 = 0;
            long timeA1 = 0;
            long timeD2 = 0;
            long timeA2 = 0;
	        for (int i=0; i<100; i++) {
	        	int a = (int)(Math.random() * graph.size()); 
	        	int b = (int)(Math.random() * graph.size()); 
	            ShortestPathData data = new ShortestPathData(graph, graph.get(a), graph.get(b), arcInspector);	
	            DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);
	        	long begin = System.currentTimeMillis();
	            algoD.doRun();
	            long duration = System.currentTimeMillis() - begin;
	            timeD1 = timeD1 + duration;
	            timeD2 = timeD2 + algoD.Duration;
	        }
	        for (int i=0; i<100; i++) {
	        	int a = (int)(Math.random() * graph.size()); 
	        	int b = (int)(Math.random() * graph.size()); 
	            ShortestPathData data = new ShortestPathData(graph, graph.get(a), graph.get(b), arcInspector);	
	            AStarAlgorithm algoA = new AStarAlgorithm(data);
	            long begin = System.currentTimeMillis();
	            algoA.doRun();
	            long duration = System.currentTimeMillis() - begin;
	            timeA1 = timeA1 + duration;
	            timeA2 = timeA2 + algoA.Duration;
	        }
	        int x = graph.size();
	        int y = graph.getGraphInformation().getArcCount();
	        int z = x + y;
	        out.write("Map name : "+ map + "| Number of nodes : " + x 
	        + " | Number of arcs : " + y + " | Data size : " + z + " | CPU time in Dijsktra : " + timeD1 + "ms | "
	        + "Real calculating time : " + timeD2 + "ms | CPU time in AStar : " + timeA1 + "ms | Real calculating time : "
	        + timeA2 + "ms\n\n");
	        System.out.println("One map done!");
        }
        out.close();
	}

	
	@Test
	public void test() throws IOException {
		//ArrayList<String> mapList = new ArrayList<String>(Arrays.asList(new String[] {"midi-pyrenees"}));
		ArrayList<String> mapList = new ArrayList<String>(Arrays.asList(new String[] {"carre","insa","mayotte", "bordeaux", "toulouse", "benin", "vietnam", "midi-pyrenees","carre-dense", "belgium","greece"}));
		tester (mapList);
	}

}

	
