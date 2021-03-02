package nl.michielgraat.adventofcode2020.day17;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day17 {

	private static final String FILENAME = "day17.txt";

	private List<Point4d> fillInitialPointList4d(List<String> lines) {
		List<Point4d> points = new ArrayList<Point4d>();
		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++) { 
				if (line.charAt(x) == '#') {
					points.add(new Point4d(x, y, 0, 0, true));
				}
			}
		}
		return points;
	}

	private List<Point3d> fillInitialPointList(List<String> lines) {
		List<Point3d> points = new ArrayList<Point3d>();
		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				if (line.charAt(x) == '#') {
					points.add(new Point3d(x, y, 0, true));
				}
			}
		}
		return points;
	}

	private int nrOfActiveNeighbors4d(Point4d p, List<Point4d> points) {
		int nr = 0;
		for (int x = p.x - 1; x <= p.x + 1; x++) {
			for (int y = p.y - 1; y <= p.y + 1; y++) {
				for (int z = p.z - 1; z <= p.z + 1; z++) {
					for (int w = p.w - 1; w <= p.w + 1; w++) {
						int index = points.indexOf(new Point4d(x, y, z, w));
						if (index != -1) {
							Point4d neighbor = points.get(index);
							if (!neighbor.equals(p)) {
								nr++;
								if (nr >= 4) {
									return nr;
								}
							}
						}
					}
				}
			}
		}
		return nr;
	}

	private int nrOfActiveNeighbors(Point3d p, List<Point3d> points) {
		int nr = 0;
		for (int x = p.x - 1; x <= p.x + 1; x++) {
			for (int y = p.y - 1; y <= p.y + 1; y++) {
				for (int z = p.z - 1; z <= p.z + 1; z++) {
					int index = points.indexOf(new Point3d(x, y, z));
					if (index != -1) {
						Point3d neighbor = points.get(index);
						if (!neighbor.equals(p)) {
							nr++;
							if (nr >= 4) {
								return nr;
							}
						}
					}
				}
			}
		}
		return nr;
	}

	private List<Point3d> runCycle(List<Point3d> points) {
		List<Point3d> result = new ArrayList<Point3d>();
		int minx = points.stream().mapToInt(p -> p.x).min().getAsInt();
		int maxx = points.stream().mapToInt(p -> p.x).max().getAsInt();
		int miny = points.stream().mapToInt(p -> p.y).min().getAsInt();
		int maxy = points.stream().mapToInt(p -> p.y).max().getAsInt();
		int minz = points.stream().mapToInt(p -> p.z).min().getAsInt();
		int maxz = points.stream().mapToInt(p -> p.z).max().getAsInt();

		for (int x = minx - 1; x <= maxx + 1; x++) {
			for (int y = miny - 1; y <= maxy + 1; y++) {
				for (int z = minz - 1; z <= maxz + 1; z++) {
					Point3d p = new Point3d(x, y, z);
					if (x != minx - 1 && x != maxx + 1 && y != miny - 1 && y != maxy + 1 && z != minz - 1
							&& z != maxz + 1) {
						int index = points.indexOf(p);
						if (index != -1) {
							p.setActive(points.get(index).isActive());
						}
					}
					int nr = nrOfActiveNeighbors(p, points);
					if (nr == 3) {
						p.setActive(true);
					} else if (nr != 2) {
						p.setActive(false);
					}
					if (p.isActive()) {
						result.add(p);
					}
				}
			}
		}
		return result;
	}

	private List<Point4d> runCycle4d(List<Point4d> points) {
		List<Point4d> result = new ArrayList<Point4d>();
		int minx = points.stream().mapToInt(p -> p.x).min().getAsInt();
		int maxx = points.stream().mapToInt(p -> p.x).max().getAsInt();
		int miny = points.stream().mapToInt(p -> p.y).min().getAsInt();
		int maxy = points.stream().mapToInt(p -> p.y).max().getAsInt();
		int minz = points.stream().mapToInt(p -> p.z).min().getAsInt();
		int maxz = points.stream().mapToInt(p -> p.z).max().getAsInt();
		int minw = points.stream().mapToInt(p -> p.w).min().getAsInt();
		int maxw = points.stream().mapToInt(p -> p.w).max().getAsInt();

		for (int x = minx - 1; x <= maxx + 1; x++) {
			for (int y = miny - 1; y <= maxy + 1; y++) {
				for (int z = minz - 1; z <= maxz + 1; z++) {
					for (int w = minw - 1; w <= maxw + 1; w++) {
						Point4d p = new Point4d(x, y, z, w);
						if (x != minx - 1 && x != maxx + 1 && y != miny - 1 && y != maxy + 1 && z != minz - 1
								&& z != maxz + 1 && w != minw - 1 && w != maxw + 1) {
							int index = points.indexOf(p);
							if (index != -1) {
								p.setActive(points.get(index).isActive());
							}
						}
						int nr = nrOfActiveNeighbors4d(p, points);
						if (nr == 3) {
							p.setActive(true);
						} else if (nr != 2) {
							p.setActive(false);
						}
						if (p.isActive()) {
							result.add(p);
						}
					}
				}
			}
		}
		return result;
	}

	public long runPart2(List<String> lines) {
		List<Point4d> points = fillInitialPointList4d(lines);
		for (int i = 1; i <= 6; i++) {
			points = runCycle4d(points);
		}
		return points.size();
	}

	public long runPart1(List<String> lines) {
		List<Point3d> points = fillInitialPointList(lines);
		for (int i = 1; i <= 6; i++) {
			points = runCycle(points);
		}
		return points.size();
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day17().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day17().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}

}