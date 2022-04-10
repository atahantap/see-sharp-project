package ui.view;

import java.awt.Graphics2D;

import domain.game.DomainObject;

public interface Drawable {
	public void draw(Graphics2D g2d, DomainObject domainObject);
}
