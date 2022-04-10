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
import domain.game.Shootable;
import domain.game.Shooter;
import domain.powerup.Powerup;

public class ShooterView implements Drawable {

	BufferedImage shooter_img;
	static ShooterView instance;

	private ShooterView() {
	}

	public static ShooterView getInstance() {
		if (instance == null) {
			instance = new ShooterView();
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
		// TODO Auto-generated method stub
		shooter_img = ImageIO.read(new File("src/assets/shooter.png"));

		// scale to unit length L
		Image scaled = shooter_img.getScaledInstance(Game.UNITLENGTH_L * 4 / 7, Game.UNITLENGTH_L * 5 / 3,
				BufferedImage.SCALE_SMOOTH);
		if (scaled instanceof BufferedImage)
			shooter_img = (BufferedImage) scaled;

		// Create a buffered image with transparency
		BufferedImage new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = new_bimage.createGraphics();
		bGr.drawImage(scaled, 0, 0, null);
		bGr.dispose();

		shooter_img = new_bimage;
	}

	@Override
	public void draw(Graphics2D g2d, DomainObject domainObject) {
		// TODO Auto-generated method stub
		Shooter shooter = (Shooter) domainObject;
		Location loc = shooter.getLocation();
		double rad = shooter.getAngle();
		g2d.rotate(rad, loc.x + shooter_img.getWidth() / 2, loc.y + shooter_img.getHeight());
		g2d.drawImage(shooter_img, loc.x, loc.y, null);

		Shootable bullet = shooter.getBullet();
		bullet.setLocation(new Location(loc.x, loc.y - 30));

		if (bullet instanceof Atom)
			AtomView.getInstance().draw(g2d, (Atom) bullet);
		else if (bullet instanceof Powerup)
			PowerupView.getInstance().draw(g2d, (Powerup) bullet);

		shooter.setWidthHeight(shooter_img.getWidth(), shooter_img.getHeight());

	}

}
