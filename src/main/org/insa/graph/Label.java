package org.insa.graph;

public class Label implements Comparable<Label>{
	protected int id;
	protected double cost;
	protected Arc father;
	protected boolean mark;
	
	public Label(int id, double cost, Arc father, boolean mark) {
		this.id = id;
		this.cost = cost;
		this.father = father;
		this.mark = mark;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Arc getFather() {
		return this.father;
	}
	
	public void setFather(Arc father) {
		this.father = father;
	}
	
	public boolean isMarked() {
		return mark;
	}
	
	public void setMark(boolean mark) {
		this.mark=mark;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public double getCost () {
		return this.cost;
	}
	
	public int compareTo(Label autre) {
		return (int)(this.cost-autre.cost);
	}
	
	public String toString() {
		return "id = " + this.id + ", cost = " + this.cost + ", father =  " + this.father + ", mark = "+ this.mark; 
	}
}
