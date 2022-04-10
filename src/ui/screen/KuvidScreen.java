package ui.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JFrame;

import domain.game.IRunModeListener;

public class KuvidScreen implements IRunModeListener {
	static JFrame jf;

	StatisticsScreen statisticsPanel;
	RunModeScreen screen;

	@Override
	public void onClickEvent(HashMap<String, Double> runSettings, String username) {
		int screenWidth = runSettings.get("screenWidth").intValue();
		int screenHeight = runSettings.get("screenHeight").intValue();
		int statisticsWidth = runSettings.get("statisticsWidth").intValue();
		initializeOuterFrameSettings(screenWidth, screenHeight);
		openRunModeScreen();
		openStatisticsScreen(statisticsWidth, screenHeight);

	}

	public KuvidScreen() {
		statisticsPanel = new StatisticsScreen();
	}

	private void initializeOuterFrameSettings(int screenWidth, int screenHeight) {
		jf = new JFrame();
		jf.setTitle("KUvid");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(new BorderLayout(5, 5));
		jf.setSize(screenWidth, screenHeight);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setFocusable(true);
	}

	private void openRunModeScreen() {
		screen = new RunModeScreen();
		screen.setVisible(true);
		jf.add(screen);
		jf.addKeyListener(screen);
	}

	public static void GameOver() {

		jf.dispose();
	}

	private void openStatisticsScreen(int statisticsWidth, int statisticsHeight) {
		// create statisticsPanel
		jf.add(statisticsPanel, BorderLayout.LINE_END);
		statisticsPanel.setPreferredSize(new Dimension(statisticsWidth, statisticsHeight));
	}

	public StatisticsScreen getStatisticsScreen() {
		return statisticsPanel;
	}

}
