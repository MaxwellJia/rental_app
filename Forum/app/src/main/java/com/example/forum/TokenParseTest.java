package com.example.forum;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TokenParseTest {

    private TokenParse parser;

    @Before
    public void setUp() {
        parser = null;
    }

    @Test
    public void testLocationParsing() {
        parser = new TokenParse("City");
        assertEquals("City", parser.getLocation());

        parser = new TokenParse("Kin3");
        assertEquals("Kingston", parser.getLocation());

        parser = new TokenParse("123 bar 456");
        assertEquals("Barton", parser.getLocation());

        // Edge case: Empty input
        parser = new TokenParse("");
        assertNull(parser.getLocation());
    }

    @Test
    public void testMinMaxPriceParsing() {
        parser = new TokenParse("1000-2000");
        assertEquals(Arrays.asList(1000, 2000), parser.getpriceRange());

        parser = new TokenParse("2000-1000");
        assertEquals(Arrays.asList(1000, 2000), parser.getpriceRange());

        parser = new TokenParse("2500");
        assertEquals(Arrays.asList(2400, 2600), parser.getpriceRange());

        // Edge case: No price info
        parser = new TokenParse("City");
        assertNull(parser.getpriceRange());

        // Edge case: Invalid range format
        parser = new TokenParse("1000-2000-3000");
        assertEquals(parser.getpriceRange(),new ArrayList<>());
        //assertNull(parser.getpriceRange());

        // Edge case: Non-numeric values
        parser = new TokenParse("abcd-efgh");
        assertNull(parser.getpriceRange());
    }

    @Test
    public void testBedroomParsing() {
        parser = new TokenParse("3 bedrooms");
        assertEquals(3, parser.getBedrooms());

        // Edge case: More than 6 rooms
        parser = new TokenParse("9 bedrooms");
        assertEquals(0, parser.getBedrooms());

        // Edge case: No bedroom info
        parser = new TokenParse("City");
        assertEquals(0, parser.getBedrooms());

        // Edge case: Invalid format
        parser = new TokenParse("three bedrooms");
        assertEquals(0, parser.getBedrooms());
    }

    @Test
    public void testComplexString() {
        parser = new TokenParse("I want 3 bedrooms in kin with price range 1000-2000");
        assertEquals("Kingston", parser.getLocation());
        assertEquals(3, parser.getBedrooms());
        assertEquals(Arrays.asList(1000, 2000), parser.getpriceRange());
    }

    @Test
    public void testAnotherComplexString() {
        parser = new TokenParse("4 br in nor for 3000");
        assertEquals("North Adelaide", parser.getLocation());
        assertEquals(4, parser.getBedrooms());
        assertEquals(Arrays.asList(2900, 3100), parser.getpriceRange());
    }

    // Add more test cases as needed
}

