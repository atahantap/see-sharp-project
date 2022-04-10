package domain.molecule;

import domain.game.Location;
import domain.strategy.StraightFallStrategy;
import domain.strategy.ZigZagFallStrategy;

public class BetaMolecule extends Molecule {

	boolean isLinear;
	boolean isRotating;

	public BetaMolecule(Location loc) {
		super(loc);
		this.setType("beta");
		isLinear = false;
		isRotating = false;

		this.fallPattern = new ZigZagFallStrategy();
	}

	public void updateFallPattern() {
		if (this.loc.y >= 192)
			this.fallPattern = new StraightFallStrategy();
	}

	@Override
	public void setStructure(String structure) {
		this.structure = structure;
		if (structure.equals("linear")) {
			this.isLinear = true;
		} else {
			this.isLinear = false;
		}
	}

	@Override
	public void setRotation(String rotation) {
		this.rotation = rotation;
		if (rotation.equals("rotating")) {
			this.isRotating = true;
		} else {
			this.isRotating = false;
		}
	}

	public boolean isLinear() {
		return isLinear;
	}

	public boolean isRotating() {
		return isRotating;
	}

}
