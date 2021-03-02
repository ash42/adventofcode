package nl.michielgraat.adventofcode2019.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {

	private static final String FILENAME = "day7.txt";

	private List<Instruction> parseInstructions(List<String> unparsed) {
		List<Instruction> instructions = new ArrayList<Instruction>();
		for (String ins : unparsed) {
			instructions.add(new Instruction(String.valueOf(ins.charAt(5)), String.valueOf(ins.charAt(36))));
		}
		return instructions;
	}

	private List<String> getInstructions() {
		List<String> instructions = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			instructions = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instructions;
	}

	private String findLast(List<Instruction> instructions) {
		String current = "";
		for (Instruction i : instructions) {
			current = i.second;
			boolean hasFirst = false;
			for (Instruction j : instructions) {
				// System.out.println("Current check: " + current + " against " + j);
				if (j.first.equals(current)) {
					// System.out.println(current + " is also a startpoint: " + j);
					hasFirst = true;
					break;
				}
			}
			if (!hasFirst) {
				return current;
			}
		}
		return current;
	}

	private void sort(Graph g, String id, Map<String, Boolean> visited, List<String> order) {
		visited.replace(id, true);

		for (String neighbourId : g.getNode(id).getNeighbours()) {
			if (!visited.get(neighbourId)) {
				sort(g, neighbourId, visited, order);
			}
		}

		order.add(id);
	}

	private List<String> topoSort(Graph g) {
		List<String> order = new ArrayList<>();

		Map<String, Boolean> visited = new HashMap<String, Boolean>();
		for (Node n : g.getNodes()) {
			visited.put(n.getId(), false);
		}

		for (Node n : g.getNodes()) {
			if (!visited.get(n.getId())) {
				sort(g, n.getId(), visited, order);
			}
		}
		Collections.reverse(order);
		return order;
	}

	public String runPart1() {
		List<Instruction> instructions = parseInstructions(getInstructions());

		Graph g = new Graph();
		for (Instruction i : instructions) {
			Node n = g.getNode(i.first);
			if (n == null) {
				n = new Node(i.first);
			}
			n.addNeighbour(i.second);
			g.addNode(n);
		}
		g.addNode(new Node(findLast(instructions)));
		System.out.println(g);
		List<String> result = topoSort(g);
		return result.stream().map(e -> e.toString()).reduce("", String::concat);
	}

	public static void main(String[] args) {
		System.out.println("The answer to part 1: " + new Day7().runPart1());

	}

	class Instruction implements Comparable<Instruction> {
		String first;
		String second;

		Instruction(String first, String second) {
			super();
			this.first = first;
			this.second = second;
		}

		@Override
		public String toString() {
			return first + " -> " + second;
		}

		@Override
		public int compareTo(Instruction o) {
			if (!first.equals(o.first)) {
				return first.compareTo(o.first);
			} else {
				return second.compareTo(o.second);
			}
		}
	}

	class Graph {
		private TreeSet<Node> nodes;

		Graph() {
			this.nodes = new TreeSet<Node>();
		}

		Graph(TreeSet<Node> nodes) {
			this.nodes = nodes;
		}

		void addNode(Node node) {
			this.nodes.add(node);
		}

		TreeSet<Node> getNodes() {
			return this.nodes;
		}

		Node getNode(String s) {
			for (Node node : nodes) {
				if (node.getId().equals(s)) {
					return node;
				}
			}
			return null;
		}

		int getSize() {
			return this.nodes.size();
		}

		@Override
		public String toString() {
			return "Graph{" + "nodes=" + nodes + "}";
		}
	}

	class Node implements Comparable<Node> {
		private String id;
		private TreeSet<String> neighbours;

		Node(String id) {
			this.id = id;
			neighbours = new TreeSet<String>();
		}

		void addNeighbour(String n) {
			this.neighbours.add(n);
		}

		String getId() {
			return this.id;
		}

		TreeSet<String> getNeighbours() {
			return this.neighbours;
		}

		@Override
		public String toString() {
			return "Node{" + "id=" + id + ", neighbours=" + neighbours + "}" + "\n";
		}

		@Override
		public int compareTo(Node o) {
			return this.id.compareTo(o.id);
		}
	}
}
