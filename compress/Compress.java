package compress;

import module.BitInputStream;
import module.BitOutputStream;
import module.HuffmanTree;


import java.io.*;
import java.util.ArrayList;
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
            out.close();
            fileOut.close();

            BitInputStream in = new BitInputStream(outputFilePath);
            ArrayList<Integer> treebits = new ArrayList<Integer>();
            while (true) {
                int bit = in.readBit();

                if (bit == -1) {
                    break;
                } else {
                    treebits.add(bit);
                }
            }
            in.close();
            BitOutputStream bit = new BitOutputStream(outputFilePath);
            for (int i = 0; i < treebits.size(); i++) {
                bit.writeBit(treebits.get(i));
            }
            String binNumber="";
            for (byte b : this.octetDuFichier) {
                if(b<0)
                {
                    binNumber =  this.codes.get(b+256);
                    System.out.print(binNumber);
                    for (int i = 0; i < binNumber.length(); i++) {
                        char c = binNumber.charAt(i);
                        int value = Character.getNumericValue(c);
                        bit.writeBit(value);
                    }
                }
                else
                {
                    binNumber =  this.codes.get((int)b);
                    System.out.print(binNumber);
                    for (int i = 0; i < binNumber.length(); i++) {
                        char c = binNumber.charAt(i);
                        int value = Character.getNumericValue(c);
                        bit.writeBit(value);
                    }
                }
            }
            bit.close();
            System.out.println("");
            System.out.println("L'objet a été sérialisé en binaire dans myclass.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public HuffmanTree decompresser(String s) {

        // Désérialiser l'objet depuis le fichier binaire et stocker dans une variable
        HuffmanTree hoffman_deserialize = null;
        try {
            // Ouverture du fichier pour lire les données
            FileInputStream fileIn = new FileInputStream("myclass.bin");
            // Création d'un objet ObjectInputStream
            ObjectInputStream inputStream = new ObjectInputStream(fileIn);
            // Désérialisation de l'objet HuffmanTree et de la chaîne de caractères
            hoffman_deserialize = (HuffmanTree) inputStream.readObject();

            // Fermeture des flux
            inputStream.close();
            fileIn.close();
        } catch(IOException i) {
            i.printStackTrace();
        } catch(ClassNotFoundException c) {
            System.out.println("Classe non trouvée");
            c.printStackTrace();
        }
        HuffmanTree.Node root2 = hoffman_deserialize.getRoot();
        Map<Integer, String> huffmanObj2 = hoffman_deserialize.getHuffmanCodes(root2);
        System.out.println("les chemin d'accés de l'arbre Déserialisé");
        for (Map.Entry<Integer, String> entry : huffmanObj2.entrySet()) {
            System.out.println("Octet: " + entry.getKey() + ", Code de Huffman: " + entry.getValue());
        }

        return hoffman_deserialize ;
    }
}
