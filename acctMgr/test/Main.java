package acctMgr.test;

import acctMgr.model.AccountList;
import acctMgr.view.AccountSelectionView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Specify the file path to load the accounts from
        String filePath = "TestLoadFile.txt";

        // Create an instance of AccountList
        AccountList accountList = new AccountList();

        // Load accounts from the file
        try {
            System.out.println("Loading accounts from file: " + filePath);
            accountList.loadAccounts(filePath);
            System.out.println("Accounts loaded successfully.");
        } catch (IOException e) {
            System.err.println("Failed to load accounts from file: " + e.getMessage());
            return;  // Exit the application if loading fails
        }

        // Create the AccountSelectionView
        System.out.println("Creating AccountSelectionView.");
        AccountSelectionView accountSelectionView = new AccountSelectionView(accountList);

        // Set the view visible
        System.out.println("Displaying AccountSelectionView.");
        accountSelectionView.setVisible(true);
    }
}