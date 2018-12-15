package manager;

import java.util.HashMap;
import java.util.Map;


import dataTypes.StateName;
import mapObjects.State;

public class StateManager {

	private static StateManager instance = null;
	private Map<StateName, State> loadedStates;

	protected StateManager() {
		loadedStates = new HashMap<StateName, State>();
                //Oregon
                State s = Preprocessing.initializeStateFromFile(StateName.OREGON,
                        "serialized/oregonSerialized.json",
                "serialized/OregonAdjacency.json");
                loadedStates.put(StateName.OREGON, s);
                
//                //Massachusetts
//                s = Preprocessing.initializeStateFromFile(StateName.MASSACHUSETTS,
//                        "/Users/spitlord/PycharmProjects/electionPreprocessing/seRetarded/massachusettsSerialized.json",
//                "/Users/spitlord/PycharmProjects/electionPreprocessing/goodAdjacencies/MassachusettsAdjacency.json");
//                loadedStates.put(StateName.MASSACHUSETTS, s);
	}

	public static StateManager getInstance() {
		if (instance == null) {
			instance = new StateManager();
		}
		return instance;
	}

	public Map<StateName, State> getStates() {
		return loadedStates;
	}

	public State getState(StateName name) {
            return loadedStates.get(name);
	}

}
