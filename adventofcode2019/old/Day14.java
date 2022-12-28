package nl.michielgraat.adventofcode2019;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day14 {
	
	private static final String ORE = "ORE";
	private static final String FUEL = "FUEL";
	
	private int calculateOre(Reaction r, List<Reaction> reactions, Map<String, Integer> surplus) {
		if (arrivedAtOre(r)) {
			return getAmountOfOre(r);
		} else {
			int total = 0;
			for (String element : r.getInputChemicals().keySet()) {
				int quantityNeeded = r.getInputChemicals().get(element);
				int quantityInSurplus = (surplus.get(element) != null) ? surplus.get(element) : 0;
				if (quantityInSurplus > quantityNeeded) {
					surplus.put(element, surplus.get(element)-quantityNeeded);
				} else {
					quantityNeeded -= quantityInSurplus;
					surplus.remove(element);
					Reaction er = getReactionForElement(element, reactions);
					long quantitySupplied = er.getQuantity();
					int quantityProduced = 0;
					while (quantityProduced < quantityNeeded) {
						total += calculateOre(er, reactions, surplus);
						quantityProduced += quantitySupplied;
					}
					
					if (quantityProduced > quantityNeeded) {
						int s = (surplus.get(er.getName()) != null) ? surplus.get(er.getName()) : 0;
						surplus.put(er.getName(), s + (quantityProduced - quantityNeeded));
					}
				}
			}
			return total;
		}
	}
	
	private long calculateOre(Reaction r, List<Reaction> reactions) {
		Map<String, Integer> surplus = new HashMap<String, Integer>();
		long totalNeeded = 0;
		for (int i=0; i< r.getQuantity(); i++) {
			totalNeeded += calculateOre(r, reactions, surplus);
		}
		return totalNeeded;
	}
	
	private void printSurplusMap(Map<String, Integer> surplus) {
		for (String s : surplus.keySet()) {
			System.out.println("Surplus of " + surplus.get(s) + " of " + s);
		}
	}
	
	private Reaction getReactionForElement(String element, List<Reaction> reactions) {
		for (Reaction reaction : reactions) {
			if (reaction.getName().equals(element)) {
				return reaction;
			}
		}
		return null;
	}
	
	private boolean arrivedAtOre(Reaction r) {
		return r.getInputChemicals().keySet().stream().allMatch(i -> i.equals(ORE));
	}
	
	private int getAmountOfOre(Reaction r) {
		int ore = r.getInputChemicals().values().iterator().next();
		return ore;
	}
	
	private void findNumberOfFuelsProducedForTrillion(Reaction fuel, List<Reaction> reactions) {
		long totalOre = 0;
		System.out.println("Started at " + new Date());
		long start = new Date().getTime();
		long amount = 11788286;
		long prev = 0;
		while (totalOre < 1000000000000L) {
			//System.out.println
			fuel.setQuantity(amount);
			totalOre = calculateOre(fuel, reactions); 
			System.out.println("Needed: " + totalOre + " for " + fuel.getQuantity());
			System.out.println("Diff with previous: " + (totalOre-prev));
			prev = totalOre;
			amount += 1;
			System.out.println("Took: " + (new Date().getTime() - start) + " ms."); 
		}
		System.out.println("Took: " + (new Date().getTime() - start) + " ms.");
	}
	
	public void run() {
		List<Reaction> reactions = readReactions();
		Reaction fuel = getReactionForElement(FUEL, reactions);
		System.out.println("Amount of ORE required for 1 FUEL: " + calculateOre(fuel, reactions));
		//fuel.setQuantity(2);
		//System.out.println("Amount of ORE required for 2 FUEL: " + calculateOre(fuel, reactions));
		//System.out.println("Number of fuels produced: " + produceFuel(1000000000000L, fuel, reactions, new HashMap<String,Integer>()));
		findNumberOfFuelsProducedForTrillion(fuel, reactions);
	}

	public static void main(String[] args) {
		new Day14().run();
	}

	private void printReactions(List<Reaction> reactions) {
		for (Reaction reaction : reactions) {
			System.out.println(reaction + ":");
			for (String name : reaction.getInputChemicals().keySet()) {
				System.out.println("\t\t" + reaction.getInputChemicals().get(name) + " " + name);
			}
		}
	}
	
	private List<Reaction> readReactions() {
		Scanner scanner = null;
		List<Reaction> reactions = new ArrayList<Reaction>();
		try {
			scanner = new Scanner(new File("reactions.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] reactionParts = line.split("=>");
				String[] inputParts = reactionParts[0].trim().split(",");
				String[] outputParts = reactionParts[1].trim().split("\\s+");
				Map<String, Integer> inputChemicals = new HashMap<String, Integer>();
				for (int j = 0; j < inputParts.length; j++) {
					String[] chemicalParts = inputParts[j].trim().split("\\s+");
					int amount = Integer.valueOf(chemicalParts[0].trim());
					String name = chemicalParts[1].trim();
					inputChemicals.put(name, amount);
				}
				int amount = Integer.valueOf(outputParts[0].trim());
				String name = outputParts[1].trim();
				reactions.add(new Reaction(name, amount, inputChemicals));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return reactions;
	}
	
	class Reaction  {
		String name;
		long quantity;
		Map<String, Integer> inputChemicals = new HashMap<String, Integer>();
		
		public String getName() {
			return name;
		}
		
		public long getQuantity() {
			return quantity;
		}
		
		public void setQuantity(long quantity) {
			this.quantity = quantity;
		}
		
		public Map<String, Integer> getInputChemicals() {
			return inputChemicals;
		}
		
		public Reaction(String name, int quantity, Map<String, Integer> inputChemicals) {
			super();
			this.name = name;
			this.quantity = quantity;
			this.inputChemicals = inputChemicals;
		}
		
		public String toString() {
			return quantity + " " + name; 
		}
	}
}
