package nl.michielgraat.adventofcode2020.day17;

public class Point3d {
	boolean active;
	int x;
	int y;
	int z;

	Point3d(int x, int y, int z, boolean active) {
		super();
		this.active = active;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	Point3d(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.active = false;
	}

	boolean isActive() {
		return active;
	}

	void setActive(boolean active) {
		this.active = active;
	}

	int getX() {
		return x;
	}

	void setX(int x) {
		this.x = x;
	}

	int getY() {
		return y;
	}

	void setY(int y) {
		this.y = y;
	}

	int getZ() {
		return z;
	}

	void setZ(int z) {
		this.z = z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		Point3d other = (Point3d) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ") (active=" + active + ")";
	}
}
