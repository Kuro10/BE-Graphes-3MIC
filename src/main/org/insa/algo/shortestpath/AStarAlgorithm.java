package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public long Duration = 0;
    
    @Override
    public ShortestPathSolution doRun() {
    	
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
     	//Calculate of estimated cost from given node to the destination (data.getDestination().getId())
        	double costEstimate = Point.distance(graph.get(i).getPoint(), data.getDestination().getPoint());
        	if (data.getMode() == Mode.TIME) {
        		costEstimate = (costEstimate)/(data.getMaximumSpeed()*1000f);
        	}
        	labels[i] = new LabelStar(i,Double.POSITIVE_INFINITY,null,false, costEstimate);
        }
        labels[data.getOrigin().getId()].setCost(0);

       
        //Notify observes about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
                
        //Initialize Binary Heap 
        BinaryHeap <Label> queue = new BinaryHeap<Label>();
        queue.insert(labels[data.getOrigin().getId()]);

        long begin = System.currentTimeMillis();
        //While there are some unmarked nodes
        while (!queue.isEmpty() && !labels[data.getDestination().getId()].isMarked()) {
        	
        	//Find the minimum of the table "Distances"
        	int costMin = queue.findMin().getId();
        	queue.deleteMin();
        	labels[costMin].setMark(true);        	
        											
        	Node markedNode = graph.get(labels[costMin].getId());
        	for (Arc arc : markedNode) {
        		
				// Small test to check allowed roads...
				if (!data.isAllowed(arc)) {
					continue;
				}
				
				// Retrieve weight of the arc.
				double w = data.getCost(arc);
				double oldDistance = labels[arc.getDestination().getId()].getCost();
				double newDistance = labels[markedNode.getId()].getCost() + w 
						- labels[markedNode.getId()].getCostEstimate() 
						+ labels[arc.getDestination().getId()].getCostEstimate();

				if (Double.isInfinite(oldDistance) && Double.isFinite(newDistance)) {
					notifyNodeReached(arc.getDestination());
				}
				
				// Check if new distances would be better, if so update...
				if (newDistance < oldDistance ) {
					
					labels[arc.getDestination().getId()].setCost(newDistance - labels[arc.getDestination().getId()].getCostEstimate());
					labels[arc.getDestination().getId()].setFather(arc);
					//if this node doesn't exist in the queue, we insert it into the queue
					//else update it, In fact, since the binary heap is automatically sorted,
					//we only need to insert labels with new better distance in it
					//without removing the old ones
					queue.insert(labels[arc.getDestination().getId()]);
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
        Duration = System.currentTimeMillis() - begin;

        return solution;
    }

}
