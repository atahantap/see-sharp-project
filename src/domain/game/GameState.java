package domain.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import domain.atom.Atom;
import domain.factory.MoleculeFactory;
import domain.factory.PowerupFactory;
import domain.factory.ReactionBlockerFactory;
import domain.molecule.Molecule;
import domain.powerup.Powerup;
import domain.reactionBlockers.ReactionBlocker;

public class GameState {
	ArrayList<Player> players;
	// Alpha 1,Beta 2,Gamma 3,Sigma 4
	private ArrayList<DomainObject> domainObjects;
	public HashMap<Integer, Integer> moleculeCounts;
	public HashMap<Integer, String> moleculeTypes;
	public HashMap<Integer, Boolean> moleculeRotations;
	private HashMap<Integer, Integer> powerupCounts;
	private HashMap<Integer, String> powerupTypes;
	private HashMap<Integer, Integer> reactionBlockerCounts;
	private HashMap<Integer, String> reactionBlockerTypes;

	public String saveType;
	private double time;

	public HashMap<Integer, Shootable> shieldedAtoms;

	private int currentReactionBlockerDropTime;
	private int currentPowerupDropTime;
	// when this is equal to moleculeDropPeriod, drop a molecule
	private int currentMoleculeDropTime;
	private int powerupDropPeriod;
	private int moleculeDropPeriod;
	private int reactionBlockerDropPeriod;

	private int frac;
	private int dec;

	private double difficulty;

	public boolean isRunning = true;

	public void decreaseTime() {

		if (!isRunning)
			return;

		if (frac == 0) {
			dec -= 1;
			frac = 59;
			this.time = dec + (0.01 * frac);
		} else {
			this.frac -= 1;
			this.time = dec + (0.01 * frac);
		}

		if (players.size() >= 1) {
			players.get(0).getPlayerState().decreaseTime();
		}

	}

	public double getTime() {
		return this.time;
	}

	public int getFrac() {
		return frac;
	}

	public void setFrac(int frac) {
		this.frac = frac;
	}

	public int getDec() {
		return dec;
	}

