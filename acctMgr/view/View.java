package acctMgr.view;

import acctMgr.controller.Controller;
import acctMgr.model.Model;

/**
 * Interface representing a view in the MVC architecture.
 */
public interface View {
    /**
     * Returns the controller associated with this view.
     *
     * @return The associated controller.
     */
    Controller getController();

    /**
     * Sets the controller for this view.
     *
     * @param aController The controller to set.
     */
    void setController(Controller aController);

    /**
     * Returns the model associated with this view.
     *
     * @return The associated model.
     */
    Model getModel();

    /**
     * Sets the model for this view.
     *
     * @param aModel The model to set.
     */
    void setModel(Model aModel);

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The error message to display.
     */
    void showErrorMessage(String errorMessage);

    /**
     * Refreshes the view based on changes in the model.
     */
    void refreshView();

    /**
     * Initializes the view, including setting up listeners and initial state.
     */
    void initialize();
}
