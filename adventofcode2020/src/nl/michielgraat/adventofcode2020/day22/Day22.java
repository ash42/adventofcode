package nl.michielgraat.adventofcode2020.day22;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day22 {

	private static final String FILENAME = "day22.txt";

	private LinkedList<Integer> getPlayer2Start(List<String> lines) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		boolean foundPlayer = false;
		for (String line : lines) {
			if (line.startsWith("Player 2:")) {
				foundPlayer = true;
			} else if (foundPlayer) {
				list.add(Integer.valueOf(line));
			}
		}
		return list;
	}

	private LinkedList<Integer> getPlayer1Start(List<String> lines) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		for (String line : lines) {
			if (line.isBlank()) {
				break;
			} else {
				if (!line.startsWith("Player 1:")) {
					list.add(Integer.valueOf(line));
				}
			}
		}
		return list;
	}

	private String getDeck(LinkedList<Integer> deck) {
		String result = new String();
		for (int d : deck) {
			result += d + ", ";
		}
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 2);
		}
		return result;
	}

	private long calculateResult(LinkedList<Integer> deck) {
		long result = 0;
		for (int i = 0; i < deck.size(); i++) {
			long current = deck.get(i);
			current *= (deck.size() - i);
			result += current;
		}
		return result;
	}

	private LinkedList<Integer> runGame(LinkedList<Integer> p1, LinkedList<Integer> p2) {
		boolean p1won = false;
		boolean p2won = false;
		int round = 1;
		while (!p1won && !p2won) {
			int p1nr = p1.removeFirst();
			int p2nr = p2.removeFirst();
			if (p1nr >= p2nr) {
				p1.add(p1nr);
				p1.add(p2nr);
			} else {
				p2.add(p2nr);
				p2.add(p1nr);
			}
			if (p1.isEmpty()) {
				p2won = true;
			} else if (p2.isEmpty()) {
				p1won = true;
			} else {
				round++;
			}
		}
		return (p1won) ? p1 : p2;
	}

	private LinkedList<Integer> copy(LinkedList<Integer> list, int number) {
		LinkedList<Integer> copy = new LinkedList<Integer>();
		for (int i = 0; i < number; i++) {
			copy.add(Integer.valueOf(list.get(i)));
		}
		return copy;
	}

	private Winner runRecursiveGame(LinkedList<Integer> p1, LinkedList<Integer> p2, int gamenr) {
//		System.out.println("=== Game " + gamenr + " ===");
//		System.out.println();
		List<Round> previousRounds = new ArrayList<Round>();
		if (p2.size() == 0) {
			return new Winner(true, p1);
		} else if (p1.size() == 0) {
			return new Winner(false, p2);
		} else {
			boolean p1won = false;
			boolean p2won = false;
			int round = 1;
			while (!p1won && !p2won) {
//				System.out.println("-- Round " + round + " (Game " + gamenr + ") --");
//				System.out.println("Player 1's deck: " + getDeck(p1));
//				System.out.println("Player 2's deck: " + getDeck(p2));
				Round r = new Round(copy(p1,p1.size()), copy(p2,p2.size()));
				if (previousRounds.contains(r)) {
//					System.out.println("Player 1 wins round " + round + " of game " + gamenr + "!");
//					System.out.println();
					p1won = true;
				} else {
					previousRounds.add(r);
					int p1nr = p1.removeFirst();
					int p2nr = p2.removeFirst();
//					System.out.println("Player 1 plays: " + p1nr);
//					System.out.println("Player 2 plays: " + p2nr);
					if (p1.size() >= p1nr && p2.size() >= p2nr) {
//						System.out.println("Playing a sub-game to determine the winner...");
						LinkedList<Integer> p1copy = copy(p1, p1nr);
						LinkedList<Integer> p2copy = copy(p2, p2nr);
						Winner winner = runRecursiveGame(p1copy, p2copy, gamenr + 1);
						if (winner.isP1won()) {
//							System.out.println("Player 1 wins round " + round + " of game " + gamenr + "!");
//							System.out.println();
							p1.add(p1nr);
							p1.add(p2nr);
						} else {
//							System.out.println("Player 2 wins round " + round + " of game " + gamenr + "!");
//							System.out.println();
							p2.add(p2nr);
							p2.add(p1nr);
						}
						if (p1.isEmpty()) {
//							System.out.println("The winner of game " + gamenr + " is player 2!");
							p2won = true;
						} else if (p2.isEmpty()) {
//							System.out.println("The winner of game " + gamenr + " is player 1!");
							p1won = true;
						} else {
							round++;
						}
					} else {
						if (p1nr >= p2nr) {
//							System.out.println("Player 1 wins round " + round + " of game " + gamenr + "!");
//							System.out.println();
							p1.add(p1nr);
							p1.add(p2nr);
						} else {
//							System.out.println("Player 2 wins round " + round + " of game " + gamenr + "!");
//							System.out.println();
							p2.add(p2nr);
							p2.add(p1nr);
						}
						if (p1.isEmpty()) {
//							System.out.println("The winner of game " + gamenr + " is player 2!");
							p2won = true;
						} else if (p2.isEmpty()) {
//							System.out.println("The winner of game " + gamenr + " is player 1!");
							p1won = true;
						} else {
							round++;
						}
					}
				}
			}
//			System.out.println();
			return (p1won) ? new Winner(true, p1) : new Winner(false, p2);
		}
	}

	public long runPart2(List<String> lines) {
		Winner winner = runRecursiveGame(getPlayer1Start(lines), getPlayer2Start(lines), 1);
		return calculateResult(winner.getWinner());
	}

	public long runPart1(List<String> lines) {
		return calculateResult(runGame(getPlayer1Start(lines), getPlayer2Start(lines)));
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day22().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day22().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}

class Winner {
	boolean p1won = false;
	LinkedList<Integer> winner;

	Winner(boolean p1won, LinkedList<Integer> winner) {
		super();
		this.p1won = p1won;
		this.winner = winner;
	}

	boolean isP1won() {
		return p1won;
	}

	void setP1won(boolean p1won) {
		this.p1won = p1won;
	}

	LinkedList<Integer> getWinner() {
		return winner;
	}

	void setWinner(LinkedList<Integer> winner) {
		this.winner = winner;
	}

}

class Round {
	LinkedList<Integer> p1;
	LinkedList<Integer> p2;

	Round(LinkedList<Integer> p1, LinkedList<Integer> p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}

	LinkedList<Integer> getP1() {
		return p1;
	}

	void setP1(LinkedList<Integer> p1) {
		this.p1 = p1;
	}

	LinkedList<Integer> getP2() {
		return p2;
	}

	void setP2(LinkedList<Integer> p2) {
		this.p2 = p2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
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
		Round other = (Round) obj;
		if (p1 == null) {
			if (other.p1 != null)
				return false;
		} else if (!p1.equals(other.p1))
			return false;
		if (p2 == null) {
			if (other.p2 != null)
				return false;
		} else if (!p2.equals(other.p2))
			return false;
		return true;
	}

}