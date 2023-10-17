package com.example.forum;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTreeTest {
    private AccountTree accountTree;

    @Before
    public void setUp() {
        // Create an AVL tree with a larger dataset of accounts for testing
        Account account1 = new Account("user1", "password1", 1, 101);
        Account account2 = new Account("user2", "password2", 2, 102);
        Account account3 = new Account("user3", "password3", 3, 103);
        Account account4 = new Account("user4", "password4", 4, 104);
        Account account5 = new Account("user5", "password5", 5, 105);
        Account account6 = new Account("user6", "password6", 6, 106);
        Account account7 = new Account("user7", "password7", 7, 107);
        // Add more accounts to the dataset as needed

        accountTree = new AccountTree(account1);
        accountTree.insert(account2);
        accountTree.insert(account3);
        accountTree.insert(account4);
        accountTree.insert(account5);
        accountTree.insert(account6);
        accountTree.insert(account7);
        // Insert more accounts into the tree as needed
    }

    @Test
    public void testSearchExistingAccount() {
        Account foundAccount = accountTree.search("user3");
        assertNotNull(foundAccount);
        assertEquals("user3", foundAccount.account);
        assertEquals("password3", foundAccount.password);
    }

    @Test
    public void testSearchNonExistingAccount() {
        Account foundAccount = accountTree.search("nonexistent");
        assertNull(foundAccount);
    }

    @Test
    public void testInsertAndSearch() {
        Account newAccount = new Account("user8", "password8", 8, 108);
        accountTree.insert(newAccount);
        Account foundAccount = accountTree.search("user8");
        assertNotNull(foundAccount);
        assertEquals("user8", foundAccount.account);
        assertEquals("password8", foundAccount.password);
    }

    @Test
    public void testDeleteExistingAccount() {
        accountTree.delete("user4");
        Account deletedAccount = accountTree.search("user4");
        assertNull(deletedAccount);
    }

    @Test
    public void testDeleteNonExistingAccount() {
        accountTree.delete("nonexistent");
        // Ensure that deleting a non-existing account doesn't cause errors
    }

    @Test
    public void testInOrderTraversal() {
        StringBuilder result = new StringBuilder();
        for (Account account : accountTree) {
            result.append(account.account).append(" ");
        }
        assertEquals("user1 user2 user3 user4 user5 user6 user7", result.toString().trim());
    }

    @Test
    public void testToList() {
        // Convert the tree to a list and check if it contains the expected values
        assertEquals(7, accountTree.toList().size());
        assertTrue(accountTree.toList().contains("user1;password1;1;101"));
        assertTrue(accountTree.toList().contains("user2;password2;2;102"));
        assertTrue(accountTree.toList().contains("user3;password3;3;103"));
        assertTrue(accountTree.toList().contains("user4;password4;4;104"));
        assertTrue(accountTree.toList().contains("user5;password5;5;105"));
        assertTrue(accountTree.toList().contains("user6;password6;6;106"));
        assertTrue(accountTree.toList().contains("user7;password7;7;107"));
    }
}

