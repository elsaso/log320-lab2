package compress;

import module.BitInputStream;
import module.BitOutputStream;
import module.HuffmanTree;


import java.io.*;
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

        try {
            FileOutputStream fileOut = new FileOutputStream(outputFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tree);

            //BitOutputStream bit = new BitOutputStream(outputFilePath);
            String binNumber="";
            for (byte b : this.octetDuFichier) {
                if(b<0)
                {
                    binNumber =  this.codes.get(b+256);
                    System.out.print(binNumber);
                    for (int i = 0; i < binNumber.length(); i++) {
                        char c = binNumber.charAt(i);
                        int value = Character.getNumericValue(c);
                        //bit.writeBit(value);
                    }
                }
                else
                {
                    binNumber =  this.codes.get((int)b);
                    System.out.print(binNumber);
                    for (int i = 0; i < binNumber.length(); i++) {
                        char c = binNumber.charAt(i);
                        int value = Character.getNumericValue(c);
                        //bit.writeBit(value);
                    }
                }
            }
            //bit.close();

            out.close();
            fileOut.close();
            System.out.println("");
            System.out.println("L'objet a été sérialisé en binaire dans myclass.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
