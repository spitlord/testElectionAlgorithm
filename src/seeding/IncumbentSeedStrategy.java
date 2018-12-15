package seeding;

import java.util.Set;

import dataTypes.Representative;
import mapObjects.District;
import mapObjects.Precinct;
import mapObjects.State;

public class IncumbentSeedStrategy implements SeedStrategy {

	@Override
	public void seed(State s) {
		Set<Representative> reps = s.getRepresentatives();
		District unassigned = s.getUnassignedDistrict();
		getSeedsByRep(unassigned, reps);
		for (Precinct seed : unassigned.getSeeds()) {
			District d = new District();
			s.addDistrict(d);
			d.addPrecinct(seed);
			unassigned.removePrecinct(seed);
		}
	}

	public void getSeedsByRep(District unassigned, Set<Representative> reps) {
		reps.forEach(rep -> unassigned.addSeed(rep.getHomePrecinct()));
	}
}
