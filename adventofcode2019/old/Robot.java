package nl.michielgraat.adventofcode2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Robot {
	
	private static final boolean DEBUG = false;
	
	private static final int BLACK = 0;
	private static final int WHITE = 1;
	
	private static final int TURN_LEFT = 0;
	private static final int TURN_RIGHT = 1;
	
	private final IntcodeComputer computer;
	
	private Direction direction = Direction.UP;
	
	private Map<GridPoint, Color> pointToColor = new HashMap<GridPoint, Color>();
	
	private int gridIndex = 0;
	
	private GridPoint currentPosition;

	public Robot (long[] program, boolean startWithBlack) {
		computer = new IntcodeComputer(program,10000);
		computer.setPrintOutput(false);
		currentPosition = new GridPoint(0,0);
		if (startWithBlack) {
			pointToColor.put(currentPosition, Color.BLACK);
		} else {
			pointToColor.put(currentPosition, Color.WHITE);
		}
	}
	
	private void setNewDirection(int directionToTurn) {
		if (direction == Direction.UP) {
			direction = directionToTurn == TURN_LEFT ? Direction.LEFT : Direction.RIGHT;
		} else if (direction == Direction.LEFT) {
			direction = directionToTurn == TURN_LEFT ? Direction.DOWN : Direction.UP;
		} else if (direction == Direction.DOWN) {
			direction = directionToTurn == TURN_LEFT ? Direction.RIGHT : Direction.LEFT;
		} else if (direction == Direction.RIGHT) {
			direction = directionToTurn == TURN_LEFT ? Direction.UP : Direction.DOWN;
		}
	}
	
	private void move() {
		switch (direction) {
		case UP:
			currentPosition = new GridPoint(currentPosition.getX(), currentPosition.getY()+1);
			break;
		case DOWN:
			currentPosition = new GridPoint(currentPosition.getX(), currentPosition.getY()-1);
			break;
		case LEFT:
			currentPosition = new GridPoint(currentPosition.getX()-1, currentPosition.getY());
			break;
		default:
			currentPosition = new GridPoint(currentPosition.getX()+1, currentPosition.getY());
			break;
		}
	}
	
	public int run() {
		while (!computer.isHalted()) {
			if (DEBUG) {
				System.out.println("At: " + currentPosition.toString());
			}
			Color currentColor = pointToColor.getOrDefault(currentPosition, Color.BLACK);
			long input = (currentColor == Color.BLACK) ? 0L : 1L;
			computer.addInput(input);
			computer.run();
			long newColor = computer.getOutput();
			long directionToTurn = computer.getOutput();
			
			if (newColor == BLACK) {
				pointToColor.put(currentPosition, Color.BLACK);
			} else {
				pointToColor.put(currentPosition, Color.WHITE);
			}
			
			setNewDirection((int) directionToTurn);
			
			move();
		}
		return pointToColor.keySet().size();
	}
	
	public void printOutput() {
		int minX = pointToColor.keySet().stream().map(GridPoint::getX).min(Integer::compareTo).get();
		int maxX = pointToColor.keySet().stream().map(GridPoint::getX).max(Integer::compareTo).get();
		int minY = pointToColor.keySet().stream().map(GridPoint::getY).min(Integer::compareTo).get();
		int maxY = pointToColor.keySet().stream().map(GridPoint::getY).max(Integer::compareTo).get();
				
		for (int y = maxY; y >= minY; y--) {
			for (int x = minX; x <= maxX; x++) {
				Color color = pointToColor.getOrDefault(new GridPoint(x,y), Color.BLACK);
				System.out.print(color == Color.BLACK ? " " : "#");
			}
			System.out.println();
		}
	}
	
	enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT;
	}
	
	enum Color {
		BLACK,
		WHITE;
	}
	
	class GridPoint {
		int x;
		int y;
		
		public GridPoint (int x, int y) {
			this.x = x;
			this.y = y;
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
			GridPoint other = (GridPoint) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "GridPoint [x=" + x + ", y=" + y + "]";
		}
		
		
	}
}