	public void setDec(int dec) {
		this.dec = dec;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public GameState() {
		domainObjects = new ArrayList<DomainObject>();
		players = new ArrayList<Player>();
		moleculeCounts = new HashMap<Integer, Integer>();
		moleculeTypes = new HashMap<Integer, String>();
		moleculeRotations = new HashMap<Integer, Boolean>();
		powerupCounts = new HashMap<Integer, Integer>();
		powerupTypes = new HashMap<Integer, String>();
		reactionBlockerCounts = new HashMap<Integer, Integer>();
		reactionBlockerTypes = new HashMap<Integer, String>();
		shieldedAtoms = new HashMap<Integer, Shootable>();
		currentMoleculeDropTime = 0;
		currentReactionBlockerDropTime = 0;
		currentPowerupDropTime = 0;
		time = 10.0;
		dec = 9;
		frac = 60;
	}

	public HashMap<Integer, Integer> getMoleculeCount() {
		return this.moleculeCounts;
	}

	public void updateMoleculeTypes(HashMap<String, Double> startParameters) {
		if (startParameters.get("linearAlpha") == 1) {
			moleculeTypes.put(1, "alpha-2");
		} else {
			moleculeTypes.put(1, "alpha-1");

		}
		if (startParameters.get("linearBeta") == 1) {
			moleculeTypes.put(2, "beta-2");

		} else {
			moleculeTypes.put(2, "beta-1");

		}
		if (startParameters.get("rotatingAlpha") == 0) {
			moleculeRotations.put(1, true);

		} else {
			moleculeRotations.put(1, false);

		}
		if (startParameters.get("rotatingBeta") == 0) {
			moleculeRotations.put(2, true);

		} else {
			moleculeRotations.put(2, false);

		}
	}

	public void initializeGameState(HashMap<String, Double> startParameters, String username) {
		RIGHT_WALL_COORDINATES = startParameters.get("screenWidth").intValue()
				- startParameters.get("statisticsWidth").intValue();
		X_UPPER_LIMIT = RIGHT_WALL_COORDINATES - OFFSET;
		SCREEN_HEIGHT = startParameters.get("screenHeight").intValue();
		this.getPlayers().get(0).setName(username);
		// initialize molecule counts
		int moleculeCount = startParameters.get("moleculeCount").intValue();

		updateMoleculeTypes(startParameters);

		if (startParameters.get("saveMethod") == 1) {
			saveType = "MongoDB";
		} else {
			saveType = "local";
		}
		// initialize molecule counts from run settings
		moleculeTypes.put(3, "gamma");
		moleculeTypes.put(4, "sigma");

		moleculeCounts.put(1, moleculeCount);
		moleculeCounts.put(2, moleculeCount);
		moleculeCounts.put(3, moleculeCount);
		moleculeCounts.put(4, moleculeCount);

		// initialize powerup counts from run settings
		int powerupCount = startParameters.get("powerupCount").intValue();
		powerupTypes.put(1, "alpha");
		powerupTypes.put(2, "beta");
		powerupTypes.put(3, "gamma");
		powerupTypes.put(4, "sigma");

		powerupCounts.put(1, powerupCount);
		powerupCounts.put(2, powerupCount);
		powerupCounts.put(3, powerupCount);
		powerupCounts.put(4, powerupCount);

		// initialize reactionBlockers counts from run settings
		int reactionBlockerCount = startParameters.get("reactionBlockerCount").intValue();
		reactionBlockerTypes.put(1, "alpha");
		reactionBlockerTypes.put(2, "beta");
		reactionBlockerTypes.put(3, "gamma");
		reactionBlockerTypes.put(4, "sigma");

		reactionBlockerCounts.put(1, reactionBlockerCount);
		reactionBlockerCounts.put(2, reactionBlockerCount);
		reactionBlockerCounts.put(3, reactionBlockerCount);
		reactionBlockerCounts.put(4, reactionBlockerCount);

		setDropPeriodsGivenDifficulty(startParameters);
	}

	// adds drop objects, according to their timer / or according to the randomness
	public void addAvailableDomainObjects(int timerSpeed) {
		// increment the timer of the drop times of the objects.
		this.currentMoleculeDropTime += timerSpeed;
		this.currentPowerupDropTime += timerSpeed;
		this.currentReactionBlockerDropTime += timerSpeed;

		Random r = new Random();

		// add molecules
		if (this.currentMoleculeDropTime >= this.moleculeDropPeriod) {
			int randomMolecule = r.nextInt(moleculeTypes.size()) + 1;
			String moleculeType = moleculeTypes.get(randomMolecule);
			int moleculeCount = moleculeCounts.get(randomMolecule);

			if (moleculeCount != 0) {
				domainObjects.add(MoleculeFactory.getInstance().getMoleculeWithRandomX(moleculeType, X_UPPER_LIMIT,
						Y_DROP_POINT));
				moleculeCounts.put(randomMolecule, moleculeCount - 1);
				currentMoleculeDropTime = 0;
			}
		}
		// add powerups
		if (this.currentPowerupDropTime >= this.powerupDropPeriod) {
			int randomPowerup = r.nextInt(powerupTypes.size()) + 1;
			String powerupType = powerupTypes.get(randomPowerup);
			int powerupCount = powerupCounts.get(randomPowerup);

			if (powerupCount != 0) {
				domainObjects.add(
						PowerupFactory.getInstance().getPowerupWithRandomX(powerupType, X_UPPER_LIMIT, Y_DROP_POINT));
				powerupCounts.put(randomPowerup, powerupCount - 1);
				currentPowerupDropTime = 0;
			}
		}
		// add reaction blockers
		if (this.currentReactionBlockerDropTime >= this.reactionBlockerDropPeriod)
		// if timer of reaction blocker is larger than its period, than drop a
		// reactionblocker
		{
			int randomReactionBlocker = r.nextInt(reactionBlockerTypes.size()) + 1;
			String reactionBlockerType = reactionBlockerTypes.get(randomReactionBlocker);
			int reactionBlockerCount = reactionBlockerCounts.get(randomReactionBlocker);
			// if there is any reaction blocker left in the game state
			if (reactionBlockerCount != 0) {
				// add reactionblocker to the domainobjects list
				domainObjects.add(ReactionBlockerFactory.getInstance()
						.getReactionBlockerWithRandomX(reactionBlockerType, X_UPPER_LIMIT, Y_DROP_POINT));
				// decrement its count
				reactionBlockerCounts.put(randomReactionBlocker, reactionBlockerCount - 1);
				// initialize drop time to 0
				currentReactionBlockerDropTime = 0;
			}
		}

	}

	public void removeObjectsIfOutsideScreen() {
		// if greater than 1400, means domainObject is outside screen
		// similarly if less than Y_DROP_POINT means domainObject is outside screen
		this.domainObjects.removeIf(domainObject -> (domainObject.getLocation().getY() > SCREEN_HEIGHT
				|| domainObject.getLocation().getY() < Y_DROP_POINT));

	}

	public void setDropPeriodsGivenDifficulty(HashMap<String, Double> startParameters) {
		// initializes drop periods according to the difficulty

		// easy
		if (startParameters.get("difficulty") == 0.0) {
			this.moleculeDropPeriod = DROP_PERIOD;
			this.powerupDropPeriod = DROP_PERIOD * 4;
			this.reactionBlockerDropPeriod = DROP_PERIOD * 8;
			// medium
		} else if (startParameters.get("difficulty") == 1.0) {
			this.moleculeDropPeriod = DROP_PERIOD / 2;
			this.powerupDropPeriod = DROP_PERIOD * 2;
			this.reactionBlockerDropPeriod = DROP_PERIOD * 4;
			// hard
		} else if (startParameters.get("difficulty") == 2.0) {
			this.moleculeDropPeriod = DROP_PERIOD / 4;
			this.powerupDropPeriod = DROP_PERIOD;
			this.reactionBlockerDropPeriod = DROP_PERIOD * 2;

		}
	}

	public ArrayList<DomainObject> getDomainObjectArr() {
		return this.domainObjects;
	}

	/***
	 * MODIFIES: this.domainObjects EFFECTS: removes the atom molecule pair(s): if
	 * they are of same type AND if they collide does not remove the atom molecule
	 * pair(s) if they are of different types or if they don't collide
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void checkCollisions() {
		ArrayList<DomainObject> copyOfDomainObjects = new ArrayList<DomainObject>();
		copyOfDomainObjects = (ArrayList<DomainObject>) this.domainObjects.clone();
		for (DomainObject d1 : copyOfDomainObjects) {
			// check if there are any atoms
			if (d1 instanceof Atom) {
				// for each atom, check if it is near wall,
				checkAtomWallCollision(d1);
				// for each atom, check if there are any molecules
				for (DomainObject d2 : copyOfDomainObjects) {
					if (d2 instanceof Molecule) {
						// check if their type is matching,
						// alpha molecule can be shot by alpha atom
						if (((Atom) d1).getType().equalsIgnoreCase(((Molecule) d2).getType())) {
							// check if they collide and remove from list
							checkCollisionAndRemove(d1, d2);
						}
					}

				}
			}
			// check if powerup is picked by the shooter
			if (d1 instanceof Powerup) {
				checkPowerupPickup(d1);
			}
			if (d1 instanceof ReactionBlocker) {
				checkExplodeStatus(d1);
				checkBlockStatus(d1);
			}

		}
	}

	@SuppressWarnings("unchecked")
	private void checkBlockStatus(DomainObject r) {
		String type = ((ReactionBlocker) r).getType();
		int radius = 3 * (Game.UNITLENGTH_L / 2); // 200;
		int atom = 0;
		int molecule = 0;
		ArrayList<DomainObject> copyOfDomainObjects = new ArrayList<DomainObject>();
		copyOfDomainObjects = (ArrayList<DomainObject>) this.domainObjects.clone();
		ArrayList<DomainObject> destroyed = new ArrayList<DomainObject>();

		int centerx = r.getLocation().x + (r.getWidth() / 2);
		int centery = r.getLocation().y + (r.getHeight() / 2);

		for (DomainObject d1 : copyOfDomainObjects) {

			int centerX = d1.getLocation().x + (d1.getWidth() / 2);
			int centerY = d1.getLocation().y + (d1.getHeight() / 2);

			if (d1 instanceof Molecule) {

				if (((Molecule) d1).getType().equals(type)) {
					if (Math.sqrt(Math.pow((centerx - centerX), 2) + Math.pow(centerY - centery, 2)) < radius) {
						destroyed.add(d1);
					}

				}
			}

			if (d1 instanceof Atom) {

				if (((Atom) d1).getType().equals(type)) {

					if (Math.sqrt(Math.pow((centerx - centerX), 2) + Math.pow(centerY - centery, 2)) < radius) {
						destroyed.add(d1);
					}

				}
			}
			if (d1 instanceof Powerup) {

				if (d1.dy > 0)
					continue;
				;

				if (((Powerup) d1).getType().equals(type)) {

					if (Math.sqrt(Math.pow((centerx - centerX), 2) + Math.pow(centerY - centery, 2)) < radius) {
						this.domainObjects.remove(r);
						this.domainObjects.remove(d1);
						return;
					}

				}
			}
		}

		for (DomainObject d : destroyed) {

			if (d instanceof Molecule) {
				molecule++;
			}

			if (d instanceof Atom) {
				atom++;
			}
		}

		if (atom > 0 && molecule > 0) {
			for (DomainObject d : destroyed) {

				this.domainObjects.remove(d);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void checkExplodeStatus(DomainObject r) {

		int radius = 3 * (Game.UNITLENGTH_L / 2);
		int y = r.getLocation().y + r.getHeight();

		int centerx = r.getLocation().x + (r.getWidth() / 2);
		int centery = r.getLocation().y + (r.getHeight() / 2);

		ArrayList<DomainObject> copyOfDomainObjects = new ArrayList<DomainObject>();
		copyOfDomainObjects = (ArrayList<DomainObject>) this.domainObjects.clone();

		if (y > SCREEN_HEIGHT) {

			if (Math.abs((r.getLocation().x + (r.getWidth() / 2))
					- (Game.getInstance().getPlayers().get(0).getShooter().getLocation().x
							+ (Game.getInstance().getPlayers().get(0).getShooter().getWidth() / 2))) <= radius) {
				Game.getInstance().getPlayers().get(0).getPlayerState().decreaseHealthPoints(10);
			}

			for (DomainObject d1 : copyOfDomainObjects) {
				int centerX = d1.getLocation().x + (d1.getWidth() / 2);
				int centerY = d1.getLocation().y + (d1.getHeight() / 2);

				if (Math.sqrt(Math.pow((centerx - centerX), 2) + Math.pow(centerY - centery, 2)) < radius) {

					if (d1 instanceof Powerup == false && d1 instanceof ReactionBlocker == false) {
						this.domainObjects.remove(d1);
					}
				}

			}

			this.domainObjects.remove(r);
		}
	}

	private void checkPowerupPickup(DomainObject d1) {
		PlayerState playerState = Game.getInstance().getPlayers().get(0).player_state;
		int powerupType = getIntOfType(((Powerup) d1).getType());
		Shooter shooter = Game.getInstance().getPlayers().get(0).getShooter();
		int shooterX = shooter.getLocation().getX();
		int shooterY = shooter.getLocation().getY();

		Powerup pw = (Powerup) d1;
		if (pw.dy <= 0)
			return;

		// check if they collide
		int powerupUpperLimitX = d1.getLocation().getX() + d1.getWidth();
		int powerupUpperLimitY = d1.getLocation().getY() + d1.getHeight();

		// check if y coordinates match
		if (shooterY <= powerupUpperLimitY && shooterY >= d1.getLocation().getY()) {
			// check if x coordinates match
			if (shooterX <= powerupUpperLimitX && shooterX >= d1.getLocation().getX()) {
				playerState.updatePowerupInventory(powerupType, +1);
				this.domainObjects.remove(d1);
			}

		}
	}

	private int getIntOfType(String type) {
		int intType = 0;
		switch (type) {
		case "alpha":
			intType = 1;
			break;
		case "beta":
			intType = 2;
			break;
		case "gamma":
			intType = 3;
			break;
		case "sigma":
			intType = 4;
			break;
		default:
			intType = 1;
		}
		return intType;
	}

	private void checkAtomWallCollision(DomainObject atom) {
		int AtomX = atom.getLocation().getX();
		int atomUpperLimitX = AtomX + atom.getWidth();
		// check right and left wall, we only need to check x coordinates
		// right wall has coordinates (0,y)
		// left wall has coordinates (RIGHT_WALL_COORDINATES,y)
		if (AtomX <= 0 || (atomUpperLimitX) >= RIGHT_WALL_COORDINATES) {
			atom.setSpeed(-atom.getDx(), atom.getDy());
		}

	}

	/***
	 * REQUIRES: Atom and Molecule must be of the same type. MODIFIES:
	 * this.domainObjectArr EFFECTS: If the atom or molecule is null throws
	 * NullPointerException. Else, whether molecule has a negative width or not)
	 * removes the molecule and atom .
	 */

	public void checkCollisionAndRemove(DomainObject atom, DomainObject molecule) {
		int AtomX = atom.getLocation().getX();
		int AtomY = atom.getLocation().getY();
		int moleculeUpperLimitX = molecule.getLocation().getX() + molecule.getWidth();
		int moleculeUpperLimitY = molecule.getLocation().getY() + molecule.getHeight();

		// check if y coordinates match
		if (AtomY <= moleculeUpperLimitY && AtomY >= molecule.getLocation().getY()) {
			// check if x coordinates match
			if (AtomX <= moleculeUpperLimitX && AtomX >= molecule.getLocation().getX()) {
				updateScore((Atom) atom, this.players.get(0).getPlayerState());
				this.domainObjects.remove(atom);
				this.domainObjects.remove(molecule);
			}

		}

	}

	public void setDomainList(ArrayList<DomainObject> list) {
		this.domainObjects = list;
	}

	private void updateScore(Atom atom, PlayerState ps) {
		ps.increaseScore(atom.getEfficiency());
	}

	public String gameStateToString() {

		Player player = players.get(0);
		PlayerState playerState = player.getPlayerState();
		ArrayList<DomainObject> domainObjects = Game.getInstance().getDomainObjectArr();

		String domainLocs = domainObjects.stream().map(d -> d.toString()).collect(Collectors.joining(""));

		return String.format("PLAYER: %s%n%n%n" + "PLAYER STATISTICS %n%n%s%n%n%n" + "POSITIONS %n%n%s%n",
				player.getName(), playerState.toString(), domainLocs);

	}

	private static final int OFFSET = 150;
	private static final int DROP_PERIOD = 1000;
	private static int X_UPPER_LIMIT;
	private static int SCREEN_HEIGHT;
	private static int RIGHT_WALL_COORDINATES;
	private static final int Y_DROP_POINT = -50;

	public void setTime(double time) {
		// TODO Auto-generated method stub
		this.time = time;
	}

	public double getDifficulty() {
		// TODO Auto-generated method stub
		return difficulty;
	}

	public void setDifficulty(double difficulty) {
		// TODO Auto-generated method stub
		this.difficulty = difficulty;
	}

}
