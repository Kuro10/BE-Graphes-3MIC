package org.insa.graph;

public class Label implements Comparable<Label>{
	public double cout;
	private Node precedent;
	private boolean mark;
	
	public int compareTo(Label autre) {
		return (int)(this.cout-autre.cout);
	}
	
	public Node getFather() {
		return this.precedent;
	}
	
	public boolean isMarked() {
		return mark;
	}
	
	public void setMark(boolean mark) {
		this.mark=mark;
	}
}
