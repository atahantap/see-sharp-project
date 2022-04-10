package domain.save;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import domain.atom.Atom;
import domain.factory.AtomFactory;
import domain.factory.MoleculeFactory;
import domain.factory.PowerupFactory;
import domain.factory.ReactionBlockerFactory;
import domain.game.DomainObject;
import domain.game.Game;
import domain.game.Location;
import domain.molecule.Molecule;
import domain.powerup.Powerup;
import domain.reactionBlockers.ReactionBlocker;

public class LocalService implements SaveServiceAdapter {

	String username = "Player";
	String FILEPATH = "src/saves/" + username + ".json";

	public LocalService() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadGame(HashMap<Integer, Integer> atominv, HashMap<Integer, Integer> powerupinv,
			HashMap<Integer, Integer> moleculeCount) {

		username = Game.getInstance().gameState.getPlayers().get(0).getName();
		FILEPATH = "src/saves/" + username + ".json";
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(FILEPATH)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONObject doc = (JSONObject) obj;

			double difficulty = (Double) doc.get("difficulty");
			double score = (Double) doc.get("score");
			int health = ((Long) doc.get("health")).intValue();
			int timeDec = ((Long) doc.get("timeDec")).intValue();
			int timeFrac = ((Long) doc.get("timeFrac")).intValue();

			ArrayList<DomainObject> listDO = new ArrayList<DomainObject>();
			ArrayList<ArrayList<String>> listOnScreen = (ArrayList<ArrayList<String>>) doc.get("onScreen");
			ArrayList<ArrayList<String>> listAtomInv = (ArrayList<ArrayList<String>>) doc.get("invAtom");
			ArrayList<ArrayList<String>> listMoleInv = (ArrayList<ArrayList<String>>) doc.get("invMole");
			ArrayList<ArrayList<String>> listPowerupInv = (ArrayList<ArrayList<String>>) doc.get("invPowerup");
			ArrayList<String> shield = (ArrayList<String>) doc.get("shield");
			HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

			for (ArrayList<String> l : listAtomInv) {

				atominv.put(Integer.parseInt(l.get(0)), Integer.parseInt(l.get(1)));
			}
			for (ArrayList<String> l : listMoleInv) {

				moleculeCount.put(Integer.parseInt(l.get(0)), Integer.parseInt(l.get(1)));
			}
			for (ArrayList<String> l : listPowerupInv) {

				powerupinv.put(Integer.parseInt(l.get(0)), Integer.parseInt(l.get(1)));
			}
			for (ArrayList<String> l : listOnScreen) {

				String s = l.get(0);

				switch (s) {
				case "Atom":
					String type = l.get(1);
					int x = Integer.parseInt(l.get(2));
					int y = Integer.parseInt(l.get(3));
					int dx = Integer.parseInt(l.get(4));
					int dy = Integer.parseInt(l.get(5));
					Atom a = AtomFactory.getInstance().getAtom(x, y, type);
					a.setSpeed(dx, dy);
					listDO.add(a);
					break;

				case "Molecule":
					String type1 = l.get(1);
					int x1 = Integer.parseInt(l.get(2));
					int y1 = Integer.parseInt(l.get(3));
					int dx1 = Integer.parseInt(l.get(4));
					int dy1 = Integer.parseInt(l.get(5));
					Molecule a1 = MoleculeFactory.getInstance().getMolecule(type1, new Location(x1, y1));
					a1.setSpeed(dx1, dy1);
					listDO.add(a1);
					break;
				case "Powerup":
					String type2 = l.get(1);
					int x2 = Integer.parseInt(l.get(2));
					int y2 = Integer.parseInt(l.get(3));
					int dx2 = Integer.parseInt(l.get(4));
					int dy2 = Integer.parseInt(l.get(5));
					Powerup a2 = PowerupFactory.getInstance().getPowerup(type2, new Location(x2, y2));
					a2.setSpeed(dx2, dy2);
					listDO.add(a2);
					break;
				case "Shooter":
					int x3 = Integer.parseInt(l.get(1));
					int y3 = Integer.parseInt(l.get(2));
					double angle = Double.parseDouble(l.get(5));
					Game.getInstance().getPlayers().get(0).getShooter().setLoc(new Location(x3, y3));
					Game.getInstance().getPlayers().get(0).getShooter().setAngle(angle);
					break;
				case "ReactionBlocker":
					String type3 = l.get(1);
					int x4 = Integer.parseInt(l.get(2));
					int y4 = Integer.parseInt(l.get(3));
					int dx4 = Integer.parseInt(l.get(4));
					int dy4 = Integer.parseInt(l.get(5));
					ReactionBlocker a3 = ReactionBlockerFactory.getInstance().getReactionBlocker(type3,
							new Location(x4, y4));
					a3.setSpeed(dx4, dy4);
					listDO.add(a3);
					break;
				default:
					break;

				}

			}
			HashMap<String, Double> mapDifficulty = new HashMap<String, Double>();
			mapDifficulty.put("difficulty", (Double) doc.get("difficulty"));

			HashMap<Integer, String> moleculeTypes = Game.getInstance().gameState.moleculeTypes;
			moleculeTypes.put(1, (String) doc.get("alphaType"));
			moleculeTypes.put(2, (String) doc.get("betaType"));

			HashMap<Integer, Boolean> moleculeRotations = Game.getInstance().gameState.moleculeRotations;
			moleculeRotations.put(1, (Boolean) doc.get("isAlphaRotating"));
			moleculeRotations.put(2, (Boolean) doc.get("isBetaRotating"));

			m.put(Integer.parseInt(shield.get(0)), Integer.parseInt(shield.get(1)));
			m.put(Integer.parseInt(shield.get(2)), Integer.parseInt(shield.get(3)));
			m.put(Integer.parseInt(shield.get(4)), Integer.parseInt(shield.get(5)));
			m.put(Integer.parseInt(shield.get(6)), Integer.parseInt(shield.get(7)));

			Game.getInstance().getPlayers().get(0).getPlayerState().c("atom");

			Game.getInstance().setDomainObjectArr(listDO);
			Game.getInstance().getPlayers().get(0).getPlayerState().setShieldInventory(m);
			Game.getInstance().getPlayers().get(0).getPlayerState().setHealth(health);
			Game.getInstance().getPlayers().get(0).getPlayerState().setScore(score);

			Game.getInstance().gameState.setDec(timeDec);
			Game.getInstance().gameState.setFrac(timeFrac);

			Game.getInstance().gameState.setDropPeriodsGivenDifficulty(mapDifficulty);
			Game.getInstance().gameState.setDifficulty(difficulty);

			System.out.println("Loaded from local directory.");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void saveGame(ArrayList<ArrayList<String>> list, double score, HashMap<Integer, Integer> atominv,
			HashMap<Integer, Integer> powerupinv, HashMap<Integer, Integer> moleculeTypes) {
		// TODO Auto-generated method stub
		username = Game.getInstance().gameState.getPlayers().get(0).getName();
		FILEPATH = "src/saves/" + username + ".json";
		Document doc = new Document();
		ArrayList<ArrayList<String>> map = new ArrayList<ArrayList<String>>();
		ArrayList<String> temp;
		ArrayList<String> shield = new ArrayList<String>();

		HashMap<Integer, Integer> mapShieldInv = new HashMap<Integer, Integer>();

		mapShieldInv = Game.getInstance().getPlayers().get(0).getPlayerState().getShieldInventory();

		for (Entry<Integer, Integer> e : atominv.entrySet()) {
			temp = new ArrayList<String>();
			temp.add(String.valueOf(e.getKey()));
			temp.add(String.valueOf(e.getValue()));
			map.add(temp);
		}

		ArrayList<ArrayList<String>> map1 = new ArrayList<ArrayList<String>>();

		for (Entry<Integer, Integer> e : moleculeTypes.entrySet()) {
			temp = new ArrayList<String>();
			temp.add(String.valueOf(e.getKey()));
			temp.add(String.valueOf(e.getValue()));
			map1.add(temp);

		}

		for (Entry<Integer, Integer> e : mapShieldInv.entrySet()) {
			shield.add(String.valueOf(e.getKey()));
			shield.add(String.valueOf(e.getValue()));
		}
		ArrayList<ArrayList<String>> map2 = new ArrayList<ArrayList<String>>();

		for (Entry<Integer, Integer> e : powerupinv.entrySet()) {
			temp = new ArrayList<String>();
			temp.add(String.valueOf(e.getKey()));
			temp.add(String.valueOf(e.getValue()));
			map2.add(temp);
		}

		doc.put("username", username);
		doc.put("difficulty", Game.getInstance().gameState.getDifficulty());
		doc.put("score", Game.getInstance().gameState.getPlayers().get(0).getPlayerState().getScore());
		doc.put("timeDec", Game.getInstance().gameState.getDec());
		doc.put("timeFrac", Game.getInstance().gameState.getFrac());
		doc.put("health", Game.getInstance().gameState.getPlayers().get(0).getPlayerState().getHealth_points());
		doc.put("onScreen", list);
		doc.put("invAtom", map);
		doc.put("invMole", map1);
		doc.put("invPowerup", map2);
		doc.put("shield", shield);
		doc.put("alphaType", Game.getInstance().gameState.moleculeTypes.get(1));
		doc.put("betaType", Game.getInstance().gameState.moleculeTypes.get(2));
		doc.put("isAlphaRotating", Game.getInstance().gameState.moleculeRotations.get(1));
		doc.put("isBetaRotating", Game.getInstance().gameState.moleculeRotations.get(2));

		try {
			FileWriter file = new FileWriter(FILEPATH);
			file.write(doc.toJson());
			file.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Saved to local directory.");
	}

}
