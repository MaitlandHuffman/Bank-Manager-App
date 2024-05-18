package acctMgr.model;

/**
 * Interface representing the model in the MVC architecture.
 */
public interface Model {

    /**
     * Notifies listeners about a change in the model.
     *
     * @param me The model event that occurred.
     */
    void notifyChanged(ModelEvent me);

    /**
     * Adds a model listener to this model.
     *
     * @param listener The model listener to add.
     */
    void addModelListener(ModelListener listener);

    /**
     * Removes a model listener from this model.
     *
     * @param listener The model listener to remove.
     */
    void removeModelListener(ModelListener listener);
}
