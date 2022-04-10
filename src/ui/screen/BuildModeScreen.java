package ui.screen;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import domain.game.IRunModeListener;

@SuppressWarnings("serial")
public class BuildModeScreen extends JFrame {

	static final int ATOM_COUNT = 100;
	static final int MOLECULE_COUNT = 100;
	static final int POWERUP_COUNT = 20;
	static final int REACTIONBLOCKER_COUNT = 10;
	static final int SHIELD_COUNT = 10;
	static final int SCREEN_WIDTH = 1368;
	static final int SCREEN_HEIGHT = 766;
	static final int UNITLENGTH_L = (int) (SCREEN_HEIGHT * 0.1 + 0.5); // 10% of game height
	static final int STATISTICS_SCREEN_WIDTH = 200;

	static final String SAVE_METHOD_1 = "Database";
	static final String SAVE_METHOD_2 = "Local";

	private JTextField atomCount;
	private JTextField moleculeCount;
	private JTextField powerupCount;
	private JTextField reactionBlockerCount;
	private JTextField shieldCount;
	private JTextField username;
	private JButton gameStart;
	private JRadioButton linearAlpha;
	private JRadioButton nonlinearAlpha;
	private JRadioButton linearBeta;
	private JRadioButton nonlinearBeta;
	private JRadioButton stationaryAlpha;
	private JRadioButton rotatingAlpha;
	private JRadioButton stationaryBeta;
	private JRadioButton rotatingBeta;
	private JTextField unitLengthL;
	private JComboBox<String> difficulty;
	private JComboBox<String> saveMethod;
	private HashMap<String, Double> runSettings;
	private List<IRunModeListener> runModeListeners = new ArrayList<>();

	public void addListener(IRunModeListener listener) {
		runModeListeners.add(listener);
	}

	public void removeListener(IRunModeListener listener) {
		runModeListeners.remove(listener);
	}

	public void setRunSettings() {
		HashMap<String, Double> runSettings = new HashMap<String, Double>();
		runSettings.put("atomCount", Double.parseDouble(atomCount.getText()));
		runSettings.put("moleculeCount", Double.parseDouble(moleculeCount.getText()));
		runSettings.put("powerupCount", Double.parseDouble(powerupCount.getText()));
		runSettings.put("reactionBlockerCount", Double.parseDouble(reactionBlockerCount.getText()));
		runSettings.put("shieldCount", Double.parseDouble(shieldCount.getText()));
		runSettings.put("linearAlpha", (linearAlpha.isSelected() ? 1.0 : 0));
		runSettings.put("linearBeta", (linearBeta.isSelected() ? 1.0 : 0));
		runSettings.put("stationaryAlpha", (isStationaryOrRotating(linearAlpha, stationaryAlpha)));
		runSettings.put("stationaryBeta", (isStationaryOrRotating(linearBeta, stationaryBeta)));
		runSettings.put("rotatingAlpha", (isStationaryOrRotating(linearAlpha, stationaryAlpha)));
		runSettings.put("rotatingBeta", (isStationaryOrRotating(linearBeta, stationaryBeta)));
		runSettings.put("unitLengthL", Double.parseDouble(unitLengthL.getText()));
		runSettings.put("difficulty", getDifficultyAsDouble(String.valueOf(difficulty.getSelectedItem())));
		runSettings.put("saveMethod", getSaveMethodAsDouble(String.valueOf(saveMethod.getSelectedItem())));
		runSettings.put("statisticsWidth", (double) STATISTICS_SCREEN_WIDTH);
		runSettings.put("screenHeight", (double) SCREEN_HEIGHT);
		runSettings.put("screenWidth", (double) SCREEN_WIDTH);

		this.runSettings = runSettings;
	}

	private double isStationaryOrRotating(JRadioButton radiobtn, JRadioButton childRadiobtn) {
		if (radiobtn.isSelected()) {
			if (childRadiobtn.isSelected()) {
				return 1.0;
			} else {
				return 0.0;
			}
		} else {
			return -1.0;
		}
	}

	private Double getDifficultyAsDouble(String difficulty) {
		// TODO Auto-generated method stub

		switch (difficulty) {
		case "Easy":
			return 0.0;
		case "Medium":
			return 1.0;
		case "Hard":
			return 2.0;
		default:
			return 1.0;
		}
	}

	private Double getSaveMethodAsDouble(String saveMethod) {
		// TODO Auto-generated method stub

		switch (saveMethod) {
		case SAVE_METHOD_1:
			return 1.0;
		case SAVE_METHOD_2:
			return 2.0;
		default:
			return 1.0;
		}
	}

	public BuildModeScreen() {
		initializeBuildScreen();
		add(initializeGameObjectPanel());
		add(initializeGameSettingsPanel());
		add(runGamePanel(this));
	}

