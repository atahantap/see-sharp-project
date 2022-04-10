package domain.powerup;

import java.util.ArrayList;

import domain.game.DomainObject;
import domain.game.Game;
import domain.game.Location;
import domain.game.Shootable;

public abstract class Powerup extends DomainObject implements Shootable {

	protected String type;

	private double speed = Game.UNITLENGTH_L / 20;

	public Powerup(Location loc) {
		this.loc = loc;
		this.dy = Game.UNITLENGTH_L / 26;
	}

	// from interface
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	public void setType(String type) {
		// TODO Auto-generated method stub
		this.type = type;
	}

	@Override
	public void setLocation(Location loc) {
		this.loc = loc;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return this.speed;
	}

	@Override
	public void setSpeedCoordinates(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public String toString() {
		String firstLetterUppercaseType = Character.toUpperCase(this.getType().charAt(0)) + this.getType().substring(1);
		return String.format("%s Powerup:        %s%n", firstLetterUppercaseType, this.getLocation().toString());
	}

	/*
	 * * EFFECTS: Turns the given PowerUp object in the array of string representing
	 * the properties of the given domain object. Output is in the order type, name,
	 * location x, location y , speed x, speed y
	 * 
	 */
	public ArrayList<String> makeList() {
		ArrayList<String> powerupList = new ArrayList<String>();
		powerupList.add("Powerup");
		powerupList.add(this.getType());
		powerupList.add(String.valueOf(this.loc.x));
		powerupList.add(String.valueOf(this.loc.y));
		powerupList.add(String.valueOf(this.getDx()));
		powerupList.add(String.valueOf(this.getDy()));
		return powerupList;
	}
}