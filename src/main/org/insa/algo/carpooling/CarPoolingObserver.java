package org.insa.algo.carpooling;

import org.insa.graph.Node;

public interface CarPoolingObserver {
	/**
	 * Notify the observer that the origin has been processed.
	 * 
	 * @param node Car's Origin.
	 */
	public void notifyOriginCarProcessed(Node node);
	
	/**
	 * Notify the observer that the origin has been processed.
	 * 
	 * @param node Pedestrian's Origin.
	 */
	public void notifyOriginPedestrianProcessed(Node node);
	
	/**
	 * Notify the observer that a node has been reached for the first
	 * time.
	 * 
	 * @param node Node that has been reached.
	 */
	public void notifyNodeReached(Node node);
	
	/**
	 * Notify the observer that a node has been marked, i.e. its final
	 * value has been set.
	 * 
	 * @param node Node that has been marked.
	 */
	public void notifyNodeMarked(Node node);
	public void notifyNodeMarked2(Node node);
	public void notifyNodeMarked3(Node node);

	/**
	 * Notify the observer that the destination has been reached.
	 * 
	 * @param node Destination.
	 */
	public void notifyDestinationReached(Node node);
}
