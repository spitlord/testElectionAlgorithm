package algorithm;

import java.util.concurrent.ThreadLocalRandom;
import mapObjects.District;
import mapObjects.Precinct;
import mapObjects.State;
import properties.PropertiesManager;
import seeding.SeedStrategy;

public class RegionGrowing extends Algorithm {

	private SeedStrategy seedStrategy;
	private static double RegionGrowingThreshold;
        public static boolean complete;

	public RegionGrowing(State s, ObjectiveFunction of, SeedStrategy seedStrategy) {
		super();
		this.currentState = s;
		this.objectiveFunction = of;
		this.seedStrategy = seedStrategy;
		RegionGrowingThreshold = Double.parseDouble(PropertiesManager.getInstance().getValue("RegionGrowingThreshold"));
	}

	@Override
	public void run() {
		seedStrategy.seed(currentState);
		District unassigned = currentState.getUnassignedDistrict();
		while (unassigned.getPrecincts().size() > currentState.getNumPrecincts() * RegionGrowingThreshold) {
			District d = this.selectDistrictToGrow();
			Precinct precinctToMove = d.getRandomCandidate();
			Move tempMove = new Move(precinctToMove, unassigned, d);
			tempMove.setIsFinalized(true);
			moves.push(tempMove);
			d.addPrecinct(precinctToMove);
			unassigned.removePrecinct(precinctToMove);
		}
//		while (this.checkTerimanationConditions() != true) {
//			District d = this.selectDistrictToGrow();
//			Precinct precinctToMove = d.findMovablePrecinct(currentState, objectiveFunction);
//			Move tempMove = new Move(precinctToMove, unassigned, d);
//			tempMove.setIsFinalized(true);
//			moves.push(tempMove);
//			d.addPrecinct(precinctToMove);
//			unassigned.removePrecinct(precinctToMove);
//		}
                complete = true;
	}

	@Override
	protected boolean checkTerimanationConditions() {
		District unassigned = this.currentState.getUnassignedDistrict();
		return unassigned.getPrecincts().isEmpty();
	}

	private District selectDistrictToGrow() {
            District d = this.currentState.getLowestPolulationDistrict();
            while (!hasUnassignedCandidates(d)) {
                int randomInd = ThreadLocalRandom.current().nextInt(this.currentState.getDistricts().size());
                d = this.currentState.getDistricts().get(randomInd);
            } 
            return d;
	}
        
        private boolean hasUnassignedCandidates(District d) {
            for (Precinct p : d.getCandidates()) {
                if (p.getDistrictID() == 0) {
                    return true;
                }
            }
            return false;
        }

	public void setSeedStrategy(SeedStrategy seedStrategy) {
		this.seedStrategy = seedStrategy;
	}

    public boolean isComplete() {
        return complete;
    }
        
}
