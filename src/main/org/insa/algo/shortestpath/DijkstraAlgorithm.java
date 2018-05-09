package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
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

        //Retrieve the graph.
        Graph graph = data.getGraph();
        
        final int nbNodes = graph.size();
        
        //Initialize array of distances.
        //Initialize array of predecessors.
        //Initialize array of marks.
        Label [] labels = new Label[nbNodes];
        for (int i=0;i<nbNodes;i++) {
        	labels[i] = new Label(i,Double.POSITIVE_INFINITY,null,false);
        }
        labels[data.getOrigin().getId()].setCost(0);
       
        //Notify observes about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
                
        //Algorithm of Dijkstra. 
        BinaryHeap <Label> queue = new BinaryHeap<Label>();
        queue.insert(labels[data.getOrigin().getId()]);
        int nbMarkedNodes = 0;
        //While there are some unmarked nodes
        while (nbMarkedNodes != nbNodes && !queue.isEmpty()) {
        	//queue.printSorted();

        	//Find the minimum of the table "Distances"
        	int costMin = queue.findMin().getId();
        	
        	//System.out.println("Deleted " + labels[costMin]);
        	queue.deleteMin();
        	 
        	labels[costMin].setMark(true);
        	nbMarkedNodes++;
        	Node markedNode = graph.get(labels[costMin].getId());
        	for (Arc arc : markedNode) {
        		
				// Small test to check allowed roads...
				if (!data.isAllowed(arc)) {
					continue;
				}
				
				// Retrieve weight of the arc.
				double w = data.getCost(arc);
				double oldDistance = labels[arc.getDestination().getId()].getCost();
				double newDistance = labels[markedNode.getId()].getCost() + w;
				

				if (Double.isInfinite(oldDistance) && Double.isFinite(newDistance)) {
					notifyNodeReached(arc.getDestination());
				}
				
				// Check if new distances would be better, if so update...
				if (newDistance < oldDistance) {
					labels[arc.getDestination().getId()].setCost(newDistance);
					labels[arc.getDestination().getId()].setFather(arc);
					//if this node doesn't exist in the queue, we insert it into the queue
					//else update it, In fact, since the binary heap is automatically sorted,
					//we only need to insert labels with new better distance in it
					//without removing the old ones
					if(Double.isInfinite(oldDistance)) {
						queue.insert(labels[arc.getDestination().getId()]);
					}
					else {
						queue.insert(labels[arc.getDestination().getId()]);
					}
				}
        	}
        }
        
     
        //Destination has no predecessor, the solution is infeasible...  
        if (labels[data.getDestination().getId()].getFather() == null) {
        	solution = new ShortestPathSolution (data, Status.INFEASIBLE);
        }else {
        	
        	// The destination has been found, notify the observers.
        	notifyDestinationReached (data.getDestination());
        	
        	//Create the path from the array of predecessors...
        	ArrayList <Arc> arcs = new ArrayList<Arc>();
        	Arc arc = labels[data.getDestination().getId()].getFather();
        	while (arc != null) {
        		arcs.add(arc);
        		arc = labels[arc.getOrigin().getId()].getFather();
        	}
        	
        	//Reserve the path...
        	Collections.reverse(arcs);
        	
        	//Create the final solution
        	solution = new ShortestPathSolution(data,Status.OPTIMAL, new Path(graph,arcs));
        }
        return solution;
    }

}
