package domain.game;

public class Location {

	public int x;
	public int y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Location(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public Location() {

	}

	@Override
	public String toString() {
		return String.format("\t\t x:%d\ty:%d", this.x, this.y);
	}
}
