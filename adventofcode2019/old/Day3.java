package nl.michielgraat.adventofcode2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day3 {
	
	private List<String> wire1 = new ArrayList<String>(Arrays.asList("R1009","U263", "L517", "U449", "L805", "D78",
			"L798", "D883", "L777", "D562", "R652", "D348", "R999", "D767", "L959", "U493", "R59", "D994", "L225",
			"D226", "R634", "D200", "R953", "U343", "L388", "U158", "R943", "U544", "L809", "D785", "R618", "U499",
			"L476", "U600", "L452", "D693", "L696", "U764", "L927", "D346", "L863", "D458", "L789", "U268", "R586",
			"U884", "L658", "D371", "L910", "U178", "R524", "U169", "R973", "D326", "R483", "U233", "R26", "U807",
			"L246", "D711", "L641", "D75", "R756", "U365", "R203", "D377", "R624", "U430", "L422", "U367", "R547",
			"U294", "L916", "D757", "R509", "D332", "R106", "D401", "L181", "U5", "L443", "U197", "R406", "D829",
			"R878", "U35", "L958", "U31", "L28", "D362", "R188", "D582", "R358", "U750", "R939", "D491", "R929", "D513",
			"L541", "U418", "R861", "D639", "L917", "U582", "R211", "U725", "R711", "D718", "L673", "U921", "L157",
			"U83", "L199", "U501", "L66", "D993", "L599", "D947", "L26", "U237", "L981", "U833", "L121", "U25", "R641",
			"D372", "L757", "D645", "R287", "U390", "R274", "U964", "R288", "D209", "R109", "D364", "R983", "U715",
			"L315", "U758", "R36", "D500", "R626", "U893", "L840", "U716", "L606", "U831", "L969", "D643", "L300",
			"D838", "R31", "D751", "L632", "D702", "R468", "D7", "L169", "U149", "R893", "D33", "R816", "D558", "R152",
			"U489", "L237", "U415", "R434", "D472", "L198", "D874", "L351", "U148", "R761", "U809", "R21", "D25",
			"R586", "D338", "L568", "U20", "L157", "U221", "L26", "U424", "R261", "D227", "L551", "D754", "L90", "U110",
			"L791", "U433", "R840", "U323", "R240", "U124", "L723", "D418", "R938", "D173", "L160", "U293", "R773",
			"U204", "R192", "U958", "L472", "D703", "R556", "D168", "L263", "U574", "L845", "D932", "R165", "D348",
			"R811", "D834", "R960", "U877", "R935", "D141", "R696", "U748", "L316", "U236", "L796", "D566", "R524",
			"U449", "R378", "U480", "L79", "U227", "R867", "D185", "R474", "D757", "R366", "U153", "R882", "U252",
			"R861", "U900", "R28", "U381", "L845", "U642", "L849", "U352", "R134", "D294", "R788", "D406", "L693",
			"D697", "L433", "D872", "R78", "D364", "R240", "U995", "R48", "D681", "R727", "D825", "L583", "U44", "R743",
			"D929", "L616", "D262", "R997", "D15", "R575", "U341", "R595", "U889", "R254", "U76", "R962", "D944",
			"R724", "D261", "R608", "U753", "L389", "D324", "L569", "U308", "L488", "D358", "L695", "D863", "L712",
			"D978", "R149", "D177", "R92"));
	private List<String> wire2 = new ArrayList<String>(Arrays.asList("L1003", "D960", "L10", "D57", "R294", "U538",
			"R867", "D426", "L524", "D441", "R775", "U308", "R577", "D785", "R495", "U847", "R643", "D895", "R448",
			"U685", "L253", "U312", "L312", "U753", "L89", "U276", "R799", "D923", "L33", "U595", "R400", "U111",
			"L664", "D542", "R171", "U709", "L809", "D713", "L483", "U918", "L14", "U854", "L150", "D69", "L158",
			"D500", "L91", "D800", "R431", "D851", "L798", "U515", "L107", "U413", "L94", "U390", "L17", "U221", "L999",
			"D546", "L191", "U472", "L568", "U114", "L913", "D743", "L713", "D215", "L569", "D674", "L869", "U549",
			"L789", "U259", "L330", "D76", "R243", "D592", "L646", "U880", "L363", "U542", "L464", "D955", "L107",
			"U473", "R818", "D786", "R852", "U968", "R526", "D78", "L275", "U891", "R480", "U991", "L981", "D391",
			"R83", "U691", "R689", "D230", "L217", "D458", "R10", "U736", "L317", "D145", "R902", "D428", "R344",
			"U334", "R131", "D739", "R438", "D376", "L652", "U304", "L332", "D452", "R241", "D783", "R82", "D317",
			"R796", "U323", "R287", "D487", "L302", "D110", "R233", "U631", "R584", "U973", "L878", "D834", "L930",
			"U472", "R120", "U78", "R806", "D21", "L521", "U988", "R251", "D817", "R44", "D789", "R204", "D669", "R616",
			"D96", "R624", "D891", "L532", "U154", "R438", "U469", "R785", "D431", "R945", "U649", "R670", "D11",
			"R840", "D521", "L235", "D69", "L551", "D266", "L454", "U807", "L885", "U590", "L647", "U763", "R449",
			"U194", "R68", "U809", "L884", "U962", "L476", "D648", "L139", "U96", "L300", "U351", "L456", "D202",
			"R168", "D698", "R161", "U834", "L273", "U47", "L8", "D157", "L893", "D200", "L454", "U723", "R886", "U92",
			"R474", "U262", "L190", "U110", "L407", "D723", "R786", "D786", "L572", "D915", "L904", "U744", "L820",
			"D663", "R205", "U878", "R186", "U247", "L616", "D386", "R582", "U688", "L349", "D399", "R702", "U132",
			"L276", "U866", "R851", "D633", "R468", "D263", "R678", "D96", "L50", "U946", "R349", "D482", "R487",
			"U525", "R464", "U977", "L499", "D187", "R546", "U708", "L627", "D470", "R673", "D886", "L375", "U616",
			"L503", "U38", "L775", "D8", "L982", "D556", "R159", "U680", "L124", "U777", "L640", "D607", "R248", "D671",
			"L65", "D290", "R445", "U778", "L650", "U679", "L846", "D1", "L769", "U659", "R734", "D962", "R588", "U178",
			"R888", "D753", "R223", "U318", "L695", "D586", "R430", "D61", "R105", "U801", "R953", "U721", "L856",
			"U769", "R937", "D335", "R895"));

	// private List<String> wire1 = new
	// ArrayList<String>(Arrays.asList("R75","D30","R83","U83","L12","D49","R71","U7","L72"));
	// private List<String> wire2 = new
	// ArrayList<String>(Arrays.asList("U62","R66","U55","R34","D71","R55","D58","R83"));

	// private List<String> wire1 = new
	// ArrayList<String>(Arrays.asList("R98","U47","R26","D63","R33","U87","L62","D20","R33","U53","R51"));
	// private List<String> wire2 = new
	// ArrayList<String>(Arrays.asList("U98","R91","D20","R16","D67","R40","U7","R15","U6","R7"));

	private List<Coordinate> getCoordinates(List<String> wire) {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		int x = 0;
		int y = 0;
		int distance = 0;
		coordinates.add(new Coordinate(x, y, distance));
		for (String next : wire) {
			//System.out.println("Processing: " + next);
			if (next.startsWith("U")) {
				int toAdd = Integer.valueOf(next.substring(1));
				for (int i = 0; i < toAdd; i++) {
					distance++;
					y += 1;
					coordinates.add(new Coordinate(x, y, distance));
				}
			} else if (next.startsWith("D")) {
				int toAdd = Integer.valueOf(next.substring(1));
				for (int i = 0; i < toAdd; i++) {
					distance++;
					y -= 1;
					coordinates.add(new Coordinate(x, y, distance));
				}
			} else if (next.startsWith("L")) {
				int toAdd = Integer.valueOf(next.substring(1));
				for (int i = 0; i < toAdd; i++) {
					distance++;
					x -= 1;
					coordinates.add(new Coordinate(x, y, distance));
				}
			} else if (next.startsWith("R")) {
				int toAdd = Integer.valueOf(next.substring(1));
				for (int i = 0; i < toAdd; i++) {
					distance++;
					x += 1;
					coordinates.add(new Coordinate(x, y, distance));
				}
			}
			// String coordinate = new String(x+","+y);
			// System.out.println("Adding " + coordinate);
			// coordinates.add(coordinate);
		}
		System.out.println("WIRE DONE");
		return coordinates;
	}

	private List<Coordinate> getSameCoordinates(List<Coordinate> c1, List<Coordinate> c2) {
		List<Coordinate> same = new ArrayList<Coordinate>();
		System.out.println("List1 contains " + c1.size());
		System.out.println("List2 contains " + c2.size());
		Coordinate origin = new Coordinate(0, 0, 0);
		for (Coordinate c : c1) {
			if (c2.contains(c) && !c.equals(origin)) {
				System.out.println("Found same: " + c);
				same.add(c);
			}
		}
		return same;
	}

	private int getShortestDistance(List<Coordinate> coordinates) {
		int total = -1;
		for (Coordinate coordinate : coordinates) {
			
			int x = Math.abs(coordinate.getX());
			int y = Math.abs(coordinate.getY());
			int sum = x + y;
			if (total == -1) {
				total = sum;
			} else if (sum < total) {
				total = sum;
			}
		}
		return total;
	}
	
	private int getShortestDelay(List<Coordinate> c1, List<Coordinate> c2) {
		System.out.println("List1 contains " + c1.size());
		System.out.println("List2 contains " + c2.size());
		Coordinate origin = new Coordinate(0, 0, 0);
		int shortest = -1;
		for (Coordinate c : c1) {
			if (c2.contains(c) && !c.equals(origin)) {
				System.out.println("Found same: " + c);
				int distance1 = c.getDistance();
				int distance2 = c2.get(c2.indexOf(c)).getDistance();
				int sum = distance1 + distance2;
				System.out.println("C1 distance: " + distance1 + ", C2 distance: " + distance2 +  ", total distance: " + sum);
				if (shortest == -1 || sum < shortest) {
					shortest = sum;
				}
			}
		}
		return shortest;
	}

	public void run1() {
		// List<String> sameCoordinates = getSameCoordinates(getCoordinates(wire1),
		// getCoordinates(wire2));
		// for (String s : sameCoordinates) {
		// System.out.println(s);
		// }
		System.out.println(
				"Shortest: " + getShortestDistance(getSameCoordinates(getCoordinates(wire1), getCoordinates(wire2))));
	}
	
	public void run2() {
		// List<String> sameCoordinates = getSameCoordinates(getCoordinates(wire1),
		// getCoordinates(wire2));
		// for (String s : sameCoordinates) {
		// System.out.println(s);
		// }
		System.out.println(
				"Shortest: " + getShortestDelay(getCoordinates(wire1), getCoordinates(wire2)));
	}

	public static void main(String[] args) {
		new Day3().run2();
	}

	class Coordinate {
		private int x;
		private int y;
		private int distance;
		
		public Coordinate(int x, int y, int distance) {
			this.x = x;
			this.y = y;
			this.distance = distance;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getDistance() {
			return distance;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
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
			Coordinate other = (Coordinate) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Coordinate [x=" + x + ", y=" + y + ", distance=" + distance + "]";
		}
	}
}
