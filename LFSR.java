public class LFSR {
//    static byte[] Crypt(byte[] data, long initialValue) {
//    long LFSR = 87654321;
//        int stepTime = 8;
//
//        for (int i = 0; i < stepTime; i++) {
//
//        }
//
//    }
    public static void main(String[] args) {
        // S
        RightShifter.init();
        String initialValue = "FFFFFFFF";
        String initBinaryVal = RightShifter.hexToBinary(initialValue);
        long initDecimalVal = Long.parseLong(initBinaryVal, 2);

        System.out.println("Hex to binary: " + initialValue + " " + initBinaryVal);
        System.out.println("Numeric Value: " + initDecimalVal);

        // F
        String LFSR = "87654321";
        String LFSRBinaryVal = RightShifter.hexToBinary(LFSR);
        long LFSRDecimalVal = Long.parseLong(LFSRBinaryVal, 2);

        System.out.println("Hex to binary: " + LFSR + " " + LFSRBinaryVal);
        System.out.println("Numeric Value: " + LFSRDecimalVal);
        // TODO: CHANGE TO ACTUAL START STATE AFTER MAKING SURE ALGO WORKS!!
    }

    /*
     * Resources used:
     * https://en.wikipedia.org/wiki/Linear-feedback_shift_register
    */
}