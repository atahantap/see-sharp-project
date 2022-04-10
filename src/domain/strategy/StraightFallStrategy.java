package domain.strategy;

import domain.game.Game;

public class StraightFallStrategy implements FallStrategy {

	@Override
	public int[] fall() {
		// TODO Auto-generated method stub
		int speeds[] = { 0, Game.UNITLENGTH_L / 26 };
		return speeds;
	}

}
