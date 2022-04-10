package domain.reactionBlockers;

import domain.game.Location;

public class GammaReactionBlocker extends ReactionBlocker {

	public GammaReactionBlocker(Location loc) {
		super(loc);
		this.setType("gamma");
	}

}
