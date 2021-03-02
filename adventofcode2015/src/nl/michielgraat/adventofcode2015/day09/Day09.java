package nl.michielgraat.adventofcode2015.day09;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day09 {

	private static final String FILENAME = "day09.txt";

	private City getCity(String city, List<City> cities) {
		if (cities.contains(new City(city))) {
			return cities.get(cities.indexOf(new City(city)));
		}
		City c = new City(city);
		cities.add(c);
		return c;
	}

	private List<City> getCities(List<String> lines) {
		List<City> cities = new ArrayList<City>();
		for (String line : lines) {
			String city1 = line.substring(0, line.indexOf(" "));
			String city2 = line.substring(line.indexOf("to ") + 3, line.indexOf(" ="));
			int distance = Integer.valueOf(line.substring(line.indexOf("=") + 2));
			City c = getCity(city1, cities);
			City c2 = getCity(city2, cities);
			c.addDistance(c2, distance);
			c2.addDistance(c, distance);
		}
		return cities;
	}

	private void getPaths(City city, List<City> cities, int sum, Map<String, Integer> paths, int n) {
		cities.add(city);
		for (City c : city.distanceTo.keySet()) {
			if (!cities.contains(c)) {
				getPaths(c, new ArrayList<City>(cities), sum + city.distanceTo.get(c), paths, n);
			} else if (cities.size() == n) {
				String key = "";
				for (City c2 : cities) {
					key += c2.name + ", ";
				}
				paths.put(key, sum);
			}
		}
	}

	private Map<String, Integer> getPaths(List<String> lines, boolean print) {
		List<City> cities = getCities(lines);
		Map<String, Integer> paths = new HashMap<String, Integer>();
		for (City city : cities) {
			getPaths(city, new ArrayList<City>(), 0, paths, cities.size());
		}

		if (print) {
			for (String key : paths.keySet()) {
				System.out.println(key.substring(0, key.length() - 2) + " has distance: " + paths.get(key));
			}
		}

		return paths;
	}

	private int runPart2(List<String> lines) {
		Map<String, Integer> paths = getPaths(lines, false);

		int max = 0;
		for (String key : paths.keySet()) {
			int distance = paths.get(key);
			if (distance > max) {
				max = distance;
			}
		}

		return max;
	}

	private int runPart1(List<String> lines) {
		Map<String, Integer> paths = getPaths(lines, false);

		int min = Integer.MAX_VALUE;
		for (String key : paths.keySet()) {
			int distance = paths.get(key);
			if (distance < min) {
				min = distance;
			}
		}

		return min;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day09().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day09().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}

class City {
	String name;
	Map<City, Integer> distanceTo = new HashMap<City, Integer>();

	City(String name) {
		this.name = name;
	}

	void addDistance(City city, int dist) {
		distanceTo.put(city, dist);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		City other = (City) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}