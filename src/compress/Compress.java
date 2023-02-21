package compress;

import module.BitOutputStream;
import module.HuffmanTree;

import java.io.IOException;
import java.util.Map;

public class Compress {
    private HuffmanTree tree;
    private Map<Integer, String> codes;
    private byte[] octetDuFichier;
    public Compress(HuffmanTree tree, Map<Integer, String> codes,byte[] tab) {
        this.tree = tree;
        this.codes = codes;
        this.octetDuFichier = tab;
    }

    public void compressfile(String outputFilePath) throws IOException {
        BitOutputStream out = new BitOutputStream(outputFilePath);
        String binNumber="";
        for (byte b : this.octetDuFichier) {
            if(b<0)
            {
                binNumber =  this.codes.get(b+256);
                System.out.print("-"+binNumber);
                for (int i = 0; i < binNumber.length(); i++) {
                    char c = binNumber.charAt(i);
                    int value = Character.getNumericValue(c);
                    out.writeBit(value);
                }
            }
            else
            {
                binNumber =  this.codes.get((int)b);
                System.out.print("-"+binNumber);
                for (int i = 0; i < binNumber.length(); i++) {
                    char c = binNumber.charAt(i);
                    int value = Character.getNumericValue(c);
                    out.writeBit(value);
                }
            }
        }
        out.close();
        System.out.println("");
    }



}
