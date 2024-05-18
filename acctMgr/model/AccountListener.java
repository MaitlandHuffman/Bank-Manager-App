package acctMgr.model;

/**
 * Interface for listening to account updates. Implementations of this interface
 * are notified when an account undergoes a change in state (e.g., balance update).
 */
public interface AccountListener {

    /**
     * Called when an account is updated. Implementations should define how they
     * handle the update based on the account's new state.
     *
     * @param account The account that has been updated.
     */
    void accountUpdated(Account account);
}
