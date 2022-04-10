package domain.atom;

import domain.game.Location;

public class Sigma extends Atom {

	public Sigma(Location loc) {
		this.type = "sigma";
		this.diameter = 0;
		this.loc = loc;

		this.setProtonNum(SIGMA_PROTON);

		int neutron_num = (int) (3 * Math.random()) + 63;
		if (neutron_num == 65)
			neutron_num = 67;
		this.setNeutronNum(neutron_num);

		double efficiency = (1 + SIGMA_STABILITY_CONSTANT) / 2 + (Math.abs(neutron_num - SIGMA_PROTON) / SIGMA_PROTON);
		this.setEfficiency(efficiency);

	}

	private static final int SIGMA_PROTON = 64;
	private static final double SIGMA_STABILITY_CONSTANT = 0.7;

}
