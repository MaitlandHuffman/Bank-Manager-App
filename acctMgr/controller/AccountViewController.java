package acctMgr.controller;

import acctMgr.model.Account;
import acctMgr.model.Model;
import acctMgr.model.OverdrawException;
import acctMgr.view.AccountView;
import acctMgr.view.View;
import java.math.BigDecimal;

/**
 * Controller for handling interactions in the AccountView.
 */
public class AccountViewController extends AbstractController {

    /**
     * Constructs an AccountViewController with the specified view and model.
     *
     * @param view The view associated with this controller.
     * @param model The model associated with this controller.
     */
    public AccountViewController(View view, Model model) {
        setView(view);
        setModel(model);
    }

    /**
     * Handles different user actions based on the provided action command.
     * Supported action commands are "deposit", "withdraw", and "dismiss".
     *
     * @param actionCommand The action command representing the user input.
     */
    @Override
    public void operation(String actionCommand) {
        AccountView view = (AccountView) getView();
        Account account = (Account) getModel();

        try {
            BigDecimal amount = new BigDecimal(view.getAmount());
            if ("deposit".equals(actionCommand)) {
                account.deposit(amount);
            } else if ("withdraw".equals(actionCommand)) {
                account.withdraw(amount);
            } else if ("dismiss".equals(actionCommand)) {
                view.dismiss();  // Dismiss the view
            }
        } catch (OverdrawException e) {
            // Display an error message in the AccountView when an overdraw occurs
            view.showErrorMessage(e.getMessage());
        }
    }

    /**
     * Updates the view based on changes in the model.
     * Called when the model changes to refresh the view.
     */
    @Override
    public void updateView() {
        getView().refreshView();
    }
}
