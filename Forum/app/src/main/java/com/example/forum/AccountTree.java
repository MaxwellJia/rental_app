package com.example.forum;

import com.google.firebase.database.core.utilities.Tree;

public class AccountTree extends Tree {

    public AccountTree( Account root) {
        this.root = root;
    }

    public AccountTree() {
    }

    public Account root;

    public Account getRoot() {
        return root;
    }

    public void setRoot(Account root) {
        this.root = root;
    }

    // Helper method to get the height of a node
    private int height(Account node) {
        if (node == null) return 0;
        return node.height;
    }

    // Helper method to update the height of a node
    private void updateHeight(Account node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    // Helper method to perform a right rotation
    private Account rotateRight(Account y) {
        Account x = y.left;
        Account T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Helper method to perform a left rotation
    private Account rotateLeft(Account x) {
        Account y = x.right;
        Account T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Helper method to get the balance factor of a node
    private int getBalance(Account node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    // Insert a new node into the AVL tree
    public void insert(String account, String password) {
        root = insert(root, account, password);
    }

    private Account insert(Account node, String account, String password) {
        if (node == null) {
            return new Account(account, password);
        }

        if (account.compareTo(node.account) < 0) {
            node.left = insert(node.left, account, password);
        } else if (account.compareTo(node.account) > 0) {
            node.right = insert(node.right, account, password);
        } else {
            // Handle duplicate accounts if needed
            return node;
        }

        updateHeight(node);

        int balance = getBalance(node);

        // Perform rotations to balance the tree
        if (balance > 1) {
            if (account.compareTo(node.left.account) < 0) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }

        if (balance < -1) {
            if (account.compareTo(node.right.account) > 0) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    // Search for a node by account name and return it
    public String search(String account) {
        return search(root, account);
    }

    private String search(Account node, String account) {
        if (node == null) {
            return null; // Account not found
        }

        int cmp = account.compareTo(node.account);

        if (cmp < 0) {
            return search(node.left, account);
        } else if (cmp > 0) {
            return search(node.right, account);
        } else {
            return node.password; // Found the node with the specified account name
        }
    }
}
