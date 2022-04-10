package domain.shield;

import domain.atom.Atom;
import domain.game.Location;

public class ZotaShieldDecorator extends AtomShieldDecorator {

	Atom atom;

	public ZotaShieldDecorator(Atom atom) {
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
		return "Zota" + atom.toString();
	}

	// main decorator methods
	public double getEfficiency() {

		double efficiency = atom.getEfficiency();
		int proton = atom.getProtonNum();
		int neutron = atom.getNeutronNum();

		if (proton != neutron)
			return efficiency;

		double improvement = (1 - efficiency) * ZETA_EFFICIENCY_BOOST;
		System.out.println("zota efficiency called");

		return efficiency + improvement;
	}

	public double getSpeed() {
		return atom.getSpeed() * 0.89;
	}

	private static final double ZETA_EFFICIENCY_BOOST = 0.2;

}
