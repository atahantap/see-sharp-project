package ui.screen;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.game.Game;
import domain.game.InventoryListener;
import domain.game.StatScreenButtonsListener;
import domain.game.TimeListener;

@SuppressWarnings("serial")
public class StatisticsScreen extends JPanel implements InventoryListener, TimeListener {
	BufferedImage img;
	JPanel atomPanel;
	JLabel 	sigma;
	JLabel 	beta;
	JLabel 	gamma;
	JLabel 	alpha;
	JLabel etaCount;
	JLabel lotaCount; 
	JLabel thetaCount; 
	JLabel zotaCount;
	JLabel 	sigmaPowerupCount;
	JLabel 	betaPowerupCount;
	JLabel 	gammaPowerupCount;
	JLabel 	alphaPowerupCount;
	JLabel score;
	JLabel health;
	
	JLabel time;
	
	private List<StatScreenButtonsListener> buttonListeners = new ArrayList<>();

	public void addListener(StatScreenButtonsListener listener) {
		buttonListeners.add(listener);
	}
	public void removeListener(StatScreenButtonsListener listener) {
		buttonListeners.remove(listener);
	}
	
	public StatisticsScreen() {
		this.setLayout(new GridLayout(5,0));
		this.setOpaque(true);
		
		JPanel scorePanel = initializeScoreAndHealth();
		JPanel powerupPanel = initializePowerupCounts();
		JPanel atomPanel = initializeAtomCounts();
		JPanel shieldPanel = initializeShieldCounts();
		JLabel blenderIcon = createScaledIcon("src/assets/mixer.png");
		this.add(scorePanel);
		this.add(powerupPanel);
		this.add(blenderIcon);
		this.add(atomPanel);
		this.add(shieldPanel);

	}
	private JPanel initializeScoreAndHealth() {
		JPanel scoreAndHealthPanel = new JPanel();
		scoreAndHealthPanel.setLayout(new GridLayout(3,2));
		JLabel scoreIcon = new JLabel(new ImageIcon("src/assets/score.png"));
		JLabel timeIcon = new JLabel(new ImageIcon("src/assets/timer.png"));
		JLabel healthIcon = new JLabel(new ImageIcon("src/assets/health.png"));

		score = new JLabel("score");
		health = new JLabel("100");
		time = new JLabel("10.00");
		scoreAndHealthPanel.add(scoreIcon);
		scoreAndHealthPanel.add(score);
		scoreAndHealthPanel.add(timeIcon);
		scoreAndHealthPanel.add(time);
		scoreAndHealthPanel.add(healthIcon);
		scoreAndHealthPanel.add(health);
		
		
		return scoreAndHealthPanel;
	}
	private JPanel initializeShieldCounts() {
		// TODO Auto-generated method stub
		JPanel shieldPanel = new JPanel();
		shieldPanel.setLayout(new GridLayout(4,2));
		JButton eta= new JButton("ETA");
		JButton lota= new JButton("lota");
		JButton theta= new JButton("theta");
		JButton zota= new JButton("zota");
		eta.setBackground(Color.cyan);
		lota.setBackground(Color.orange);
		theta.setBackground(Color.green);
		zota.setBackground(Color.magenta);
		eta.setOpaque(true);
		lota.setOpaque(true);
		theta.setOpaque(true);
		zota.setOpaque(true);
		eta.setFocusable(false);
		lota.setFocusable(false);
		theta.setFocusable(false);
		zota.setFocusable(false);
		eta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				notifyAllShieldButtonsListeners(1);

			}
		});
		lota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				notifyAllShieldButtonsListeners(2);


			}
		});
		theta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				notifyAllShieldButtonsListeners(3);


			}
		});
		zota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				notifyAllShieldButtonsListeners(4);


			}
		});

		etaCount = new JLabel("etaCount");
		lotaCount = new JLabel("lotaCount");
		thetaCount = new JLabel("thetaCount");
		zotaCount = new JLabel("zotaCount");
		shieldPanel.add(eta);
		shieldPanel.add(etaCount);
		
		shieldPanel.add(lota);
		shieldPanel.add(lotaCount);
		
		shieldPanel.add(theta);
		shieldPanel.add(thetaCount);
		
		shieldPanel.add(zota);
		shieldPanel.add(zotaCount);
		return shieldPanel;
	}
	private JPanel initializePowerupCounts() {
		// TODO Auto-generated method stub
		JPanel powerupPanel = new JPanel();
		powerupPanel.setLayout(new GridLayout(4,2));
		JLabel alphaIcon = createScaledIcon("src/assets/powerups/+alpha-b.png");
		JLabel betaIcon = createScaledIcon("src/assets/powerups/+beta-b.png");
		JLabel gammaIcon = createScaledIcon("src/assets/powerups/+gamma-b.png");
		JLabel sigmaIcon = createScaledIcon("src/assets/powerups/+sigma-b.png");
		sigmaPowerupCount = new JLabel("sigmaPowerup");
		betaPowerupCount = new JLabel("betaPowerup");
		gammaPowerupCount = new JLabel("gammaPowerup");
		alphaPowerupCount = new JLabel("alphaPowerup");
		alphaIcon.addMouseListener(powerupClickListener(1));
		betaIcon.addMouseListener(powerupClickListener(2));
		gammaIcon.addMouseListener(powerupClickListener(3));
		sigmaIcon.addMouseListener(powerupClickListener(4));

		powerupPanel.add(alphaIcon);
		powerupPanel.add(alphaPowerupCount);
		
		powerupPanel.add(betaIcon);
		powerupPanel.add(betaPowerupCount);
		
		powerupPanel.add(gammaIcon);
		powerupPanel.add(gammaPowerupCount);
		
		powerupPanel.add(sigmaIcon);
		powerupPanel.add(sigmaPowerupCount);
		
		return powerupPanel;
	}
	
	private JLabel createScaledIcon(String path) {
		
		ImageIcon imageIcon = new ImageIcon(path); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new JLabel(new ImageIcon(newimg));  // transform it back

	}
	private MouseAdapter powerupClickListener(int type) {
		MouseAdapter adapter = new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	notifyAllPowerupButtonsListeners(type);
		    }  
		};
		return adapter;
	}
	private JPanel initializeAtomCounts() {
		// TODO Auto-generated method stub
		atomPanel = new JPanel();
		atomPanel.setLayout(new GridLayout(4,2));
		JLabel alphaIcon = new JLabel(new ImageIcon("src/assets/atoms/alpha.png"));
		JLabel gammaIcon = new JLabel(new ImageIcon("src/assets/atoms/gamma.png"));
		JLabel betaIcon = new JLabel(new ImageIcon("src/assets/atoms/beta.png"));
		JLabel sigmaIcon = new JLabel(new ImageIcon("src/assets/atoms/sigma.png"));

		sigma = new JLabel("100");
		beta = new JLabel("100");
		gamma = new JLabel("100");
		alpha = new JLabel("100");
		

		atomPanel.add(sigmaIcon);
		atomPanel.add(sigma);
		atomPanel.add(betaIcon);

		atomPanel.add(beta);
		atomPanel.add(gammaIcon);

		atomPanel.add(gamma);
		atomPanel.add(alphaIcon);

		atomPanel.add(alpha);
		return atomPanel;
	}
	
	
	@Override
	public void onInventoryChange(String toUpdate) {
		switch(toUpdate) {
		case "atom":
			updateAtomStatistics();
			break;
		case "shield":
			updateShieldStatistics();
			break;
		case "powerup":
			updatePowerupStatistics();
			break;
		case "score":
			updateScore();
			break;
		case "health":
			updateHealth();
			break;
		case "all":
			updatePowerupStatistics();
			updateShieldStatistics();
			updateAtomStatistics();
			updateScore();
			updateHealth();
			break;
		}
	}
	
	private void updateHealth() {
		health.setText(String.valueOf(Game.getInstance().getPlayers().get(0).getPlayerState().getHealth_points()));
	}

	private void updateScore() {
		 score.setText(String.format("%.2f", Game.getInstance().getPlayers().get(0).getPlayerState().getScore())); 

	}
	
	public void updateTime() {
		
		time.setText(String.format("%.2f", Game.getInstance().gameState.getTime()));
	}
	private void updateShieldStatistics() {
		 etaCount.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getShieldInventory().get(1).toString()); //alpha count
		 lotaCount.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getShieldInventory().get(2).toString()); //beta count
		 thetaCount.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getShieldInventory().get(3).toString()); //gamma count
		 zotaCount.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getShieldInventory().get(4).toString()); //sigma count
		}
	private void updatePowerupStatistics() {
		 alphaPowerupCount.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getPowerup_inventory().get(1).toString()); //alpha count
		 betaPowerupCount.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getPowerup_inventory().get(2).toString()); //beta count
		 gammaPowerupCount.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getPowerup_inventory().get(3).toString()); //gamma count
		 sigmaPowerupCount.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getPowerup_inventory().get(4).toString()); //sigma count
		}
	private void updateAtomStatistics() {
		 alpha.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getAtom_inventory().get(1).toString()); //alpha count
		 beta.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getAtom_inventory().get(2).toString()); //beta count
		 gamma.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getAtom_inventory().get(3).toString()); //gamma count
		 sigma.setText(Game.getInstance().getPlayers().get(0).getPlayerState().getAtom_inventory().get(4).toString()); //sigma count
	

	}
	private void notifyAllShieldButtonsListeners(int type){
		for( StatScreenButtonsListener listener :buttonListeners ) {
			listener.onStatScreenButtonClick("Shield", type);
		}
	}
	private void notifyAllPowerupButtonsListeners(int type){
		for( StatScreenButtonsListener listener :buttonListeners ) {
			listener.onStatScreenButtonClick("Powerup", type);
		}
	}
	@Override
	public void onTimeEvent() {
		updateTime();
		
	}
}