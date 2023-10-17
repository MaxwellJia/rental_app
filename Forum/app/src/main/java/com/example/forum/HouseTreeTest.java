package com.example.forum;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class HouseTreeTest {
    private HouseTree houseTree;

    @Before
    public void setUp() {
        // Initialize the AVL tree with some sample data
        House root = new House("1", "City1", "Suburb1", "Street1", "1", "Unit1", 100000, 1, "email1", 5);
        houseTree = new HouseTree(root);

        // Add more nodes to the tree for testing
        houseTree.insert(new House("2", "City2", "Suburb2", "Street2", "2", "Unit2", 80000, 2, "email2", 10));
        houseTree.insert(new House("3", "City3", "Suburb3", "Street3", "3", "Unit3", 120000, 3, "email3", 7));
        houseTree.insert(new House("4", "City4", "Suburb4", "Street4", "4", "Unit4", 95000, 4, "email4", 15));
        houseTree.insert(new House("5", "City5", "Suburb5", "Street5", "5", "Unit5", 110000, 5, "email5", 20));
        houseTree.insert(new House("6", "City6", "Suburb6", "Street6", "6", "Unit6", 75000, 6, "email6", 8));
        houseTree.insert(new House("7", "City7", "Suburb7", "Street7", "7", "Unit7", 130000, 7, "email7", 12));
        houseTree.insert(new House("8", "City8", "Suburb8", "Street8", "8", "Unit8", 90000, 8, "email8", 18));
        houseTree.insert(new House("9", "City9", "Suburb9", "Street9", "9", "Unit9", 85000, 9, "email9", 25));
        houseTree.insert(new House("10", "City10", "Suburb10", "Street10", "10", "Unit10", 140000, 10, "email10", 14));
    }

    @Test
    public void testGetHousesPriceRange() {
        List<House> housesInRange = houseTree.getHousesPriceRange(90000, 120000);
        for(House h:housesInRange){
            System.out.println(h.getPrice());
        }
        assertEquals(5, housesInRange.size());
        // Ensure that houses in the range are correctly retrieved
        for (House house : housesInRange) {
            int price = house.getPrice();
            assertTrue(price >= 90000 && price <= 120000);
        }
    }

    @Test
    public void testToList() {
        List<House> houseList = houseTree.toList();

        assertEquals(10, houseList.size());
        // Ensure that the list contains all nodes in the tree
        for (House house : houseList) {
            assertNotNull(houseTree.getRoot().getId());
        }
    }

    @Test
    public void testIterator() {
        int count = 0;
        for (House house : houseTree) {
            count++;
        }

        assertEquals(10, count);
    }

    // Add more test cases as needed

    @Test
    public void testInsert() {
        // Test inserting a new house and ensure it's in the tree
        House newHouse = new House("11", "City11", "Suburb11", "Street11", "11", "Unit11", 95000, 11, "email11", 30);
        houseTree.insert(newHouse);
        List<House> houseList = houseTree.toList();

        assertTrue(houseList.contains(newHouse));
    }
}

