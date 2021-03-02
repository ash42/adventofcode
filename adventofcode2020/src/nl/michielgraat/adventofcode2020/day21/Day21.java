package nl.michielgraat.adventofcode2020.day21;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day21 {

	private static final String FILENAME = "day21.txt";

	private Map<String, Integer> getIngredients(List<String> lines) {
		Map<String, Integer> ingredientsToAmount = new HashMap<String, Integer>();
		
		for (String line : lines) {
			String[] ingredientArray = line.substring(0, line.indexOf('(') - 1).split(" ");
			for (String ingredient : ingredientArray) {
				if (!ingredientsToAmount.containsKey(ingredient)) {
					ingredientsToAmount.put(ingredient, 0);
				}
				ingredientsToAmount.put(ingredient, ingredientsToAmount.get(ingredient)+1);
			}
		}
		return ingredientsToAmount;
	}
	
	private List<String> getAllergens(String line) {
		List<String> allergens = new ArrayList<String>();
		String[] allergenArray = line.substring(line.indexOf('(')+10, line.indexOf(')')).split(", ");
		for (String allergen : allergenArray) {
			allergens.add(allergen);
		}
		return allergens;
	}
	
	private List<String> getIngredients(String line) {
		List<String> ingredients = new ArrayList<String>();
		String[] ingredientArray = line.substring(0, line.indexOf('(') - 1).split(" ");
		for (String ingredient : ingredientArray) {
			ingredients.add(ingredient);
		}
		return ingredients;
	}
	
	private void filter(List<String> lines, Set<String> ingredients, Map<String, Set<String>> allergenToIngredients) {
		for (String line : lines) {
			List<String> allergensInProduct = getAllergens(line);
			List<String> ingredientsInProduct = getIngredients(line);
			for (String allergen : allergensInProduct) {
				for (String ingredient : ingredients) {
					if (!ingredientsInProduct.contains(ingredient)) {
						allergenToIngredients.get(allergen).remove(ingredient);
					}
				}
			}
		}
	}

	private Map<String, Set<String>> createAllergenToIngredientsMap(List<String> lines) {
		Map<String, Set<String>> allergenToIngredients = new HashMap<String, Set<String>>();

		for (String line : lines) {
			List<String> allergensInProduct = getAllergens(line);
			List<String> ingredientsInProduct = getIngredients(line);
			for (String allergen : allergensInProduct) {
				if (!allergenToIngredients.containsKey(allergen)) {
					allergenToIngredients.put(allergen, new HashSet<String>());
				}
				allergenToIngredients.get(allergen).addAll(ingredientsInProduct);
			}
		}
		return allergenToIngredients;
	}
	
	private boolean solutionFound(Map<String, Set<String>> aToI) {
		for (Set<String> v : aToI.values()) {
			if (v.size() > 1) {
				return false;
			}
		}
		return true;
	}
	
	private List<String> getIngredientsToRemove(Map<String, Set<String>> aToI) {
		List<String> result = new ArrayList<String>();
		for (Set<String> ingredients : aToI.values()) {
			if (ingredients.size() == 1) {
				result.add(ingredients.iterator().next());
			}
		}
		return result;
	}
	
	private Set<String> getSortedAllergens(Map<String, Set<String>> allergenToIngredients) {
		Set<String> sortedAllergens = new TreeSet<String>();
		for (String allergen : allergenToIngredients.keySet()) {
			sortedAllergens.add(allergen);
		}
		return sortedAllergens;
	}

	private void mapIngredientToAllergen(Map<String, Set<String>> allergenToIngredients) {
		while (!solutionFound(allergenToIngredients)) {
			List<String> ingredientsToRemove = getIngredientsToRemove(allergenToIngredients);
			for (String allergen : allergenToIngredients.keySet()) {
				Set<String> ins = allergenToIngredients.get(allergen);
				if (ins.size() == 1) {
					allergenToIngredients.put(allergen, ins);
				} else {
					Set<String> newIns = new HashSet<String>();
					for (String in : ins) {
						if (!ingredientsToRemove.contains(in)) {
							newIns.add(in);
						}
					}
					allergenToIngredients.put(allergen, newIns);
				}
			}
		}
	}
	 
	private String buildResultString(Map<String, Set<String>> allergenToIngredients, Set<String> sortedAllergens) {
		String result = new String();
		for (String allergen : sortedAllergens) {
			result += allergenToIngredients.get(allergen).iterator().next() + ",";
		}
		result = result.substring(0, result.length()-1);
		return result;
	}

	public String runPart2(List<String> lines) {
		Map<String, Integer> ingredients = getIngredients(lines);
		Map<String, Set<String>> allergenToIngredients = createAllergenToIngredientsMap(lines);
		filter(lines, ingredients.keySet(), allergenToIngredients);
		mapIngredientToAllergen(allergenToIngredients);
		return buildResultString(allergenToIngredients, getSortedAllergens(allergenToIngredients));
	}

	public long runPart1(List<String> lines) {
		Map<String, Integer> ingredients = getIngredients(lines);
		Map<String, Set<String>> allergenToIngredients = createAllergenToIngredientsMap(lines);
		filter(lines, ingredients.keySet(), allergenToIngredients);
		
		Set<String> remainingIngredients = new HashSet<String>();
		for (Set<String> aIngredients : allergenToIngredients.values()) {
			for (String aIngredient : aIngredients) {
				remainingIngredients.add(aIngredient);
			}
		}
		
		long total = 0;
		for (String ingredient : ingredients.keySet()) {
			if (!remainingIngredients.contains(ingredient)) {
				total += ingredients.get(ingredient);
			}
		}
		return total;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day21().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day21().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}
}