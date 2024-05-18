package acctMgr.view;

import acctMgr.controller.AccountSelectionViewController;
import acctMgr.model.Account;
import acctMgr.model.AccountList;
import acctMgr.model.Model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Represents the view for selecting an account from a list and managing selection interactions.
 */
public class AccountSelectionView extends JFrameView {
    private static final long serialVersionUID = 1L;

    private AccountSelectionViewController controller;
    private JComboBox<Account> accountComboBox;
    private JButton selectButton;
    private JButton saveButton;
    private JButton exitButton;

    /**
     * Constructs an AccountSelectionView with the specified model.
     *
     * @param model The model to associate with this view.
     */
    public AccountSelectionView(Model model) {
        super(model);
        controller = new AccountSelectionViewController(this, (AccountList) model);
        initialize();
    }

    /**
     * Initializes the view with the necessary components and listeners.
     */
    @Override
    public void initialize() {
        // Set up UI components
        accountComboBox = new JComboBox<>();
        selectButton = new JButton("Select Account");
        saveButton = new JButton("Save");
        exitButton = new JButton("Exit");

        // Add action listeners to the buttons
        selectButton.addActionListener(e -> controller.operation("select"));
        saveButton.addActionListener(e -> handleSaveButtonClick());
        exitButton.addActionListener(e -> handleExitButtonClick());

        // Create a panel with FlowLayout to arrange buttons in a row
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center alignment with gaps

        // Add components to the panel
        panel.add(new JLabel("Select Account:"));
        panel.add(accountComboBox);
        panel.add(selectButton);
        panel.add(saveButton);
        panel.add(exitButton);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);
        pack();
        
        // Set the initial size of the frame
        setSize(400, 100);

        // Add a window listener to handle close event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClose();
            }
        });

        refreshView();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Handles the save button click event by saving accounts to the file.
     */
    private void handleSaveButtonClick() {
        AccountList model = (AccountList) getModel();
        try {
            model.saveAccounts("testSaveFile.txt");
            showInfoMessage("Accounts saved successfully.");
        } catch (IOException ex) {
            showErrorMessage("Error saving accounts: " + ex.getMessage());
        }
    }

    /**
     * Handles the exit button click event by saving accounts before exiting.
     */
    private void handleExitButtonClick() {
        handleWindowClose();
    }

    /**
     * Handles the window close event by saving accounts before exiting.
     */
    private void handleWindowClose() {
        AccountList model = (AccountList) getModel();
        try {
            model.saveAccounts("testSaveFile.txt");
            System.exit(0);
        } catch (IOException ex) {
            showErrorMessage("Error saving accounts: " + ex.getMessage());
        }
    }

    /**
     * Refreshes the view based on changes in the model.
     */
    @Override
    public void refreshView() {
        accountComboBox.removeAllItems();
        AccountList model = (AccountList) getModel();
        for (Account account : model.getAccounts()) {
            accountComboBox.addItem(account);
        }
    }

    /**
     * Returns the currently selected account from the combo box.
     *
     * @return The currently selected account.
     */
    public Account getSelectedAccount() {
        return (Account) accountComboBox.getSelectedItem();
    }

    /**
     * Displays an informational message dialog.
     *
     * @param infoMessage The informational message to display.
     */
    private void showInfoMessage(String infoMessage) {
        JOptionPane.showMessageDialog(this, infoMessage, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays an error message dialog.
     *
     * @param errorMessage The error message to display.
     */
    public void showErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
