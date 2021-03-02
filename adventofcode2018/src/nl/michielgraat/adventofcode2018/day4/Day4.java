package nl.michielgraat.adventofcode2018.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4 {

	private static final String FILENAME = "day4.txt";

	public void run() {
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			List<String> srecords = stream.collect(Collectors.toList());
			Set<Record> records = new TreeSet<Record>();
			srecords.stream().forEach(s -> records.add(new Record(s)));
			String guard = new String();
			int sleepStart = 0;
			int sleepEnd = 0;
			Map<String, Integer> guardToMinutes = new HashMap<String, Integer>();
			Map<String, Integer[]> guardToMinuteCount = new HashMap<String, Integer[]>();
			for (Record record : records) {
				// System.out.println(record);
				if (record.getGuard() != null) {
					guard = record.getGuard();
					// System.out.println(guard + " starting duty");
				} else if (record.isFallsAsleep()) {
					sleepStart = record.getMinute();
				} else if (record.isWakesUp()) {
					sleepEnd = record.getMinute() - 1;
					// System.out.println("Guard " + guard + " was asleep for " +
					// (sleepEnd-sleepStart) + " minutes, starting at 00:" + sleepStart);
					int total = 0;
					if (guardToMinutes.containsKey(guard)) {
						total = guardToMinutes.get(guard);
					}
					total += (sleepEnd - sleepStart);
					guardToMinutes.put(guard, total);

					int[] minutes = minuteArray();
					if (guardToMinuteCount.containsKey(guard)) {
						minutes = Arrays.stream(guardToMinuteCount.get(guard)).mapToInt(Integer::intValue).toArray();
					}
					for (int i = sleepStart; i <= sleepEnd; i++) {
						minutes[i] += 1;
					}
					guardToMinuteCount.put(guard, IntStream.of(minutes).boxed().toArray((Integer[]::new)));
				}
			}

			int max = 0;
			String maxGuard = new String();
			for (String g : guardToMinutes.keySet()) {
				// System.out.println("Guard #" + g + " was asleep for " + guardToMinutes.get(g)
				// + " minutes");
				int maxMinutes = guardToMinutes.get(g);
				if (maxMinutes > max) {
					max = maxMinutes;
					maxGuard = g;
				}
			}
			System.out.println("Guard #" + maxGuard + " slept the most: " + max + " minutes");
			int[] minuteCount = Arrays.stream(guardToMinuteCount.get(maxGuard)).mapToInt(Integer::intValue).toArray();
			int maxCount = 0;
			int maxMinute = 0;
			for (int i = 0; i < minuteCount.length; i++) {
				int count = minuteCount[i];
				if (count > maxCount) {
					maxCount = count;
					maxMinute = i;
				}
			}
			System.out.println(
					"The guard slept the most during minute " + maxMinute + ", a total of " + maxCount + " minutes");
			System.out.println("The answer (" + maxGuard + "*" + maxMinute + ") for part 1 is: "
					+ (Integer.valueOf(maxGuard) * maxMinute));

			Map<Integer, GuardCount> minuteToGuardCount = new HashMap<Integer, GuardCount>();
			for (String g : guardToMinuteCount.keySet()) {
				int[] counts = Arrays.stream(guardToMinuteCount.get(g)).mapToInt(Integer::intValue).toArray();
				for (int i = 0; i < counts.length; i++) {
					if (minuteToGuardCount.containsKey(i)) {
						GuardCount gc = minuteToGuardCount.get(i);
						if (counts[i] > gc.getCount()) {
							minuteToGuardCount.put(i, new GuardCount(g, counts[i]));
						}
 					} else {
 						minuteToGuardCount.put(i, new GuardCount(g, counts[i]));
 					}
				}
			}
			
			maxCount = 0;
			maxMinute = 0;
			for (int minute : minuteToGuardCount.keySet()) {
				GuardCount gc = minuteToGuardCount.get(minute);
				//System.out.println("Minute " + minute + " has max of " + gc.getCount() + " for guard " + gc.getGuard());
				if (gc.getCount() > maxCount) {
					maxCount = gc.getCount();
					maxMinute = minute;
					maxGuard = gc.getGuard();
				}
			}
			System.out.println("Guard #" + maxGuard + " is most frequently asleep on " + maxMinute + " with a total of " + maxCount + " minutes");
			System.out.println("The answer (" + maxGuard + "*" + maxMinute + ") to part 2 is: " + (Integer.valueOf(maxGuard) * maxMinute));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int[] minuteArray() {
		int[] minutes = new int[60];
		for (int i = 0; i < 60; i++) {
			minutes[i] = 0;
		}
		return minutes;
	}

	public static void main(String[] args) {
		new Day4().run();

	}

	class GuardCount {
		String guard;
		int count;

		public GuardCount(String guard, int count) {
			super();
			this.guard = guard;
			this.count = count;
		}

		public String getGuard() {
			return guard;
		}

		public void setGuard(String guard) {
			this.guard = guard;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	}
}
