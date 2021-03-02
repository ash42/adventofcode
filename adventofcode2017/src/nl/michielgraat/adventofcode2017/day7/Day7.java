package nl.michielgraat.adventofcode2017.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.michielgraat.adventofcode2017.util.FileReader;

public class Day7 {

	private static final String FILENAME = "day7.txt";
	private static final String CHILD_INDICATOR = "->";

	/*
	 * private Tree convertToTree(String tree) { String name = tree.substring(0,
	 * tree.indexOf(" ")); int weight =
	 * Integer.valueOf(tree.substring(tree.indexOf("(") + 1, tree.indexOf(")"))); }
	 */

	public String runPart1() {
		List<String> treeinfo = new FileReader(FILENAME).getStringList();
		Set<String> parents = new TreeSet<String>();
		Set<String> children = new TreeSet<String>();
		for (String tree : treeinfo) {
			if (tree.contains(CHILD_INDICATOR)) {
				parents.add(tree.substring(0, tree.indexOf(" ")));
				String[] childrenArray = (tree.substring(tree.indexOf(CHILD_INDICATOR) + 3)).split(",");
				for (String child : childrenArray) {
					children.add(child.trim());
				}
			}
		}
		for (String parent : parents) {
			if (!children.contains(parent)) {
				return parent;
			}
		}
		return "Unknown";
	}

	private String findTree(String parent, List<String> subtrees) {
		for (String subtree : subtrees) {
			if (parent.equals(subtree.substring(0, subtree.indexOf(" ")))) {
				return subtree;
			}
		}
		return null;
	}

	private int getWeight(String subtree) {
		return Integer.parseInt(subtree.substring(subtree.indexOf("(") + 1, subtree.indexOf(")")));
	}

	private List<String> getChildren(String subtree) {
		List<String> children = new ArrayList<String>();
		if (subtree.indexOf(CHILD_INDICATOR) > -1) {
			String[] childrenArray = (subtree.substring(subtree.indexOf(CHILD_INDICATOR) + 3)).split(",");
			for (String child : childrenArray) {
				children.add(child.trim());
			}
		}
		return children;
	}

	private int getWeight(Tree tree) {
		int weight = tree.getWeight();
		if (tree.getChildren().size() == 0) {
			return 0;
		} else {
			for (Tree child : tree.getChildren()) {
				weight += getWeight(child);
			}
		}
		return weight;
	}
	
	private Tree buildTree(String parent, List<String> treeinfo) {
		String subtree = findTree(parent, treeinfo);
		int weight = getWeight(subtree);
		Tree tree = new Tree(parent, weight);
		List<String> children = getChildren(subtree);
		if (children.size() > 0) {
			for (String child : children) {
				tree.addChild(buildTree(child, treeinfo));
			}
		}
		return tree;
	}

	public String runPart2() {
		List<String> treeinfo = new FileReader(FILENAME).getStringList();
		Tree tree = buildTree(runPart1(), treeinfo);
		for (Tree child : tree.getChildren()) {
			System.out.println("Child " + child.getName() + " has weight: " + getWeight(child));
		}
		return "" + tree.getChildren().size();
	}

	public static void main(String[] args) {
		System.out.println("Part 1: " + new Day7().runPart1());
		System.out.println("Part 2: " + new Day7().runPart2());
	}

}
