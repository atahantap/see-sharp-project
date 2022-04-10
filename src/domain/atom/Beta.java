package domain.atom;

import domain.game.Location;

public class Beta extends Atom {

	public Beta(Location loc) {
		this.type = "beta";
		this.diameter = 0;
		this.loc = loc;

		this.setProtonNum(BETA_PROTON);

		int neutron_num = (int) (5 * Math.random()) + 15;
		if (neutron_num == 19)
			neutron_num = 21;
		this.setNeutronNum(neutron_num);

		double efficiency = BETA_STABILITY_CONSTANT - (0.5 * Math.abs(neutron_num - BETA_PROTON) / BETA_PROTON);
		this.setEfficiency(efficiency);
	}

	private static final int BETA_PROTON = 16;
	private static final double BETA_STABILITY_CONSTANT = 0.9;

}
