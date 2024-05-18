package acctMgr.view;

import acctMgr.model.Model;
import acctMgr.controller.Controller;
import javax.swing.*;

/**
 * Abstract base class for views that extends JFrame and implements the View interface.
 */
public abstract class JFrameView extends JFrame implements View {
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for serialization

    private Model model;
    private Controller controller;

    /**
     * Constructs a JFrameView with the specified model.
     *
     * @param model The model associated with this view.
     */
    public JFrameView(Model model) {
        this.model = model;
    }

    /**
     * Returns the model associated with this view.
     *
     * @return The associated model.
     */
    @Override
    public Model getModel() {
        return model;
    }

    /**
     * Sets the model for this view.
     *
     * @param aModel The model to set.
     */
    @Override
    public void setModel(Model aModel) {
        this.model = aModel;
        if (controller != null) {
            controller.setModel(aModel);
        }
    }

    /**
     * Returns the controller associated with this view.
     *
     * @return The associated controller.
     */
    @Override
    public Controller getController() {
        return controller;
    }

    /**
     * Sets the controller for this view.
     *
     * @param aController The controller to set.
     */
    @Override
    public void setController(Controller aController) {
        controller = aController;
    }

    /**
     * Displays an error message to the user using a dialog box.
     *
     * @param errorMessage The error message to display.
     */
    @Override
    public void showErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Abstract method to refresh the view based on changes in the model.
     */
    public abstract void refreshView();

    /**
     * Initializes the view, including setting up listeners and initial state.
     */
    public abstract void initialize();
}
