package acctMgr.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import acctMgr.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * JUnit test class for the Account class.
 */
public class AccountTest {
    private Account testAccount;

    /**
     * Sets up the test fixture.
     */
    @Before
    public void setUp() {
        // Initialize an Account object for testing
        testAccount = new Account("Maitland Huffman", "12345", BigDecimal.valueOf(100.0));
    }

    /**
     * Tests the deposit method of the Account class.
     */
    @Test
    public void testDeposit() {
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
        // Test withdraw method of Account class
        BigDecimal withdrawalAmount = BigDecimal.valueOf(50.0);
        testAccount.withdraw(withdrawalAmount);
    
        assertEquals(BigDecimal.valueOf(50.00).setScale(2, RoundingMode.HALF_EVEN), 
                     testAccount.getBalance().setScale(2, RoundingMode.HALF_EVEN));
    }

    /**
     * Tests the withdraw method of the Account class when an OverdrawException is expected.
     *
     * @throws OverdrawException if an attempt is made to withdraw more than the balance
     */
    @Test(expected = OverdrawException.class)
    public void testWithdrawWithOverdrawException() throws OverdrawException {
        // Test withdraw method of Account class with OverdrawException
        BigDecimal withdrawalAmount = BigDecimal.valueOf(200.00);
        testAccount.withdraw(withdrawalAmount);
    }

    /**
     * Tests the getName method of the Account class.
     */
    @Test
    public void testGetName() {
        // Test getName method of Account class
        assertEquals("Maitland Huffman", testAccount.getName());
    }

    /**
     * Tests the getID method of the Account class.
     */
    @Test
    public void testGetID() {
        // Test getID method of Account class
        assertEquals("12345", testAccount.getId());
    }

   
}
