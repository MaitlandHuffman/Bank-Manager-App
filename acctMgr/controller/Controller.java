package acctMgr.controller;

import acctMgr.model.Model;
import acctMgr.view.View;

/**
 * Interface representing a controller in the MVC (Model-View-Controller) architecture.
 * A controller acts as an intermediary between a view and a model, handling user input and updating the model and view accordingly.
 */
public interface Controller {

    /**
     * Sets the model for this controller.
     *
     * @param model The model to associate with this controller.
     */
    void setModel(Model model);

    /**
     * Returns the model associated with this controller.
     *
     * @return The associated model.
     */
    Model getModel();

    /**
     * Sets the view for this controller.
     *
     * @param view The view to associate with this controller.
     */
    void setView(View view);

    /**
     * Returns the view associated with this controller.
     *
     * @return The associated view.
     */
    View getView();

    /**
     * Handles different user actions based on the provided action command.
     * This method should interpret the action command and perform the necessary operations on the model.
     *
     * @param actionCommand The action command representing user input (e.g., button click or other events).
     */
    void operation(String actionCommand);
}
