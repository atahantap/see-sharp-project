package ui.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import domain.game.DomainObject;
import domain.game.Game;
import domain.game.Location;
import domain.reactionBlockers.ReactionBlocker;

public class ReactionBlockerView implements Drawable {

	BufferedImage alpha_img;
	BufferedImage beta_img;
	BufferedImage sigma_img;
	BufferedImage gamma_img;

	static ReactionBlockerView instance;

	private ReactionBlockerView() {
	}

	public static ReactionBlockerView getInstance() {
		if (instance == null) {
			instance = new ReactionBlockerView();
			try {
				instance.fillImgs();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	private void fillImgs() throws IOException {
		alpha_img = ImageIO.read(new File("src/assets/blockers/alpha-b.png"));
		beta_img = ImageIO.read(new File("src/assets/blockers/beta-b.png"));
		gamma_img = ImageIO.read(new File("src/assets/blockers/gamma-b.png"));
		sigma_img = ImageIO.read(new File("src/assets/blockers/sigma-b.png"));

		alpha_img = scaleBImage(alpha_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L + 20); // to preserve aesthetics
		beta_img = scaleBImage(beta_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L + 20);
		gamma_img = scaleBImage(gamma_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L + 20);
		sigma_img = scaleBImage(sigma_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L + 20);

	}

	private BufferedImage scaleBImage(BufferedImage bimg, int width, int height) {
		// scale to unit length L
		Image scaled = bimg.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		if (scaled instanceof BufferedImage)
			return (BufferedImage) scaled;

		// Create a buffered image with transparency
		BufferedImage new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = new_bimage.createGraphics();
		bGr.drawImage(scaled, 0, 0, null);
		bGr.dispose();

		return new_bimage;
	}

	@Override
	public void draw(Graphics2D g2d, DomainObject domainObject) {
		ReactionBlocker reactionBlocker = (ReactionBlocker) domainObject;
		String type = reactionBlocker.getType();
		BufferedImage bi;

		// deciding which image to use
		switch (type) {

		case "alpha":
			bi = alpha_img;
			break;
		case "beta":
			bi = beta_img;
			break;
		case "gamma":
			bi = gamma_img;
			break;
		case "sigma":
			bi = sigma_img;
			break;
		default:
			bi = alpha_img;
		}

		// getting the location
		Location loc = reactionBlocker.getLocation();
		// drawing the component
		g2d.drawImage(bi, loc.x, loc.y, null);
		reactionBlocker.setWidthHeight(bi.getWidth(), bi.getHeight());

	};

}
