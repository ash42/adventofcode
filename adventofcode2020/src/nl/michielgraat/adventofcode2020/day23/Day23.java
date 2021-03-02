package nl.michielgraat.adventofcode2020.day23;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 {

	private List<Integer> convertToList(String s) {
		List<Integer> result = new ArrayList<Integer>();
		for (char c : s.toCharArray()) {
			result.add(Character.getNumericValue(c));
		}
		return result;
	}
	
	private int getDestination(int current, int first, int second, int third, List<Integer> list) {
		int dest = (current-1)%list.size();
		if (dest == 0) {
			dest = list.size();
		}
		while (dest == first || dest == second || dest == third) {
			dest = (dest-1)%list.size();
			if (dest == 0) {
				dest = list.size();
			}
		}
		return dest;
	}
	
	private List<Integer> copy (List<Integer> list) {
		List<Integer> result = new ArrayList<Integer>();
		for (int l : list) {
			result.add(Integer.valueOf(l));
		}
		return result;
	}
	
	private String getCupsAsString(List<Integer> input, int current, int first, int second, int third, int dest) {
		String result = getCups(input, current);
		result += "\r\npick up: " + first + ", " + second + ", " + third + "\r\n";
		result += "destination: " + dest;
		return result;
	}

	private String getCups(List<Integer> input, int current) {
		String result = "cups: ";
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i) == current) {
				result += "(" + current + ") ";
			} else {
				result += input.get(i) + " ";
			}
		}
		return result;
	}

	private void shiftRight(List<Integer> list) {
		int last = list.get(list.size() -1);
		for (int i = list.size() - 1; i> 0; i--) {
			list.set(i, list.get(i-1));
		}
		list.set(0, last);
	}
	
	private String getResult(List<Integer> list) {
		int idx1 = list.indexOf(1);
		String result = "";
		int idx = (idx1+1)%list.size();
		while (result.length() < list.size() -1) {
			result += list.get(idx);
			idx = (idx+1)%list.size();
		}
		return result;
	}
	
	private void addToOneMillion(List<Integer> l) {
		int start = Collections.max(l);
		for (int i=start+1; i<=1000000; i++) {
			l.add(i);
		}
	}
	
	private Map<Long, Cup> fillValueToCupMap(List<Integer> input) {
		Map<Long, Cup> valueToCup = new HashMap<Long, Cup>();
		
		Cup first = null;
		Cup current = null;
		for (int i : input) {
			if (current == null) {
				current = new Cup(i);
				first = current;
			} else {
				current.next = new Cup(i);
				current = current.next;
			}
			valueToCup.put(Long.valueOf(i), current);
		}
		current.next = first;
		return valueToCup;
	}
	
	private Cup performMove(Cup current, Map<Long, Cup> valueToCup, int size) {
		Cup first = current.next;
		Cup second = current.next.next;
		Cup third = current.next.next.next;
		current.next = third.next;
		
		long dest = current.val - 1;
		while (dest == 0 || dest == first.val || dest == second.val || dest == third.val) {
			if (dest == 0) {
				dest = size;
			} else {
				dest = dest - 1;
			}
		}
		Cup destCup = valueToCup.get(dest);
		third.next = destCup.next;
		destCup.next = first;
		
		return current.next;
	}
	
	public long runPart2(String s) {
		List<Integer> input = convertToList(s);
		addToOneMillion(input);
		Map<Long, Cup> valueToCup = fillValueToCupMap(input);
		
		Cup current = valueToCup.get(Long.valueOf(s.substring(0,1)));
		for (int move = 1; move <= 10000000; move++) {
			current = performMove(current, valueToCup, input.size());
		}
		return valueToCup.get(1L).next.val * valueToCup.get(1L).next.next.val;
	}

	public String runPart1(String s) {
		List<Integer> input = convertToList(s);
		List<Integer> result = convertToList(s);
		int curIndex = 0;
		int current = input.get(curIndex);
		for (int move = 1; move <= 100; move++) {
			int removeIdx = (curIndex+1)%result.size();
			int first = result.remove(removeIdx);
			removeIdx = (removeIdx)%result.size();
			int second = result.remove(removeIdx);
			removeIdx = (removeIdx)%result.size();
			int third = result.remove(removeIdx);
			int dest = getDestination(current, first, second, third, input);
//			System.out.println("-- move " + move + " --");
//			System.out.println(getCupsAsString(input, current, first, second, third, dest));
//			System.out.println();
			int addIdx = result.indexOf(dest)+1;
			result.add(addIdx, third);
			result.add(addIdx, second);
			result.add(addIdx, first);
			while (result.indexOf(current) != curIndex) {
				shiftRight(result);
			}
			curIndex = (curIndex+1) % input.size();
			current = result.get(curIndex);
			input = copy(result);
		}
//		System.out.println("-- final --");
//		System.out.println(getCups(result, current));
		
		return getResult(result);
	}

	public static void main(String[] args) {
		long start = Calendar.getInstance().getTimeInMillis();
		//System.out.println("Answer to part 1: " + new Day23().runPart1("389125467"));
		System.out.println("Answer to part 1: " + new Day23().runPart1("614752839"));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		//System.out.println("Answer to part 2: " + new Day23().runPart2("389125467"));
		System.out.println("Answer to part 2: " + new Day23().runPart2("614752839"));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}

class Cup {
	public long val;
	public Cup prev;
	public Cup next;
	
	public Cup(long val) {
		this.val = val;
	}
}