package mapObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import algorithm.Move;
import algorithm.ObjectiveFunction;

public class District {

	private int ID;
	private Map<Integer, Precinct> precincts;
	private int population;
	private ArrayList<Precinct> candidates;
	private Set<Precinct> seeds;

	public District() {
		this.ID = 0;
		this.precincts = new HashMap<Integer, Precinct>();
		this.population = 0;
		this.candidates = new ArrayList<Precinct>();
	}

	public Precinct getPrecinctById(int ID) {
		return this.precincts.get(ID);
	}

	public boolean addPrecinct(Precinct precinct) {
		if (!precincts.containsValue(precinct)) {
			precincts.put(precinct.getID(), precinct);
			updatePopulation(population + precinct.getPopulation());
			precinct.setDistrictID(this.ID);
			updateCandidates(precinct);
			return true;
		}
		return false;
	}

	public boolean removePrecinct(Precinct precinct) {
		if (precincts.containsKey(precinct.getID())) {
			precincts.remove(precinct.getID());
			updatePopulation(population - precinct.getPopulation());
			updateCandidates(precinct);
			return true;
		}
		return false;
	}

	public void updateCandidates(Precinct precinct) {
		if (precinct.getDistrictID() == this.ID) {
			precinct.getNeighbors().forEach(neighbor -> {
				if (neighbor.getDistrictID() != this.ID) {
					this.candidates.add(neighbor);
				}
			});
			return;
		}
		for (Precinct neighbor : precinct.getNeighbors()) {
			boolean hasNeighborInDistrict = false;
			for (Precinct newNeighbor : neighbor.getNeighbors()) {
				if (newNeighbor.getDistrictID() == this.ID) {
					hasNeighborInDistrict = true;
				}
			}
			if (!hasNeighborInDistrict) {
				this.candidates.remove(neighbor);
			}
		}
	}

	public Precinct findMovablePrecinct(State state, ObjectiveFunction of) {
		Precinct bestP = null;
		double bestOFV = -1;
		ArrayList<Precinct> candidateClone = new ArrayList<>(candidates);
		int i = 0;
		for (Precinct p : candidateClone) {
                        if (state.getUnassignedDistrict().getPrecincts().size() > 0 && p.getDistrictID() != 0){
                            continue;
                        }
			if (i == 5) return bestP;
			District src = state.getDistrict(p.getDistrictID());
			src.removePrecinct(p);
			this.addPrecinct(p);
			Move tempMove = new Move(p, src, state.getDistrict(p.getDistrictID()));
			double currentOFV = of.calculateObjectiveFunctionValue(state, tempMove);
                        System.out.println(currentOFV);
			if (currentOFV > bestOFV) {
				bestOFV = currentOFV;
				bestP = p;
			}
			this.removePrecinct(p);
			src.addPrecinct(p);
			i++;
		}
		return bestP;
	}

	public ArrayList<Precinct> getCandidates() {
		return candidates;
	}

	protected void setPrecincts(Map<Integer, Precinct> precincts) {
		this.precincts = precincts;
	}

	public Map<Integer, Precinct> getPrecincts() {
		return precincts;
	}

	public void updatePopulation(int population) {
		this.population = population;
	}

	public int getID() {
		return ID;
	}

	public int getPopulation() {
		return population;
	}

	public Precinct getRandomCandidate() {
            Precinct temp = this.candidates.get(ThreadLocalRandom.current().nextInt(this.candidates.size()));
            while (temp.getDistrictID() != 0) {
                temp = this.candidates.get(ThreadLocalRandom.current().nextInt(this.candidates.size()));
            }
            return temp;
	}

	public int getNumPrecincts() {
		return precincts.size();
	}

	public Set<Precinct> getSeeds() {
		if (seeds == null) {
			seeds = new HashSet<Precinct>();
		}
		return seeds;
	}

	public boolean addSeed(Precinct seed) {
		this.getSeeds();
		if (this.getSeeds().contains(seed)) {
			return false;
		}
		this.getSeeds().add(seed);
		return true;
	}

	public void setID(int ID) {
		this.ID = ID;
	}
}
