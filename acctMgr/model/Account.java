package acctMgr.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an account with a balance, name, and unique identifier.
 * The account can notify registered listeners of updates to its state.
 */
public class Account {
    private BigDecimal balance;
    private String name;
    private String id;
    private List<AccountListener> listeners;

    /**
     * Constructs an Account object with the given name, ID, and initial balance.
     *
     * @param name    The name of the account holder.
     * @param id      The unique identifier for the account.
     * @param balance The initial balance of the account.
     */
    public Account(String name, String id, BigDecimal balance) {
        this.name = name;
        this.id = id;
        this.balance = balance;
        this.listeners = new ArrayList<>();
    }

    /**
     * Returns the current balance of the account.
     *
     * @return The current balance.
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Deposits the specified amount into the account.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
        notifyListeners();
    }

    /**
     * Withdraws the specified amount from the account.
     *
     * @param amount The amount to withdraw.
     * @throws OverdrawException If the withdrawal would result in a negative balance.
     */
    public void withdraw(BigDecimal amount) throws OverdrawException {
        BigDecimal newBalance = balance.subtract(amount);

        // Check if the new balance would be negative after the withdrawal
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            // Calculate the overdraft amount
            BigDecimal overdraftAmount = newBalance.abs();
            
            // Throw an OverdrawException with the overdraft amount
            throw new OverdrawException(overdraftAmount);
        }

        // Update the balance if the withdrawal is successful
        balance = newBalance;
        notifyListeners();
    }

    /**
     * Adds a listener to be notified of account updates.
     *
     * @param listener The listener to add.
     */
    public void addListener(AccountListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners of an account update.
     */
    private void notifyListeners() {
        for (AccountListener listener : listeners) {
            listener.accountUpdated(this);
        }
    }

    /**
     * Returns the name of the account holder.
     *
     * @return The name of the account holder.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the unique identifier of the account.
     *
     * @return The unique identifier of the account.
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        // Returns a string representation of the account, including its ID and name.
        return id + " - " + name;
    }
}
