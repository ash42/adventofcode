package nl.michielgraat.adventofcode2019;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Day10 {

	private List<Asteroid> readField() {
		List<Asteroid> asteroids = new ArrayList<Asteroid>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("asteroidfield.txt"));
			int i = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] chars = line.split("(?!^)");
				for (int j = 0; j < chars.length; j++) {
					if (chars[j].equals("#")) {
						asteroids.add(new Asteroid(j, i));
					}
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return asteroids;
	}

	private int findMost(List<Asteroid> asteroids) {
		for (Asteroid asteroidInCentre : asteroids) {
			// System.out.println("Handling asteroid at (" + asteroidInCentre.getX() + "," +
			// asteroidInCentre.getY() + ")");
			List<Double> angles = new ArrayList<Double>();
			List<Double> distances = new ArrayList<Double>();
			for (Asteroid asteroid : asteroids) {
				if (!asteroidInCentre.equals(asteroid)) {
					// System.out.print("Angle and distance to (" + asteroid.getX() + "," +
					// asteroid.getY() + "): ");
					double angle = getAngle(asteroidInCentre, asteroid);
					double distance = getDistance(asteroidInCentre, asteroid);
					// System.out.println(angle + ", " + distance);
					if (!angles.contains(angle)) {
						// System.out.println("Angle not found earlier");
						angles.add(angle);
						distances.add(distance);
					} else {
						double oldDistance = distances.get(angles.indexOf(angle));
						// System.out.println("Angle found earlier, distance: " + oldDistance + ", new:
						// " + distance);
						if (distance < oldDistance) {
							distances.set(angles.indexOf(angle), distance);
						}
					}
				}
			}
			asteroidInCentre.setNumberOfAsteroidsInView(angles.size());
		}

		int indexOfMost = -1;
		int most = -1;
		int i = 0;
		for (Asteroid asteroid : asteroids) {
			// System.out.println("Asteroid at (" + asteroid.getX() + "," + asteroid.getY()
			// + ") sees: "
			// + asteroid.getNumberOfAsteroidsInView());
			if (asteroid.getNumberOfAsteroidsInView() > most) {
				indexOfMost = i;
				most = asteroid.getNumberOfAsteroidsInView();
			}
			i++;
		}
		Asteroid asteroid = asteroids.get(indexOfMost);
		System.out.println("Asteroid at (" + asteroid.getX() + "," + asteroid.getY() + ") sees most asteroids: "
				+ asteroid.getNumberOfAsteroidsInView());
		return indexOfMost;
	}

	private double getAngle(Asteroid a, Asteroid b) {
		double angle = Math.toDegrees(Math.atan2(a.getY() - b.getY(), a.getX() - b.getX()));
		if (angle < 0.0) {
			angle += 360;
		}
		return angle;
	}
	
	private double getAngleForPart2(Asteroid a, Asteroid b) {
		double angle = Math.toDegrees(Math.atan2(a.getY() - b.getY(), a.getX() - b.getX()));
		angle -= 90;
		if (angle < 0.0) {
			angle += 360;
		}
		return angle;
	}

	private double getDistance(Asteroid a, Asteroid b) {
		return Math.hypot(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
	}

	private int find200(Asteroid base, List<Asteroid> asteroids) {
		Set<AngleDistance> angleDistances = new TreeSet<AngleDistance>();
		for (Asteroid asteroid : asteroids) {
			if (!base.equals(asteroid)) {
				double angle = getAngleForPart2(base, asteroid);
				double distance = getDistance(base, asteroid);
				AngleDistance ad = new AngleDistance(angle, distance, asteroid.getX(), asteroid.getY());
				// System.out.println("Adding: (" + ad.getX() + "," + ad.getY() + "), angle: " +
				// ad.getAngle() + ", distance: "
				// + ad.getDistance());
				angleDistances.add(ad);
				// System.out.println("Total: " + angleDistances.size());
			}
		}
		List<AngleDistance> adList = new ArrayList<AngleDistance>(angleDistances);
		for (int j=0; j < adList.size(); j++ ) {
			AngleDistance a = adList.get(j);
			System.out.println(j + ": (" + a.getX() + "," + a.getY() + "), angle: " + a.getAngle() + ", distance: "
					+ a.getDistance());
		}
		
		int i = 1;
		int listIndex = 0;
		while (true) {
			AngleDistance a = adList.get(listIndex);
			if (a.isDestroyed()) {
				listIndex++;
				if (listIndex >= adList.size()) {
					listIndex = 0;
				}
			} else {
				a.setDestroyed(true);
				System.out.println("Destroyed " + i + " asteroid at (" + a.getX() + "," + a.getY() + "), angle: "
						+ a.getAngle() + ", distance: " + a.getDistance());
				i++;
				if (i > 200) {
					return a.getX() * 100 + a.getY();
				}
				double angle = a.getAngle();
				listIndex++;
				if (listIndex >= adList.size()) {
					listIndex = 0;
				}
				while (adList.get(listIndex).getAngle() == angle) {
					listIndex++;
					if (listIndex >= adList.size()) {
						listIndex = 0;
					}
				}
			}
		}

//		AngleDistance[] ads = new AngleDistance[angleDistances.size()];
//		angleDistances.toArray(ads);
//		AngleDistance twohundred = ads[200];
//
//		for (int j = 0; j < ads.length; j++) {
//			AngleDistance a = ads[j];
//			System.out.println(j + ": (" + a.getX() + "," + a.getY() + "), angle: " + a.getAngle() + ", distance: "
//					+ a.getDistance());
//		}
//
//		return twohundred.getX() * 100 + twohundred.getY();
	}

	public void run1() {
		List<Asteroid> asteroids = readField();
		findMost(asteroids);
	}

	public void run2() {
		List<Asteroid> asteroids = readField();
		int baseIndex = findMost(asteroids);
		System.out.println("Output for 200: " + find200(asteroids.get(baseIndex), asteroids));
	}

	public static void main(String[] args) {
		new Day10().run1();
		new Day10().run2();
	}

	class Asteroid {
		final int x;
		final int y;
		int numberOfAsteroidsInView = 0;

		public Asteroid(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getNumberOfAsteroidsInView() {
			return numberOfAsteroidsInView;
		}

		public void setNumberOfAsteroidsInView(int numberOfAsteroidsInView) {
			this.numberOfAsteroidsInView = numberOfAsteroidsInView;
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
			Asteroid other = (Asteroid) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private Day10 getEnclosingInstance() {
			return Day10.this;
		}
	}

	class AngleDistance implements Comparable<AngleDistance> {
		double angle;
		double distance;
		int x;
		int y;
		boolean destroyed = false;

		public AngleDistance(double angle, double distance, int x, int y) {
			this.angle = angle;
			this.distance = distance;
			this.x = x;
			this.y = y;
		}

		public double getAngle() {
			return angle;
		}

		public double getDistance() {
			return distance;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void setDestroyed(boolean destroyed) {
			this.destroyed = destroyed;
		}

		public boolean isDestroyed() {
			return destroyed;
		}

		@Override
		public int compareTo(AngleDistance o) {

			if (this.angle != o.getAngle()) {
				Double a1 = Double.valueOf(this.angle);
				Double a2 = Double.valueOf(o.getAngle());
				return a1.compareTo(a2);
			} else {

			}
			Double a1 = Double.valueOf(this.distance);
			Double a2 = Double.valueOf(o.getDistance());
			return a1.compareTo(a2);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			long temp;
			temp = Double.doubleToLongBits(angle);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(distance);
			result = prime * result + (int) (temp ^ (temp >>> 32));
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
			AngleDistance other = (AngleDistance) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (Double.doubleToLongBits(angle) != Double.doubleToLongBits(other.angle))
				return false;
			if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private Day10 getEnclosingInstance() {
			return Day10.this;
		}

	}
}
