package com.example.forum;

import java.util.ArrayList;
import java.util.List;

public class HouseTree {
    private House root;

    public void insert(House house) {
        root = insert(root, house);
    }

    private House insert(House node, House house) {
        if (node == null) {
            return house;
        }

        if (house.getPrice() <= node.getPrice()) {
            node.left = insert(node.left, house);
        } else {
            node.right = insert(node.right, house);
        }

        node.setHeight(1 + Math.max(getHeight(node.left), getHeight(node.right)));

        int balance = getBalance(node);

        // Perform AVL rotations if needed
        if (balance > 1 && house.getPrice() <= node.left.getPrice()) {
            return rotateRight(node);
        }
        if (balance < -1 && house.getPrice() > node.right.getPrice()) {
            return rotateLeft(node);
        }
        if (balance > 1 && house.getPrice() > node.left.getPrice()) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && house.getPrice() <= node.right.getPrice()) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private int getHeight(House node) {
        return (node != null) ? node.getHeight() : 0;
    }

    private int getBalance(House node) {
        return (node != null) ? getHeight(node.left) - getHeight(node.right) : 0;
    }

    private House rotateRight(House y) {
        House x = y.left;
        House T2 = x.right;

        x.right = y;
        y.left = T2;

        y.setHeight(Math.max(getHeight(y.left), getHeight(y.right)) + 1);
        x.setHeight(Math.max(getHeight(x.left), getHeight(x.right)) + 1);

        return x;
    }

    private House rotateLeft(House x) {
        House y = x.right;
        House T2 = y.left;

        y.left = x;
        x.right = T2;

        x.setHeight(Math.max(getHeight(x.left), getHeight(x.right)) + 1);
        y.setHeight(Math.max(getHeight(y.left), getHeight(y.right)) + 1);

        return y;
    }

    public List<House> getHousesInDistrict(String district) {
        List<House> result = new ArrayList<>();
        getHousesInDistrict(root, district, result);
        return result;
    }

    private void getHousesInDistrict(House node, String district, List<House> result) {
        if (node == null) {
            return;
        }

        int cmp = district.compareTo(node.getDistrict());

        if (cmp < 0) {
            getHousesInDistrict(node.left, district, result);
        } else if (cmp > 0) {
            getHousesInDistrict(node.right, district, result);
        } else {
            getHousesInDistrict(node.left, district, result);
            if (node.getDistrict().equals(district)) {
                result.add(node);
            }
            getHousesInDistrict(node.right, district, result);
        }
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

        if (price < node.getPrice()) {
            getHousesByPrice(node.left, price, result);
        } else if (price > node.getPrice()) {
            getHousesByPrice(node.right, price, result);
        } else {
            getHousesByPrice(node.left, price, result);
            if (node.getPrice() == price) {
                result.add(node);
            }
            getHousesByPrice(node.right, price, result);
        }
    }

    public List<House> getHousesInPriceRange(int minPrice, int maxPrice) {
        List<House> result = new ArrayList<>();
        getHousesInPriceRange(root, minPrice, maxPrice, result);
        return result;
    }

    private void getHousesInPriceRange(House node, int minPrice, int maxPrice, List<House> result) {
        if (node == null) {
            return;
        }

        if (node.getPrice() >= minPrice && node.getPrice() <= maxPrice) {
            getHousesInPriceRange(node.left, minPrice, maxPrice, result);
            result.add(node);
            getHousesInPriceRange(node.right, minPrice, maxPrice, result);
        } else if (node.getPrice() < minPrice) {
            getHousesInPriceRange(node.right, minPrice, maxPrice, result);
        } else {
            getHousesInPriceRange(node.left, minPrice, maxPrice, result);
        }
    }

    public void deleteHouseById(int id) {
        root = deleteHouseById(root, id);
    }

    private House deleteHouseById(House node, int id) {
        if (node == null) {
            return null;
        }

        if (id < node.getId()) {
            node.left = deleteHouseById(node.left, id);
        } else if (id > node.getId()) {
            node.right = deleteHouseById(node.right, id);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            node.setId(findMin(node.right).getId());
            node.right = deleteHouseById(node.right, node.getId());
        }

        node.setHeight(Math.max(getHeight(node.left), getHeight(node.right)) + 1);

        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private House findMin(House node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
