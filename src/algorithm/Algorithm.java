package algorithm;

import java.util.Stack;

import mapObjects.State;

public abstract class Algorithm {

	protected double currentObjectiveValue;
	protected ObjectiveFunction objectiveFunction;
	protected State currentState;
	protected Stack<Move> moves;

	abstract public void run();

	abstract protected boolean checkTerimanationConditions();
        
        public Algorithm() {
            moves = new Stack<>();
        }

    public Stack<Move> getMoves() {
        return moves;
    }

        
}
