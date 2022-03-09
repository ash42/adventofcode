package nl.michielgraat.adventofcode2017.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day07 {

	private static final String FILENAME = "day07.txt";
	private static final String CHILD_INDICATOR = "->";

	private String findTree(final String parent, final List<String> subtrees) {
		for (final String subtree : subtrees) {
			if (parent.equals(subtree.substring(0, subtree.indexOf(" ")))) {
				return subtree;
			}
		}
		return null;
	}

	private int getWeight(final String subtree) {
		return subtree != null ? Integer.parseInt(subtree.substring(subtree.indexOf("(") + 1, subtree.indexOf(")")))
				: 0;
	}

	private List<String> getChildren(final String subtree) {
		final List<String> children = new ArrayList<String>();
		if (subtree != null) {
			if (subtree.indexOf(CHILD_INDICATOR) > -1) {
				final String[] childrenArray = (subtree.substring(subtree.indexOf(CHILD_INDICATOR) + 3)).split(",");
				for (final String child : childrenArray) {
					children.add(child.trim());
				}
			}
		}
		return children;
	}

	private Tree buildTree(final String parent, final List<String> rest) {
		final String subtree = findTree(parent, rest);
		final int weight = getWeight(subtree);
		final Tree tree = new Tree(parent, weight);
		final List<String> children = getChildren(subtree);
		if (!children.isEmpty()) {
			for (final String child : children) {
				final Tree childTree = buildTree(child, rest);
				tree.setWeight(tree.getWeight() + childTree.getWeight());
				tree.addChild(childTree);
			}
		}
		return tree;
	}

	private List<Integer> getWeights(final Tree t) {
		final List<Integer> weights = new ArrayList<>();
		for (final Tree child : t.getChildren()) {
			weights.add(child.getWeight());
		}
		return weights;
	}

	private boolean childrenHaveSameWeight(final Tree t) {
		final List<Integer> distinct = getWeights(t).stream().distinct().collect(Collectors.toList());
		return distinct.size() == 1;
	}

	private int getUniqueElement(final List<Integer> weights, final List<Integer> distinct) {
		for (final Integer i : distinct) {
			final int occ = Collections.frequency(weights, i);
			if (occ == 1) {
				return i;
			}
		}
		return -1;
	}

	private int getWeightOfChildren(final Tree t) {
		int childrenWeight = 0;
		for (final Tree child : t.getChildren()) {
			childrenWeight += child.getWeight();
		}
		return childrenWeight;
	}

	private int getAdjustment(final Tree tree) {
		final List<Integer> weights = getWeights(tree);
		final List<Integer> distinct = weights.stream().distinct().collect(Collectors.toList());
		if (distinct.size() > 1) {
			final int unique = getUniqueElement(weights, distinct);
			distinct.remove(Integer.valueOf(unique));
			final int nonUnique = distinct.get(0);
			for (final Tree child : tree.getChildren()) {
				if (child.getWeight() == unique) {
					if (childrenHaveSameWeight(child)) {
						final int childrenWeight = getWeightOfChildren(child);
						return nonUnique - childrenWeight;
					} else {
						return getAdjustment(child);
					}
				}
			}
		}
		return 0;
	}

	public int runPart2(final List<String> lines) {
		final Tree tree = buildTree(runPart1(lines), lines);
		return getAdjustment(tree);
	}

	public String runPart1(final List<String> lines) {
		final Set<String> parents = new TreeSet<String>();
		final Set<String> children = new TreeSet<String>();
		for (final String tree : lines) {
			if (tree.contains(CHILD_INDICATOR)) {
				parents.add(tree.substring(0, tree.indexOf(" ")));
				final String[] childrenArray = (tree.substring(tree.indexOf(CHILD_INDICATOR) + 3)).split(",");
				for (final String child : childrenArray) {
					children.add(child.trim());
				}
			}
		}
		for (final String parent : parents) {
			if (!children.contains(parent)) {
				return parent;
			}
		}
		return "Unknown";
	}

	public static void main(final String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = System.nanoTime();
		System.out.println("Answer to part 1: " + new Day07().runPart1(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
		start = System.nanoTime();
		System.out.println("Answer to part 2: " + new Day07().runPart2(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
	}

}
