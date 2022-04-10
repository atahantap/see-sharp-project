package ui.screen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import domain.atom.Atom;
import domain.controller.KeyboardController;
import domain.game.DomainObject;
import domain.game.Game;
import domain.game.Player;
import domain.game.Shooter;
import domain.molecule.Molecule;
import domain.powerup.Powerup;
import domain.reactionBlockers.ReactionBlocker;
import ui.view.AtomView;
import ui.view.MoleculeView;
import ui.view.PowerupView;
import ui.view.ReactionBlockerView;
import ui.view.ShooterView;

@SuppressWarnings("serial")
public class RunModeScreen extends JPanel implements ActionListener, KeyListener {

	Timer tm = new Timer(TIMER_SPEED, this);
	BufferedImage img; // background
	String infoString = "";
	int infoRefreshCount;
	KeyboardController kc = new KeyboardController();
	Player player = Game.getInstance().getPlayers().get(0);

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.drawImage(img, 0, 0, null);

		for (DomainObject domainObject : Game.getInstance().getDomainObjectArr()) {
			drawComponent(g2d, domainObject);
			g2d.setTransform(old);

		}

		drawShooter(g2d, player.getShooter());
		g2d.setTransform(old);
		int textWidth = g.getFontMetrics().stringWidth(infoString);
		g2d.drawString(infoString, this.getWidth() / 2 - textWidth / 2, 20);
	}

	private void drawShooter(Graphics2D g2d, Shooter d) {
		// TODO Auto-generated method stub
		ShooterView.getInstance().draw(g2d, d);

	}

	private void drawComponent(Graphics2D g2d, DomainObject d) {
		// TODO Auto-generated method stub
		if (d instanceof Atom) {
			AtomView.getInstance().draw(g2d, d);
		} else if (d instanceof Molecule) {
			MoleculeView.getInstance().draw(g2d, d);
		} else if (d instanceof Powerup) {
			PowerupView.getInstance().draw(g2d, d);
		} else if (d instanceof ReactionBlocker) {
			ReactionBlockerView.getInstance().draw(g2d, d);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		gameOverCheck();
		Game.getInstance().gameState.checkCollisions();
		update();
		repaint();
		dropObjects();
		Game.getInstance().gameState.removeObjectsIfOutsideScreen();
		infoRefreshCount += TIMER_SPEED;
		if (infoRefreshCount >= INFO_REFRESH_PERIOD) {
			infoString = "";
			infoRefreshCount = 0;
		}

	}

	private boolean checkInventoryIsOut(HashMap<Integer, Integer> inventory) {
		if (inventory.size() == 0)
			return false;
		if (inventory.get(1) == 0 && inventory.get(2) == 0 && inventory.get(3) == 0 && inventory.get(4) == 0)
			return true;
		return false;
	}

	private boolean checkDomainObjectsAreOut(HashMap<Integer, Integer> inventory) {

		if (inventory.size() != 0) {
			if (inventory.get(1) == 0 && inventory.get(2) == 0 && inventory.get(3) == 0 && inventory.get(4) == 0)
				return true;
		}
		return false;
	}

	public void gameOverCheck() {

		double t = Game.getInstance().gameState.getTime();
		if (t <= 0)
			gameOver("time");
		if (Game.getInstance().getPlayers().get(0).getPlayerState().getHealth_points() <= 0)
			gameOver("health");
		if (checkInventoryIsOut(Game.getInstance().getPlayers().get(0).getPlayerState().getAtom_inventory()))
			gameOver("inventory");
		if (checkDomainObjectsAreOut(Game.getInstance().gameState.getMoleculeCount()) && checkMoleculeOnScreen())
			gameOver("domain");

	}

	private boolean checkMoleculeOnScreen() {
		for (DomainObject d : Game.getInstance().getDomainObjectArr()) {
			if (d instanceof Molecule) {
				return false;
			}
		}
		return true;
	}

	private void gameOver(String reason) {
		tm.stop();
		Game.getInstance().cancelTime();
		Object[] options = { "OK" };
		String message;
		switch (reason) {
		case "time":
			message = "Time is up!";
			break;
		case "health":
			message = "You are dead.";
			break;
		case "inventory":
			message = "Out of atoms!";
			break;
		case "domain":
			message = "Out of molecules.";
			break;
		default:
			message = "Time is up!";
			break;
		}

		int input = JOptionPane.showOptionDialog(null,
				"Score " + String.format("%.2f", Game.getInstance().getPlayers().get(0).getPlayerState().getScore()),
				"GameOver: " + message, JOptionPane.ERROR_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]);

		if (input == JOptionPane.OK_OPTION || input == JOptionPane.CLOSED_OPTION) {
			KuvidScreen.GameOver();
		}
	}

	private void dropObjects() {
		Game.getInstance().gameState.addAvailableDomainObjects(TIMER_SPEED);
	}

	public void update() {
		for (DomainObject domainObject : Game.getInstance().getDomainObjectArr()) {
			domainObject.updatePosition();
			domainObject.updateAngle();
		}
		player.getShooter().updatePosition();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int input = e.getKeyCode();
		// pause resume
		switch (input) {
		case 80: // p: pause
			infoString = "Game Paused.";
			repaint();
			tm.stop();
			Game.getInstance().gameState.isRunning = false;
			return;
		case 66: // b: blender (do not return)
			infoString = "Blend Mode";
			repaint();
			tm.stop();
			Game.getInstance().gameState.isRunning = false;
			infoRefreshCount = INFO_REFRESH_PERIOD;
			break;
		case 82: // r: resume
			infoString = "Game Resumed.";
			tm.restart();
			Game.getInstance().gameState.isRunning = true;

			break;
		case 83: // s: save
			if (!tm.isRunning()) {
				infoString = "Game Saved.";
				kc.getInput(input, player.getShooter()); // only works if game was paused
				return;
			} else {
				infoString = "Press \"Pause\" Button before saving.";
				return;
			}
		case 76: // l: load
			if (!tm.isRunning()) {
				infoString = "Game Loaded.";
				kc.getInput(input, player.getShooter());
				tm.restart();// only works if game was paused
				Game.getInstance().gameState.isRunning = true;
				Game.getInstance().getPlayers().get(0).player_state.notifyAllInventoryListeners("all");
				return;
			} else {
				infoString = "Press \"Pause\" Button before loading.";
				return;
			}
		default:
		}

		if (kc.getInput(input, player.getShooter())) { // when returns true restart
			tm.restart();
			Game.getInstance().gameState.isRunning = true;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		kc.released(player.getShooter());
	}

	public RunModeScreen() {
		this.setSize(1366, 768);
		try {
			initializeRunModeScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initializeRunModeScreen() throws IOException {
		this.setFocusable(true);
		img = ImageIO.read(new File("src/assets/kuvid_bc.png"));
		tm.start();
	}

	private static final int TIMER_SPEED = 10;
	private static final int INFO_REFRESH_PERIOD = 3000;

}
