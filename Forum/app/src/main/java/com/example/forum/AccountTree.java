package com.example.forum;

import com.google.firebase.database.core.utilities.Tree;

import java.util.ArrayList;
import java.util.List;

public class AccountTree extends Tree {

    public AccountTree(Account account) {
        root=account;
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
    public Account search(String account) {
        return search(root, account);
    }

    private Account search(Account node, String account) {
        if (node == null) {
            return null; // Account not found
        }

        int cmp = account.compareTo(node.account);

        if (cmp < 0) {
            return search(node.left, account);
        } else if (cmp > 0) {
            return search(node.right, account);
        } else {
            return node; // Found the node with the specified account name
        }
    }
    // Delete a node with a given account name
    public void delete(String account) {
        root = delete(root, account);
    }

    private Account delete(Account node, String account) {
        if (node == null) {
            return node; // Account not found, nothing to delete
        }

        int cmp = account.compareTo(node.account);

        // Recursively search for the node to delete
        if (cmp < 0) {
            node.left = delete(node.left, account);
        } else if (cmp > 0) {
            node.right = delete(node.right, account);
        } else {
            // Node with the account name to be deleted is found

            // Node with only one child or no child
            if (node.left == null || node.right == null) {
                Account temp = (node.left != null) ? node.left : node.right;

                // No child case
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    // One child case
                    node = temp; // Copy the contents of the non-empty child
                }

                temp = null; // Set the temporary node to null
            } else {
                // Node with two children: get the inorder successor (smallest in the right subtree)
                Account temp = minValueNode(node.right);

                // Copy the inorder successor's data to this node
                node.account = temp.account;
                node.password = temp.password;

                // Delete the inorder successor
                node.right = delete(node.right, temp.account);
            }
        }

        // If the tree had only one node, then return
        if (node == null) {
            return node;
        }

        // Update the height of the current node
        updateHeight(node);

        // Get the balance factor of this node
        int balance = getBalance(node);

        // Perform rotations to balance the tree (same as insert)
        if (balance > 1) {
            if (getBalance(node.left) >= 0) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }

        if (balance < -1) {
            if (getBalance(node.right) <= 0) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    // Helper method to find the node with the minimum value in a subtree
    private Account minValueNode(Account node) {
        Account current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    // Traverse the AVL tree in-order and return a list of all accounts
    public List<String> getNewJSONArrays() {
        List<String> accountList = new ArrayList<>();
        inOrderTraversal(root, accountList);
        return accountList;
    }

    private void inOrderTraversal(Account node, List<String> accountList) {
        if (node != null) {
            inOrderTraversal(node.left, accountList);
            accountList.add(node.account+";"+node.password);
            inOrderTraversal(node.right, accountList);
        }
    }
}
