package domain.game;

import java.util.HashMap;

public class Player implements IRunModeListener {

	private Shooter shooter;

	public PlayerState player_state;

	/**
	 * name of the player , it may not be unique
	 */
	private String name;

	/**
	 * Integer id of the player, unique to each player
	 */
	private int id;

	/**
	 * Creates a default Player object
	 */

	/**
	 * Creates player with specified values
	 * 
	 * @param name name of the player, does not have to be unique
	 * @param id   Integer id of the player, must be unique
	 */
	public Player(String name, int id) {
		this.name = name;
		this.id = id;
		player_state = new PlayerState();
		shooter = new Shooter();
	}

	public Player() {
		player_state = new PlayerState();
		shooter = new Shooter();
	}

	/**
	 * 
	 * @return name of the player as string
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return ID of the player as a integer
	 */
	public int getId() {
		return id;
	}

	public PlayerState getPlayerState() {
		// TODO Auto-generated method stub
		return player_state;
	}

	@Override
	public void onClickEvent(HashMap<String, Double> runSettings, String username) {
		// TODO Auto-generated method stub
		int unitLengthL = runSettings.get("unitLengthL").intValue();
		Shooter.SHOOTER_WIDTH = unitLengthL * 4 / 7;
		Shooter.SHOOTER_HEIGHT = unitLengthL * 5 / 3;
		Shooter.SHOOTER_OFFSET = unitLengthL / 2;

		shooter = new Shooter();

	}

	public Shooter getShooter() {
		// TODO Auto-generated method stub
		return this.shooter;
	}

}
