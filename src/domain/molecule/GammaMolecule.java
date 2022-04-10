package domain.molecule;

import domain.game.Location;
import domain.strategy.StraightFallStrategy;
import domain.strategy.ZigZagFallStrategy;

public class GammaMolecule extends Molecule {
	public GammaMolecule(Location loc) {
		super(loc);
		this.setType("gamma");

		this.fallPattern = new ZigZagFallStrategy();
	}

	public void updateFallPattern() {
		if (this.loc.y >= 384)
			this.fallPattern = new StraightFallStrategy();
	}

}
