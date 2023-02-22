package compress;

import module.BitOutputStream;
import module.HuffmanTree;

import java.io.IOException;
import java.util.Map;

public class Compress {
    private HuffmanTree tree;
    private Map<Integer, String> codes;
    private byte[] octetDuFichier;
    private int numBitsWritten; // counter for the number of bits written

    public Compress(HuffmanTree tree, Map<Integer, String> codes, byte[] tab) {
        this.tree = tree;
        this.codes = codes;
        this.octetDuFichier = tab;
        this.numBitsWritten = 0;
    }

    public void compressfile(String outputFilePath) throws IOException {
        BitOutputStream out = new BitOutputStream(outputFilePath);

        // Write the header information
        out.writeLong(tree..size()); // Write the number of distinct symbols
        for (Map.Entry<Integer, Long> entry : tree.getFrequencyTable().entrySet()) {
            int symbol = entry.getKey();
            long freq = entry.getValue();
            out.writeInt(symbol); // Write the symbol value as an int
            out.writeLong(freq); // Write the frequency as a long
        }

        // Write the compressed data
        String binNumber = "";
        for (byte b : this.octetDuFichier) {
            if (b < 0) {
                binNumber = this.codes.get(b + 256);
            } else {
                binNumber = this.codes.get((int) b);
            }

            for (int i = 0; i < binNumber.length(); i++) {
                char c = binNumber.charAt(i);
                int value = Character.getNumericValue(c);
                writeBits(out, value, 1);
            }
        }

        // calculate the number of padding bits needed to make the total number of bits a multiple of 8
        int numPaddingBits = 8 - (this.numBitsWritten % 8);
        if (numPaddingBits != 8) {
            for (int i = 0; i < numPaddingBits; i++) {
                out.writeBit(0);
            }
        }

        out.close();
    }

    private void writeBits(BitOutputStream out, int value, int numBits) throws IOException {
        for (int i = numBits - 1; i >= 0; i--) {
            int bit = (value >> i) & 1;
            out.writeBit(bit);
            this.numBitsWritten++; // increment the counter for each bit written
        }
    }
}