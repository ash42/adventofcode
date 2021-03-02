package nl.michielgraat.adventofcode2015.day14;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day14 {
	
	private static final String FILENAME = "day14.txt";
	
	private List<Reindeer> parseInput(List<String> lines) {
		List<Reindeer> reindeer = new ArrayList<Reindeer>();
		
		for (String line : lines) {
			String name = line.substring(0, line.indexOf(' '));
			int topSpeed = Integer.valueOf(line.substring(line.indexOf("fly") + 4, line.indexOf("km") - 1));
			int moveSecs = Integer.valueOf(line.substring(line.indexOf("for") + 4, line.indexOf("sec") - 1));
			int restSecs = Integer.valueOf(line.substring(line.indexOf("rest for") + 9, line.indexOf("seconds.") - 1));
			reindeer.add(new Reindeer(name, topSpeed, moveSecs, restSecs));
		}
		return reindeer;
	}
	
	private int getDistanceAfter(Reindeer r, int sec) {
		int speed = r.topSpeed;
		int move = r.moveSecs;
		int rest = r.restSecs;
		int totalMove = 0;
		boolean moving = true;
		while (sec > 0) {
			if (moving) {
				if (sec - move > 0) {
					sec -= move;
					totalMove += move;
					moving = false;
				} else {
					totalMove += sec;
					sec = 0;
				}
			} else {
				if (sec - rest > 0) {
					sec -= rest;
					moving = true;
				} else {
					sec = 0;
				}
			}
		}
		return totalMove*speed;
	}
	
	private List<Reindeer> getReindeerInLead(List<Reindeer> reindeer, int sec) {
		List<Reindeer> leaders = new ArrayList<Reindeer>();
		int max = 0;
		for (Reindeer r : reindeer) {
			int dist = getDistanceAfter(r, sec);
			if (dist > max) {
				leaders = new ArrayList<Reindeer>();
				leaders.add(r);
				max = dist;
			} else if (dist == max) {
				leaders.add(r);
			}
		}
		return leaders;
	}

	private int runPart2(List<String> lines) {
		List<Reindeer> reindeer = parseInput(lines);
		
		for (int i = 1; i <= 2503; i++) {
			List<Reindeer> leaders = getReindeerInLead(reindeer, i);
			for(Reindeer leader : leaders) {
				leader.points++;
			}
		}
		
		int max = 0;
		for (Reindeer r : reindeer) {
			max = Math.max(max, r.points);
		}
		
		return max;
	}
	
	private int runPart1(List<String> lines) {
		List<Reindeer> reindeer = parseInput(lines);
		int max = 0;
		for (Reindeer r : reindeer) {
			//System.out.println(r.name + ": " + getDistanceAfter(r, 2503));
			max = Math.max(max, getDistanceAfter(r, 2503));
		}
		return max;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day14().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day14().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}
}

class Reindeer {
	String name;
	int topSpeed;
	int moveSecs;
	int restSecs;
	int points = 0;
	
	Reindeer(String name, int topSpeed, int moveSecs, int restSecs) {
		super();
		this.name = name;
		this.topSpeed = topSpeed;
		this.moveSecs = moveSecs;
		this.restSecs = restSecs;
	}
	
	@Override
	public String toString() {
		return "Reindeer [name=" + name + ", topSpeed=" + topSpeed + ", moveSecs=" + moveSecs + ", restSecs=" + restSecs + ", points=" + points + "]";
	}
} 