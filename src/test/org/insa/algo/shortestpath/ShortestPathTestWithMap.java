package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
//import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShortestPathTestWithMap {
	
	protected static ArcInspector arcInspector;
    
    // Small graph use for tests
    private static Graph graph;
	
	/*typeEval : 0 = time, 1 = distance */
	public void testScenarioWithOracle (String mapName, int typeEval) throws IOException {
		
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
        
		if (typeEval == 0) { //Evaluation with time
			//Fastest path, all roads allowed
			arcInspector = ArcInspectorFactory.getAllFilters().get(2);
    		ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data);
    		if (algoD.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
	    		assertEquals((int)algoD.doRun().getPath().getMinimumTravelTime(), (int)algoB.doRun().getPath().getMinimumTravelTime());
    		}
		}
		
		if (typeEval == 1) { //Evaluation with distance
			//Shortest path, all roads allowed
			arcInspector = ArcInspectorFactory.getAllFilters().get(0);
			ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data);
    		if (algoD.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
	    		assertEquals((int)algoD.doRun().getPath().getLength(), (int)algoB.doRun().getPath().getLength());
    		}
			
		}
	}
	
	/*typeEval : 0 = time, 1 = distance */
	public void testScenarioWithoutOracle (String mapName, int typeEval) throws IOException {
		
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
        
		if (typeEval == 0) { //Evaluation with time
			//Fastest path, all roads allowed
			arcInspector = ArcInspectorFactory.getAllFilters().get(2);
    		ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
    		if (algoD.doRun().getStatus() == Status.OPTIMAL) {	
	    		//TODO
    		}
		}
		
		if (typeEval == 1) { //Evaluation with distance
			//Shortest path, all roads allowed
			arcInspector = ArcInspectorFactory.getAllFilters().get(0);
			ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
    		
    		if (algoD.doRun().getStatus() == Status.OPTIMAL) {	
	    		//TODO 
     		}
			
		}
	}
	
	@Test
	public void testFastestPathAtINSAWithOracle() throws IOException {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		String mapName = "G:\\3eme_annee\\Graphes\\Maps\\insa.mapgr";
		testScenarioWithOracle(mapName,0);
	}
	
	@Test
	public void testShortestPathAtINSAWithOracle() throws IOException {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		String mapName = "G:\\3eme_annee\\Graphes\\Maps\\insa.mapgr";
		testScenarioWithOracle(mapName,1);
	}	
	
	@Test
	public void testFastestPathAtINSAWithoutOracle() throws IOException {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		String mapName = "G:\\3eme_annee\\Graphes\\Maps\\insa.mapgr";
		testScenarioWithoutOracle(mapName,0);
	}
	
	@Test
	public void testShortestPathAtINSAWithoutOracle() throws IOException {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		String mapName = "G:\\3eme_annee\\Graphes\\Maps\\insa.mapgr";
		testScenarioWithOracle(mapName,1);
	}
	
}
