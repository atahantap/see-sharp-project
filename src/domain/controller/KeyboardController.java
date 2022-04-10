
package domain.controller;

import domain.game.Blender;
import domain.game.Game;
import domain.game.Shooter;

public class KeyboardController {
	Boolean isBlender = false;
	int keySequence[] = new int[2];

	public boolean getInput(int input, Object obj) {
		// returns a boolean indicating whether to restart timer

		if (isBlender) {
			switch (input) {
			case 49: // 1
				if (keySequence[0] == 0) {
					keySequence[0] = 1;
				} else {
					keySequence[1] = 1;
				}
				break;
			case 50: // 2
				if (keySequence[0] == 0) {
					keySequence[0] = 2;
				} else {
					keySequence[1] = 2;
				}
				break;
			case 51: // 3
				if (keySequence[0] == 0) {
					keySequence[0] = 3;
				} else {
					keySequence[1] = 3;
				}
				break;
			case 52: // 4
				if (keySequence[0] == 0) {
					keySequence[0] = 4;
				} else {
					keySequence[1] = 4;
				}
				break;
			}

		}

		if (keySequence[1] != 0) {
			isBlender = false;
			Blender.getInstance().blendOrBreakAtom(Game.getInstance().getPlayers().get(0), keySequence[0],
					keySequence[1]);
			keySequence[0] = 0;
			keySequence[1] = 0;
			return true; // restart timer
		}

		switch (input) {
		case 37: // left
			((Shooter) obj).setSpeed(-Game.UNITLENGTH_L / 10, 0); // to preserve aesthetics
			return false;
		case 39: // right
			((Shooter) obj).setSpeed(Game.UNITLENGTH_L / 10, 0); // to preserve aesthetics
			return false;
		case 65: // rotate counter-clockwise
			((Shooter) obj).updateAngle(input);
			return false;
		case 68: // rotate clockwise
			((Shooter) obj).updateAngle(input);
			return false;
		case 32: // space character
			((Shooter) obj).shoot();
			return false;
		case 67: // c
			((Shooter) obj).change();
			return false;
		case 66: // b
			isBlender = true;
			return false;
		case 83: // s
			Game currentGame = Game.getInstance();
			currentGame.saveGame(currentGame.getPlayers().get(0), Game.getInstance().gameState.saveType);
			return false;
		case 76: // l
			Game currentGame1 = Game.getInstance();
			currentGame1.loadGame(Game.getInstance().gameState.saveType);
			return false;
		default:
			return false;
		}

	}

	public void released(Object obj) {
		((Shooter) obj).setSpeed(0, 0);
	}

	public void input(Object s, int x) {
		if (x == 0x27) { // right
			((Shooter) s).setSpeed(5, 0);
		} else if (x == 0x25) { // left
			((Shooter) s).setSpeed(-5, 0);
		}
	}
}
