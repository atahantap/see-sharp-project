package domain.reactionBlockers;

import java.util.ArrayList;

import domain.game.DomainObject;
import domain.game.Game;
import domain.game.Location;

public abstract class ReactionBlocker extends DomainObject {

	private String type;

//	private int speed;
//	private int explosionField;
//	
	public ReactionBlocker(Location loc) {
		this.loc = loc;
		this.dy = Game.UNITLENGTH_L / 26;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	public void setType(String type) {
		// TODO Auto-generated method stub
		this.type = type;
	}

	public ArrayList<String> makeList() {
		ArrayList<String> reactionBlockerList = new ArrayList<String>();
		reactionBlockerList.add("ReactionBlocker");
		reactionBlockerList.add(this.getType());
		reactionBlockerList.add(String.valueOf(this.loc.x));
		reactionBlockerList.add(String.valueOf(this.loc.y));
		reactionBlockerList.add(String.valueOf(this.getDx()));
		reactionBlockerList.add(String.valueOf(this.getDy()));
		return reactionBlockerList;
	}

}