package laboratoire2;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Huffman huffman = new Huffman();
        huffman.Compresser("test.txt", "test-out.txt");
    }


}
