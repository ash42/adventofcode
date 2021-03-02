package nl.michielgraat.adventofcode2018.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {

	private static final String FILENAME = "day3.txt";

	private static final int UNCLAIMED = 0;
	private static final int CLAIMED = 1;
	private static final int DOUBLE_CLAIMED = 2;

	private int[][] fabric = new int[1000][1000];
	private String[][] claims = new String[1000][1000];

	public Day3() {
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				fabric[i][j] = UNCLAIMED;
				claims[i][j] = "unclaimed";
			}
		}
	}

	private void claim(int startx, int starty, int width, int height, String claimant) {
		for (int x = startx; x < startx + width; x++) {
			for (int y = starty; y < starty + height; y++) {
				if (fabric[x][y] == UNCLAIMED) {
					fabric[x][y] = CLAIMED;
					claims[x][y] = claimant;
				} else {
					fabric[x][y] = DOUBLE_CLAIMED;
					claims[x][y] += "," + claimant;
				}
			}
		}
	}

	private int doubleClaimedInches() {
		int total = 0;
		for (int x = 0; x < 1000; x++) {
			for (int y = 0; y < 1000; y++) {
				if (fabric[x][y] == DOUBLE_CLAIMED) {
					total++;
				}
			}
		}
		return total;
	}

	private String getNonOverlappingClaim(List<String> claimants) {
		for (String claimant : claimants) {
			boolean overlapping = false;
			for (int x = 0; x < 1000; x++) {
				for (int y = 0; y < 1000; y++) {
					if (!claims[x][y].equals("unclaimed") && claims[x][y].contains(",")) {
						String[] cs = claims[x][y].split(",");
						for (String c : cs) {
							if (c.equals(claimant)) {
								overlapping = true;
								break;
							}
						}
					}
				}
				if (overlapping) {
					break;
				}
			}
			if (!overlapping) {
				return claimant;
			}
		}
		return "none";
	}

	private void print(int maxx, int maxy) {
		for (int x = 0; x < maxx; x++) {
			for (int y = 0; y < maxy; y++) {
				System.out.print(fabric[x][y]);
			}
			System.out.println();
		}
	}

	private void printClaims(int maxx, int maxy) {
		for (int x = 0; x < maxx; x++) {
			for (int y = 0; y < maxy; y++) {
				if (!claims[x][y].equals("unclaimed")) {
					System.out.println("claims[" + x + "][" + y + "]: " + claims[x][y]);
				}
			}
			System.out.println();
		}
	}

	private String getClaimant(String s) {
		return s.substring(1, s.indexOf("@") - 1);
	}

	private int getStartx(String s) {
		return Integer.valueOf(s.substring(s.indexOf("@") + 2, s.indexOf(",")));
	}

	private int getStarty(String s) {
		return Integer.valueOf(s.substring(s.indexOf(",") + 1, s.indexOf(":")));
	}

	private int getWidth(String s) {
		return Integer.valueOf(s.substring(s.indexOf(":") + 2, s.indexOf("x")));
	}

	private int getHeight(String s) {
		return Integer.valueOf(s.substring(s.indexOf("x") + 1).trim());
	}

	public void run() {
		List<String> claimants = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			List<String> claims = stream.collect(Collectors.toList());
			for (String claim : claims) {
				String claimant = getClaimant(claim);
				claim(getStartx(claim), getStarty(claim), getWidth(claim), getHeight(claim), claimant);
				claimants.add(claimant);
			}
			System.out.println("Number of claimaints: " + claimants.size());
			System.out.println("Total number of at least double claimed inches: " + doubleClaimedInches());
			System.out.println("Non overlapping claim: " + getNonOverlappingClaim(claimants));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Day3().run();
	}

}
