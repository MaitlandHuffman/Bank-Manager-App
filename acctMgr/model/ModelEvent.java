package acctMgr.model;

import java.math.BigDecimal;

/**
 * Represents an event within the model, providing information about the event kind, balance, and agent status.
 */
public class ModelEvent {
    /**
     * Enumeration of possible event kinds.
     */
    public enum EventKind {
        BalanceUpdate, AgentStatusUpdate, AmountTransferredUpdate
    }

    private final EventKind kind;
    private final BigDecimal balance;
    private final AgentStatus agSt;

    /**
     * Constructs a ModelEvent with the specified kind, balance, and agent status.
     *
     * @param kind The kind of the event.
     * @param balance The account balance related to the event.
     * @param agSt The agent status related to the event.
     */
    public ModelEvent(EventKind kind, BigDecimal balance, AgentStatus agSt) {
        this.balance = balance;
        this.kind = kind;
        this.agSt = agSt;
    }

    /**
     * Returns the kind of the event.
     *
     * @return The kind of the event.
     */
    public EventKind getKind() {
        return kind;
    }

    /**
     * Returns the balance associated with the event.
     *
     * @return The balance associated with the event.
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Returns the agent status associated with the event.
     *
     * @return The agent status associated with the event.
     */
    public AgentStatus getAgStatus() {
        return agSt;
    }
}
