package demo;

import javax.swing.JFrame;

import domain.game.Game;
import domain.game.Player;
import ui.screen.BuildModeScreen;
import ui.screen.KuvidScreen;

public class Main {
	static JFrame frame;
	static BuildModeScreen buildMode;

	public static void main(String arr[]) {
		
		//main JFrame
		KuvidScreen kuvidScreen = new KuvidScreen();
		
		//starts with the build mode screen
		buildMode = new BuildModeScreen();
		buildMode.setVisible(true);
		buildMode.addListener(kuvidScreen);
		buildMode.addListener(Game.getInstance());
		
		//adds the player
		Game.getInstance().addPlayer(new Player());
		buildMode.addListener(Game.getInstance().getPlayers().get(0));
		buildMode.addListener(Game.getInstance().getPlayers().get(0).getPlayerState());
		
		
		// add listener to the playerstate to update statisticsScreen
		Game.getInstance().getPlayers().get(0).getPlayerState().addListener(kuvidScreen.getStatisticsScreen());
		Game.getInstance().getPlayers().get(0).getPlayerState().addTimelistener(kuvidScreen.getStatisticsScreen());
		
		// add listener to the shooter to update button and add shields
		kuvidScreen.getStatisticsScreen().addListener(Game.getInstance().getPlayers().get(0).getShooter());

	}

}
