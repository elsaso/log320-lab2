package compress;

import java.util.*;
import java.io.*;
import module.*;

public class Decompress {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Decompress input_file output_file");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        // Open input stream
        BitInputStream in = new BitInputStream(inputFile);

        // Read header information
        int numSymbols = in.readBit();
        int numBits = in.readBit();
        List<Integer> counts = new ArrayList<>();
        for (int i = 0; i < numSymbols; i++) {
            counts.add(in.readBit());
        }

        // Reconstruct Huffman tree from header information
        PriorityQueue<HuffmanTree> pq = new PriorityQueue<>();
        for (int i = 0; i < numSymbols; i++) {
            int count = counts.get(i);
            if (count > 0) {
                pq.offer(new HuffmanLeaf(i, count));
            }
        }
        while (pq.size() < 2) {
            pq.offer(new HuffmanLeaf(0, 0));
        }
        while (pq.size() > 1) {
            HuffmanTree left = pq.poll();
            HuffmanTree right = pq.poll();
            pq.offer(new HuffmanNode(left, right));
        }
        HuffmanTree huffmanTree = pq.poll();

        // Open output stream
        BitOutputStream out = new BitOutputStream(outputFile);

        // Decompress file
        int numDecoded = 0;
        HuffmanTree node = huffmanTree;
        while (numDecoded < numBits) {
            int bit = in.readBit();
            if (bit == -1) {
                System.out.println("Error: unexpected end of file");
                break;
            }
            node = (bit == 0) ? node.getLeft() : node.getRight();
            if (node instanceof HuffmanLeaf) {
                out.writeBits(((HuffmanLeaf) node).getSymbol(), 8);
                node = huffmanTree;
                numDecoded++;
            }
        }

        // Close input and output streams
        in.close();
        out.close();

        System.out.println("Decompression complete.");
    }
}