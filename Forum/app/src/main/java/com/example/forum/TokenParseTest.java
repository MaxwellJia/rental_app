package com.example.forum;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TokenParseTest {

    @Test
    public void testExtractLocation() {
        TokenParse parser = new TokenParse("Citdsd 500-600 4");
        assertEquals("City", parser.getLocation());

        parser = new TokenParse("Kindadw 3");
        assertEquals("Kingston", parser.getLocation());

        // Test for an unknown location
        parser = new TokenParse("Cdsds 700-750 1");
        assertNull(parser.getLocation());
    }

    @Test
    public void testExtractMinMaxPrice() {
        TokenParse parser = new TokenParse("Acton 2 600");
        assertEquals(500, (int) parser.getpriceRange().get(0));
        assertEquals(700, (int) parser.getpriceRange().get(1));

        // Test for different price format
        parser = new TokenParse("Acton 450-550");
        assertEquals(450, (int) parser.getpriceRange().get(0));
        assertEquals(550, (int) parser.getpriceRange().get(1));

        // Test for an invalid input
        parser = new TokenParse("Acton 1");
        assertNull(parser.getpriceRange());
    }

    @Test
    public void testExtractBedrooms() {
        TokenParse parser = new TokenParse("Turner 600 6");
        assertEquals(6, parser.getBedrooms());

        parser = new TokenParse("Turner 600 4");
        assertEquals(4, parser.getBedrooms());

        // Test for no bedroom information
        parser = new TokenParse("Turner 600");
        assertEquals(0, parser.getBedrooms());

        // Test for an invalid input
        parser = new TokenParse("Turner 600 7");
        assertEquals(0, parser.getBedrooms());
    }
}
