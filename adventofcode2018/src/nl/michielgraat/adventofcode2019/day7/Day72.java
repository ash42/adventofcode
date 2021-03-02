package nl.michielgraat.adventofcode2019.day7;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day72 {
	
	private static final String FILENAME = "day7.txt";
	public static class Node {
		public char name;
		public boolean visited_p1 = false;
		public List<Node> inEdges = new ArrayList<>();
	}
	public static void main(String[] args) throws IOException {
		//Scanner in = new Scanner(new File(FILENAME));
		List<String> instructions = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			instructions = stream.collect(Collectors.toList());
		}
		Node[] graph = new Node[26];
		for (int i = 0; i < graph.length; i++) {
			graph[i] = new Node();
			graph[i].name = (char) ('A'+i);
		}
		for (String line : instructions) {
			int edgeSource = line.charAt(36) - 'A';
			int edgeDest = line.charAt(5) - 'A';
			graph[edgeSource].inEdges.add(graph[edgeDest]);
		}
		System.out.printf("Part 1: %s%n", part1(graph));
		
	}
	private static String part1(Node[] graph) {
		List<Node> ans = new ArrayList<>();
		int visited = 0;
		while (visited < graph.length) {
			search:for (int i = 0; i < graph.length; i++) {
				if (graph[i].visited_p1)
					continue search;
				for (Node edgeSource : graph[i].inEdges)
					if (!edgeSource.visited_p1)
						continue search;
				Node source = graph[i];
				ans.add(source);
				source.visited_p1 = true;
				visited++;
				break search;
			}
		}
		String s = "";
		for (Node n : ans)
			s += n.name;
		return s;
	}
}
