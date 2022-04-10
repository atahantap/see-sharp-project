package domain.save;

import java.util.ArrayList;
import java.util.HashMap;

public interface SaveServiceAdapter {
	void saveGame(ArrayList<ArrayList<String>> list, double score, HashMap<Integer, Integer> atominv,
			HashMap<Integer, Integer> powerupinv, HashMap<Integer, Integer> moleculeTypes);

	void loadGame(HashMap<Integer, Integer> atominv, HashMap<Integer, Integer> powerupinv,
			HashMap<Integer, Integer> moleculeCount);

}
