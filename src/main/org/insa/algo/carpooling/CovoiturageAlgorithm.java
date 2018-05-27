package org.insa.algo.carpooling;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;
import org.insa.graph.Point;


public class CovoiturageAlgorithm extends CarPoolingAlgorithm{

	protected CovoiturageAlgorithm(CarPoolingData data) {
		super(data);
	}
	
	private double disMax (double a, double b , double c) {
		double res;
		res = (a - b)>0?a:b;
		res = (res - c)>0?res:c;
		return res;
	}
	
	@Override
    protected CarPoolingSolution doRun() {
        CarPoolingData data = getInputData();
        CarPoolingSolution solution = null;
        
		ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(0);
        //Retrieve the graph.
        Graph graph = data.getGraph();
        Graph graphTrans = data.getGraph().transpose();
        CarPoolingData dataTrans = new CarPoolingData(graphTrans,arcInspector,data.getOriginCar(),data.getOriginPedestrian(),data.getDestinationCar(),data.getDestinationPedestrian());
		final int nbNodes = graph.size();
        //Notify observes about the first event (origin processed).
        notifyOriginCarProcessed(data.getOriginCar());
        notifyOriginPedestrianProcessed(data.getOriginPedestrian());
        

		
		//Find the shortest path from car's origin to the rest node
		double tabA[] = new double[nbNodes];
		double min = Double.POSITIVE_INFINITY;
		int idRdv=-1;
		
		//Initialize array of distances.
        //Initialize array of predecessors.
        //Initialize array of marks.
        Label [] labelsOfCar = new Label[nbNodes];
        Label [] labelsOfPedes = new Label[nbNodes];
        Label [] labelsOfDes = new Label[nbNodes];
        for (int i=0;i<nbNodes;i++) {
        	//Calculate of estimated cost from given node to the destination (data.getDestination().getId())
        	double costEstimate = this.disMax(
        			Point.distance(graph.get(i).getPoint(), data.getDestinationCar().getPoint()),
        			Point.distance(graph.get(i).getPoint(), data.getOriginCar().getPoint()),
        			Point.distance(graph.get(i).getPoint(), data.getOriginPedestrian().getPoint())		
        			);
        	
        	labelsOfCar[i] = new LabelStar(i,Double.POSITIVE_INFINITY,null,false,costEstimate);
        	labelsOfPedes[i] = new LabelStar(i,Double.POSITIVE_INFINITY,null,false,costEstimate);
        	labelsOfDes[i] = new LabelStar(i,Double.POSITIVE_INFINITY,null,false,costEstimate);
        }
        labelsOfCar[data.getOriginCar().getId()].setCost(0);
        labelsOfPedes[data.getOriginPedestrian().getId()].setCost(0);
        labelsOfDes[data.getDestinationCar().getId()].setCost(0);

        //Notify observes about the first event (origin processed).
        notifyOriginCarProcessed(data.getOriginCar());
        notifyOriginPedestrianProcessed(data.getOriginPedestrian());
                
        //Algorithm of AStar
        BinaryHeap <Label> queueC = new BinaryHeap<Label>();
        queueC.insert(labelsOfCar[data.getOriginCar().getId()]);
        BinaryHeap <Label> queueP = new BinaryHeap<Label>();
        queueP.insert(labelsOfPedes[data.getOriginPedestrian().getId()]);
        BinaryHeap <Label> queueD = new BinaryHeap<Label>();
        queueD.insert(labelsOfPedes[data.getDestinationCar().getId()]);
        
        int costMinC = 0; 
    	int	costMinP = 0;
    	int costMinD = 0;
        //While there are some unmarked nodes
        while (!(labelsOfCar[data.getDestinationCar().getId()].isMarked() 
        		&& labelsOfPedes[data.getDestinationCar().getId()].isMarked() 
        		&& labelsOfDes[data.getOriginCar().getId()].isMarked() 
        		&& labelsOfDes[data.getOriginPedestrian().getId()].isMarked() )
        		&& !queueP.isEmpty()  && !queueC.isEmpty() && !queueD.isEmpty()
        		){
        	
        	if (!labelsOfCar[data.getDestinationCar().getId()].isMarked()) {
            	costMinC = queueC.findMin().getId();
            	queueC.deleteMin();
            	labelsOfCar[costMinC].setMark(true);
        		Node markedNodeC = graph.get(labelsOfCar[costMinC].getId());
            	for (Arc arc : markedNodeC) {
    				// Small test to check allowed roads...
    				if (!data.isAllowed(arc)) {
    					continue;
    				}	
    				// Retrieve weight of the arc.
    				double w = data.getCost(arc);
    				double oldDistance = labelsOfCar[arc.getDestination().getId()].getCost();
    				double newDistance = labelsOfCar[markedNodeC.getId()].getCost() + w 
    						- labelsOfCar[markedNodeC.getId()].getCostEstimate() 
    						+ labelsOfCar[arc.getDestination().getId()].getCostEstimate(); 
    				if (Double.isInfinite(oldDistance) && Double.isFinite(newDistance)) {
    					notifyNodeMarked(arc.getDestination());
    				}
    				// Check if new distances would be better, if so update...
    				if (newDistance < oldDistance) {
    					labelsOfCar[arc.getDestination().getId()].setCost(newDistance - labelsOfCar[arc.getDestination().getId()].getCostEstimate());
    					labelsOfCar[arc.getDestination().getId()].setFather(arc);
    					queueC.insert(labelsOfCar[arc.getDestination().getId()]);
    				}
            	}
        	}
        	
        	if(!labelsOfPedes[data.getDestinationCar().getId()].isMarked()) {
            	costMinP = queueP.findMin().getId();
            	queueP.deleteMin();
            	labelsOfPedes[costMinP].setMark(true);
        		Node markedNodeP = graph.get(labelsOfPedes[costMinP].getId());
            	for (Arc arc : markedNodeP) {
    				// Small test to check allowed roads...
    				if (!data.isAllowed(arc)) {
    					continue;
    				}
    				// Retrieve weight of the arc.
    				double w = data.getCost(arc);
    				double oldDistance = labelsOfPedes[arc.getDestination().getId()].getCost();
    				double newDistance = labelsOfPedes[markedNodeP.getId()].getCost() + w 
    						- labelsOfPedes[markedNodeP.getId()].getCostEstimate() 
    						+ labelsOfPedes[arc.getDestination().getId()].getCostEstimate();;
    				if (Double.isInfinite(oldDistance) && Double.isFinite(newDistance)) {
    					notifyNodeMarked2(arc.getDestination());
    				}
    				// Check if new distances would be better, if so update...
    				if (newDistance < oldDistance) {
    					labelsOfPedes[arc.getDestination().getId()].setCost(newDistance - labelsOfPedes[arc.getDestination().getId()].getCostEstimate());
    					labelsOfPedes[arc.getDestination().getId()].setFather(arc);
    					queueP.insert(labelsOfPedes[arc.getDestination().getId()]);
    				}
            	}
        	}
        	
        	if(!labelsOfDes[data.getOriginCar().getId()].isMarked() || !labelsOfDes[data.getOriginPedestrian().getId()].isMarked() ) {
            	costMinD = queueD.findMin().getId();
            	queueD.deleteMin();
            	labelsOfDes[costMinD].setMark(true);
        		Node markedNodeD = graphTrans.get(labelsOfDes[costMinD].getId());
            	for (Arc arc : markedNodeD) {
    				// Small test to check allowed roads...
    				if (!dataTrans.isAllowed(arc)) {
    					continue;
    				}
    				// Retrieve weight of the arc.
    				double w = dataTrans.getCost(arc);
    				double oldDistance = labelsOfDes[arc.getDestination().getId()].getCost();
    				double newDistance = labelsOfDes[markedNodeD.getId()].getCost() + w 
    						- labelsOfPedes[markedNodeD.getId()].getCostEstimate() 
    						+ labelsOfPedes[arc.getDestination().getId()].getCostEstimate();;
    				if (Double.isInfinite(oldDistance) && Double.isFinite(newDistance)) {
    					notifyNodeMarked3(arc.getDestination());
    				}
    				// Check if new distances would be better, if so update...
    				if (newDistance < oldDistance) {
    					labelsOfDes[arc.getDestination().getId()].setCost(newDistance - labelsOfDes[arc.getDestination().getId()].getCostEstimate());
    					labelsOfDes[arc.getDestination().getId()].setFather(arc);
    					queueD.insert(labelsOfDes[arc.getDestination().getId()]);
    				}
            	}
        	}
        	
        }
		
        for (int i=0; i<nbNodes; i++) {
			if(labelsOfCar[i].isMarked() && labelsOfPedes[i].isMarked() && labelsOfDes[i].isMarked() ) {
				tabA[i]= labelsOfCar[i].getCost() + labelsOfPedes[i].getCost() + labelsOfDes[i].getCost() ;
				if (tabA[i] < min) {
					min = tabA[i];
					idRdv = i;
				}
			}
		}
		
		if (idRdv == -1) {
			solution = new CarPoolingSolution (data, Status.INFEASIBLE);
		}
		else {
			// The destination has been found, notify the observers.
        	notifyDestinationReached (data.getDestinationCar());
        	
			ShortestPathData dataSolution1 = new ShortestPathData(graph, data.getOriginCar(), graph.get(idRdv), arcInspector);
			AStarAlgorithm algo1 = new AStarAlgorithm(dataSolution1);
			ShortestPathData dataSolution2 = new ShortestPathData(graph, data.getOriginPedestrian(), graph.get(idRdv), arcInspector);
			AStarAlgorithm algo2 = new AStarAlgorithm(dataSolution2);
			ShortestPathData dataSolution3 = new ShortestPathData(graph, graph.get(idRdv), data.getDestinationCar(),  arcInspector);
			AStarAlgorithm algo3 = new AStarAlgorithm(dataSolution3);
			
			solution = new CarPoolingSolution(data,Status.OPTIMAL,algo1.run().getPath(),algo2.run().getPath(),algo3.run().getPath());
		}

		System.out.println(idRdv);
		return solution;
	}


}
