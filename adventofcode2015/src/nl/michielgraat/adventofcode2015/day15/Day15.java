package nl.michielgraat.adventofcode2015.day15;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day15 {

	private static final String FILENAME = "day15.txt";

	private List<Ingredient> getIngredients(List<String> lines) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		for (String line : lines) {
			int capacity = Integer.valueOf(line.substring(line.indexOf("capacity") + 9, line.indexOf(',')));
			int durability = Integer.valueOf(line.substring(line.indexOf("durability") + 11, line.indexOf(',', line.indexOf("durability"))));
			int flavor = Integer.valueOf(line.substring(line.indexOf("flavor") + 7, line.indexOf(',', line.indexOf("flavor"))));
			int texture = Integer.valueOf(line.substring(line.indexOf("texture") + 8, line.indexOf(',', line.indexOf("texture"))));
			int calories = Integer.valueOf(line.substring(line.indexOf("calories") + 9));
			ingredients.add(new Ingredient(capacity, durability, flavor, texture, calories));
		}
		return ingredients;
	}
	
	private long runPart2(List<String> lines) {
		List<Ingredient> ingredients = getIngredients(lines);
		
		long maxScore = 0;
		for (int i = 0; i <= 100; i++) {
			for (int j = 0; j <= 100 - i; j++) {
				for (int k = 0; k <= 100 - i - j; k++) {
					for (int l = 0; l <= 100 - i - j - k; l++) {
						Cookie c = new Cookie();
						c.ingredientToAmount.put(ingredients.get(0), i);
						c.ingredientToAmount.put(ingredients.get(1), j);
						c.ingredientToAmount.put(ingredients.get(2), k);
						c.ingredientToAmount.put(ingredients.get(3), l);
						if (c.has500C()) {
							long score = c.getScore();
							if (score > maxScore) {
								maxScore = score;
							}
						}
					}
				}
			}
		}
		
		return maxScore;
	}

	private long runPart1(List<String> lines) {
		List<Ingredient> ingredients = getIngredients(lines);
		
		long maxScore = 0;
		for (int i = 0; i <= 100; i++) {
			for (int j = 0; j <= 100 - i; j++) {
				for (int k = 0; k <= 100 - i - j; k++) {
					for (int l = 0; l <= 100 - i - j - k; l++) {
						Cookie c = new Cookie();
						c.ingredientToAmount.put(ingredients.get(0), i);
						c.ingredientToAmount.put(ingredients.get(1), j);
						c.ingredientToAmount.put(ingredients.get(2), k);
						c.ingredientToAmount.put(ingredients.get(3), l);
						long score = c.getScore();
						if (score > maxScore) {
							maxScore = score;
						}
					}
				}
			}
		}
		
		return maxScore;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day15().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day15().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}

class Ingredient {
	int capacity;
	int durability;
	int flavor;
	int texture;
	int calories;

	Ingredient(int capacity, int durability, int flavor, int texture, int calories) {
		super();
		this.capacity = capacity;
		this.durability = durability;
		this.flavor = flavor;
		this.texture = texture;
		this.calories = calories;
	}

	@Override
	public String toString() {
		return "Ingredient [capacity=" + capacity + ", durability=" + durability + ", flavor=" + flavor + ", texture="
				+ texture + ", calories=" + calories + "]";
	}
}

class Cookie {
	Map<Ingredient, Integer> ingredientToAmount = new HashMap<Ingredient, Integer>();
	
	long getScore() {
		long capacity = 0;
		long durability = 0;
		long flavor = 0;
		long texture = 0;
		for (Ingredient i : ingredientToAmount.keySet()) {
			int amount = ingredientToAmount.get(i);
			capacity += amount * i.capacity;
			durability += amount * i.durability;
			flavor += amount * i.flavor;
			texture += amount * i.texture;
		}
		if (capacity < 0) capacity = 0;
		if (durability < 0) durability = 0;
		if (flavor < 0) flavor = 0;
		if (texture < 0) texture = 0;
		return (long) capacity * (long) durability * (long) flavor * (long) texture;
	}
	
	boolean has500C() {
		int calories = 0;
		for (Ingredient i : ingredientToAmount.keySet()) {
			int amount = ingredientToAmount.get(i);
			calories += amount * i.calories;
		}
		return calories == 500;
	}
}