package domain.molecule;

import domain.game.Location;
import domain.strategy.ZigZagFallStrategy;

public class AlphaMolecule extends Molecule {

	boolean isLinear;
	private boolean isRotating;

	public AlphaMolecule(Location loc) {
		super(loc);
		this.setType("alpha");
		isLinear = false;

		this.fallPattern = new ZigZagFallStrategy();
	}

	public void updateFallPattern() {
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

	@Override
	public boolean isRotating() {
		return isRotating;
	}

}
