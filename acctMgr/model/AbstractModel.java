package acctMgr.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for models in the MVC (Model-View-Controller) architecture.
 * This class provides the common functionality for handling model listeners and notifying them of changes.
 */
public abstract class AbstractModel implements Model {
    
    private List<ModelListener> listeners = new ArrayList<>(5);

    /**
     * Notifies all registered listeners of a model change event.
     *
     * @param event The model event that occurred.
     */
    public void notifyChanged(ModelEvent event) {
        for (ModelListener ml : listeners) {
            ml.modelChanged(event);
        }
    }

    /**
     * Adds a model listener to the list of listeners that will be notified of model changes.
     *
     * @param l The model listener to add.
     */
    @Override
    public void addModelListener(ModelListener l) {
        listeners.add(l);
    }

    /**
     * Removes a model listener from the list of listeners.
     *
     * @param l The model listener to remove.
     */
    @Override
    public void removeModelListener(ModelListener l) {
        listeners.remove(l);
    }
}
