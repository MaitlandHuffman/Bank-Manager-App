package acctMgr.controller;

import acctMgr.model.Model;
import acctMgr.model.ModelEvent;
import acctMgr.model.ModelListener;
import acctMgr.view.View;

/**
 * Abstract base class for controllers in the MVC architecture.
 * This class provides basic functionality for managing the relationship between a model and a view in an MVC application.
 */
public abstract class AbstractController implements Controller, ModelListener {

    private View view;
    private Model model;

    /**
     * Sets the model for this controller and adds a model listener.
     *
     * @param model The model to set.
     */
    public void setModel(Model model) {
        if (this.model != null) {
            this.model.removeModelListener(this);
        }
        this.model = model;
        if (model != null) {
            model.addModelListener(this);
        }
    }

    /**
     * Returns the model associated with this controller.
     *
     * @return The associated model.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Sets the view for this controller.
     *
     * @param view The view to set.
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Returns the view associated with this controller.
     *
     * @return The associated view.
     */
    public View getView() {
        return view;
    }

    /**
     * Handles model changes by updating the view based on the model event.
     * Calls the abstract method {@link #updateView()} to synchronize the view with the model's state.
     *
     * @param event The model event that occurred.
     */
    @Override
    public void modelChanged(ModelEvent event) {
        updateView();
    }

    /**
     * Abstract method to handle different user actions based on the provided action command.
     * Derived classes must implement this method to perform operations based on user input.
     *
     * @param actionCommand The action command representing the user input.
     */
    public abstract void operation(String actionCommand);

    /**
     * Abstract method to update the view based on changes in the model.
     * Derived classes must implement this method to synchronize the view with the model's state.
     */
    public abstract void updateView();

    /**
     * Handles errors that may arise during model operations.
     * Displays an error message in the view.
     *
     * @param exception The exception that occurred during a model operation.
     */
    protected void handleError(Exception exception) {
        if (view != null) {
            view.showErrorMessage("An error occurred: " + exception.getMessage());
        }
    }
}
