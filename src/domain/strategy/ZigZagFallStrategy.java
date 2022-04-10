package domain.strategy;

import domain.game.Game;

public class ZigZagFallStrategy implements FallStrategy {

	private int counter;
	private boolean isRight;

	public ZigZagFallStrategy() {
		this.counter = 0;
		this.isRight = true;

	}

	@Override
	public int[] fall() {
		updateCounter();
		if (isRight) {
			int speeds[] = { Game.UNITLENGTH_L / 39, Game.UNITLENGTH_L / 39 };
			return speeds;
		} else {
			int speeds[] = { -Game.UNITLENGTH_L / 39, Game.UNITLENGTH_L / 39 };
			return speeds;
		}

	}

	private void updateCounter() {

		if (this.counter++ > 40) {
			counter = 0;
			if (isRight)
				isRight = false;
			else
				isRight = true;
		}

	}

}
