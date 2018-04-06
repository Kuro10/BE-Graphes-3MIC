package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.Path; 


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        //Retrieve the graph.
        Graph graph = data.getGraph();
        
        final int nbNodes = graph.size();
        
        //Initialize array of distances.
        double [] distances = new double [nbNodes];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[data.getOrigin().getId()] = 0;
        
        BinaryHeap <Label> heap = new BinaryHeap<Label>();
        
        //Notify observes about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        //Initialize array of predecessors.
        Arc [] predecessorArcs = new Arc[nbNodes];
        
        //Algorithm of Dijkstra. 
        
        
        boolean found = false;
        //While there are some unmarked nodes 
        while (!found ) {
        	//Find min of the table "Distances"
        	double x = Double.POSITIVE_INFINITY;
        	
        }
        
        
        //Destination has no predecessor, the solution is infeasible...  
        if (predecessorArcs[data.getDestination().getId()] == null) {
        	solution = new ShortestPathSolution (data, Status.INFEASIBLE);
        }else {
        	
        	// The destination has been found, notify the observers.
        	notifyDestinationReached (data.getDestination());
        	
        	//Create the path from the array of predecessors...
        	ArrayList <Arc> arcs = new ArrayList<Arc>();
        	Arc arc = predecessorArcs[data.getDestination().getId()];
        	while (arc != null) {
        		arcs.add(arc);
        		arc = predecessorArcs[data.getDestination().getId()];
        	}
        	
        	//Reserve the path...
        	Collections.reverse(arcs);
        	
        	//Create the final solution
        	solution = new ShortestPathSolution(data,Status.OPTIMAL, new Path(graph,arcs));
        }
        return solution;
    }

}
