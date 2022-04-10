package domain.reactionBlockers;

import domain.game.Location;

public class SigmaReactionBlocker extends ReactionBlocker {

	public SigmaReactionBlocker(Location loc) {
		super(loc);
		this.setType("sigma");
	}

}
