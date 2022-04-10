package domain.atom;

import java.util.ArrayList;

import domain.game.DomainObject;
import domain.game.Game;
import domain.game.Location;
import domain.game.Shootable;

public abstract class Atom extends DomainObject implements Shootable {

	protected String type;

	protected int diameter;
	private double speed = Game.UNITLENGTH_L / 20;

	private double efficiency;
	private int proton_num;
	private int neutron_num;

	/**
	 * Default method for the Atoms
	 * 
	 * @return type of the power up as string
	 */
	public String getType() {
		return this.type;
	}

	public Location getLocation() {
		return this.loc;
	}

	public void setLocation(Location loc) {
		this.loc = loc;
	}

	public void updatePosition() {

		loc.y += dy;
		loc.x += dx;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setSpeedCoordinates(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public String toString() {
		String firstLetterUppercaseType = Character.toUpperCase(this.getType().charAt(0)) + this.getType().substring(1);
		return String.format("%s Atom:        %s%n", firstLetterUppercaseType, this.getLocation().toString());
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}

	public int getProtonNum() {
		return proton_num;
	}

	public void setProtonNum(int proton_num) {
		this.proton_num = proton_num;
	}

	public int getNeutronNum() {
		return neutron_num;
	}

	public void setNeutronNum(int neutron_num) {
		this.neutron_num = neutron_num;
	}

	/*
	 * * EFFECTS: Turns the given Atom object in the array of string representing
	 * the properties of the given domain object. Output is in the order type, name,
	 * location x, location y , speed x, speed y
	 * 
	 */
	public ArrayList<String> makeList() {
		ArrayList<String> atomList = new ArrayList<String>();
		atomList.add("Atom");
		atomList.add(this.getType());
		atomList.add(String.valueOf(this.loc.x));
		atomList.add(String.valueOf(this.loc.y));
		atomList.add(String.valueOf(this.getDx()));
		atomList.add(String.valueOf(this.getDy()));
		return atomList;
	}

}