	private void initializeBuildScreen() {
		this.setLayout(new GridLayout(3, 0));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 1000);
	}

	private JPanel initializeGameObjectPanel() {
		GridLayout gameObjLayout = new GridLayout(6, 2);
		JPanel GameObjectPanel = new JPanel(gameObjLayout);
		atomCount = new JTextField(Integer.toString(ATOM_COUNT), 30);
		moleculeCount = new JTextField(Integer.toString(MOLECULE_COUNT), 30);
		powerupCount = new JTextField(Integer.toString(POWERUP_COUNT), 30);
		reactionBlockerCount = new JTextField(Integer.toString(REACTIONBLOCKER_COUNT), 30);
		shieldCount = new JTextField(Integer.toString(SHIELD_COUNT), 30);
		username = new JTextField("Curious Monkey", 30);

		GameObjectPanel.add(new JLabel("Number of atoms"));
		GameObjectPanel.add(atomCount);
		GameObjectPanel.add(new JLabel("Number of molecules"));
		GameObjectPanel.add(moleculeCount);
		GameObjectPanel.add(new JLabel("Number of powerups"));
		GameObjectPanel.add(powerupCount);
		GameObjectPanel.add(new JLabel("Number of reaction blockers"));
		GameObjectPanel.add(reactionBlockerCount);
		GameObjectPanel.add(new JLabel("Number of shields"));
		GameObjectPanel.add(shieldCount);
		GameObjectPanel.add(new JLabel("Username:"));
		GameObjectPanel.add(username);
		return GameObjectPanel;
	}

	private JPanel initializeGameSettingsPanel() {
		GridLayout varLayout = new GridLayout(5, 3);
		JPanel GameSettingsPanel = new JPanel(varLayout);
		linearAlpha = new JRadioButton("linear");
		nonlinearAlpha = new JRadioButton("non-linear");
		linearBeta = new JRadioButton("linear");
		nonlinearBeta = new JRadioButton("non-linear");
		stationaryAlpha = new JRadioButton("stationary");
		rotatingAlpha = new JRadioButton("rotating");
		stationaryBeta = new JRadioButton("stationary");
		rotatingBeta = new JRadioButton("rotating");
		linearAlpha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stationaryAlpha.setEnabled(true);
				rotatingAlpha.setEnabled(true);
			}
		});
		nonlinearAlpha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stationaryAlpha.setEnabled(false);
				rotatingAlpha.setEnabled(false);
			}
		});
		linearBeta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stationaryBeta.setEnabled(true);
				rotatingBeta.setEnabled(true);
			}
		});
		nonlinearBeta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stationaryBeta.setEnabled(false);
				rotatingBeta.setEnabled(false);
			}
		});
		ButtonGroup AlphaMoleculeStructure = new ButtonGroup();
		AlphaMoleculeStructure.add(linearAlpha);
		AlphaMoleculeStructure.add(nonlinearAlpha);

		ButtonGroup AlphaMoleculeRotation = new ButtonGroup();
		AlphaMoleculeRotation.add(stationaryAlpha);
		AlphaMoleculeRotation.add(rotatingAlpha);

		ButtonGroup BetaMoleculeStructure = new ButtonGroup();
		BetaMoleculeStructure.add(linearBeta);
		BetaMoleculeStructure.add(nonlinearBeta);

		ButtonGroup BetaMoleculeRotation = new ButtonGroup();
		BetaMoleculeRotation.add(stationaryBeta);
		BetaMoleculeRotation.add(rotatingBeta);

		GameSettingsPanel.add(new JLabel("Alpha Structure"));
		GameSettingsPanel.add(linearAlpha);
		GameSettingsPanel.add(nonlinearAlpha);
		linearAlpha.setSelected(true);

		GameSettingsPanel.add(new JLabel("Alpha Rotation"));
		GameSettingsPanel.add(stationaryAlpha);
		GameSettingsPanel.add(rotatingAlpha);
		stationaryAlpha.setSelected(true);

		GameSettingsPanel.add(new JLabel("Beta Structure"));
		GameSettingsPanel.add(linearBeta);
		GameSettingsPanel.add(nonlinearBeta);
		linearBeta.setSelected(true);

		GameSettingsPanel.add(new JLabel("Beta Rotation"));
		GameSettingsPanel.add(stationaryBeta);
		GameSettingsPanel.add(rotatingBeta);
		stationaryBeta.setSelected(true);

		GameSettingsPanel.add(new JLabel("Unit length L"));
		unitLengthL = new JTextField(Integer.toString(UNITLENGTH_L), 30);
		GameSettingsPanel.add(unitLengthL);
		return GameSettingsPanel;

	}

	private JPanel runGamePanel(JFrame frame) {
		GridLayout panelLayout = new GridLayout(3, 0);
		JPanel runGamePanel = new JPanel(panelLayout);
		gameStart = new JButton("Click to start the game!");
		gameStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				setRunSettings();
				notifyButtonisClickedListeners();

			}
		});
		String difficulties[] = { "Easy", "Medium", "Hard" };
		difficulty = new JComboBox<String>(difficulties);
		String saveMethods[] = { SAVE_METHOD_1, SAVE_METHOD_2 };
		saveMethod = new JComboBox<String>(saveMethods);
		runGamePanel.add(difficulty);
		runGamePanel.add(saveMethod);
		runGamePanel.add(gameStart);
		return runGamePanel;
	}

	public void notifyButtonisClickedListeners() {
		System.out.println("ALL LISTENERS ARE NOTIFIED THAT THE BUTTON IS CLICKED \n\n\n");

		for (IRunModeListener listener : runModeListeners) {
			listener.onClickEvent(this.runSettings, username.getText());
		}
		this.setVisible(false);
		this.dispose();
	}

}