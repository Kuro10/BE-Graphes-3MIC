package org.insa.algo.carpooling;

import org.insa.algo.AbstractSolution;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Path;

public class CarPoolingSolution extends AbstractSolution {

	//Optimal solution
	Path pathCarRdv;
	Path pathPedestrianRdv;
	Path pathRdvDestinationC;
	//Path pathRdvDestinationP;
	
	
    /**
     * {@inheritDoc}
     */
    protected CarPoolingSolution(CarPoolingData data) {
        super(data);
    }
    
    /**
     * Create a new infeasible Car-Pooling solution for the given input and
     * status.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (UNKNOWN / INFEASIBLE).
     */
    public CarPoolingSolution(CarPoolingData data, Status status) {
        super(data, status);
    }
    
    /**
     * Create a new Car-Pooling solution.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (FEASIBLE / OPTIMAL).
     * @param pathCarRdv Path corresponding from the Car to the node Rdv.
     * @param pathPedestrianRdv Path corresponding from the Pedestrian to the node Rdv.
     * @param pathRdvDestinationC Path corresponding from the Node Rdv to the destination of Car.
     */
    public CarPoolingSolution(CarPoolingData data, Status status, Path pathCR, Path pathPR, Path pathRDesC) {
        super(data, status);
        this.pathCarRdv = pathCR;
        this.pathPedestrianRdv = pathPR;
        this.pathRdvDestinationC = pathRDesC;
    }
    
    @Override
    public CarPoolingData getInputData() {
        return (CarPoolingData) super.getInputData();
    }
    
    /**
     * @return The path of this solution, if any.
     */
    public Path getPathCR() {
        return this.pathCarRdv;
    }

    /**
     * @return The path of this solution, if any.
     */
    public Path getPathPR() {
        return this.pathPedestrianRdv;
    }
    /**
     * @return The path of this solution, if any.
     */
    public Path getPathRD() {
        return this.pathRdvDestinationC;
    }
}
