package com.example.forum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;
public class HouseAVLTree {
    public Node getHouseRoot() {
        return houseRoot;
    }

    public void setHouseRoot(Node houseRoot) {
        this.houseRoot = houseRoot;
    }

    private Node houseRoot;

    private class Node {
        House house;


        Node left;
        Node right;

        public House getHouse() {
            return house;
        }

        public void setHouse(House house) {
            this.house = house;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        int height;
        int size;

        Node(House house) {
            this.house = house;
            this.height = 1;
            this.size = 1;
        }
    }
    @JsonCreator
    public HouseAVLTree() {
    }

    public void insert(House house) {
        houseRoot = insert(houseRoot, house);
    }

    private Node insert(Node node, House house) {
        if (node == null) {
            return new Node(house);
        }

        int cmp = house.price - node.house.price;

        if (cmp <= 0) {
            // Handle duplicates by inserting on the left subtree
            node.left = insert(node.left, house);
        } else {
            node.right = insert(node.right, house);
        }

        // Update height and size of this node
        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);

        // Perform balancing
        return balance(node);
    }

    public void delete(int price) {
        houseRoot = delete(houseRoot, price);
    }

    private Node delete(Node node, int price) {
        if (node == null) {
            return null;
        }

        int cmp = price - node.house.price;

        if (cmp < 0) {
            node.left = delete(node.left, price);
        } else if (cmp > 0) {
            node.right = delete(node.right, price);
        } else {
            // Node with this price found
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node minNode = findMin(node.right);
                node.house = minNode.house;
                node.right = delete(node.right, minNode.house.price);
            }
        }

        // Update height and size of this node
        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);

        // Perform balancing
        return balance(node);
    }

    public List<House> getHousesInPriceRange(int lowerBound, int upperBound) {
        List<House> result = new ArrayList<>();
        getHousesInPriceRange(houseRoot, lowerBound, upperBound, result);
        return result;
    }

    private void getHousesInPriceRange(Node node, int lowerBound, int upperBound, List<House> result) {
        if (node == null) {
            return;
        }

        int cmpLower = lowerBound - node.house.price;
        int cmpUpper = upperBound - node.house.price;

        if (cmpLower <=0) {
            getHousesInPriceRange(node.left, lowerBound, upperBound, result);
        }
        if (cmpLower <= 0 && cmpUpper >= 0) {
            result.add(node.house);
        }
        if (cmpUpper >= 0) {
            getHousesInPriceRange(node.right, lowerBound, upperBound, result);
        }
    }

    private Node balance(Node node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.right) < 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        if (balance < -1) {
            if (getBalance(node.left) > 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        return node;
    }

    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private int size(Node node) {
        return (node == null) ? 0 : node.size;
    }

    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.right) - height(node.left);
    }

    private Node rotateLeft(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        x.left = y;
        y.right = T2;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        y.size = 1 + size(y.left) + size(y.right);
        x.size = 1 + size(x.left) + size(x.right);

        return x;
    }

    private Node rotateRight(Node x) {
        Node y = x.left;
        Node T2 = y.right;

        y.right = x;
        x.left = T2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        x.size = 1 + size(x.left) + size(x.right);
        y.size = 1 + size(y.left) + size(y.right);

        return y;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
