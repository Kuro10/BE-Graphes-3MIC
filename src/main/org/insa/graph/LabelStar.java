package org.insa.graph;

public class LabelStar extends Label  {
	
	private double costEstimate; // the estimated cost between the given node and the destination based on the skyway
	
	public LabelStar(int id, double cost, Arc father, boolean mark) {
		super(id,cost,father,mark);
		this.costEstimate = 0.0;
	}
	
	public LabelStar(int id, double cost, Arc father, boolean mark, double costEstimate) {
		super(id,cost,father,mark);
		this.costEstimate = costEstimate;
	}	
	
	@Override
	public double getCostEstimate() {
		return this.costEstimate;
	}
	
	@Override
	public double getCost () {
		return this.cost +this.costEstimate; 
	}
	
	
	@Override 
	public String toString() {
		return super.toString() + ", costEstimate = " + this.costEstimate; 
	}
	
}
