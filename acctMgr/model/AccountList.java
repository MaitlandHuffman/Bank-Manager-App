package acctMgr.model;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Manages a list of accounts and provides methods for loading and saving accounts to files.
 * Includes currency conversion methods and synchronization with listeners.
 */
public class AccountList implements Model {
    private final List<Account> accounts;
    private final List<ModelListener> listeners;

    // Currency conversion rates (hardcoded)
    private static final double USD_TO_EUR = 0.79;
    private static final double USD_TO_YEN = 94.1;

    /**
     * Constructs an AccountList with an empty list of accounts.
     */
    public AccountList() {
        accounts = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     * Adds an account to the list.
     *
     * @param account The account to add.
     */
    public void addAccount(Account account) {
        accounts.add(account);
        notifyChanged(new ModelEvent(ModelEvent.EventKind.BalanceUpdate, account.getBalance(), AgentStatus.NA));
    }

    /**
     * Removes an account from the list.
     *
     * @param account The account to remove.
     */
    public void removeAccount(Account account) {
        accounts.remove(account);
        notifyChanged(new ModelEvent(ModelEvent.EventKind.BalanceUpdate, account.getBalance(), AgentStatus.NA));
    }

    /**
     * Loads accounts from a specified file and sorts them by ID.
     *
     * @param fileName The name of the file to load accounts from.
     * @throws IOException If an I/O error occurs during reading.
     */
    public synchronized void loadAccounts(String fileName) throws IOException {
        List<Account> tempAccounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    try {
                        String name = parts[0].trim();
                        String id = parts[1].trim();
                        BigDecimal balance = new BigDecimal(parts[2].trim());
                        Account account = new Account(name, id, balance);
                        tempAccounts.add(account);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid balance format on line: " + line);
                    }
                } else {
                    System.err.println("Invalid line format (missing parts): " + line);
                }
            }
            // Sort the accounts by ID
            tempAccounts.sort(Comparator.comparing(Account::getId));
            accounts.clear();
            accounts.addAll(tempAccounts);
        } catch (IOException e) {
            System.err.println("Error loading accounts from file: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Saves accounts to a specified file.
     *
     * @param fileName The name of the file to save accounts to.
     * @throws IOException If an I/O error occurs during writing.
     */
    public void saveAccounts(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Account account : accounts) {
                String line = account.getName() + "," + account.getId() + "," + account.getBalance();
                writer.write(line);
                writer.newLine();
            }
        }
    }

    /**
     * Returns the list of accounts.
     *
     * @return The list of accounts.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Adds a ModelListener to the list of listeners.
     *
     * @param listener The ModelListener to add.
     */
    public void addModelListener(ModelListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a ModelListener from the list of listeners.
     *
     * @param listener The ModelListener to remove.
     */
    public void removeModelListener(ModelListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners of a model change.
     *
     * @param event The ModelEvent to notify listeners of.
     */
    public void notifyChanged(ModelEvent event) {
        for (ModelListener listener : listeners) {
            listener.modelChanged(event);
        }
    }

    /**
     * Converts an amount from USD to Euros.
     *
     * @param amount The amount in USD.
     * @return The equivalent amount in Euros.
     */
    public BigDecimal convertUsdToEur(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(USD_TO_EUR));
    }

    /**
     * Converts an amount from USD to Yen.
     *
     * @param amount The amount in USD.
     * @return The equivalent amount in Yen.
     */
    public BigDecimal convertUsdToYen(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(USD_TO_YEN));
    }

    /**
     * Converts an amount from Euros to USD.
     *
     * @param amount The amount in Euros.
     * @return The equivalent amount in USD.
     */
    public BigDecimal convertEurToUsd(BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(USD_TO_EUR), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Converts an amount from Yen to USD.
     *
     * @param amount The amount in Yen.
     * @return The equivalent amount in USD.
     */
    public BigDecimal convertYenToUsd(BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(USD_TO_YEN), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Updates all open views for an account after a deposit or withdrawal operation.
     *
     * @param account The account that was updated.
     */
    public void updateAccountViews(Account account) {
        notifyChanged(new ModelEvent(ModelEvent.EventKind.BalanceUpdate, account.getBalance(), AgentStatus.NA));
    }
}
