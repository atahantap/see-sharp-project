package domain.reactionBlockers;

import domain.game.Location;

public class BetaReactionBlocker extends ReactionBlocker {

	public BetaReactionBlocker(Location loc) {
		super(loc);
		this.setType("beta");
	}

}
