package nl.michielgraat.adventofcode2020.day13;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day13 {

	private static final String FILENAME = "day13.txt";

	private List<Integer> getRemainders(String[] busses) {
		List<Integer> remainders = new ArrayList<Integer>();
		for (int i = 0; i < busses.length; i++) {
			if (!busses[i].equals("x")) {
				remainders.add(i);
			}
		}
		return remainders;
	}

	private List<Integer> getMods(String[] busses) {
		List<Integer> mods = new ArrayList<Integer>();
		for (int i = 0; i < busses.length; i++) {
			if (!busses[i].equals("x")) {
				mods.add(Integer.valueOf(busses[i]));
			}
		}
		return mods;
	}

	private long calculateN(String[] busses) {
		long total = 1;
		for (int i = 0; i < busses.length; i++) {
			if (!busses[i].equals("x")) {
				long b = Long.valueOf(busses[i]);
				total *= b;
			}
		}
		return total;
	}

	private List<Long> getNi(long N, List<Integer> mods) {
		List<Long> ni = new ArrayList<Long>();
		for (int mod : mods) {
			ni.add(N / mod);
		}
		return ni;
	}

	private long getInverse(long x, long mod) {
		x = x % mod;
		for (int i = 1; i < mod; i++) {
			if ((x * i) % mod == 1) {
				return i;
			}
		}
		return 1;
	}

	private List<Long> getXi(List<Long> ni, List<Integer> mods) {
		List<Long> xi = new ArrayList<Long>();
		for (int i = 0; i < ni.size(); i++) {
			xi.add(getInverse(ni.get(i), mods.get(i)));
		}
		return xi;
	}

	private List<Long> getProducts(List<Integer> bi, List<Long> ni, List<Long> xi) {
		List<Long> products = new ArrayList<Long>();
		for (int i = 0; i < bi.size(); i++) {
			products.add(((long) bi.get(i)) * ni.get(i) * xi.get(i));
		}
		return products;
	}

	private long getFirstX(List<Long> products) {
		long total = 0;
		for (long p : products) {
			total += p;
		}
		return total;
	}

	private long find(String[] busses) {
		List<Integer> bi = getRemainders(busses);
		List<Integer> mods = getMods(busses);
		long N = calculateN(busses);
		List<Long> ni = getNi(N, mods);
		List<Long> xi = getXi(ni, mods);
		List<Long> products = getProducts(bi, ni, xi);
		long firstX = getFirstX(products);
		return N - (firstX % N);
	}

	public long runPart2(List<String> lines) {
		String[] busses = lines.get(1).split(",");
		return find(busses);
	}

	private List<Integer> getBusses(String line) {
		List<Integer> busses = new ArrayList<Integer>();
		String[] bs = line.split(",");
		for (String b : bs) {
			if (!b.equals("x")) {
				busses.add(Integer.valueOf(b));
			}
		}
		return busses;
	}

	public long runPart1(List<String> lines) {
		long start = Long.valueOf(lines.get(0));
		List<Integer> busses = getBusses(lines.get(1));
		for (long i = start;; i++) {
			for (int bus : busses) {
				if (i % bus == 0) {
					return (i - start) * bus;
				}
			}
		}
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day13().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day13().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}
}