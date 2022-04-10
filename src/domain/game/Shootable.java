package domain.game;

public interface Shootable {

	public String getType();

	public double getSpeed();

	public void setSpeedCoordinates(int i, int j);

	public void setLocation(Location location);
}
