package demo.test.domain.test_bora;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.atom.Atom;
import domain.factory.AtomFactory;
import domain.factory.MoleculeFactory;
import domain.game.DomainObject;
import domain.game.Game;
import domain.game.Location;
import domain.game.Player;
import domain.molecule.Molecule;

public class GameStateTest {
	/*
	 * what to test BlackBox Testing: - colliding atom molecule pair with same type
	 * - colliding atom molecule pair with different type - not colliding atom
	 * molecule pair with same type - not colliding atom molecule pair with
	 * different type - more than one atom molecule pair colliding with same type -
	 * more than one atom molecule pair colliding with different type - more than
	 * one atom molecule pair not colliding with same type - more than one atom
	 * molecule pair not colliding with different type - more than one atom molecule
	 * pair where one pair is removed, the other one is not removed
	 * 
	 * GlassBox Testing: - not executing the loop - first outer iteration with
	 * correct values - first outer iteration with wrong values - second outer
	 * iteration with correct values - second outer iteration with wrong values
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Game.getInstance().addPlayer(new Player("Ufkun", 1));

	}

	@After
	public void tearDown() throws Exception {
		Game.getInstance().gameState.getDomainObjectArr().clear();
	}

	@Test
	public void testCheckCollisionsOnePairSameTypeColliding() {
		// - colliding atom molecule pair with same type
		// this covers also glassbox (first outer iteration with correct values)
		Atom atom = AtomFactory.getInstance().getAtom(100, 100, "gamma");
		atom.setWidthHeight(44, 44);
		Molecule molecule = MoleculeFactory.getInstance().getMolecule("gamma", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Game.getInstance().gameState.getDomainObjectArr().add(atom);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule);
		Game.getInstance().gameState.checkCollisions();
		assertTrue(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testCheckCollisionsOnePairDifferentTypeColliding() {
		// - colliding atom molecule pair with different type
		// this covers also glassbox (first outer iteration with wrong values)
		Atom atom = AtomFactory.getInstance().getAtom(100, 100, "alpha");
		atom.setWidthHeight(44, 44);
		Molecule molecule = MoleculeFactory.getInstance().getMolecule("gamma", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Game.getInstance().gameState.getDomainObjectArr().add(atom);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule);
		Game.getInstance().gameState.checkCollisions();
		assertFalse(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testCheckCollisionsOnePairSameTypeNotColliding() {
		// - not colliding atom molecule pair with same type
		Atom atom = AtomFactory.getInstance().getAtom(300, 100, "gamma");
		atom.setWidthHeight(44, 44);
		Molecule molecule = MoleculeFactory.getInstance().getMolecule("gamma", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Game.getInstance().gameState.getDomainObjectArr().add(atom);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule);
		Game.getInstance().gameState.checkCollisions();
		assertFalse(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testCheckCollisionsOnePairDifferentTypeNotColliding() {
		// - not colliding atom molecule pair with different type
		Atom atom = AtomFactory.getInstance().getAtom(100, 100, "alpha");
		atom.setWidthHeight(44, 44);
		Molecule molecule = MoleculeFactory.getInstance().getMolecule("gamma", new Location(500, 100));
		molecule.setWidthHeight(86, 75);
		Game.getInstance().gameState.getDomainObjectArr().add(atom);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule);
		Game.getInstance().gameState.checkCollisions();
		assertFalse(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testCheckCollisionsWithTwoPairsSameTypeColliding() {
		// - more than one atom molecule pair colliding with same type
		// this covers also glassbox (second outer iteration with correct values)
		Atom atom = AtomFactory.getInstance().getAtom(100, 100, "alpha");
		atom.setWidthHeight(44, 44);
		Atom atom2 = AtomFactory.getInstance().getAtom(100, 100, "beta");
		atom.setWidthHeight(44, 44);
		Molecule molecule = MoleculeFactory.getInstance().getMolecule("alpha-1", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Molecule molecule2 = MoleculeFactory.getInstance().getMolecule("beta-1", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Game.getInstance().gameState.getDomainObjectArr().add(atom2);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule2);
		Game.getInstance().gameState.getDomainObjectArr().add(atom);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule);
		Game.getInstance().gameState.checkCollisions();
		assertTrue(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testCheckCollisionsWithTwoPairsDifferentTypeColliding() {
		// - more than one atom molecule pair colliding with different type
		// this covers also glassbox (second outer iteration with wrong values)
		Atom atom = AtomFactory.getInstance().getAtom(100, 100, "gamma");
		atom.setWidthHeight(44, 44);
		Atom atom2 = AtomFactory.getInstance().getAtom(100, 100, "alpha");
		atom.setWidthHeight(44, 44);
		Molecule molecule = MoleculeFactory.getInstance().getMolecule("sigma", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Molecule molecule2 = MoleculeFactory.getInstance().getMolecule("beta-1", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Game.getInstance().gameState.getDomainObjectArr().add(atom2);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule2);
		Game.getInstance().gameState.getDomainObjectArr().add(atom);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule);
		Game.getInstance().gameState.checkCollisions();
		assertFalse(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testCheckCollisionsWithTwoPairsSameTypeNotColliding() {
		// - more than one atom molecule pair not colliding with same type
		Atom atom = AtomFactory.getInstance().getAtom(300, 100, "gamma");
		atom.setWidthHeight(44, 44);
		Atom atom2 = AtomFactory.getInstance().getAtom(100, 100, "alpha");
		atom.setWidthHeight(44, 44);
		Molecule molecule = MoleculeFactory.getInstance().getMolecule("gamma", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Molecule molecule2 = MoleculeFactory.getInstance().getMolecule("alpha-1", new Location(300, 100));
		molecule.setWidthHeight(86, 75);
		Game.getInstance().gameState.getDomainObjectArr().add(atom2);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule2);
		Game.getInstance().gameState.getDomainObjectArr().add(atom);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule);
		Game.getInstance().gameState.checkCollisions();
		assertFalse(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testCheckCollisionsWithTwoPairsDifferentTypeNotColliding() {
		// - more than one atom molecule pair not colliding with different type
		Atom atom = AtomFactory.getInstance().getAtom(300, 100, "beta");
		atom.setWidthHeight(44, 44);
		Atom atom2 = AtomFactory.getInstance().getAtom(100, 100, "alpha");
		atom.setWidthHeight(44, 44);
		Molecule molecule = MoleculeFactory.getInstance().getMolecule("gamma", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Molecule molecule2 = MoleculeFactory.getInstance().getMolecule("alpha-1", new Location(300, 100));
		molecule.setWidthHeight(86, 75);
		Game.getInstance().gameState.getDomainObjectArr().add(atom2);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule2);
		Game.getInstance().gameState.getDomainObjectArr().add(atom);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule);
		Game.getInstance().gameState.checkCollisions();
		assertFalse(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testCheckCollisionsWithTwoPairsButOnlyOneMatch() {
		// - more than one atom molecule pair where one pair is removed, the other one
		// is not removed
		// beta atom and molecule should be removed, alpha atom and gamma molecule
		// should remain same
		Atom atom = AtomFactory.getInstance().getAtom(100, 100, "beta");
		atom.setWidthHeight(44, 44);
		Atom atom2 = AtomFactory.getInstance().getAtom(100, 100, "alpha");
		atom.setWidthHeight(44, 44);
		Molecule molecule = MoleculeFactory.getInstance().getMolecule("gamma", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		Molecule molecule2 = MoleculeFactory.getInstance().getMolecule("beta-1", new Location(100, 100));
		molecule.setWidthHeight(86, 75);
		DomainObject[] expectedArray = new DomainObject[2];
		expectedArray[0] = atom2;
		expectedArray[1] = molecule;

		Game.getInstance().gameState.getDomainObjectArr().add(atom2);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule2);
		Game.getInstance().gameState.getDomainObjectArr().add(atom);
		Game.getInstance().gameState.getDomainObjectArr().add(molecule);
		Game.getInstance().gameState.checkCollisions();
		assertFalse(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
		assertArrayEquals(expectedArray, Game.getInstance().getDomainObjectArr().toArray());
	}

	@Test
	public void testCheckCollisionsNoIteration() {
		// - not executing the loop
		Game.getInstance().gameState.checkCollisions();
		assertTrue(Game.getInstance().gameState.getDomainObjectArr().isEmpty());
	}

}
