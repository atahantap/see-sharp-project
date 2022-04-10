package demo.test.test_ufkun;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import domain.game.Blender;
import domain.game.Player;

public class Test_Ufkun {

	Player player_enoughInv;
	Player player_zeroInv;

	HashMap<Integer, Integer> enoughInv;
	HashMap<Integer, Integer> zeroInv;

	int source_outOfBounds;
	int destination_outOfBounds;

	int source_larger;
	int destination_smaller;

	int source_normal;
	int destination_normal;

	int source_3;
	int destination_3;

	@Before
	public void setUp() throws Exception {
		player_enoughInv = new Player("enough_inventory", 0);
		player_zeroInv = new Player("zero_inventory", 1);

		enoughInv = new HashMap<Integer, Integer>();
		zeroInv = new HashMap<Integer, Integer>();

		player_enoughInv.getPlayerState().initializeInventory(player_enoughInv.player_state.getAtom_inventory(), 5);
		player_zeroInv.getPlayerState().initializeInventory(player_zeroInv.player_state.getAtom_inventory(), 0);

		source_outOfBounds = 5;
		destination_outOfBounds = 6;

		source_larger = 2;
		destination_smaller = 1;

		source_normal = 3;
		destination_normal = 4;

		source_3 = 3;
		destination_3 = 3;

	}

	@Test
	public void correctBlendTest() {

		Blender.getInstance().blendAtom(player_enoughInv, source_normal, destination_normal);

		assertEquals((Integer) 6, player_enoughInv.getPlayerState().getAtom_inventory().get(4));
		assertEquals((Integer) 3, player_enoughInv.getPlayerState().getAtom_inventory().get(3));
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void rankOutOfBoundsTest() {

		exceptionRule.expect(NullPointerException.class);

		Blender.getInstance().blendAtom(player_enoughInv, source_outOfBounds, destination_outOfBounds);

		assertEquals((Integer) 5, player_enoughInv.getPlayerState().getAtom_inventory().get(5));
		assertEquals((Integer) 5, player_enoughInv.getPlayerState().getAtom_inventory().get(6));

	}

	@Test
	public void unblendableRanksTest() {

		Blender.getInstance().blendAtom(player_enoughInv, source_larger, destination_smaller);

		assertEquals((Integer) 5, player_enoughInv.getPlayerState().getAtom_inventory().get(2));
		assertEquals((Integer) 5, player_enoughInv.getPlayerState().getAtom_inventory().get(1));
	}

	@Test
	public void sameRankBlendTest() {

		Blender.getInstance().blendAtom(player_enoughInv, source_3, destination_3);

		assertEquals((Integer) 5, player_enoughInv.getPlayerState().getAtom_inventory().get(3));
		assertEquals((Integer) 5, player_enoughInv.getPlayerState().getAtom_inventory().get(3));
	}

	@Test
	public void blendWithNoInventoryTest() {

		Blender.getInstance().blendAtom(player_zeroInv, source_normal, destination_normal);

		assertEquals((Integer) 0, player_zeroInv.getPlayerState().getAtom_inventory().get(4));
		assertEquals((Integer) 0, player_zeroInv.getPlayerState().getAtom_inventory().get(3));
	}

	@After
	public void tearDown() throws Exception {

		player_enoughInv.getPlayerState().updateAtomInventory(1, 5);
		player_enoughInv.getPlayerState().updateAtomInventory(2, 5);
		player_enoughInv.getPlayerState().updateAtomInventory(3, 5);
		player_enoughInv.getPlayerState().updateAtomInventory(4, 5);

		player_zeroInv.getPlayerState().updateAtomInventory(1, 0);
		player_zeroInv.getPlayerState().updateAtomInventory(2, 0);
		player_zeroInv.getPlayerState().updateAtomInventory(3, 0);
		player_zeroInv.getPlayerState().updateAtomInventory(4, 0);

	}

}
