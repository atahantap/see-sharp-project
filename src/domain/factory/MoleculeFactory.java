package domain.factory;

import java.util.Random;

import domain.game.Location;
import domain.molecule.AlphaMolecule;
import domain.molecule.BetaMolecule;
import domain.molecule.GammaMolecule;
import domain.molecule.Molecule;
import domain.molecule.SigmaMolecule;

public class MoleculeFactory {
	private static MoleculeFactory instance;
	static Random r;

	private MoleculeFactory() {
	}

	public static MoleculeFactory getInstance() {
		if (instance == null) {
			instance = new MoleculeFactory();
			r = new Random();
		}
		return instance;
	}

	public Molecule getMoleculeWithRandomX(String moleculeType, int xUpperLimit, int y) {
		Location randomLocation = new Location(r.nextInt(xUpperLimit), y);
		return getMolecule(moleculeType, randomLocation);
	}

	public Molecule getMolecule(String moleculeType, Location location) {
		Molecule molecule;
		switch (moleculeType) {
		case "alpha-1":
			molecule = new AlphaMolecule(location);
			molecule.setStructure("non-linear");
			break;
		case "alpha-2":
			molecule = new AlphaMolecule(location);
			molecule.setStructure("linear");
			break;
		case "beta-1":
			molecule = new BetaMolecule(location);
			molecule.setStructure("non-linear");
			break;
		case "beta-2":
			molecule = new BetaMolecule(location);
			molecule.setStructure("linear");
			break;
		case "gamma":
			molecule = new GammaMolecule(location);
			break;
		case "sigma":
			molecule = new SigmaMolecule(location);
			break;
		default:
			molecule = new AlphaMolecule(location);
			break;
		}
		return molecule;
	}
}
