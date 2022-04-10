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
import domain.molecule.Molecule;

public class MoleculeView implements Drawable {

	BufferedImage alpha1_img;
	BufferedImage alpha2_img;
	BufferedImage beta1_img;
	BufferedImage beta2_img;
	BufferedImage sigma_img;
	BufferedImage gamma_img;

	static MoleculeView instance;

	private MoleculeView() {
	}

	public static MoleculeView getInstance() {
		if (instance == null) {
			instance = new MoleculeView();
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
		alpha1_img = ImageIO.read(new File("src/assets/molecules/alpha-1.png"));
		alpha2_img = ImageIO.read(new File("src/assets/molecules/alpha-2.png"));
		beta1_img = ImageIO.read(new File("src/assets/molecules/beta-1.png"));
		beta2_img = ImageIO.read(new File("src/assets/molecules/beta-2.png"));
		gamma_img = ImageIO.read(new File("src/assets/molecules/gamma.png"));
		sigma_img = ImageIO.read(new File("src/assets/molecules/sigma.png"));

		alpha1_img = scaleBImage(alpha1_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L);
		alpha2_img = scaleBImage(alpha2_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L / 2); // to preserve aesthetics
		beta1_img = scaleBImage(beta1_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L);
		beta2_img = scaleBImage(beta2_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L / 2); // to preserve aesthetics
		gamma_img = scaleBImage(gamma_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L);
		sigma_img = scaleBImage(sigma_img, Game.UNITLENGTH_L, Game.UNITLENGTH_L);

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

	public void draw(Graphics2D g2d, DomainObject domainObject) {
		Molecule molecule = (Molecule) domainObject;
		String type = molecule.getType();
		BufferedImage bi;
		double rad = molecule.getAngle();
		Location loc = molecule.getLocation();

		// deciding which image to use
		switch (type) {

		case "alpha":
			if (molecule.isLinear()) {
				bi = alpha2_img;
				if (Game.getInstance().gameState.moleculeRotations.get(1))
					g2d.rotate(rad, loc.x + bi.getWidth() / 2, loc.y + bi.getHeight() / 2);
			} else {
				bi = alpha1_img;
			}

			break;
		case "beta":

			if (molecule.isLinear()) {
				bi = beta2_img;
				if (Game.getInstance().gameState.moleculeRotations.get(2))
					g2d.rotate(rad, loc.x + bi.getWidth() / 2, loc.y + bi.getHeight() / 2);
			} else {
				bi = beta1_img;
			}

			break;
		case "gamma":
			bi = gamma_img;
			break;
		case "sigma":
			bi = sigma_img;
			break;
		default:
			bi = alpha1_img;
		}

		g2d.drawImage(bi, loc.x, loc.y, null);
		molecule.setWidthHeight(bi.getWidth(), bi.getHeight());
	}

}
