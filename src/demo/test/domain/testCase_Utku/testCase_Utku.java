package demo.test.domain.testCase_Utku;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import domain.atom.Atom;
import domain.factory.AtomFactory;
import domain.factory.MoleculeFactory;
import domain.game.GameState;
import domain.game.Location;
import domain.molecule.Molecule;

public class testCase_Utku {
	GameState initState;
	Atom testAtom;
	Molecule testMolecule;

	@Before
	public void setUp() throws Exception {
		initState = new GameState();
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testNullCaseMolecule() {
		testAtom = AtomFactory.getInstance().getAtom(55, 55, "alpha");
		exceptionRule.expect(NullPointerException.class);
		initState.checkCollisionAndRemove(testAtom, null);

	}

	@Test
	public void testNullCaseAtom() {
		testMolecule = MoleculeFactory.getInstance().getMolecule("alpha-1", new Location(55, 55));
		exceptionRule.expect(NullPointerException.class);
		initState.checkCollisionAndRemove(testAtom, null);

	}

	@Test
	public void testNegativeWidthCollusion() {
		testAtom = AtomFactory.getInstance().getAtom(55, 55, "alpha");
		testMolecule = MoleculeFactory.getInstance().getMolecule("alpha-1", new Location(55, 55));
		testMolecule.setWidthHeight(-3, -3);
		initState.getDomainObjectArr().add(testAtom);
		initState.getDomainObjectArr().add(testMolecule);
		initState.checkCollisionAndRemove(testAtom, testMolecule);
		assertFalse(initState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testNoCollusion() {
		testAtom = AtomFactory.getInstance().getAtom(20, 20, "alpha");
		testMolecule = MoleculeFactory.getInstance().getMolecule("alpha-1", new Location(55, 55));
		testMolecule.setWidthHeight(10, 10);
		initState.getDomainObjectArr().add(testAtom);
		initState.getDomainObjectArr().add(testMolecule);
		initState.checkCollisionAndRemove(testAtom, testMolecule);
		assertFalse(initState.getDomainObjectArr().isEmpty());
	}

	@Test
	public void testOnlyY() {
		testAtom = AtomFactory.getInstance().getAtom(150, 58, "alpha");
		testMolecule = MoleculeFactory.getInstance().getMolecule("alpha-1", new Location(55, 55));
		testMolecule.setWidthHeight(5, 5);
		initState.getDomainObjectArr().add(testAtom);
		initState.getDomainObjectArr().add(testMolecule);
		initState.checkCollisionAndRemove(testAtom, testMolecule);
		assertFalse(initState.getDomainObjectArr().isEmpty());
	}

}
