package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
//import org.insa.graph.Arc;
import org.insa.graph.Graph;
//import org.insa.graph.Node;
//import org.insa.graph.Path;

import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class ShortestPathTestWithMap {
	
	protected static ArcInspector arcInspector;
    
    // Small graph use for tests
    private static Graph graph;
	
	public void testScenarioWithOracle (String mapName, Mode mode) throws IOException {
		
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
        
		if (mode == Mode.TIME) { //Evaluation with time
			//Fastest path, all roads allowed
			arcInspector = ArcInspectorFactory.getAllFilters().get(2);
    		ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
    		BellmanFordAlgorithm algoB = new BellmanFordAlgorithm(data);
    		if (algoD.doRun().getStatus() == Status.OPTIMAL && algoB.doRun().getStatus() == Status.OPTIMAL ) {	
	    		assertEquals((int)algoD.doRun().getPath().getMinimumTravelTime(), (int)algoB.doRun().getPath().getMinimumTravelTime());
    		}
		}
		
		if (mode == Mode.LENGTH) { //Evaluation with distance
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
	

	public void testScenarioWithoutOracle (String mapName, Mode mode) throws IOException {
		
        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
        
		if (mode == Mode.TIME) { //Evaluation with time
			//Fastest path, all roads allowed
			arcInspector = ArcInspectorFactory.getAllFilters().get(2);
			//origin = node[0] and destination = node[last]
    		ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
    		if (algoD.doRun().getStatus() == Status.OPTIMAL) {	
	    		//TODO
    			System.out.println(algoD.doRun().getPath().getLength());
    		}
		}
		
		if (mode == Mode.LENGTH) { //Evaluation with distance
			//Shortest path, all roads allowed
			arcInspector = ArcInspectorFactory.getAllFilters().get(0);
			//origin = node[0] and destination = node[last]
			ShortestPathData data = new ShortestPathData(graph, graph.get(0), graph.get(graph.size()-1), arcInspector);	
    		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
    		if (algoD.doRun().getStatus() == Status.OPTIMAL) {	
	    		//TODO 
     		}
			
		}
	}
	
	@Test
	public void testFastestPathAtINSAWithOracle() throws IOException {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		//String mapName = "G:\\3eme_annee\\Graphes\\Maps\\insa.mapgr";
		testScenarioWithOracle(mapName,Mode.TIME);
	}
	
	@Test
	public void testShortestPathAtINSAWithOracle() throws IOException {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		//String mapName = "G:\\3eme_annee\\Graphes\\Maps\\insa.mapgr";
		testScenarioWithOracle(mapName,Mode.LENGTH);
	}	
	
	@Test
	public void testFastestPathAtINSAWithoutOracle() throws IOException {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		//String mapName = "G:\\3eme_annee\\Graphes\\Maps\\insa.mapgr";
		testScenarioWithoutOracle(mapName,Mode.TIME);
	}
	
	@Test
	public void testShortestPathAtINSAWithoutOracle() throws IOException {
		String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		//String mapName = "G:\\3eme_annee\\Graphes\\Maps\\insa.mapgr";
		testScenarioWithOracle(mapName,Mode.LENGTH);
	}
	
}
