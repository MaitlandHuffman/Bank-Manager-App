package acctMgr.model;

/**
 * Interface representing a listener for model events.
 * Implementing classes will receive notifications of changes in the model.
 */
public interface ModelListener {
    /**
     * Invoked when a change occurs in the model.
     *
     * @param event The model event containing information about the change.
     */
    void modelChanged(ModelEvent event);
}
