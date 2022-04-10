package domain.molecule;

import java.util.ArrayList;

import domain.game.DomainObject;
import domain.game.Game;
import domain.game.Location;
import domain.strategy.FallStrategy;

public abstract class Molecule extends DomainObject {

	protected String type;
	protected String structure;
	protected String rotation;

	protected FallStrategy fallPattern;

	public Molecule(Location loc) {
		this.loc = loc;
		this.dy = Game.UNITLENGTH_L / 26;
	}

	public Molecule() {
		this.dy = Game.UNITLENGTH_L / 26;
	}

	// Strategy pattern is used for fall
	@Override
	public void updatePosition() {
		updateFallPattern();
		int speeds[] = this.fallPattern.fall();
		this.loc.x += speeds[0];
		this.loc.y += speeds[1];
	}

	protected abstract void updateFallPattern();

	public boolean isLinear() {
		return false;
	}

	public boolean isRotating() {
		return false;
	}

	public Location getLocation() {
		return loc;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getType() {
		return type;
	}

	protected void setType(String type) {
		this.type = type;
	}

	public String getRotation() {
		return rotation;
	}

	public void setRotation(String rotation) {
		// TODO Auto-generated method stub
		this.rotation = rotation;
	}

	@Override
	public String toString() {
		String firstLetterUppercaseType = Character.toUpperCase(this.getType().charAt(0)) + this.getType().substring(1);
		return String.format("%s Molecule:        %s%n", firstLetterUppercaseType, this.getLocation().toString());
	}

	/*
	 * * EFFECTS: Turns the given Molecule object in the array of string
	 * representing the properties of the given domain object. Output is in the
	 * order type, name, location x, location y , speed x, speed y
	 * 
	 */

	public ArrayList<String> makeList() {
		ArrayList<String> moleculeList = new ArrayList<String>();
		moleculeList.add("Molecule");

		if (this.structure == "linear") {
			moleculeList.add(this.getType() + "-2");
		} else if (this.structure != null) {
			moleculeList.add(this.getType() + "-1");
		} else {
			moleculeList.add(this.getType());
		}
		//
		moleculeList.add(String.valueOf(this.loc.x));
		moleculeList.add(String.valueOf(this.loc.y));
		moleculeList.add(String.valueOf(this.getDx()));
		moleculeList.add(String.valueOf(this.getDy()));
		return moleculeList;
	}

}
