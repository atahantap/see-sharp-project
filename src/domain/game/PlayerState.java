package domain.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerState implements IRunModeListener {

	private int health_points;
	private double score;
	private HashMap<Integer, Integer> atom_inventory;
	private HashMap<Integer, Integer> powerup_inventory;
	private HashMap<Integer, Integer> shield_inventory;
	private List<InventoryListener> inventoryListeners = new ArrayList<>();
	private List<TimeListener> timeListeners = new ArrayList<>();

	public PlayerState() {
		health_points = 100;
		score = 0;
		atom_inventory = new HashMap<Integer, Integer>();
		powerup_inventory = new HashMap<Integer, Integer>();
		shield_inventory = new HashMap<Integer, Integer>();

	}

	public void addListener(InventoryListener listener) {
		inventoryListeners.add(listener);
	}

	public void removeListener(InventoryListener listener) {
		inventoryListeners.remove(listener);
	}

	public void addTimelistener(TimeListener l) {
		this.timeListeners.add(l);
	}

	/**
	 * Initializes either atom or powerup inventory with the same String keys:
	 * alpha, beta, sigma, gama. Initial values of all atoms/powerups are 0.
	 * 
	 * @param inv : either an atom or a powerup inventory implemented as a HashMap
	 */
	public void initializeInventory(HashMap<Integer, Integer> inv, int count) {
		inv.put(1, count);
		inv.put(2, count);
		inv.put(3, count);
		inv.put(4, count);
	}

	/**
	 * Increment the specified powerup's inventory by one
	 * 
	 * @param powerup type to be incremented, this is the key of the inventory
	 */

	public void incrementPowerup(Integer powerup) {
		Integer newVal = this.atom_inventory.get(powerup);
		newVal++;
		this.powerup_inventory.put(powerup, newVal);

	}

	public int getHealth_points() {
		return health_points;
	}

	public void decreaseHealthPoints(int health_points_offset) {
		this.health_points -= health_points_offset;
		notifyAllInventoryListeners("health");
	}

	public double getScore() {
		return score;
	}

	public void increaseScore(double score_offset) {
		this.score += score_offset;
		notifyAllInventoryListeners("score");
	}

	public HashMap<Integer, Integer> getAtom_inventory() {
		return atom_inventory;
	}

	public HashMap<Integer, Integer> getPowerup_inventory() {
		return powerup_inventory;
	}

	public HashMap<Integer, Integer> getShieldInventory() {
		return shield_inventory;
	}

	@Override
	public void onClickEvent(HashMap<String, Double> runSettings, String username) {
		initializeInventory(powerup_inventory, 0);
		initializeInventory(atom_inventory, runSettings.get("atomCount").intValue());
		initializeInventory(shield_inventory, runSettings.get("shieldCount").intValue());
		notifyAllInventoryListeners("all");

	}

	public void updateAtomInventory(int atomType, int changeInAtomCount) {

		int currentCount = atom_inventory.get(atomType);
		atom_inventory.put(atomType, currentCount + changeInAtomCount);
		notifyAllInventoryListeners("atom");
	}

	public void updatePowerupInventory(int powerupType, int changeInPowerupCount) {
		int currentCount = powerup_inventory.get(powerupType);
		powerup_inventory.put(powerupType, currentCount + changeInPowerupCount);
		notifyAllInventoryListeners("powerup");

	}

	public void updateShieldInventory(int shieldType, int changeInShieldCount) {
		int currentCount = shield_inventory.get(shieldType);
		shield_inventory.put(shieldType, currentCount + changeInShieldCount);
		notifyAllInventoryListeners("shield");
	}

	public void c(String type) {
		notifyAllInventoryListeners(type);

	}

	public void notifyAllInventoryListeners(String toUpdate) {
		for (InventoryListener listener : inventoryListeners) {
			listener.onInventoryChange(toUpdate);
		}
	}

	private void notifyTimeListeners() {
		for (TimeListener listener : timeListeners) {
			listener.onTimeEvent();
		}
	}

	@Override
	public String toString() {

		return String.format(
				"HP: %d\t Score: %d\t%n" + "ATOMS \t\t Alpha: %d Beta: %d Gamma: %d Sigma: %d%n"
						+ "POWERUPS \t Alpha: %d Beta: %d Gamma: %d Sigma: %d%n",
				health_points, score, atom_inventory.get(1), atom_inventory.get(2), atom_inventory.get(3),
				atom_inventory.get(4), powerup_inventory.get(1), powerup_inventory.get(2), powerup_inventory.get(3),
				powerup_inventory.get(4));
	}

	public void setShieldInventory(HashMap<Integer, Integer> m) {
		// TODO Auto-generated method stub
		this.shield_inventory = m;
		this.updateShieldInventory(1, 0);
	}

	public void decreaseTime() {
		notifyTimeListeners();

	}

	public void setHealth(int health_points) {
		// TODO Auto-generated method stub
		this.health_points = health_points;

	}

	public void setScore(double score) {
		// TODO Auto-generated method stub
		this.score = score;
	}

}
