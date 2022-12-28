package nl.michielgraat.adventofcode2019;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day12 {

	private List<Moon> moons = new ArrayList<Moon>();

	private void applyGravity() {
		for (Moon moon : moons) {
			for (Moon pairedMoon : moons) {
				if (!moon.equals(pairedMoon)) {
					if (moon.getX() < pairedMoon.getX()) {
						moon.increaseVelX();
					} else if (moon.getX() > pairedMoon.getX()) {
						moon.decreaseVelX();
					}

					if (moon.getY() < pairedMoon.getY()) {
						moon.increaseVelY();
					} else if (moon.getY() > pairedMoon.getY()) {
						moon.decreaseVelY();
					}

					if (moon.getZ() < pairedMoon.getZ()) {
						moon.increaseVelZ();
					} else if (moon.getZ() > pairedMoon.getZ()) {
						moon.decreaseVelZ();
					}
				}
			}
		}
	}

	private void applyVelocity() {
		for (Moon moon : moons) {
			moon.applyVelocity();
		}
	}

	private void readMoons() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("moons.txt"));
			int i = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] chars = line.split(",");
				int x = Integer.valueOf(chars[0].split("=")[1]);
				int y = Integer.valueOf(chars[1].split("=")[1]);
				String zString = chars[2].split("=")[1];
				int z = Integer.valueOf(zString.substring(0, zString.length() - 1));
				Moon moon = new Moon(i, x, y, z);
				moons.add(moon);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	class Moon {
		int index;
		int x;
		int y;
		int z;
		int velX;
		int velY;
		int velZ;

		public Moon(int index, int x, int y, int z) {
			super();
			this.index = index;
			this.x = x;
			this.y = y;
			this.z = z;
			this.velX = 0;
			this.velY = 0;
			this.velY = 0;
		}

		public String toString() {
			return "pos=<x=" + x + ", y=" + y + ", z=" + z + ">, vel=<x=" + velX + ", y=" + velY + ", z=" + velZ + ">";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + index;
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
			Moon other = (Moon) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (index != other.index)
				return false;
			return true;
		}

		private Day12 getEnclosingInstance() {
			return Day12.this;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getZ() {
			return z;
		}

		public void setZ(int z) {
			this.z = z;
		}

		public int getVelX() {
			return velX;
		}

		public void setVelX(int velX) {
			this.velX = velX;
		}

		public int getVelY() {
			return velY;
		}

		public void setVelY(int velY) {
			this.velY = velY;
		}

		public int getVelZ() {
			return velZ;
		}

		public void setVelZ(int velZ) {
			this.velZ = velZ;
		}

		public void increaseVelX() {
			this.velX++;
		}

		public void increaseVelY() {
			this.velY++;
		}

		public void increaseVelZ() {
			this.velZ++;
		}

		public void decreaseVelX() {
			this.velX--;
		}

		public void decreaseVelY() {
			this.velY--;
		}

		public void decreaseVelZ() {
			this.velZ--;
		}

		public void applyVelX() {
			this.x += this.velX;
		}

		public void applyVelY() {
			this.y += this.velY;
		}

		public void applyVelZ() {
			this.z += this.velZ;
		}
		
		public int getIndex() {
			return index;
		}

		public void applyVelocity() {
			applyVelX();
			applyVelY();
			applyVelZ();
		}

		public int getPotentialEnergy() {
			return Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z);
		}

		public int getKineticEnery() {
			return Math.abs(this.velX) + Math.abs(this.velY) + Math.abs(this.velZ);
		}

		public int getTotalEnery() {
			return getPotentialEnergy() * getKineticEnery();
		}

		public String getTotalEnergyAsString() {
			return "pot: " + x + " + " + y + " + " + z + " = " + getPotentialEnergy() + "; kin: " + velX + " + " + velY
					+ " + " + velZ + " = " + getKineticEnery() + "; total: " + getPotentialEnergy() + " * "
					+ getKineticEnery() + " = " + getTotalEnery();
		}
	}

	public void run1() {
		readMoons();
		for (int i = 1; i<=1000; i++) {
			applyGravity();
			applyVelocity();
			if (i % 1000 == 0) {
				System.out.println("After " + i + " steps:");
				int totalEnergyInSystem = 0;
				for (Moon moon : moons) {
					//System.out.println(moon);
					//System.out.println(moon.getTotalEnergyAsString());
					totalEnergyInSystem += moon.getTotalEnery();
				}
				System.out.println("Total energy in system: " + totalEnergyInSystem);
			}
		}
	}
	
	private void move(List<Coordinate> cs) {
		for (Coordinate c : cs) {
			for (Coordinate c2 : cs) {
				if (!c.same(c2)) {
					c.applyGravity(c2);
					//if (c.getPos() < c2.getPos()) {
					//	c.increaseVelocity();
					//}
				}
			}
		}
		for (Coordinate c:cs) {
			c.applyVelocity();
		}
	}
	
	private boolean same (List<Coordinate> c1, List<Coordinate> c2) {
		for (int i = 0; i<c1.size(); i++) {
			if (!c1.get(i).same(c2.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	private long cycle (List<Coordinate> cs) {
		List<Coordinate> orig = clone(cs);
		long i = 0;
		while (true) {
			move(cs);
			i++;
			if (same(cs, orig)) {
				return i;
			}
		}
	}
	
	private List<Coordinate> clone(List<Coordinate> cs) {
		return cs.stream().map(Coordinate::new).collect(Collectors.toList());
	}
	
	public void run2() {
		readMoons();
		List<Coordinate> x = new ArrayList<Coordinate>();
		List<Coordinate> y = new ArrayList<Coordinate>();
		List<Coordinate> z = new ArrayList<Coordinate>();
		
		for (Moon moon : moons) {
			Coordinate cx = new Coordinate(moon.getX(), moon.getVelX());
			Coordinate cy = new Coordinate(moon.getY(), moon.getVelY());
			Coordinate cz = new Coordinate(moon.getZ(), moon.getVelZ());
			x.add(cx);
			y.add(cy);
			z.add(cz);
		}
		
		long cycleX = cycle(x);
		System.out.println("X cycles after: " + cycleX);
		long cycleY = cycle(y);
		System.out.println("Y cycles after: " + cycleY);
		long cycleZ = cycle(z);
		System.out.println("Z cycles after: " + cycleZ);
				
		System.out.println("System cycles after: " + lcm(lcm(cycleX, cycleY), cycleZ));
	}
	
	private long gcd(long number1, long number2) {
	    if (number1 == 0 || number2 == 0) {
	        return number1 + number2;
	    } else {
	    	long absNumber1 = Math.abs(number1);
	    	long absNumber2 = Math.abs(number2);
	    	long biggerValue = Math.max(absNumber1, absNumber2);
	        long smallerValue = Math.min(absNumber1, absNumber2);
	        return gcd(biggerValue % smallerValue, smallerValue);
	    }
	}
	
	private long lcm(long number1, long number2) {
	    if (number1 == 0 || number2 == 0)
	        return 0;
	    else {
	        long gcd = gcd(number1, number2);
	        return Math.abs(number1 * number2) / gcd;
	    }
	}

	class Coordinate {
		int pos;
		int vel;
		
		public Coordinate (int pos, int vel) {
			this.pos = pos;
			this.vel = vel;
		}
		
		public Coordinate (Coordinate c) {
			this.pos = c.pos;
			this.vel = c.vel;
		}
		
		void applyGravity(Coordinate c) {
			if (pos < c.getPos()) {
				vel++;
			} else if (pos > c.getPos()) {
				vel--;
			}
		}
		
		void applyVelocity() {
			pos += vel;
		}
		
		void increaseVelocity() {
			vel++;
		}
		
		public int getPos() {
			return pos;
		}
		
		public int getVel() {
			return vel;
		}
		
		boolean same(Coordinate c) {
			return c.pos == pos && c.vel == vel;
		}
	}
	
	public static void main(String... args) {
		new Day12().run1();
		new Day12().run2();
		
		
	}
}
