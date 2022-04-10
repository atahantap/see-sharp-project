package domain.game;

import java.util.ArrayList;
import java.util.HashMap;

import domain.atom.Atom;
import domain.factory.AtomFactory;
import domain.factory.PowerupFactory;
import domain.shield.AtomShieldDecorator;
import domain.shield.EtaShieldDecorator;
import domain.shield.LotaShieldDecorator;
import domain.shield.ThetaShieldDecorator;
import domain.shield.ZotaShieldDecorator;
import utilities.MathOperations;

public class Shooter extends DomainObject implements StatScreenButtonsListener {

	/**
	 * angle of rotation from the vertical line (radiant)
	 */
	private double angle;

	private Shootable bullet;

	public boolean isShot = false;

	/**
	 * Default constructor for Shooter, initializes variables to zero
	 */
	public Shooter() {
		this.loc = new Location(GAME_WIDTH / 2, GAME_HEIGHT - SHOOTER_OFFSET - SHOOTER_HEIGHT);
		this.dx = 0;
		this.angle = 0; // Default angle
		this.bullet = AtomFactory.getInstance().getAtom(this.loc.x + BULLET_OFFSET_X, this.loc.y - BULLET_OFFSET_Y,
				"alpha");
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public void updatePosition() {
		if (loc.x <= 0) {
			loc.x += 10;
			return;
		} else if (loc.x + SHOOTER_WIDTH >= GAME_WIDTH) {
			loc.x -= 10;
			return;
		}
		loc.x += dx;
		loc.y += dy;
	}

	/**
	 * Updates the angle of the shooter with defined speed
	 * 
	 * @param angle decides where to rotate
	 * @return true if it was successful , false otherwise
	 */
	public boolean updateAngle(int input) {

		boolean turn = this.canTurn();
		if (input == 65 && turn)
			this.angle -= Game.UNITLENGTH_L / 500.0;
		else if (input == 68 && turn)
			this.angle += Game.UNITLENGTH_L / 500.0;
		else
			return false;
		return true;
	}

	private boolean canTurn() {
		if (this.angle <= -Math.PI / 2) {
			this.angle += Game.UNITLENGTH_L / 250.0;
			return false;
		} else if (this.angle >= Math.PI / 2) {
			this.angle -= Game.UNITLENGTH_L / 250.0;
		}
		return true;
	}

	public void change() {
		HashMap<Integer, Shootable> shieldedAtomsMap = Game.getInstance().gameState.shieldedAtoms;

		// if the bullet is shielded
		if (this.bullet instanceof AtomShieldDecorator) {
			int int_atom_type = 1;
			String atom_type = this.bullet.getType();
			switch (atom_type) {
			case "alpha":
				int_atom_type = 1;
				break;
			case "beta":
				int_atom_type = 2;
				break;
			case "gamma":
				int_atom_type = 3;
				break;
			case "sigma":
				int_atom_type = 4;
				break;
			default:
				break;
			}
			shieldedAtomsMap.put(int_atom_type, this.bullet);

		}

		this.bullet = null;

		int s = (int) (Math.random() * 4);

		// if there is an existing shielded atom available
		if (shieldedAtomsMap.get(s + 1) != null) {
			this.bullet = shieldedAtomsMap.get(s + 1);
			return;
		}

		HashMap<Integer, Integer> inventory = Game.getInstance().getPlayers().get(0).getPlayerState()
				.getAtom_inventory();
		while (inventory.get(s + 1) <= 0) {
			s = (int) (Math.random() * 4);
			// if there is an existing shielded atom available
			if (shieldedAtomsMap.get(s + 1) != null) {
				this.bullet = shieldedAtomsMap.get(s + 1);
				return;
			}

			if (checkInventoryIsOut(inventory))
				return;
		}

		switch (s) {
		case 0:
			this.bullet = AtomFactory.getInstance().getAtom(this.loc.x + BULLET_OFFSET_X, this.loc.y - BULLET_OFFSET_Y,
					"alpha");
			break;
		case 1:
			this.bullet = AtomFactory.getInstance().getAtom(this.loc.x + BULLET_OFFSET_X, this.loc.y - BULLET_OFFSET_Y,
					"beta");
			break;
		case 2:
			this.bullet = AtomFactory.getInstance().getAtom(this.loc.x + BULLET_OFFSET_X, this.loc.y - BULLET_OFFSET_Y,
					"gamma");
			break;
		case 3:
			this.bullet = AtomFactory.getInstance().getAtom(this.loc.x + BULLET_OFFSET_X, this.loc.y - BULLET_OFFSET_Y,
					"sigma");
			break;
		default:
			break;

		}
	}

	private boolean checkInventoryIsOut(HashMap<Integer, Integer> inventory) {
		if (inventory.size() == 0)
			return false;
		if (inventory.get(1) == 0 && inventory.get(2) == 0 && inventory.get(3) == 0 && inventory.get(4) == 0)
			return true;
		return false;
	}

	public void shoot() {

		int speed_arr[] = MathOperations.speedCalculator(bullet.getSpeed(), this.angle);

		bullet.setSpeedCoordinates(speed_arr[0], -speed_arr[1]);

		int dx = (int) ((SHOOTER_HEIGHT + 15) * Math.sin(this.angle));
		int dy = (int) ((SHOOTER_HEIGHT - 15) - (SHOOTER_HEIGHT + 15) * Math.cos(this.angle));

		bullet.setLocation(new Location(this.loc.x + dx, this.loc.y + dy));

		Game.getInstance().getDomainObjectArr().add((DomainObject) bullet);
		boolean isAtom = this.bullet instanceof Atom;
		String type = bullet.getType();
		int int_type = 1;
		switch (type) {
		case "alpha":
			int_type = 1;
			break;
		case "beta":
			int_type = 2;
			break;
		case "gamma":
			int_type = 3;
			break;
		case "sigma":
			int_type = 4;
			break;
		default:
			break;
		}

		if (isAtom)
			Game.getInstance().getPlayers().get(0).getPlayerState().updateAtomInventory(int_type, -1);

		this.change();

	}

	public Shootable getBullet() {
		return this.bullet;
	}

	public void setBullet(Shootable bullet) {
		this.bullet = bullet;
	}

	@Override
	public String toString() {
		return String.format("Shooter Object with Loc: %s%n", this.getLocation().toString());
	}

	@Override
	public void onStatScreenButtonClick(String buttonType, int type) {

		if (buttonType.equals("Shield")) {

			if (!(this.bullet instanceof Atom))
				return;

			PlayerState playerState = Game.getInstance().getPlayers().get(0).getPlayerState();

			if (playerState.getShieldInventory().get(type) > 0) {

				switch (type) {
				case 1:
					this.bullet = new EtaShieldDecorator((Atom) bullet);
					break;
				case 2:
					this.bullet = new LotaShieldDecorator((Atom) bullet);
					break;
				case 3:
					this.bullet = new ThetaShieldDecorator((Atom) bullet);
					break;
				case 4:
					this.bullet = new ZotaShieldDecorator((Atom) bullet);
					break;
				}
				playerState.updateShieldInventory(type, -1);

			}

		} else if (buttonType.equals("Powerup")) {

			preChangeToPowerup();
			PlayerState playerState = Game.getInstance().getPlayers().get(0).getPlayerState();
			Player player = Game.getInstance().getPlayers().get(0);

			if (playerState.getPowerup_inventory().get(type) > 0) {

				switch (type) {
				case 1:
					player.getShooter().setBullet(PowerupFactory.getInstance().getPowerup("alpha",
							new Location(this.loc.x + BULLET_OFFSET_X, this.loc.y - BULLET_OFFSET_Y)));
					Game.getInstance().getPlayers().get(0).getPlayerState().updatePowerupInventory(1, -1);

					break;
				case 2:
					player.getShooter().setBullet(PowerupFactory.getInstance().getPowerup("beta",
							new Location(this.loc.x + BULLET_OFFSET_X, this.loc.y - BULLET_OFFSET_Y)));
					Game.getInstance().getPlayers().get(0).getPlayerState().updatePowerupInventory(2, -1);

					break;
				case 3:
					player.getShooter().setBullet(PowerupFactory.getInstance().getPowerup("gamma",
							new Location(this.loc.x + BULLET_OFFSET_X, this.loc.y - BULLET_OFFSET_Y)));
					Game.getInstance().getPlayers().get(0).getPlayerState().updatePowerupInventory(3, -1);

					break;
				case 4:
					player.getShooter().setBullet(PowerupFactory.getInstance().getPowerup("sigma",
							new Location(this.loc.x + BULLET_OFFSET_X, this.loc.y - BULLET_OFFSET_Y)));
					Game.getInstance().getPlayers().get(0).getPlayerState().updatePowerupInventory(4, -1);

					break;
				}

			}
		}

	}

	public void preChangeToPowerup() {

		HashMap<Integer, Shootable> shieldedAtomsMap = Game.getInstance().gameState.shieldedAtoms;

		// if the bullet is shielded
		if (this.bullet instanceof AtomShieldDecorator) {
			int int_atom_type = 1;
			String atom_type = this.bullet.getType();
			switch (atom_type) {
			case "alpha":
				int_atom_type = 1;
				break;
			case "beta":
				int_atom_type = 2;
				break;
			case "gamma":
				int_atom_type = 3;
				break;
			case "sigma":
				int_atom_type = 4;
				break;
			default:
				break;
			}
			shieldedAtomsMap.put(int_atom_type, this.bullet);

		}

	}

	public static final int BULLET_OFFSET_X = 15;
	public static final int BULLET_OFFSET_Y = 30;
	public static int SHOOTER_OFFSET = 0;
	public static int SHOOTER_HEIGHT = 0;
	public static int SHOOTER_WIDTH = 0;
	public static final int GAME_HEIGHT = 768;
	public static final int GAME_WIDTH = 1166;

	/*
	 * * EFFECTS: Turns the given Shooter object in the array of string representing
	 * the properties of the given domain object. Output is in the order type,
	 * location x, location y , speed x, speed y, angle in radians
	 * 
	 */
	public ArrayList<String> makeList() {
		ArrayList<String> shooterList = new ArrayList<String>();
		shooterList.add("Shooter");
		shooterList.add(String.valueOf(this.loc.x));
		shooterList.add(String.valueOf(this.loc.y));
		shooterList.add(String.valueOf(this.getDx()));
		shooterList.add(String.valueOf(this.getDy()));
		shooterList.add(String.valueOf(this.getAngle()));
		return shooterList;
	}

}
