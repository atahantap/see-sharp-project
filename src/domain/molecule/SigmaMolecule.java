package domain.molecule;

import domain.game.Location;
import domain.strategy.StraightFallStrategy;

public class SigmaMolecule extends Molecule {

	public SigmaMolecule(Location loc) {
		super(loc);
		this.setType("sigma");

		this.fallPattern = new StraightFallStrategy();
	}

	public void updateFallPattern() {
	}

}
