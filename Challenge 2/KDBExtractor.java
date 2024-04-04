import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class KDBExtractor {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java KDBExtractor <path_to_kdb_file>");
            System.exit(1);
        }

        String filePath = args[0];
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);

            // Verify magic bytes
            byte[] magicBytes = new byte[6];
            bis.read(magicBytes);
            String magicString = new String(magicBytes);
            if (!magicString.equals("CT2018")) {
                System.out.println("Invalid KDB file.");
                System.exit(1);
            }

            // Read entry list pointer
            byte[] entryListPointerBytes = new byte[4];
            bis.read(entryListPointerBytes);
            int entryListPointer = ByteBuffer.wrap(entryListPointerBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();

            // Process entry list
            while (entryListPointer != -1) {
                // Move to entry list position
                bis.skip(entryListPointer);

                // Read entry name
                byte[] nameBytes = new byte[16];
                bis.read(nameBytes);
                String entryName = new String(nameBytes).trim();

                // Read block list pointer
                byte[] blockListPointerBytes = new byte[4];
                bis.read(blockListPointerBytes);
                int blockListPointer = ByteBuffer.wrap(blockListPointerBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();

                // Process block list
                while (blockListPointer != -1) {
                    // Move to block list position
                    bis.skip(blockListPointer);

                    // Read block size
                    byte[] blockSizeBytes = new byte[2];
                    bis.read(blockSizeBytes);
                    short blockSize = ByteBuffer.wrap(blockSizeBytes).order(ByteOrder.LITTLE_ENDIAN).getShort();

                    // Read block data pointer
                    byte[] dataPointerBytes = new byte[4];
                    bis.read(dataPointerBytes);
                    int dataPointer = ByteBuffer.wrap(dataPointerBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();

                    // Process block data
                    byte[] blockData = new byte[blockSize];
                    bis.skip(dataPointer);
                    bis.read(blockData);

                    // Decode block data using LFSR algorithm
                    long initialValue = 0x4F574154; // Initial value for LFSR algorithm
                    byte[] decodedData = Crypt(blockData, initialValue);

                    // Print decoded information
                    System.out.println("Entry Name: " + entryName);
                    System.out.println("Decoded Data: " + new String(decodedData));

                    // Move to next block
                    bis.skip(4); // Skip data pointer for the next block
                    bis.read(blockListPointerBytes);
                    blockListPointer = ByteBuffer.wrap(blockListPointerBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
                }

                // Move to next entry
                bis.skip(4); // Skip block list pointer for the next entry
                bis.read(entryListPointerBytes);
                entryListPointer = ByteBuffer.wrap(entryListPointerBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
            }

            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static byte[] Crypt(byte[] data, long initialValue) {
        // Feedback value
        int F = 0x87654321;
        // Current state
        int S = (int) initialValue;

        byte[] encryptedData = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 8; j++) {
                // Step the LFSR
                S = LFSR(S, F);
            }
            // Extract the lowest byte as the keystream
            byte keystream = (byte) (S & 0xFF);
            // XOR the data with the keystream
            encryptedData[i] = xor(data[i], keystream);
        }

        return encryptedData;
    }

    static int LFSR(int state, int F) {
        if ((state & 1) == 0) {
            return state >>> 1;
        } else {
            return (state >>> 1) ^ F;
        }
    }

    static byte xor(byte a, byte b) {
        return (byte) (a ^ b);
    }
}
