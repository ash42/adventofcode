package nl.michielgraat.adventofcode2020.day17;

public class Point4d {

	boolean active;
	int x;
	int y;
	int z;
	int w;

	Point4d(int x, int y, int z, int w, boolean active) {
		super();
		this.active = active;
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	Point4d(int x, int y, int z, int w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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

	int getW() {
		return w;
	}

	void setW(int w) {
		this.w = w;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + w;
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
		Point4d other = (Point4d) obj;
		if (w != other.w)
			return false;
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
		return "(" + x + "," + y + "," + z + "," + w + ") (active=" + active + ")";
	}

}