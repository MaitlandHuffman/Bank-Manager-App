package acctMgr.controller;

import acctMgr.model.Account;
import acctMgr.model.Model;
import acctMgr.view.AccountSelectionView;
import acctMgr.view.AccountView;
import acctMgr.view.View;

/**
 * Controller for handling interactions in the AccountSelectionView.
 */
public class AccountSelectionViewController extends AbstractController {

    /**
     * Constructs an AccountSelectionViewController with the specified view and model.
     *
     * @param view The view associated with this controller.
     * @param model The model associated with this controller.
     */
    public AccountSelectionViewController(View view, Model model) {
        setView(view);
        setModel(model);
    }

    /**
     * Handles different user actions based on the provided action command.
     * Currently handles the "select" action command to manage account selection.
     *
     * @param actionCommand The action command representing the user input.
     */
    @Override
    public void operation(String actionCommand) {
        if ("select".equals(actionCommand)) {
            handleAccountSelection();
        }
    }

    /**
     * Handles the selection of an account based on user input from the view.
     * If an account is selected, it creates an AccountView for the selected account and makes it visible.
     */
    private void handleAccountSelection() {
        AccountSelectionView view = (AccountSelectionView) getView();
        Account selectedAccount = view.getSelectedAccount();

        if (selectedAccount != null) {
            // Create and display an AccountView for the selected account
            AccountView accountView = new AccountView(selectedAccount);
            accountView.setVisible(true);
        } else {
            System.out.println("No account selected.");
        }
    }

    /**
     * Updates the view based on changes in the model.
     * This method is called when the model changes, and it refreshes the view.
     */
    @Override
    public void updateView() {
        getView().refreshView();
    }
}
