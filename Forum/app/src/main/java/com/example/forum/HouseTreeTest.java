package com.example.forum;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class HouseTreeTest {

    private HouseTree tree;

    @Before
    public void setUp() {
        tree = new HouseTree(null);
    }

    @Test
    public void testEmptyTree() {
        assertNull(tree.getRoot());
        List<House> result = tree.getHousesPriceRange(50, 150);
        assertTrue(result.isEmpty());

        result = tree.toList();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testInsertSingleHouse() {
        House house = new House("1", "CityA", "SuburbA", "StreetA", "10", "UnitA", 100, 1, "emailA@example.com", 5);
        tree.insert(house);

        assertEquals(house, tree.getRoot());
    }

    @Test
    public void testInsertAndRetrieveWithinPriceRange() {
        House house1 = new House("1", "CityA", "SuburbA", "StreetA", "10", "UnitA", 70, 1, "emailA@example.com", 5);
        House house2 = new House("2", "CityB", "SuburbB", "StreetB", "20", "UnitB", 100, 2, "emailB@example.com", 10);
        House house3 = new House("3", "CityC", "SuburbC", "StreetC", "30", "UnitC", 150, 3, "emailC@example.com", 15);

        tree.insert(house1);
        tree.insert(house2);
        tree.insert(house3);

        List<House> result = tree.getHousesPriceRange(50, 120);
        assertEquals(2, result.size());
        assertTrue(result.contains(house1));
        assertTrue(result.contains(house2));
        assertFalse(result.contains(house3));
    }

    @Test
    public void testBalancingAfterInsertion() {
        House house1 = new House("1", "CityA", "SuburbA", "StreetA", "10", "UnitA", 60, 1, "emailA@example.com", 5);
        House house2 = new House("2", "CityB", "SuburbB", "StreetB", "20", "UnitB", 70, 2, "emailB@example.com", 10);
        House house3 = new House("3", "CityC", "SuburbC", "StreetC", "30", "UnitC", 80, 3, "emailC@example.com", 15);
        House house4 = new House("4", "CityD", "SuburbD", "StreetD", "40", "UnitD", 90, 4, "emailD@example.com", 20);

        tree.insert(house1);
        tree.insert(house2);
        tree.insert(house3);
        tree.insert(house4);
        assertEquals(house2, tree.getRoot());
        assertEquals(house1, tree.getRoot().getLeft());
        assertEquals(house3, tree.getRoot().getRight());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testIteratorException() {
        tree.iterator().next();
    }

    @Test
    public void testIteratorTraversal() {
        House house1 = new House("1", "CityA", "SuburbA", "StreetA", "10", "UnitA", 60, 1, "emailA@example.com", 5);
        House house2 = new House("2", "CityB", "SuburbB", "StreetB", "20", "UnitB", 70, 2, "emailB@example.com", 10);
        House house3 = new House("3", "CityC", "SuburbC", "StreetC", "30", "UnitC", 80, 3, "emailC@example.com", 15);

        tree.insert(house1);
        tree.insert(house2);
        tree.insert(house3);

        Iterator<House> iterator = tree.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(house1, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(house2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(house3, iterator.next());

        assertFalse(iterator.hasNext());
    }
}
