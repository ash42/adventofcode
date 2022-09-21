package nl.michielgraat.adventofcode2018.day13;

public class Cart implements Comparable<Cart> {
    int id;
    int x;
    int y;
    Direction direction;
    int intersectionCntr = 0;
    boolean toRemove = false;

    public Cart(int id, int x, int y, Direction direction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }

    public void move(char[][] grid) {
        if (direction == Direction.LEFT) {
            x--;
        } else if (direction == Direction.RIGHT) {
            x++;
        } else if (direction == Direction.UP) {
            y--;
        } else if (direction == Direction.DOWN) {
            y++;
        }
        char c = grid[x][y];
        if (c == '\\' || c == '/' || c == '+') {
            setNewDirection(c);
        }
    }

    public void setNewDirection(char c) {
        if (c == '\\') {
            handleLeftTurn(c);
        } else if (c == '/') {
            handleRightTurn(c);
        } else if (c == '+') {
            handleIntersection();
        }
    }

    private void handleIntersection() {
        intersectionCntr++;
        if (intersectionCntr > 3) {
            intersectionCntr = 1;
        }
        if (intersectionCntr == 1) {
            if (direction == Direction.UP)
                direction = Direction.LEFT;
            else if (direction == Direction.DOWN)
                direction = Direction.RIGHT;
            else if (direction == Direction.LEFT)
                direction = Direction.DOWN;
            else
                direction = Direction.UP;
        } else if (intersectionCntr == 3) {
            if (direction == Direction.UP)
                direction = Direction.RIGHT;
            else if (direction == Direction.DOWN)
                direction = Direction.LEFT;
            else if (direction == Direction.LEFT)
                direction = Direction.UP;
            else
                direction = Direction.DOWN;
        }
    }

    private void handleRightTurn(char c) {
        if (direction == Direction.UP)
            direction = Direction.RIGHT;
        else if (direction == Direction.DOWN)
            direction = Direction.LEFT;
        else if (direction == Direction.RIGHT)
            direction = Direction.UP;
        else
            direction = Direction.DOWN;
    }

    private void handleLeftTurn(char c) {
        if (direction == Direction.UP)
            direction = Direction.LEFT;
        else if (direction == Direction.DOWN)
            direction = Direction.RIGHT;
        else if (direction == Direction.LEFT)
            direction = Direction.UP;
        else
            direction = Direction.DOWN;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        Cart other = (Cart) obj;
        if (id != other.id)
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public int compareTo(Cart o) {
        if (this.x != o.x) {
            return Integer.compare(this.x, o.x);
        } else {
            return Integer.compare(this.y, o.y);
        }
    }

    @Override
    public String toString() {
        return "Cart [id=" + id + ", direction=" + direction + ", x=" + x + ", y=" + y + "]";
    }
}
