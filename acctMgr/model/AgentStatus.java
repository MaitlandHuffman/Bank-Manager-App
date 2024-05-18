package acctMgr.model;

/**
 * Enumeration representing the status of an agent.
 * Agents can have different states, such as Running, Blocked, Paused, or NA (Not Applicable).
 */
public enum AgentStatus {
    /**
     * Indicates that the agent is currently running and actively processing.
     */
    Running,

    /**
     * Indicates that the agent is blocked, often due to waiting for a condition to be met.
     */
    Blocked,

    /**
     * Indicates that the agent is paused and temporarily not processing.
     */
    Paused,

    /**
     * Indicates that the agent status is not applicable or not determined.
     */
    NA
}
