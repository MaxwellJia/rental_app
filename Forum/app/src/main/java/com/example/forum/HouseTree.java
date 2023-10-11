package com.example.forum;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class HouseTree implements Iterable<House>{
    private House root;

    public House getRoot() {
        return root;
    }

    public void setRoot(House root) {
        this.root = root;
    }

    public HouseTree(House root) {
        this.root = root;
    }

    public void insert(House house) {
        root = insert(root, house);
    }

    private House insert(House node, House house) {
        if (node == null) {
            return house;
        }

        int priceComparison = Integer.compare(house.getPrice(), node.getPrice());

        if (priceComparison <= 0) {
            node.setLeft(insert(node.getLeft(), house));
        } else {
            node.setRight(insert(node.getRight(), house));
        }

        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));

        int balance = getBalance(node);

        if (balance > 1) {
            if (priceComparison < 0) {
                return rotateRight(node);
            } else if (priceComparison > 0) {
                node.setLeft(rotateLeft(node.getLeft()));
                return rotateRight(node);
            }
        }

        if (balance < -1) {
            if (priceComparison > 0) {
                return rotateLeft(node);
            } else if (priceComparison < 0) {
                node.setRight(rotateRight(node.getRight()));
                return rotateLeft(node);
            }
        }

        return node;
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

    public List<House> getHousesPriceRange(int lowerBound,int upperBound) {
        List<House> result = new ArrayList<>();
        getHousesPriceRange(root, lowerBound,upperBound, result);
        return result;
    }

    private void getHousesPriceRange(House node, int lowerBound,int upperBound, List<House> result) {
        if (node == null) {
            return;
        }
        if (node.getPrice()>upperBound) {
            // The current house price is above the upper bound.
            // Add it to the result list.

            // Continue searching in the left subtree for more houses below the upper bound.
            getHousesPriceRange(node.getLeft(), lowerBound,upperBound, result);
        } else if(node.getPrice()<lowerBound) {
            // The current house price is below to the upper bound.
            // Continue searching in right subtree.
            getHousesPriceRange(node.getLeft(), lowerBound,upperBound, result);
        }else {
            result.add(node);
            getHousesPriceRange(node.getLeft(), lowerBound,upperBound, result);
            getHousesPriceRange(node.getLeft(), lowerBound,upperBound, result);
        }
    }


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

        y.setHeight(1 + Math.max(getHeight(y.getLeft()), getHeight(y.getRight())));
        x.setHeight(1 + Math.max(getHeight(x.getLeft()), getHeight(x.getRight())));

        return x;
    }

    private House rotateLeft(House x) {
        House y = x.getRight();
        House T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.setHeight(1 + Math.max(getHeight(x.getLeft()), getHeight(x.getRight())));
        y.setHeight(1 + Math.max(getHeight(y.getLeft()), getHeight(y.getRight())));

        return y;
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

}
