package domain.reactionBlockers;

import domain.game.Location;

public class AlphaReactionBlocker extends ReactionBlocker {

	public AlphaReactionBlocker(Location loc) {
		super(loc);
		this.setType("alpha");
	}

}
