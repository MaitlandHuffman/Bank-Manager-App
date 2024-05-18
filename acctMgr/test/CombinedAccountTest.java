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
 * JUnit test class for both Account and AccountList classes.
 */
public class CombinedAccountTest {
    private AccountList accountList;
    private final static String TEST_LOAD_FILE = "testLoadFile.txt";
    private final static String TEST_SAVE_FILE = "testSaveFile.txt";

    /**
     * Sets up the test fixture.
     */
    @Before
    public void setUp() throws IOException {
        accountList = new AccountList();
        // Ensure TEST_LOAD_FILE exists with correct data before running tests
        // Ensure TEST_SAVE_FILE is empty or non-existent before running tests
    }

    /**
     * Tests loading accounts from a file into AccountList.
     *
     * @throws IOException if there's an I/O error while reading the file
     */
    @Test
    public void testLoadAccountsFromFile() throws IOException {
        // Load accounts from file
        accountList.loadAccounts(TEST_LOAD_FILE);

        // Assertions depend on the known contents of your TEST_LOAD_FILE
        List<Account> loadedAccounts = accountList.getAccounts();
        assertFalse("Loaded account list should not be empty", loadedAccounts.isEmpty());

        // Verify the loaded accounts are sorted by ID
        for (int i = 1; i < loadedAccounts.size(); i++) {
            assertTrue("Accounts should be sorted by ID", 
                loadedAccounts.get(i - 1).getId().compareTo(loadedAccounts.get(i).getId()) <= 0);
        }

        // Specific assertions based on expected file content
        Account firstAccount = loadedAccounts.get(0);
        assertEquals("Expected name", "Maitland Huffman", firstAccount.getName());
        assertEquals("Expected ID", "123", firstAccount.getId());
        assertEquals(0, BigDecimal.valueOf(100.00).compareTo(firstAccount.getBalance()));

        // Additional tests as needed for file content
    }

    /**
     * Tests saving accounts from AccountList to a file.
     *
     * @throws IOException if there's an I/O error while writing to the file
     */
    @Test
    public void testSaveAccountsToFile() throws IOException {
        // Prepare test data
        Account account1 = new Account("Maitland Huffman", "123", BigDecimal.valueOf(100.00));
        Account account2 = new Account("Professor", "2341", BigDecimal.valueOf(200.00));
        accountList.addAccount(account1);
        accountList.addAccount(account2);

        // Save to file
        accountList.saveAccounts(TEST_SAVE_FILE);

        // Load saved accounts to verify
        AccountList verifyAccountList = new AccountList();
        verifyAccountList.loadAccounts(TEST_SAVE_FILE);
        List<Account> savedAccounts = verifyAccountList.getAccounts();

        // Validate saved file contents
        assertEquals("Number of saved accounts", 2, savedAccounts.size());

        // Manually check attributes of each account
        for (Account savedAccount : savedAccounts) {
            if (savedAccount.getName().equals("Maitland Huffman")) {
                assertEquals("Maitland Huffman's ID", "123", savedAccount.getId());
                assertEquals("Maitland Huffman's balance", BigDecimal.valueOf(100.00)
                    .setScale(2, RoundingMode.HALF_EVEN), savedAccount.getBalance()
                    .setScale(2, RoundingMode.HALF_EVEN));
            } else if (savedAccount.getName().equals("Professor")) {
                assertEquals("Professor's ID", "2341", savedAccount.getId());
                assertEquals("Professor's balance", BigDecimal.valueOf(200.00)
                    .setScale(2, RoundingMode.HALF_EVEN), savedAccount.getBalance()
                    .setScale(2, RoundingMode.HALF_EVEN));
            } else {
                fail("Unexpected account found: " + savedAccount.getName());
            }
        }
    }

    /**
     * Tests the deposit method of the Account class.
     */
    @Test
    public void testDeposit() {
        Account testAccount = new Account("Maitland Huffman", "123", BigDecimal.valueOf(100.00));

        // Test deposit method of Account class
        BigDecimal depositAmount = BigDecimal.valueOf(50.00);
        testAccount.deposit(depositAmount);
        assertEquals(BigDecimal.valueOf(150.00).setScale(2, RoundingMode.HALF_EVEN),
                     testAccount.getBalance().setScale(2, RoundingMode.HALF_EVEN));
    }

    /**
     * Tests the withdraw method of the Account class.
     *
     * @throws OverdrawException if an attempt is made to withdraw more than the balance
     */
    @Test
    public void testWithdraw() throws OverdrawException {
        Account testAccount = new Account("Maitland Huffman", "123", BigDecimal.valueOf(100.00));

        // Test withdraw method of Account class
        BigDecimal withdrawalAmount = BigDecimal.valueOf(50.00);
        testAccount.withdraw(withdrawalAmount);
        assertEquals(BigDecimal.valueOf(50.00).setScale(2, RoundingMode.HALF_EVEN), 
                testAccount.getBalance().setScale(2, RoundingMode.HALF_EVEN));
    }

    /**
     * Tests the withdraw method of the Account class with an OverdrawException.
     *
     * @throws OverdrawException if an attempt is made to withdraw more than the balance
     */
    @Test(expected = OverdrawException.class)
    public void testWithdrawWithOverdrawException() throws OverdrawException {
        Account testAccount = new Account("Maitland Huffman", "123", BigDecimal.valueOf(100.00));

        // Test withdraw method of Account class with OverdrawException
        BigDecimal withdrawalAmount = BigDecimal.valueOf(200.00);
        testAccount.withdraw(withdrawalAmount);
    }
}
