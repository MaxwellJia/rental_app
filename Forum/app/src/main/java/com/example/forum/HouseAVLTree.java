package com.example.forum;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HouseAVLTree  {
    public House root;

    // Constructor

    @JsonCreator
    public HouseAVLTree() {
    }

    public HouseAVLTree(House root) {
        this.root = root;
    }

    // Insert a new House object with duplicate prices allowed
    public void insert(House house) {
        root = insert(root, house);
    }

    private House insert(House node, House house) {
        if (node == null) {
            return house;
        }

        if (house.getPrice() <= node.getPrice()) {
            node.setLeft(insert(node.getLeft(), house));
        } else {
            node.setRight(insert(node.getRight(), house));
        }

        // Update height
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);

        // Perform balance check and rotation
        int balance = getBalance(node);

        // Left Heavy
        if (balance > 1) {
            if (house.getPrice() <= node.getLeft().getPrice()) {
                return rotateRight(node);
            } else {
                node.setLeft(rotateLeft(node.getLeft()));
                return rotateRight(node);
            }
        }

        // Right Heavy
        if (balance < -1) {
            if (house.getPrice() > node.getRight().getPrice()) {
                return rotateLeft(node);
            } else {
                node.setRight(rotateRight(node.getRight()));
                return rotateLeft(node);
            }
        }

        return node;
    }

    // Delete a House object based on its price
    public void delete(int price) {
        root = delete(root, price);
    }

    private House delete(House node, int price) {
        if (node == null) {
            return node;
        }

        if (price < node.getPrice()) {
            node.setLeft(delete(node.getLeft(), price));
        } else if (price > node.getPrice()) {
            node.setRight(delete(node.getRight(), price));
        } else {
            // Node to be deleted found
            if ((node.getLeft() == null) || (node.getRight() == null)) {
                House temp = (node.getLeft() != null) ? node.getLeft() : node.getRight();

                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                House temp = minValueNode(node.getRight());
                node.setPrice(temp.getPrice());
                node.setRight(delete(node.getRight(), temp.getPrice()));
            }
        }

        if (node == null) {
            return node;
        }

        // Update height
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);

        // Perform balance check and rotation
        int balance = getBalance(node);

        // Left Heavy
        if (balance > 1) {
            if (getBalance(node.getLeft()) >= 0) {
                return rotateRight(node);
            } else {
                node.setLeft(rotateLeft(node.getLeft()));
                return rotateRight(node);
            }
        }

        // Right Heavy
        if (balance < -1) {
            if (getBalance(node.getRight()) <= 0) {
                return rotateLeft(node);
            } else {
                node.setRight(rotateRight(node.getRight()));
                return rotateLeft(node);
            }
        }

        return node;
    }

    // Search for houses with a given price
    public List<House> searchByPrice(int price) {
        List<House> result = new ArrayList<>();
        searchByPrice(root, price, result);
        return result;
    }

    private void searchByPrice(House node, int price, List<House> result) {
        if (node == null) {
            return;
        }

        if (price == node.getPrice()) {
            result.add(node);
        }

        if (price <= node.getPrice()) {
            searchByPrice(node.getLeft(), price, result);
        } else {
            searchByPrice(node.getRight(), price, result);
        }
    }

    // Search for houses within a price range
    public List<House> searchByPriceRange(int lowerBound, int upperBound) {
        List<House> result = new ArrayList<>();
        searchByPriceRange(root, lowerBound, upperBound, result);
        return result;
    }

    private void searchByPriceRange(House node, int lowerBound, int upperBound, List<House> result) {
        if (node == null) {
            return;
        }

        int price = node.getPrice();

        if (price >= lowerBound && price <= upperBound) {
            result.add(node);
        }

        if (price > lowerBound) {
            searchByPriceRange(node.getLeft(), lowerBound, upperBound, result);
        }

        if (price < upperBound) {
            searchByPriceRange(node.getRight(), lowerBound, upperBound, result);
        }
    }

    // Utility functions for AVL tree operations
    private int getHeight(House node) {
        if (node == null) {
            return 0;
        }
        return node.getHeight();
    }

    private int getBalance(House node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    private House rotateRight(House y) {
        House x = y.getLeft();
        House T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        y.setHeight(Math.max(getHeight(y.getLeft()), getHeight(y.getRight())) + 1);
        x.setHeight(Math.max(getHeight(x.getLeft()), getHeight(x.getRight())) + 1);

        return x;
    }

    private House rotateLeft(House x) {
        House y = x.getRight();
        House T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.setHeight(Math.max(getHeight(x.getLeft()), getHeight(x.getRight())) + 1);
        y.setHeight(Math.max(getHeight(y.getLeft()), getHeight(y.getRight())) + 1);

        return y;
    }

    private House minValueNode(House node) {
        House current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }
}
