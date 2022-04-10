package ui.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import domain.atom.Atom;
import domain.game.DomainObject;
import domain.game.Game;
import domain.game.Location;

public class AtomView implements Drawable {

	Atom atom;
	BufferedImage alpha_img;
	BufferedImage beta_img;
	BufferedImage sigma_img;
	BufferedImage gamma_img;

	static AtomView instance;

	private AtomView() {
	}

	public static AtomView getInstance() {
		if (instance == null) {
			instance = new AtomView();
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
		alpha_img = ImageIO.read(new File("src/assets/atoms/alpha.png"));
		beta_img = ImageIO.read(new File("src/assets/atoms/beta.png"));
		gamma_img = ImageIO.read(new File("src/assets/atoms/gamma.png"));
		sigma_img = ImageIO.read(new File("src/assets/atoms/sigma.png"));

		int atom_size = (int) (Game.UNITLENGTH_L * 0.6);
		int offset = 10;
		alpha_img = scaleBImage(alpha_img, atom_size, atom_size);
		beta_img = scaleBImage(beta_img, atom_size - offset, atom_size - offset);
		gamma_img = scaleBImage(gamma_img, atom_size - offset, atom_size - offset);
		sigma_img = scaleBImage(sigma_img, atom_size - offset, atom_size - offset);

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
	public void draw(Graphics2D g2d, DomainObject object) {
		Atom atom = (Atom) object;
		String type = atom.getType();
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
		Location loc = atom.getLocation();

		// drawing the component
		g2d.drawImage(bi, loc.x, loc.y, null);
		// give width and height information of the image for collision checks
		atom.setWidthHeight(bi.getWidth(), bi.getHeight());

	};
}
