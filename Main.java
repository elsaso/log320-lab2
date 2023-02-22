import compress.Compress;
import compress.TableFrequence;
import module.BitInputStream;
import module.BitOutputStream;
import module.HuffmanTree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        // text Allo dans le png
        String nomFichierEntre = "test.txt";
        TableFrequence tableFrequence = new TableFrequence(nomFichierEntre);
        int tab[] = tableFrequence.getTableFrequence();
        HuffmanTree tree = new HuffmanTree(tab);
        HuffmanTree.Node root = tree.getRoot();
//         SI TU VEUX VOIR L'ARBRE RESSEMBLE À QUOI
        afficherArbre(root, "", true);

        Map<Integer, String> huffmanCodes = tree.getHuffmanCodes(root);
        System.out.println("les chemin d'accés de l'arbre");
        for (Map.Entry<Integer, String> entry : huffmanCodes.entrySet()) {
            System.out.println("Octet: " + entry.getKey() + ", Code de Huffman: " + entry.getValue());
        }
        System.out.println("le fichier en Octet");
        for (byte b : tableFrequence.getBytes()) {
            if (b < 0) {
                System.out.print(b + 256);
            } else {
                System.out.print(b);
            }
            System.out.print("-");
        }
        System.out.println("");

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // Sérialiser l'objet en binaire
        Compress compress = new Compress(tree, huffmanCodes, tableFrequence.getBytes());
        compress.compressfile("myclass.bin");

        System.out.println("L'objet a été sérialisé en binaire:");
        BitInputStream in = new BitInputStream("myclass.bin");
        while (true) {
            int bit = in.readBit();
            if (bit == -1)
                break;
            System.out.print(bit);
        }
        in.close();

        // Désérialiser l'objet depuis le fichier binaire et stocker dans une variable

        HuffmanTree obj2 = null;
        try {
            FileInputStream fileIn = new FileInputStream("myclass.bin");
            DataInputStream dataInputStream = new DataInputStream(fileIn);
            obj2 = (HuffmanTree) new ObjectInputStream(dataInputStream).readObject();
            dataInputStream.close();
            fileIn.close();
            System.out.println("L'objet a été désérialisé depuis myclass.bin : " + obj2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        HuffmanTree.Node root2 = obj2.getRoot();
        Map<Integer, String> huffmanObj2 = obj2.getHuffmanCodes(root2);
        System.out.println("les chemin d'accés de l'arbre Déserialisé");
        for (Map.Entry<Integer, String> entry : huffmanObj2.entrySet()) {
            System.out.println("Octet: " + entry.getKey() + ", Code de Huffman: " + entry.getValue());
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//        System.out.println("LE MAIN -----------");
//        String fileName6 = "test.txt";
//        int numOfReadChar_binMode = 0;
//        File file = new File(fileName6);
//        try (FileInputStream fileInputStream6 = new FileInputStream(file)) {
//            int singleCharInt;
//            char singleChar;
//            while ((singleCharInt = fileInputStream6.read()) != -1) {
//                singleChar = (char) singleCharInt;
//                System.out.println(String.format("0x%X %c", singleCharInt, singleChar));
//                numOfReadChar_binMode++;
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("LE MAIN -----------Nombre d'octets lus en mode binaire : " + numOfReadChar_binMode);
    }

    private static void afficherArbre(HuffmanTree.Node root, String s, boolean b) {
        if (root == null) {
            return;
        }

        System.out.print(s);
        if (b) {
            System.out.print("└─");
            s += "  ";
        } else {
            System.out.print("├─");
            s += "│ ";
        }
        System.out.println(root.getFreq()+"("+root.getOctet()+")");
        afficherArbre(root.getLeft(), s, root.getRight() == null);
        afficherArbre(root.getRight(), s, true);
    }


}
