package domain.save;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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

public class MongoDBService implements SaveServiceAdapter {

	public MongoDBService() {

	}

	@SuppressWarnings("unchecked")
	public void loadGame(HashMap<Integer, Integer> atominv, HashMap<Integer, Integer> powerupinv,
			HashMap<Integer, Integer> moleculeCount) {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE); // e.g. or Log.WARNING, etc.
		MongoClient mongoClient = MongoClients
				.create("mongodb+srv://comp302_user:comp302_password@sandbox.v2mqr.mongodb.net/"); // uri connection to
																									// the server
		MongoDatabase database = mongoClient.getDatabase("Comp302"); // selecting the database
		MongoCollection<Document> collection = database.getCollection("SeeSharpCollection");
		Document doc = new Document();
		doc = collection.find(eq("username", "C13#")).first();
		doc.get("username");
		doc.get("level");

		double difficulty = (Double) doc.get("difficulty");
		double score = (Double) doc.get("score");
		int health = (int) doc.get("health");
		int timeDec = (Integer) doc.get("timeDec");
		int timeFrac = (Integer) doc.get("timeFrac");

		ArrayList<DomainObject> list = new ArrayList<DomainObject>();
		ArrayList<ArrayList<String>> arrList = (ArrayList<ArrayList<String>>) doc.get("onScreen");
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

		for (ArrayList<String> l : arrList) {

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
				list.add(a);
				break;

			case "Molecule":
				String type1 = l.get(1);
				int x1 = Integer.parseInt(l.get(2));
				int y1 = Integer.parseInt(l.get(3));
				int dx1 = Integer.parseInt(l.get(4));
				int dy1 = Integer.parseInt(l.get(5));
				Molecule a1 = MoleculeFactory.getInstance().getMolecule(type1, new Location(x1, y1));
				a1.setSpeed(dx1, dy1);
				list.add(a1);
				break;
			case "Powerup":
				String type2 = l.get(1);
				int x2 = Integer.parseInt(l.get(2));
				int y2 = Integer.parseInt(l.get(3));
				int dx2 = Integer.parseInt(l.get(4));
				int dy2 = Integer.parseInt(l.get(5));
				Powerup a2 = PowerupFactory.getInstance().getPowerup(type2, new Location(x2, y2));
				a2.setSpeed(dx2, dy2);
				list.add(a2);
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
				list.add(a3);
				break;
			default:
				break;

			}

		}
		HashMap<String, Double> mapDifficulty = new HashMap<String, Double>();
		mapDifficulty.put("difficulty", (Double) doc.get("difficulty"));

		m.put(Integer.parseInt(shield.get(0)), Integer.parseInt(shield.get(1)));
		m.put(Integer.parseInt(shield.get(2)), Integer.parseInt(shield.get(3)));
		m.put(Integer.parseInt(shield.get(4)), Integer.parseInt(shield.get(5)));
		m.put(Integer.parseInt(shield.get(6)), Integer.parseInt(shield.get(7)));

		Game.getInstance().getPlayers().get(0).getPlayerState().c("atom");

		Game.getInstance().setDomainObjectArr(list);
		Game.getInstance().getPlayers().get(0).getPlayerState().setShieldInventory(m);
		Game.getInstance().getPlayers().get(0).getPlayerState().setHealth(health);
		Game.getInstance().getPlayers().get(0).getPlayerState().setScore(score);

		Game.getInstance().gameState.setDec(timeDec);
		Game.getInstance().gameState.setFrac(timeFrac);

		Game.getInstance().gameState.setDropPeriodsGivenDifficulty(mapDifficulty);
		Game.getInstance().gameState.setDifficulty(difficulty);

		System.out.println("Loaded from database.");

	}

	public void saveGame(ArrayList<ArrayList<String>> list, double score, HashMap<Integer, Integer> atominv,
			HashMap<Integer, Integer> powerupinv, HashMap<Integer, Integer> moleculeTypes) {

		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE); // e.g. or Log.WARNING, etc.
		MongoClient mongoClient = MongoClients
				.create("mongodb+srv://comp302_user:comp302_password@sandbox.v2mqr.mongodb.net/"); // uri connection to
		MongoDatabase database = mongoClient.getDatabase("Comp302"); // selecting the database
		MongoCollection<Document> collection = database.getCollection("SeeSharpCollection"); // collection
		Document doc = new Document();
		ArrayList<ArrayList<String>> map = new ArrayList<ArrayList<String>>();
		ArrayList<String> temp;
		ArrayList<String> shield = new ArrayList<String>();

		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		m = Game.getInstance().getPlayers().get(0).getPlayerState().getShieldInventory();

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

		for (Entry<Integer, Integer> e : m.entrySet()) {
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

		doc.append("username", "C13#");
		doc.put("difficulty", Game.getInstance().gameState.getDifficulty());
		doc.put("score", Game.getInstance().gameState.getPlayers().get(0).getPlayerState().getScore());
		doc.put("timeDec", Game.getInstance().gameState.getDec());
		doc.put("timeFrac", Game.getInstance().gameState.getFrac());
		doc.put("health", Game.getInstance().gameState.getPlayers().get(0).getPlayerState().getHealth_points());
		doc.append("onScreen", list);
		doc.append("invAtom", map);
		doc.append("invMole", map1);
		doc.put("invPowerup", map2);
		doc.append("shield", shield);
		Document my_doc = new Document();
		my_doc = collection.find(eq("username", "C13#")).first();
		if (my_doc == null) {
			collection.insertOne(doc);
		} else {
			Bson filter = eq("username", "C13#");
			collection.deleteOne(filter);
			collection.insertOne(doc);
		}
		System.out.println("Saved to database.");

	}

}
