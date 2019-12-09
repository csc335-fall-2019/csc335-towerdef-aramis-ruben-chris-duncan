package viewable.gameObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WaveGenerator {
	
	private List<Minion> sendEm;
	private List<String> one;
	private List<String> two;
	private List<String> three;
	private List<Integer> points;
	private int additionalMinions;

	public WaveGenerator() {
		sendEm = new ArrayList<Minion>();
		one = new ArrayList<String>();
		two = new ArrayList<String>();
		three = new ArrayList<String>();
		points = new ArrayList<Integer>();
		additionalMinions = 0;
		one.add("Hound");
		one.add("Goblin");
		two.add("Hound");
		two.add("Goblin");
		two.add("Wolf Rider");
		two.add("Goblin Knight");
		three.add("Hound");
		three.add("Goblin");
		three.add("Wolf Rider");
		three.add("Goblin Knight");
		three.add("Charger");
		three.add("Ogre");	
		points.add(1);
		points.add(1);
		points.add(2);
		points.add(3);
		points.add(4);
		points.add(5);
		points.add(10);
	}
	
	public List<Minion> generateRandom(int round) {
		sendEm = new ArrayList<Minion>();
		int enemyPoints = (round * 10) + additionalMinions;
		List<String> useableMinions = null;
		Minion minion = null;
		int points = 0;
		if (round < 6) {
			useableMinions = new ArrayList<String>(one);
		} else if (round < 11) {
			useableMinions = new ArrayList<String>(two);
		} else {
			useableMinions = new ArrayList<String>(three);
		}
		if (round % 5 == 0) {
			for (int i = 0; i < (round / 5); i++) {
				sendEm.add(new Boss());
				enemyPoints -= 50;
			}
		}
		while (enemyPoints > 0) {
			Collections.shuffle(useableMinions);
			String name = useableMinions.get(0);
			if (name.equals("Hound")) {
				minion = new Hound();
				points = 1;
			} else if (name.equals("Goblin")) {
				minion = new Goblin();
				points = 1;
			} else if (name.equals("Goblin Knight")) {
				minion = new GoblinKnight();
				points = 3;
			} else if (name.equals("Wolf Rider")) {
				minion = new WolfRider();
				points = 3;
			} else if (name.equals("Charger")) {
				minion = new Charger();
				points = 5;
			} else if (name.equals("Ogre")) {
				minion = new Ogre();
				points = 5;
			}
			sendEm.add(minion);
			enemyPoints -= points;
		}
		return sendEm;
	}
	
	public void addMinions(int amount) {
		
	}
}
