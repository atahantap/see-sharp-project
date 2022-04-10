package domain.save;

import java.util.ArrayList;
import java.util.HashMap;

public class Client {

	SaveServiceAdapter service;

	public Client(String servicet) {

		if (servicet == "MongoDB") {
			service = new MongoDBService();
		} else {
			service = new LocalService();
		}
	}

	public void saveGame(ArrayList<ArrayList<String>> list, double score, HashMap<Integer, Integer> atominv,
			HashMap<Integer, Integer> powerupinv, HashMap<Integer, Integer> moleculeTypes) {

		service.saveGame(list, score, atominv, powerupinv, moleculeTypes);
	}

	public void loadGame(HashMap<Integer, Integer> atominv, HashMap<Integer, Integer> powerupinv,
			HashMap<Integer, Integer> moleculeCount) {
		service.loadGame(atominv, powerupinv, moleculeCount);
	}

}
