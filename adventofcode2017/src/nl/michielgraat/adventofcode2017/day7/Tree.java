package nl.michielgraat.adventofcode2017.day7;

import java.util.ArrayList;
import java.util.List;

public class Tree {

	private String name;
	private int weight;
	
	private List<Tree> children;
	
	public Tree(String name, int weight, List<Tree> children) {
		this.name = name;
		this.weight = weight;
		this.children = children;
	}
	
	public Tree(String name, int weight) {
		this.name = name;
		this.weight = weight;
		this.children = new ArrayList<Tree>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	
	public void addChild(Tree child) {
		this.children.add(child);
	}
}
