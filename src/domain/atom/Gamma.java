package domain.atom;

import domain.game.Location;

public class Gamma extends Atom {

	public Gamma(Location loc) {

		this.type = "gamma";
		this.diameter = 0;
		this.loc = loc;

		this.setProtonNum(GAMMA_PROTON);

		int neutron_num = (int) (3 * Math.random()) + 31;
		if (neutron_num == 31)
			neutron_num = 29;
		this.setNeutronNum(neutron_num);

		double efficiency = GAMMA_STABILITY_CONSTANT + (Math.abs(neutron_num - GAMMA_PROTON) / (2 * GAMMA_PROTON));
		this.setEfficiency(efficiency);

	}

	private static final int GAMMA_PROTON = 32;
	private static final double GAMMA_STABILITY_CONSTANT = 0.8;

}
