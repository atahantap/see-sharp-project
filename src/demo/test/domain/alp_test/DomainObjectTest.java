package demo.test.domain.alp_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.atom.Alpha;
import domain.factory.AtomFactory;
import domain.game.DomainObject;
import domain.game.Location;
import domain.game.Shooter;
import domain.molecule.AlphaMolecule;
import domain.molecule.GammaMolecule;
import domain.powerup.GammaPowerup;

public class DomainObjectTest {

	// What to test
	// Black Box testing:
	/*
	 * - If two equal objects are added, their output string should be equal -
	 * Output string of a Atom, should match the order and values of the object -
	 * Output string of a PowerUp, should match the order and values of the object -
	 * Output should be correct for a Shooter, since it is different from Molecule
	 * and Atom
	 *
	 * Glass Box Testing:
	 *
	 * - Edge cases where some atoms have non linear structure should be written in
	 * the strings in correct form - Edge cases where some atoms have linear
	 * structure should be written in the strings in correct form - Edge cases where
	 * some atoms dont have a structure should be correct in correct form
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("unused")
	@Before
	public void setUp() throws Exception {
		DomainObject ac = new Alpha(new Location(100, 200));

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_with_equal() {

		ArrayList<DomainObject> list = new ArrayList<DomainObject>();
		list.add(AtomFactory.getInstance().getAtom(100, 100, "alpha"));
		list.add(AtomFactory.getInstance().getAtom(100, 100, "alpha"));
		ArrayList<ArrayList<String>> list2 = new ArrayList<ArrayList<String>>();

		for (DomainObject d : list) {
			list2.add(d.makeList());
		}

		assertFalse(list2.isEmpty());
		assertFalse(list2.get(0).isEmpty());
		assertFalse(list2.get(1).isEmpty());

		assertTrue(list2.get(0).get(0).equals(list2.get(1).get(0)));
		assertTrue(list2.get(0).get(1).equals(list2.get(1).get(1)));
		assertTrue(list2.get(0).get(2).equals(list2.get(1).get(2)));
		assertTrue(list2.get(0).get(3).equals(list2.get(1).get(3)));
		assertTrue(list2.get(0).get(4).equals(list2.get(1).get(4)));
		assertTrue(list2.get(0).get(5).equals(list2.get(1).get(5)));

	}

	@Test
	public void test_for_atom() {
		Alpha a = new Alpha(new Location(100, 100));
		ArrayList<String> list2 = a.makeList();
		assertFalse(list2.isEmpty());
		assertTrue(list2.get(0).equals("Atom"));
		assertTrue(list2.get(1).equals("alpha"));
		assertTrue(list2.get(2).equals("100"));
		assertTrue(list2.get(3).equals("100"));
		assertTrue(list2.get(4).equals(String.valueOf(a.getDx())));
		assertTrue(list2.get(5).equals(String.valueOf(a.getDy())));

	}

	@Test
	public void test_for_null_structure_molecule() {
		GammaMolecule g = new GammaMolecule(new Location(150, 170));

		ArrayList<String> list2 = g.makeList();

		assertFalse(list2.isEmpty());
		assertTrue(list2.get(0).equals("Molecule"));
		assertTrue(list2.get(1).equals("gamma"));
		assertTrue(list2.get(2).equals("150"));
		assertTrue(list2.get(3).equals("170"));
		assertTrue(list2.get(4).equals(String.valueOf(g.getDx())));
		assertTrue(list2.get(5).equals(String.valueOf(g.getDy())));

	}

	@Test
	public void test_for_powerup() {

		GammaPowerup b = new GammaPowerup(new Location(300, 400));
		ArrayList<String> list2 = b.makeList();
		assertFalse(list2.isEmpty());
		assertTrue(list2.get(0).equals("Powerup"));
		assertTrue(list2.get(1).equals("gamma"));
		assertTrue(list2.get(2).equals("300"));
		assertTrue(list2.get(3).equals("400"));
		assertTrue(list2.get(4).equals(String.valueOf(b.getDx())));
		assertTrue(list2.get(5).equals(String.valueOf(b.getDy())));

	}

	@Test
	public void test_with_molecule_linear() {
		AlphaMolecule a = new AlphaMolecule(new Location(560, 130));
		a.setStructure("linear");
		ArrayList<String> list2 = a.makeList();

		assertFalse(list2.isEmpty());
		assertFalse(list2.get(0).isEmpty());

		assertTrue(list2.get(0).equals("Molecule"));
		assertTrue(list2.get(1).equals("alpha-2"));
		assertTrue(list2.get(2).equals("560"));
		assertTrue(list2.get(3).equals("130"));
		assertTrue(list2.get(4).equals(String.valueOf(a.getDx())));
		assertTrue(list2.get(5).equals(String.valueOf(a.getDy())));

	}

	@Test
	public void test_with_non_linear() {
		AlphaMolecule a = new AlphaMolecule(new Location(123, 120));
		a.setStructure("non-linear");
		ArrayList<String> list2 = a.makeList();

		assertFalse(list2.isEmpty());
		assertFalse(list2.get(0).isEmpty());

		assertTrue(list2.get(0).equals("Molecule"));
		assertTrue(list2.get(1).equals("alpha-1"));
		assertTrue(list2.get(2).equals("123"));
		assertTrue(list2.get(3).equals("120"));
		assertTrue(list2.get(4).equals(String.valueOf(a.getDx())));
		assertTrue(list2.get(5).equals(String.valueOf(a.getDy())));
	}

	@Test
	public void test_for_shooter() {
		Shooter a = new Shooter();

		a.setAngle(10.2);
		a.setLoc(new Location(230, 450));

		ArrayList<String> list2 = a.makeList();

		assertFalse(list2.isEmpty());
		assertTrue(list2.get(0).equals("Shooter"));
		assertTrue(list2.get(1).equals("230"));
		assertTrue(list2.get(2).equals("450"));
		assertTrue(list2.get(3).equals(String.valueOf(a.getDx())));
		assertTrue(list2.get(4).equals(String.valueOf(a.getDy())));
		assertTrue(list2.get(5).equals(String.valueOf(a.getAngle())));

	}

}
