package model;

/**
 * Represents the context and list of methods for the use of fish states.
 *
 * @see model.FishState
 */
interface FishStateContext {
    /**
     * Set a state
     * @param state The state to set
     */
    public void setState(FishState state);

    /**
     * Get the age of the fish
     * @return The age of the fish
     */
    public int getAge();
}
