package domain.factory;

import java.util.Random;

import domain.game.Location;
import domain.reactionBlockers.AlphaReactionBlocker;
import domain.reactionBlockers.BetaReactionBlocker;
import domain.reactionBlockers.GammaReactionBlocker;
import domain.reactionBlockers.ReactionBlocker;
import domain.reactionBlockers.SigmaReactionBlocker;

public class ReactionBlockerFactory {
	private static ReactionBlockerFactory instance;
	static Random r;

	private ReactionBlockerFactory() {
	}

	public static ReactionBlockerFactory getInstance() {
		if (instance == null) {
			instance = new ReactionBlockerFactory();
			r = new Random();

		}
		return instance;
	}

	public ReactionBlocker getReactionBlockerWithRandomX(String reactionBlockerType, int xUpperLimit, int y) {
		Location randomLocation = new Location(r.nextInt(xUpperLimit), y);
		return getReactionBlocker(reactionBlockerType, randomLocation);
	}

	// NOT IMPLEMENTED YET
	public ReactionBlocker getReactionBlocker(String reactionBlockerType, Location location) {
		ReactionBlocker reactionBlocker;
		switch (reactionBlockerType) {
		case "alpha":
			reactionBlocker = new AlphaReactionBlocker(location);
			break;
		case "beta":
			reactionBlocker = new BetaReactionBlocker(location);
			break;
		case "gamma":
			reactionBlocker = new GammaReactionBlocker(location);
			break;
		case "sigma":
			reactionBlocker = new SigmaReactionBlocker(location);
			break;
		default:
			reactionBlocker = new AlphaReactionBlocker(location);
			break;
		}
		return reactionBlocker;
	}

}
