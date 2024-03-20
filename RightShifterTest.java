import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class RightShifterTest {

    @Test
    public void testHexToBinaryWithValidInput() {
        RightShifter.init();
        assertEquals("0000", RightShifter.hexToBinary("0"));
        assertEquals("0001", RightShifter.hexToBinary("1"));
        assertEquals("1010", RightShifter.hexToBinary("A"));
        assertEquals("00110110", RightShifter.hexToBinary("36"));
        assertEquals("1010101010101010", RightShifter.hexToBinary("AAAA"));
        assertEquals("100000011000000110000001", RightShifter.hexToBinary("818181"));
        assertEquals("10000111011001010100001100100001", RightShifter.hexToBinary("87654321"));
    }

    @Test
    public void testHexToBinaryWithInvalidInput() {
        // Test with invalid hexadecimal input
        // Expecting IllegalArgumentException to be thrown
        RightShifter.init();
        try {
            RightShifter.hexToBinary("G");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid hexadecimal character: G", e.getMessage());
        }
        try {
            RightShifter.hexToBinary("");
        } catch (IllegalArgumentException e) {
            assertEquals("Input hexadecimal string cannot be null or empty", e.getMessage());
        }
        try {
            RightShifter.hexToBinary(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Input hexadecimal string cannot be null or empty", e.getMessage());
        }
        try {
            RightShifter.hexToBinary("-1");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid hexadecimal character: -", e.getMessage());
        }
    }
}
