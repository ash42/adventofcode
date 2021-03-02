package nl.michielgraat.adventofcode2015.day16;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day16 {

	private static final String FILENAME = "day16.txt";
	
	private List<Sue> getSues(List<String> lines) {
		List<Sue> sues = new ArrayList<Sue>();
		for (String line : lines) {
			int nr = Integer.valueOf(line.substring(line.indexOf(' ') + 1, line.indexOf(':')));
			Sue sue = new Sue(nr);
			if (line.contains("children")) {
				if (line.indexOf(',', line.indexOf("children")) >= 0) {
					sue.children = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("children")) + 2, line.indexOf(',', line.indexOf("children"))));
				} else {
					sue.children = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("children")) + 2));
				}
			}
			
			if (line.contains("cats")) {
				if (line.indexOf(',', line.indexOf("cats")) >= 0) {
					sue.cats = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("cats")) + 2, line.indexOf(',', line.indexOf("cats"))));
				} else {
					sue.cats = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("cats")) + 2));
				}
			}
			
			if (line.contains("samoyeds")) {
				if (line.indexOf(',', line.indexOf("samoyeds")) >= 0) {
					sue.samoyeds = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("samoyeds")) + 2, line.indexOf(',', line.indexOf("samoyeds"))));
				} else {
					sue.samoyeds = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("samoyeds")) + 2));
				}
			}
			
			if (line.contains("pomeranians")) {
				if (line.indexOf(',', line.indexOf("pomeranians")) >= 0) {
					sue.pomeranians = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("pomeranians")) + 2, line.indexOf(',', line.indexOf("pomeranians"))));
				} else {
					sue.pomeranians = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("pomeranians")) + 2));
				}
			}
			
			if (line.contains("akitas")) {
				if (line.indexOf(',', line.indexOf("akitas")) >= 0) {
					sue.akitas = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("akitas")) + 2, line.indexOf(',', line.indexOf("akitas"))));
				} else {
					sue.akitas = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("akitas")) + 2));
				}
			}
			
			if (line.contains("vizslas")) {
				if (line.indexOf(',', line.indexOf("vizslas")) >= 0) {
					sue.vizslas = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("vizslas")) + 2, line.indexOf(',', line.indexOf("vizslas"))));
				} else {
					sue.vizslas = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("vizslas")) + 2));
				}
			}
			
			if (line.contains("goldfish")) {
				if (line.indexOf(',', line.indexOf("goldfish")) >= 0) {
					sue.goldfish = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("goldfish")) + 2, line.indexOf(',', line.indexOf("goldfish"))));
				} else {
					sue.goldfish = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("goldfish")) + 2));
				}
			}
			
			if (line.contains("trees")) {
				if (line.indexOf(',', line.indexOf("trees")) >= 0) {
					sue.trees = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("trees")) + 2, line.indexOf(',', line.indexOf("trees"))));
				} else {
					sue.trees = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("trees")) + 2));
				}
			}
			
			if (line.contains("cars")) {
				if (line.indexOf(',', line.indexOf("cars")) >= 0) {
					sue.cars = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("cars")) + 2, line.indexOf(',', line.indexOf("cars"))));
				} else {
					sue.cars = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("cars")) + 2));
				}
			}
			
			if (line.contains("perfumes")) {
				if (line.indexOf(',', line.indexOf("perfumes")) >= 0) {
					sue.perfumes = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("perfumes")) + 2, line.indexOf(',', line.indexOf("perfumes"))));
				} else {
					sue.perfumes = Integer.valueOf(line.substring(line.indexOf(':', line.indexOf("perfumes")) + 2));
				}
			}
			
			sues.add(sue);
		}
		return sues;
	}

	private int runPart2(List<String> lines) {
		List<Sue> sues = getSues(lines);
		
		Sue s = new Sue(-1);
		s.children = 3;
		s.cats = 7;
		s.samoyeds = 2;
		s.pomeranians = 3;
		s.akitas = 0;
		s.vizslas = 0;
		s.goldfish = 5;
		s.trees = 3;
		s.cars = 2;
		s.perfumes = 1;
		
		for (Sue sue : sues) {
			if (sue.children == 3 || sue.children < 0) {
				if (sue.cats > 7 || sue.cats < 0) {
					if (sue.samoyeds == 2 || sue.samoyeds < 0) {
						if (sue.pomeranians < 3) {
							if (sue.akitas <= 0) {
								if (sue.vizslas <= 0) {
									if (sue.goldfish < 5) {
										if (sue.trees > 3 || sue.trees < 0) {
											if (sue.cars == 2 || sue.cars < 0) {
												if (sue.perfumes == 1 || sue.perfumes < 0) {
													return sue.nr;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return -1;
	}

	private int runPart1(List<String> lines) {
		List<Sue> sues = getSues(lines);
		
		Sue s = new Sue(-1);
		s.children = 3;
		s.cats = 7;
		s.samoyeds = 2;
		s.pomeranians = 3;
		s.akitas = 0;
		s.vizslas = 0;
		s.goldfish = 5;
		s.trees = 3;
		s.cars = 2;
		s.perfumes = 1;
		
		for (Sue sue : sues) {
			if (sue.equals(s)) {
				return sue.nr;
			}
		}
		
		return -1;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day16().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day16().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}

class Sue {
	int nr;
	int children = -1;
	int cats = -1;
	int samoyeds = -1;
	int pomeranians = -1;
	int akitas = -1;
	int vizslas = -1;
	int goldfish = -1;
	int trees = -1;
	int cars = -1;
	int perfumes = -1;
	
	Sue (int nr) {
		this.nr = nr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + akitas;
		result = prime * result + cars;
		result = prime * result + cats;
		result = prime * result + children;
		result = prime * result + goldfish;
		result = prime * result + perfumes;
		result = prime * result + pomeranians;
		result = prime * result + samoyeds;
		result = prime * result + trees;
		result = prime * result + vizslas;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sue other = (Sue) obj;
		if (akitas != other.akitas && akitas >= 0 && other.akitas >= 0)
			return false;
		if (cars != other.cars && cars >= 0 && other.cars >= 0)
			return false;
		if (cats != other.cats && cats >= 0 && other.cats >= 0)
			return false;
		if (children != other.children && children >= 0 && other.children >= 0)
			return false;
		if (goldfish != other.goldfish && goldfish >= 0 && other.goldfish >= 0)
			return false;
		if (perfumes != other.perfumes && perfumes >= 0 && other.perfumes >= 0)
			return false;
		if (pomeranians != other.pomeranians && pomeranians >= 0 && other.pomeranians >= 0)
			return false;
		if (samoyeds != other.samoyeds && samoyeds >= 0 && other.samoyeds >= 0)
			return false;
		if (trees != other.trees && trees >= 0 && other.trees >= 0)
			return false;
		if (vizslas != other.vizslas && vizslas >= 0 && other.vizslas >= 0)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sue [nr=" + nr + ", children=" + children + ", cats=" + cats + ", samoyeds=" + samoyeds
				+ ", pomeranians=" + pomeranians + ", akitas=" + akitas + ", vizslas=" + vizslas + ", goldfish="
				+ goldfish + ", trees=" + trees + ", cars=" + cars + ", perfumes=" + perfumes + "]";
	}
}