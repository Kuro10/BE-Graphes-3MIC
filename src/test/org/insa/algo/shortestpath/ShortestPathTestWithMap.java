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
	
	
	@Test
	public void testFastestPathAtINSAWithOracle() throws IOException {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		String mapName = "Maps\\insa.mapgr";
		testScenarioWithOracle(mapName,Mode.TIME);
	}
	
	@Test
	public void testShortestPathAtINSAWithOracle() throws IOException {
		//String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
		String mapName = "Maps\\insa.mapgr";
		testScenarioWithOracle(mapName,Mode.LENGTH);
	}	
	
	@Test
	public void testCheminImpossible() throws IOException {
		String mapName = "Maps\\insa.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		//origin = node[1] and destination = node[1000]
		ShortestPathData data = new ShortestPathData(graph, graph.get(1), graph.get(1000), arcInspector);	
		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
		assertEquals(algoD.doRun().getStatus(), Status.INFEASIBLE);
	}
	
	@Test
	public void testPasDeChemin() throws IOException {
		String mapName = "Maps\\mayotte.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		//origin = node[1500] and destination = node[4000]
		ShortestPathData data = new ShortestPathData(graph, graph.get(1500), graph.get(4000), arcInspector);	
		DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
		assertEquals(algoD.doRun().getStatus(), Status.INFEASIBLE);
	}
	
	@Test
	public void testCheminNul() throws IOException {
		String mapName = "Maps\\insa.mapgr";
		// Create a graph reader.
      GraphReader reader = new BinaryGraphReader(
              new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
      //done Read the graph.
      graph = reader.read();
      arcInspector = ArcInspectorFactory.getAllFilters().get(0);
      ShortestPathData data = new ShortestPathData(graph, graph.get(250), graph.get(250), arcInspector);	
      DijkstraAlgorithm algoD = new DijkstraAlgorithm(data);	
      assertEquals(algoD.doRun().getStatus(), Status.INFEASIBLE);
	}
	
	@Test
	public void testCoutCheminsComposants1() throws IOException {
		//Test en distance
		String mapName = "Maps\\midi-pyrenees.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data1 = new ShortestPathData(graph, graph.get(209905), graph.get(590334), arcInspector);
		ShortestPathData data2 = new ShortestPathData(graph, graph.get(209905), graph.get(15082), arcInspector);
		ShortestPathData data3 = new ShortestPathData(graph, graph.get(15082), graph.get(590334), arcInspector);
		DijkstraAlgorithm algo1 = new DijkstraAlgorithm(data1);
		DijkstraAlgorithm algo2 = new DijkstraAlgorithm(data2);
		DijkstraAlgorithm algo3 = new DijkstraAlgorithm(data3);
		int AC = (int)algo1.doRun().getPath().getLength();
		int AB = (int)algo2.doRun().getPath().getLength();
		int BC = (int)algo3.doRun().getPath().getLength();
		assertEquals(AC, AB + BC);
	}
	
	@Test
	public void testCoutCheminsComposants2() throws IOException {
		//Test en temps
		String mapName = "Maps\\midi-pyrenees.mapgr";
		  // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        //done Read the graph.
        graph = reader.read();
		arcInspector = ArcInspectorFactory.getAllFilters().get(2);
		ShortestPathData data1 = new ShortestPathData(graph, graph.get(209905), graph.get(590334), arcInspector);
		ShortestPathData data2 = new ShortestPathData(graph, graph.get(209905), graph.get(15082), arcInspector);
		ShortestPathData data3 = new ShortestPathData(graph, graph.get(15082), graph.get(590334), arcInspector);
		DijkstraAlgorithm algo1 = new DijkstraAlgorithm(data1);
		DijkstraAlgorithm algo2 = new DijkstraAlgorithm(data2);
		DijkstraAlgorithm algo3 = new DijkstraAlgorithm(data3);
		int AC = (int)algo1.doRun().getPath().getLength();
		int AB = (int)algo2.doRun().getPath().getLength();
		int BC = (int)algo3.doRun().getPath().getLength();
		assertEquals(AC, AB + BC);
	}
	
	@Test
	public void testInegaliteTriangulaire() throws IOException {
		String mapName = "Maps\\midi-pyrenees.mapgr";
		  // Create a graph reader.
      GraphReader reader = new BinaryGraphReader(
    		  new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
      //done Read the graph.
      graph = reader.read();
      arcInspector = ArcInspectorFactory.getAllFilters().get(0);
      ShortestPathData data1 = new ShortestPathData(graph, graph.get(258986), graph.get(203326), arcInspector);
      ShortestPathData data2 = new ShortestPathData(graph, graph.get(258986), graph.get(501198), arcInspector);
      ShortestPathData data3 = new ShortestPathData(graph, graph.get(501198), graph.get(203326), arcInspector);
      DijkstraAlgorithm algo1 = new DijkstraAlgorithm(data1);
      DijkstraAlgorithm algo2 = new DijkstraAlgorithm(data2);
      DijkstraAlgorithm algo3 = new DijkstraAlgorithm(data3);
      int AC = (int)algo1.doRun().getPath().getLength();
      int AB = (int)algo2.doRun().getPath().getLength();
      int BC = (int)algo3.doRun().getPath().getLength();
      assertTrue((AB + BC > AC) && (AB - BC < AC));
	}
}
