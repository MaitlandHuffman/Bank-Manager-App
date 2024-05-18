package acctMgr.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import acctMgr.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * JUnit test class for the AccountList class.
 */
public class AccountListTest {
    private AccountList accountList;
    private static final String TEST_LOAD_FILE = "testLoadFile.txt";
    private static final String TEST_SAVE_FILE = "testSaveFile.txt";

    /**
     * Sets up the test fixture.
     * Initializes the AccountList instance and prepares any necessary files.
     *
     * @throws IOException if an I/O error occurs during setup.
     */
    @Before
    public void setUp() throws IOException {
        accountList = new AccountList();
        // Optionally, ensure TEST_LOAD_FILE contains correct data before running tests.
        // Ensure TEST_SAVE_FILE is empty or non-existent before running tests.
    }

    /**
     * Tests the loadAccounts method of the AccountList class.
     * Verifies that the accounts are loaded correctly from the specified file.
     *
     * @throws IOException if an I/O error occurs during the test.
     */
    @Test
    public void testLoadAccountsFromFile() throws IOException {
        // Load accounts from file
        accountList.loadAccounts(TEST_LOAD_FILE);

        // Assertions depend on the known contents of your TEST_LOAD_FILE
        List<Account> loadedAccounts = accountList.getAccounts();
        assertFalse("Loaded account list should not be empty.", loadedAccounts.isEmpty());

        // Specific assertions based on expected file content
        Account testAccount = loadedAccounts.get(0);
        assertEquals("Expected name: John Doe", "John Doe", testAccount.getName());
        assertEquals("Expected ID: 12345", "12345", testAccount.getId());
        assertEquals("Expected balance: 100.00", BigDecimal.valueOf(100.00), testAccount.getBalance());

        // Additional tests can be added as needed for file content validation.
    }

    /**
     * Tests the saveAccounts method of the AccountList class.
     * Verifies that accounts are saved correctly to the specified file.
     *
     * @throws IOException if an I/O error occurs during the test.
     */
    @Test
    public void testSaveAccountsToFile() throws IOException {
        // Prepare test data
        Account account1 = new Account("John Doe", "12345", BigDecimal.valueOf(100.00));
        Account account2 = new Account("Jane Smith", "67890", BigDecimal.valueOf(200.00));
        accountList.addAccount(account1);
        accountList.addAccount(account2);

        // Save accounts to file
        accountList.saveAccounts(TEST_SAVE_FILE);

        // Load saved accounts to verify
        AccountList verifyAccountList = new AccountList();
        verifyAccountList.loadAccounts(TEST_SAVE_FILE);
        List<Account> savedAccounts = verifyAccountList.getAccounts();

        // Validate saved file contents
        assertEquals("Number of saved accounts", 2, savedAccounts.size());

        // Manually check attributes of each account
        for (Account savedAccount : savedAccounts) {
            if (savedAccount.getName().equals("John Doe")) {
                assertEquals("John Doe's ID", "12345", savedAccount.getId());
                assertEquals("John Doe's balance", BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_EVEN), savedAccount.getBalance().setScale(2, RoundingMode.HALF_EVEN));
            } else if (savedAccount.getName().equals("Jane Smith")) {
                assertEquals("Jane Smith's ID", "67890", savedAccount.getId());
                assertEquals("Jane Smith's balance", BigDecimal.valueOf(200.00).setScale(2, RoundingMode.HALF_EVEN), savedAccount.getBalance().setScale(2, RoundingMode.HALF_EVEN));
            } else {
                fail("Unexpected account found: " + savedAccount.getName());
            }
        }
    }
}
