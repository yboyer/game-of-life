package model;

/**
 * Represents a fish state.
 *
 * This state moves and grows.
 * @see model.FishStateContext
 */
interface FishState {
    /**
     * Move action
     * @param shark The shark to move
     * @param sea The sea to move
     */
    public void move(Shark shark, Sea sea);

    /**
     * Grow action
     * @param fish The sea to move
     */
    public void grow(FishStateContext fish);
}
