package org.insa.algo.carpooling;

import org.insa.algo.AbstractInputData;
import org.insa.algo.ArcInspector;
import org.insa.graph.Graph;
import org.insa.graph.Node;

public class CarPoolingData extends AbstractInputData {

    // Origin and destination nodes of the Car and the Pedestrian.
    private final Node originC, destinationC, originP, destinationP;
    
    /**
     * Construct a new instance of CarPoolingInputData with the given parameters.
     * 
     * @param graph Graph in which the path should be looked for.
     * @param originC Car's origin node of the path.
     * @param destinationC Car's destination node of the path.
     * @param originP Pedestrian's origin node of the path.
     * @param destinationP Pedestrian's destination node of the path.
     * @param arcInspector Filter for arcs (used to allow only a specific set of
     *        arcs in the graph to be used).
     */
    public CarPoolingData(Graph graph, ArcInspector arcFilter, Node originC, Node originP, Node destinationC, Node destinationP) {
        super(graph, arcFilter);
        this.originC = originC;
        this.destinationC = destinationC;
        this.originP = originP;
        this.destinationP = destinationP;
    }
    
    /**
     * @return Car's Origin node for the path.
     */
    public Node getOriginCar() {
        return originC;
    }

    /**
     * @return Car's Destination node for the path.
     */
    public Node getDestinationCar() {
        return destinationC;
    }
    
    /**
     * @return Pedestrian's Origin node for the path.
     */
    public Node getOriginPedestrian() {
        return originP;
    }

    /**
     * @return Pedestrian's Destination node for the path.
     */
    public Node getDestinationPedestrian() {
        return destinationP;
    }

    @Override
    public String toString() {
        return "Shortest-path from #" + originC.getId() + "and #" + originP.getId() +" to #" + destinationC.getId() + " ["
                + this.arcInspector.toString().toLowerCase() + "]";
    }	

}
