package states;

import entities.Shark;
import entities.Sea;

/**
 * Represents a fish state.
 *
 * This state moves and grows.
 * @see states.FishStateContext
 */
public interface FishState {
    /**
     * Move action
     * @param shark The shark to move
     * @param sea The sea to interact with
     */
    public void move(Shark shark, Sea sea);

    /**
     * Grow action
     * @param fish The sea to interact with
     */
    public void grow(FishStateContext fish);
}
