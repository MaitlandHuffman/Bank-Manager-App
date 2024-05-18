package acctMgr.view;

import acctMgr.model.Account;
import acctMgr.model.AccountListener;
import acctMgr.model.OverdrawException;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Represents the view for managing an account, including displaying balance, deposits, withdrawals, and currency conversion.
 */
public class AccountView extends JFrame implements AccountListener {
    private static final long serialVersionUID = 1L;

    private Account account;
    private JTextField balanceField;
    private JTextField amountField;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton usdButton;
    private JButton euroButton;
    private JButton yenButton;
    private JButton dismissButton;

    // Conversion rates from USD to other currencies
    private static final double USD_TO_EURO = 0.79;
    private static final double USD_TO_YEN = 94.1;

    // The current currency, initially set to USD
    private String currentCurrency = "$";

    /**
     * Constructs an AccountView with the specified account.
     *
     * @param account The account to be managed by this view.
     */
    public AccountView(Account account) {
        this.account = account;
        account.addListener(this);
        initialize();
    }

    /**
     * Initializes the view by setting up the layout and components.
     */
    private void initialize() {
        // Create and configure text fields
        balanceField = new JTextField(10);
        balanceField.setEditable(false);
        amountField = new JTextField(10);

        // Create buttons
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        dismissButton = new JButton("Dismiss");
        usdButton = new JButton("Edit in $");
        euroButton = new JButton("Edit in €");
        yenButton = new JButton("Edit in ¥");

        // Add action listeners for buttons
        depositButton.addActionListener(e -> deposit());
        withdrawButton.addActionListener(e -> {
            try {
                withdraw();
            } catch (OverdrawException e1) {
                showErrorMessage(e1.getMessage());
            }
        });
        dismissButton.addActionListener(e -> dismiss());
        usdButton.addActionListener(e -> setCurrency("$"));
        euroButton.addActionListener(e -> setCurrency("€"));
        yenButton.addActionListener(e -> setCurrency("¥"));

        // Create the account information label
        JLabel accountInfoLabel = new JLabel("Account: " + account.getName() + " (ID: " + account.getId() + ")");

        // Create a panel with GridBagLayout for a flexible layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add the account information label to the top of the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3; // Span across multiple columns
        panel.add(accountInfoLabel, constraints);

        // First row: Balance field and amount field
        constraints.gridwidth = 1; // Reset grid width
        constraints.gridy = 1;
        constraints.gridx = 0;
        panel.add(new JLabel("Balance:"), constraints);
        constraints.gridx = 1;
        panel.add(balanceField, constraints);
        constraints.gridy = 2;
        constraints.gridx = 0;
        panel.add(new JLabel("Amount:"), constraints);
        constraints.gridx = 1;
        panel.add(amountField, constraints);

        // Second row: Deposit, withdraw, and dismiss buttons
        constraints.gridy = 3;
        constraints.gridx = 0;
        panel.add(depositButton, constraints);
        constraints.gridx = 1;
        panel.add(withdrawButton, constraints);
        constraints.gridx = 2;
        panel.add(dismissButton, constraints);

        // Third row: Currency buttons (USD, Euro, Yen)
        constraints.gridy = 4;
        constraints.gridx = 0;
        panel.add(usdButton, constraints);
        constraints.gridx = 1;
        panel.add(euroButton, constraints);
        constraints.gridx = 2;
        panel.add(yenButton, constraints);

        // Add the panel to the frame
        add(panel);
        pack();
        refreshView();
    }

    /**
     * Handles deposit operations based on the entered amount.
     */
    private void deposit() {
        BigDecimal amount = new BigDecimal(amountField.getText());
        account.deposit(amount);
        refreshView();
    }

    /**
     * Handles withdrawal operations based on the entered amount.
     *
     * @throws OverdrawException if the withdrawal results in an overdraft
     */
    private void withdraw() throws OverdrawException {
        BigDecimal amount = new BigDecimal(amountField.getText());
        account.withdraw(amount);
        refreshView();
    }

    /**
     * Sets the current currency and updates the display balance accordingly.
     *
     * @param currency The currency symbol to set ("$", "€", or "¥").
     */
    private void setCurrency(String currency) {
        currentCurrency = currency;
        displayBalanceInCurrency(currency);
    }

    /**
     * Displays the current account balance in the specified currency.
     *
     * @param currency The currency symbol for the balance display ("$", "€", or "¥").
     */
    private void displayBalanceInCurrency(String currency) {
        DecimalFormat df = new DecimalFormat("#.##");
        BigDecimal balance = account.getBalance();
        BigDecimal convertedBalance;
        String currencySymbol;

        switch (currency) {
            case "$": // USD
                convertedBalance = balance;
                currencySymbol = "$";
                break;
            case "€": // Euro
                convertedBalance = balance.multiply(BigDecimal.valueOf(USD_TO_EURO));
                currencySymbol = "€";
                break;
            case "¥": // Yen
                convertedBalance = balance.multiply(BigDecimal.valueOf(USD_TO_YEN));
                currencySymbol = "¥";
                break;
            default:
                convertedBalance = balance;
                currencySymbol = "";
        }

        // Update the balance field with the converted balance and currency symbol
        balanceField.setText(currencySymbol + df.format(convertedBalance));
    }

    /**
     * Refreshes the view based on the current account state.
     */
    public void refreshView() {
        displayBalanceInCurrency(currentCurrency);
        amountField.setText("0.00");
    }

    /**
     * Handles account updates by refreshing the view.
     *
     * @param account The updated account.
     */
    @Override
    public void accountUpdated(Account account) {
        refreshView();
    }

    /**
     * Displays an error message dialog with the specified message.
     *
     * @param errorMessage The error message to display.
     */
    public void showErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Dismisses the current view by closing the window.
     */
    public void dismiss() {
        this.dispose();
    }

    /**
     * Returns the entered amount as a string.
     *
     * @return The amount entered in the amount field.
     */
    public String getAmount() {
        return amountField.getText();
    }
}
