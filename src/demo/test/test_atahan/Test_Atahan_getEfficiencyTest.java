/**
 * 
 */
package demo.test.test_atahan;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import domain.atom.Alpha;
import domain.atom.Atom;
import domain.game.Location;
import domain.shield.EtaShieldDecorator;

/**
 * @author atahantap atap18
 *
 */
public class Test_Atahan_getEfficiencyTest {

	Atom p5n5_atom;

	Atom p_1n5_atom;
	Atom p5n_1_atom;

	Atom p0n5_atom;

	Atom p5n3_atom;
	Atom p4n6_atom;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		p5n3_atom = new Alpha(new Location(0, 0));
		p5n3_atom.setProtonNum(5);
		p5n3_atom.setNeutronNum(3);
		p5n3_atom.setEfficiency(0.51);
		// eff: 0.51

		p4n6_atom = new Alpha(new Location(0, 0));
		p4n6_atom.setProtonNum(4);
		p4n6_atom.setNeutronNum(6);
		p4n6_atom.setEfficiency(0.425);
		// eff: 0.425

		p5n5_atom = new Alpha(new Location(0, 0));
		p5n5_atom.setProtonNum(5);
		p5n5_atom.setNeutronNum(5);
		p5n5_atom.setEfficiency(0.85);
		// eff = 0.85

		p_1n5_atom = new Alpha(new Location(0, 0));
		p_1n5_atom.setProtonNum(-1);
		p_1n5_atom.setNeutronNum(5);
		p_1n5_atom.setEfficiency(5.95);
		// eff = 5.95 (there is something wrong)

		p5n_1_atom = new Alpha(new Location(0, 0));
		p5n_1_atom.setProtonNum(-1);
		p5n_1_atom.setNeutronNum(5);
		p5n_1_atom.setEfficiency(-0.17);
		// eff: -0.17 (there is something wrong)

		p0n5_atom = new Alpha(new Location(0, 0));
		p0n5_atom.setProtonNum(0);
		p0n5_atom.setNeutronNum(5);
		// will throw exception

	}

	@Test
	public void testCorrectEfficiencyCalculation() {

		/**
		 * shield with 5 protons 3 neutrons
		 */
		EtaShieldDecorator Eta_p5n3 = new EtaShieldDecorator(p5n3_atom);
		assertTrue(isTrue(Eta_p5n3.getEfficiency(), 0.706));

		/**
		 * shield with 4 protons 6 neutrons
		 */
		EtaShieldDecorator Eta_p4n6 = new EtaShieldDecorator(p4n6_atom);
		assertTrue(isTrue(Eta_p4n6.getEfficiency(), 0.7125));

	}

	@Test
	public void testSameProtonAndNeutronCalculation() {

		/**
		 * shield with 5 protons 5 neutrons
		 */
		EtaShieldDecorator Eta_p5n5 = new EtaShieldDecorator(p5n5_atom);
		assertTrue(isTrue(Eta_p5n5.getEfficiency(), 0.8575));

	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testExpectedArithmeticException() {

		/**
		 * shield with 0 protons 5 neutrons
		 */
		EtaShieldDecorator Eta_p0n5 = new EtaShieldDecorator(p0n5_atom);
		exceptionRule.expect(ArithmeticException.class);
		Eta_p0n5.getEfficiency();

	}

	@Test
	public void testNegativeProtonOrNeutron() {

		/**
		 * shield with -1 protons 5 neutrons
		 */
		EtaShieldDecorator Eta_p_1n5 = new EtaShieldDecorator(p_1n5_atom);
		assertTrue(isTrue(Eta_p_1n5.getEfficiency(), 0));

		/**
		 * shield with 5 protons -1 neutrons
		 */
		EtaShieldDecorator Eta_p5n_1 = new EtaShieldDecorator(p5n_1_atom);
		assertTrue(isTrue(Eta_p5n_1.getEfficiency(), 0));

	}

	@Test
	public void testRecursiveCalculation() {

		// one recursion call is already tested in BlackBox testing.

		/**
		 * shield with shield inside with 5 protons 3 neutrons
		 */
		EtaShieldDecorator Eta_eta_p5n3 = new EtaShieldDecorator(new EtaShieldDecorator(p5n3_atom));
		assertTrue(isTrue(Eta_eta_p5n3.getEfficiency(), 0.8236));

		/**
		 * shield with shield inside with shield inside with 4 protons 6 neutrons
		 */
		EtaShieldDecorator Eta_eta_eta_p4n6 = new EtaShieldDecorator(
				new EtaShieldDecorator(new EtaShieldDecorator(p4n6_atom)));
		assertTrue(isTrue(Eta_eta_eta_p4n6.getEfficiency(), 0.928125));

	}

	// method to avoid complications on double decimal roundings
	private boolean isTrue(double value, double real) {

		double epsilon = 0.005;
		if (Math.abs(value - real) < epsilon)
			return true;
		else
			return false;

	}

}
