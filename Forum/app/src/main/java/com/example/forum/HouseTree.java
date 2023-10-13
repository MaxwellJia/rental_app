package com.example.forum;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class HouseTree implements Iterable<House>{
    public House root;

    public House getRoot() {
        return root;
    }

    public void setRoot(House root) {
        this.root = root;
    }

    public HouseTree(House root) {
        this.root = root;
    }




    public List<House> getHousesByPrice(int price) {
        List<House> result = new ArrayList<>();
        getHousesByPrice(root, price, result);
        return result;
    }

    private void getHousesByPrice(House node, int price, List<House> result) {
        if (node == null) {
            return;
        }

        int priceComparison = Integer.compare(price, node.getPrice());

        if (priceComparison < 0) {
            getHousesByPrice(node.getLeft(), price, result);
        } else if (priceComparison > 0) {
            getHousesByPrice(node.getRight(), price, result);
        } else {
            result.add(node);
            getHousesByPrice(node.getLeft(), price, result);
            getHousesByPrice(node.getRight(), price, result);
        }
    }

    public List<House> getHousesPriceRange(int lowerBound, int upperBound) {
        List<House> result = new ArrayList<>();
        getHousesPriceRange(root, lowerBound, upperBound, result);
        return result;
    }

    private void getHousesPriceRange(House node, int lowerBound, int upperBound, List<House> result) {
        if (node == null) {
            return;
        }

        // Check left subtree if the current node's price is within the range.
        if (node.getPrice() >= lowerBound) {
            getHousesPriceRange(node.getLeft(), lowerBound, upperBound, result);
        }

        // If the current node's price is within the range, add it to the result.
        if (node.getPrice() >= lowerBound && node.getPrice() <= upperBound) {
            result.add(node);
        }

        // Check right subtree if the current node's price is within the range.
        if (node.getPrice() <= upperBound) {
            getHousesPriceRange(node.getRight(), lowerBound, upperBound, result);
        }
    }


    public void insert(House house) {
        root = insert(root, house);
    }

    private House insert(House node, House houseToInsert) {
        if (node == null) {
            return houseToInsert;
        }

        int priceComparison = Integer.compare(houseToInsert.getPrice(), node.getPrice());

        if (priceComparison <=0) {
            node.setLeft(insert(node.getLeft(), houseToInsert));
        } else {
            // We allow duplicates, so insert to the right subtree even if prices are equal.
            node.setRight(insert(node.getRight(), houseToInsert));
        }

        // Update the height of this node.
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));

        // Check and balance the tree to maintain AVL property.
        return balance(node);
    }

    private House balance(House node) {
        int balanceFactor = getBalanceFactor(node);

        // Left heavy
        if (balanceFactor > 1) {
            // Left-right case
            if (getBalanceFactor(node.getLeft()) < 0) {
                node.setLeft(leftRotate(node.getLeft()));
            }
            return rightRotate(node);
        }
        // Right heavy
        if (balanceFactor < -1) {
            // Right-left case
            if (getBalanceFactor(node.getRight()) > 0) {
                node.setRight(rightRotate(node.getRight()));
            }
            return leftRotate(node);
        }

        return node;
    }

    private House leftRotate(House node) {
        House newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);

        // Update heights
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
        newRoot.setHeight(1 + Math.max(getHeight(newRoot.getLeft()), getHeight(newRoot.getRight())));

        return newRoot;
    }

    private House rightRotate(House node) {
        House newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);

        // Update heights
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
        newRoot.setHeight(1 + Math.max(getHeight(newRoot.getLeft()), getHeight(newRoot.getRight())));

        return newRoot;
    }

    private int getHeight(House node) {
        return (node != null) ? node.getHeight() : 0;
    }

    private int getBalanceFactor(House node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }




    @Override
    public Iterator<House> iterator() {
        return new AVLTreeIterator(root);
    }
    private class AVLTreeIterator implements Iterator<House> {
        private Stack<House> stack = new Stack<>();
        private House current;

        public AVLTreeIterator(House root) {
            current = root;
            initializeStack(root);
        }

        private void initializeStack(House node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public House next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            House next = stack.pop();
            current = next;
            if (next.getRight() != null) {
                initializeStack(next.getRight());
            }
            return next;
        }

        public House getCurrent() {
            return current;
        }
    }
    //Traverse the whole AVL tree
    public List<House> toList(){
        List<House> storage=new ArrayList<>();
        Iterator<House> it = this.iterator();
        while (it.hasNext()) {
            House house = it.next();
            storage.add(house);
        }
        return storage;
    }
}
