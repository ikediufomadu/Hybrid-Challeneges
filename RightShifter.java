import java.util.HashMap;

public class RightShifter {
    private static final HashMap<Character, String> HEX_TO_BINARY_MAP = new HashMap<>();

    private RightShifter(){}

    public static void init() {
        HEX_TO_BINARY_MAP.put('0', "0000");
        HEX_TO_BINARY_MAP.put('1', "0001");
        HEX_TO_BINARY_MAP.put('2', "0010");
        HEX_TO_BINARY_MAP.put('3', "0011");
        HEX_TO_BINARY_MAP.put('4', "0100");
        HEX_TO_BINARY_MAP.put('5', "0101");
        HEX_TO_BINARY_MAP.put('6', "0110");
        HEX_TO_BINARY_MAP.put('7', "0111");
        HEX_TO_BINARY_MAP.put('8', "1000");
        HEX_TO_BINARY_MAP.put('9', "1001");
        HEX_TO_BINARY_MAP.put('A', "1010");
        HEX_TO_BINARY_MAP.put('B', "1011");
        HEX_TO_BINARY_MAP.put('C', "1100");
        HEX_TO_BINARY_MAP.put('D', "1101");
        HEX_TO_BINARY_MAP.put('E', "1110");
        HEX_TO_BINARY_MAP.put('F', "1111");
    }

    public static String hexToBinary(String hexadecimal) {
        if (hexadecimal == null || hexadecimal.isEmpty()) {
            throw new IllegalArgumentException("Input hexadecimal string cannot be null or empty");
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < hexadecimal.length(); i++) {
            char hexChar = hexadecimal.charAt(i);
            String binaryValue = HEX_TO_BINARY_MAP.get(hexChar);

            if (binaryValue != null) {
                result.append(binaryValue);
            } else {
                throw new IllegalArgumentException("Invalid hexadecimal character: " + hexChar);
            }
        }
        return result.toString();
    }

    public static Byte[] stringToByte(String stringBinary) {
        int stringSize = stringBinary.length();
        Byte[] byteBinary = new Byte[stringSize];

        for (int i = 0; i < stringSize; i++) {
            byteBinary[i] = (byte) stringBinary.charAt(i);
        }

        return byteBinary;
    }
}
