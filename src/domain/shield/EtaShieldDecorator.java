package domain.shield;

import domain.atom.Atom;
import domain.game.Location;

public class EtaShieldDecorator extends AtomShieldDecorator {

	Atom atom;

	public EtaShieldDecorator(Atom atom) {
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
		return "Eta " + atom.toString();
	}

	public int getProtonNum() {
		return atom.getProtonNum();
	}

	public int getNeutronNum() {
		return atom.getNeutronNum();
	}

	// main decorator methods

	public double getEfficiency() throws ArithmeticException {
		/*
		 * REQUIREMENTS: atom.getProtonNum() > 0 and atom.getNeutronNum() > 0
		 * 
		 * EFFECTS: calculates and returns the sum of the efficiency of its atom and
		 * improvement (efficiency + improvement) where improvement is: if (proton < 0
		 * || neutron < 0) : returns 0; else if (neutron != proton) and proton!=0 : (1 -
		 * efficiency) * |neutron - proton| / proton else if (neutron != proton) and
		 * proton==0 : throws ArithmeticException else : (1 - efficiency) * 0.05
		 * 
		 */

		double improvement = 0;
		int proton = atom.getProtonNum();
		int neutron = atom.getNeutronNum();
		double efficiency = atom.getEfficiency();

		// if requirements are not matched
		if (proton < 0 || neutron < 0)
			return 0;

		// score effect is decorated
		if (neutron != proton) {
			improvement = (1 - efficiency) * Math.abs(neutron - proton) / proton;
			if (proton == 0)
				throw new ArithmeticException(); // divided by zero
		} else
			improvement = (1 - efficiency) * ETA_EFFICIENCY_BOOST;

		return efficiency + improvement;
	}

	public double getSpeed() {
		return atom.getSpeed() * 0.95;
	}

	private static final double ETA_EFFICIENCY_BOOST = 0.05;

}
