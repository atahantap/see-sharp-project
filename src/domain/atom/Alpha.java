package domain.atom;

import domain.game.Location;

public class Alpha extends Atom {

	public Alpha(Location loc) {
		this.type = "alpha";
		this.diameter = 0;
		this.loc = loc;

		this.setProtonNum(ALPHA_PROTON);

		int neutron_num = (int) (3 * Math.random()) + 7;
		this.setNeutronNum(neutron_num);

		double efficiency = (1 - (Math.abs(neutron_num - ALPHA_PROTON) / ALPHA_PROTON)) * ALPHA_STABILITY_CONSTANT;
		this.setEfficiency(efficiency);
	}

	private static final int ALPHA_PROTON = 8;
	private static final double ALPHA_STABILITY_CONSTANT = 0.85;

}
