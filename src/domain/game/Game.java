package domain.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import domain.save.Client;

public class Game implements IRunModeListener {
	String gameStatus;
	public GameState gameState;
	public Client client;
	static Game instance;
	private Timer game_Timer;

	public static int UNITLENGTH_L;

	private Game() {
		gameState = new GameState();
		game_Timer = new Timer();
		game_Timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				gameState.decreaseTime();
			}
		}, 0, 1000);

	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	public void resumeTime(double remaining) {
		game_Timer = new Timer();
		game_Timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				gameState.decreaseTime();
			}
		}, 0, 200);
	}

	public void cancelTime() {
		game_Timer.cancel();
	}

	public void loadGame(String type) {
		client = new Client(type);
		client.loadGame(gameState.players.get(0).player_state.getAtom_inventory(),
				gameState.players.get(0).player_state.getPowerup_inventory(), gameState.moleculeCounts);

	}

	public void saveGame(Player player, String type) {
		client = new Client(type);
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (DomainObject a : gameState.getDomainObjectArr()) {
			list.add(a.makeList());
		}
		list.add(gameState.players.get(0).getShooter().makeList());
		client.saveGame(list, 30, gameState.players.get(0).player_state.getAtom_inventory(),
				gameState.players.get(0).player_state.getPowerup_inventory(), gameState.moleculeCounts);
	}

	@Override
	public void onClickEvent(HashMap<String, Double> startParameters, String username) {
		// TODO Auto-generated method stub
		initializeGame(startParameters, username);
		Game.UNITLENGTH_L = startParameters.get("unitLengthL").intValue();
	}

	private void initializeGame(HashMap<String, Double> startParameters, String username) {

		gameState.initializeGameState(startParameters, username);
	}

	public ArrayList<DomainObject> getDomainObjectArr() {
		return this.gameState.getDomainObjectArr();
	}

	public void setDomainObjectArr(ArrayList<DomainObject> list) {
		this.gameState.setDomainList(list);
	}

	public ArrayList<Player> getPlayers() {
		return this.gameState.getPlayers();
	}

	public void addPlayer(Player p) {
		this.gameState.addPlayer(p);
	}
}
