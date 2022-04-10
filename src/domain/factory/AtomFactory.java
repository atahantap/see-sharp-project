package domain.factory;

import domain.atom.Alpha;
import domain.atom.Atom;
import domain.atom.Beta;
import domain.atom.Gamma;
import domain.atom.Sigma;
import domain.game.Location;

public class AtomFactory {

	private static AtomFactory instance;

	private AtomFactory() {
	}

	public static AtomFactory getInstance() {
		if (instance == null) {
			instance = new AtomFactory();
		}
		return instance;
	}

	public Atom getAtom(int x, int y, String atomType) {
		Atom atom;
		switch (atomType) {
		case "alpha":
			atom = new Alpha(new Location(x, y));
			break;
		case "beta":
			atom = new Beta(new Location(x, y));
			break;
		case "gamma":
			atom = new Gamma(new Location(x, y));
			break;
		case "sigma":
			atom = new Sigma(new Location(x, y));
			break;
		default:
			atom = new Alpha(new Location(x, y));
			break;
		}

		return atom;
	}

}
