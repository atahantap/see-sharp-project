package domain.shield;

import domain.atom.Atom;
import domain.game.Location;

public class LotaShieldDecorator extends AtomShieldDecorator {

	Atom atom;

	public LotaShieldDecorator(Atom atom) {
		this.atom = atom;
	}

	/**
	 * Default method for the Atoms
	 * 
	 * @return type of the power up as string
	 */
	public String getType() {
		return atom.getType();
	}

	public Location getLocation() {
		return atom.getLocation();
	}

	public void setLocation(Location loc) {
		atom.setLocation(loc);
	}

	public void updatePosition() {
		atom.updatePosition();
	}

	public void setSpeed(double speed) {
		atom.setSpeed(speed);
	}

	public void setSpeedCoordinates(int dx, int dy) {
		atom.setSpeedCoordinates(dx, dy);
	}

	@Override
	public String toString() {
		return "Lota " + atom.toString();
	}

	public int getProtonNum() {
		return atom.getProtonNum();
	}

	public int getNeutronNum() {
		return atom.getNeutronNum();
	}

	// main decorator methods
	public double getEfficiency() {

		double efficiency = atom.getEfficiency();
		double improvement = (1 - efficiency) * LOTA_EFFICIENCY_BOOST;
		;
		System.out.println("lota efficiency called");

		return efficiency + improvement;

	}

	public double getSpeed() {
		return atom.getSpeed() * 0.93;
	}

	private static final double LOTA_EFFICIENCY_BOOST = 0.1;

}
