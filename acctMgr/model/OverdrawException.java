package acctMgr.model;

import java.math.BigDecimal;

/**
 * Exception thrown when an overdraft occurs during a withdrawal from an account.
 * Contains the amount of the overdraft.
 */
public class OverdrawException extends Exception {
    private static final long serialVersionUID = 1L;

    private final BigDecimal overdraftAmount;

    /**
     * Constructs an OverdrawException with the specified overdraft amount.
     *
     * @param overdraftAmount The amount by which the account was overdrafted.
     */
    public OverdrawException(BigDecimal overdraftAmount) {
        super("Overdraft occurred: " + overdraftAmount);
        this.overdraftAmount = overdraftAmount;
    }

    /**
     * Gets the amount by which the account was overdrafted.
     *
     * @return The overdraft amount.
     */
    public BigDecimal getOverdraftAmount() {
        return overdraftAmount;
    }
}
