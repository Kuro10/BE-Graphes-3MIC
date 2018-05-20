package org.insa.algo.carpooling;

import org.insa.algo.AbstractAlgorithm;

import org.insa.graph.Node;

public abstract class CarPoolingAlgorithm extends AbstractAlgorithm<CarPoolingObserver> {

    protected CarPoolingAlgorithm(CarPoolingData data) {
        super(data);
    }

    @Override
    public CarPoolingSolution run() {
        return (CarPoolingSolution) super.run();
    }

    @Override
    protected abstract CarPoolingSolution doRun();

    @Override
    public CarPoolingData getInputData() {
        return (CarPoolingData) super.getInputData();
    }
    
    /**
     * Notify all observers that the origin has been processed.
     * 
     * @param node Origin.
     */
    public void notifyOriginCarProcessed(Node node) {
        for (CarPoolingObserver obs: getObservers()) {
            obs.notifyOriginCarProcessed(node);
        }
    }
    
    /**
     * Notify all observers that the origin has been processed.
     * 
     * @param node Origin.
     */
    public void notifyOriginPedestrianProcessed(Node node) {
        for (CarPoolingObserver obs: getObservers()) {
            obs.notifyOriginPedestrianProcessed(node);
        }
    }

    /**
     * Notify all observers that a node has been reached for the first time.
     * 
     * @param node Node that has been reached.
     */
    public void notifyNodeReached(Node node) {
        for (CarPoolingObserver obs: getObservers()) {
            obs.notifyNodeReached(node);
        }
    }

    /**
     * Notify all observers that a node has been marked, i.e. its final value has
     * been set.
     * 
     * @param node Node that has been marked.
     */
    public void notifyNodeMarked(Node node) {
        for (CarPoolingObserver obs: getObservers()) {
            obs.notifyNodeMarked(node);
        }
    }

    /**
     * Notify all observers that the destination has been reached.
     * 
     * @param node Destination.
     */
    public void notifyDestinationReached(Node node) {
        for (CarPoolingObserver obs: getObservers()) {
            obs.notifyDestinationReached(node);
        }
    }

}
