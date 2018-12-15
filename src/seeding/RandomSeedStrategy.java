package seeding;

import java.util.concurrent.ThreadLocalRandom;

import mapObjects.District;
import mapObjects.Precinct;
import mapObjects.State;

public class RandomSeedStrategy implements SeedStrategy {

	@Override
	public void seed(State s) {
		District unassigned = s.getUnassignedDistrict();
		int numDistricts = s.getNumDistricts();
		getSeedsRandomly(unassigned, numDistricts);
		for (Precinct seed : unassigned.getSeeds()) {
			District d = new District();
			s.addDistrict(d);
			d.addPrecinct(seed);
			unassigned.removePrecinct(seed);
		}  
	}

	private Precinct pickRandomSeed(District unassigned) {
		int rand = ThreadLocalRandom.current().nextInt(unassigned.getNumPrecincts());
		return unassigned.getPrecinctById(rand);
	}

	private void getSeedsRandomly(District unassigned, int numSeeds) {
		for (int i = 0; i < numSeeds;) {
			Precinct toAdd = this.pickRandomSeed(unassigned);
			if (unassigned.addSeed(toAdd)) {
				i++;
			}
		}
	}
}
