package domain.factory;

import java.util.Random;

import domain.game.Location;
import domain.powerup.AlphaPowerup;
import domain.powerup.BetaPowerup;
import domain.powerup.GammaPowerup;
import domain.powerup.Powerup;
import domain.powerup.SigmaPowerup;

public class PowerupFactory {
	private static PowerupFactory instance;
	static Random r;

	private PowerupFactory() {
	}

	public static PowerupFactory getInstance() {
		if (instance == null) {
			instance = new PowerupFactory();
			r = new Random();
		}
		return instance;
	}

	public Powerup getPowerupWithRandomX(String PowerupType, int xUpperLimit, int y) {
		Location randomLocation = new Location(r.nextInt(xUpperLimit), y);
		return getPowerup(PowerupType, randomLocation);
	}

	public Powerup getPowerup(String PowerupType, Location location) {
		Powerup Powerup;
		switch (PowerupType) {
		case "alpha":
			Powerup = new AlphaPowerup(location);
			break;
		case "beta":
			Powerup = new BetaPowerup(location);
			break;
		case "gamma":
			Powerup = new GammaPowerup(location);
			break;
		case "sigma":
			Powerup = new SigmaPowerup(location);
			break;
		default:
			Powerup = new AlphaPowerup(location);
			break;
		}
		return Powerup;
	}
}
