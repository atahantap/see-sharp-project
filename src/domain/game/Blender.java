package domain.game;

public class Blender {
	private static Blender instance;

	private Blender() {
	}

	public static Blender getInstance() {
		if (instance == null) {
			instance = new Blender();
		}
		return instance;
	}

	public void blendOrBreakAtom(Player player, int source, int destination) {
		if (source > destination)
			breakAtom(player, source, destination);
		if (source < destination)
			blendAtom(player, source, destination);

	}

	/*
	 *
	 * 
	 * MODIFIES player.player_state.atom_inventory
	 * 
	 * 
	 * EFFECTS: Performs blending process and updates atom inventory of given
	 * player. ONLY if ((destination > source) && (1 <= destinaiton and source <= 4)
	 * && (player.getPlayerState().getAtomInventory != null) && (values of each key
	 * of player.getPlayerState().getAtomInventory > 0) && (destination != source) )
	 * 
	 * 2 Alpha atoms to get Beta, 3 to get Gamma, 4 to get Sigma. Or blend 2 Betas
	 * to get Gamma, 3 to get Sigma. Or blend 2 Gamma to get Sigma.
	 * 
	 * 
	 * 
	 */
	public void blendAtom(Player player, int source, int destination) {

		PlayerState CurrentPS = player.getPlayerState();
		int numInputAtom = CurrentPS.getAtom_inventory().get(source);
		boolean illegal = false;

		switch (source) {
		case 1:
			switch (destination) {
			case 2:
				if (numInputAtom >= 2)
					CurrentPS.updateAtomInventory(source, -2);
				else {
					System.out.println("There is no enough atom to blend!");
					illegal = true;
				}
				break;
			case 3:
				if (numInputAtom >= 3)
					CurrentPS.updateAtomInventory(source, -3);
				else {
					System.out.println("There is no enough atom to blend!");
					illegal = true;
				}
				break;
			case 4:
				if (numInputAtom >= 4)
					CurrentPS.updateAtomInventory(source, -4);
				else {
					System.out.println("There is no enough atom to blend!");
					illegal = true;
				}
				break;
			default:
				illegal = true;
				System.out.println("illegal argument");
				break;
			}
			break;
		case 2:
			switch (destination) {
			case 3:
				if (numInputAtom >= 2)
					CurrentPS.updateAtomInventory(source, -2);
				else {
					System.out.println("There is no enough atom to blend!");
					illegal = true;
				}
				break;
			case 4:
				if (numInputAtom >= 3)
					CurrentPS.updateAtomInventory(source, -3);
				else {
					System.out.println("There is no enough atom to blend!");
					illegal = true;
				}
				break;
			default:
				illegal = true;
				System.out.println("illegal argument");
				break;
			}
			break;
		case 3:
			switch (destination) {
			case 4:
				if (numInputAtom >= 2)
					CurrentPS.updateAtomInventory(source, -2);
				else {
					System.out.println("There is no enough atom to blend!");
					illegal = true;
				}
				break;
			default:
				illegal = true;
				System.out.println("illegal argument");
				break;
			}
			break;
		default:
			illegal = true;
			System.out.println("illegal argument");
			break;

		}
		if (!illegal)
			CurrentPS.updateAtomInventory(destination, 1);

	}

	public void breakAtom(Player player, int source, int destination) {

		PlayerState CurrentPS = player.getPlayerState();
		int numInputAtom = CurrentPS.getAtom_inventory().get(source);

		if (numInputAtom >= 1) {
			switch (source) {
			case 4:
				switch (destination) {
				case 3:
					CurrentPS.updateAtomInventory(destination, 2);
					break;
				case 2:
					CurrentPS.updateAtomInventory(destination, 3);
					break;
				case 1:
					CurrentPS.updateAtomInventory(destination, 4);
					break;
				default:
					System.out.println("illegal argument");
					break;
				}
				break;
			case 3:
				switch (destination) {
				case 2:
					CurrentPS.updateAtomInventory(destination, 2);
					break;
				case 1:
					CurrentPS.updateAtomInventory(destination, 3);
					break;
				default:
					System.out.println("illegal argument");
					break;
				}
				break;
			case 2:
				switch (destination) {
				case 1:
					CurrentPS.updateAtomInventory(destination, 2);
					break;
				default:
					System.out.println("illegal argument");
					break;
				}
				break;
			default:
				System.out.println("illegal argument");
				break;

			}
			CurrentPS.updateAtomInventory(source, -1);
		} else
			System.out.println("There is no enough atom to break!");

	}
}
