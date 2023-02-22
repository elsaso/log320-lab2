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
        TableFrequence tableFrequence = new TableFrequence("test.txt");
        int tab[] = tableFrequence.getTableFrequence();
        HuffmanTree tree = new HuffmanTree(tab);
        HuffmanTree.Node root = tree.getRoot();
//         SI TU VEUX VOIR L'ARBRE RESSEMBLE À QUOI
//        afficherArbre(root, "", true);

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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // Sérialiser l'objet en binaire
        try {
            FileOutputStream fileOut = new FileOutputStream("myclass.bin");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tree);
            out.close();
            fileOut.close();
            System.out.println("");
            System.out.println("L'objet a été sérialisé en binaire dans myclass.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Compress compress = new Compress(tree, huffmanCodes, tableFrequence.getBytes());
        compress.compressfile("myclass.bin");






        HuffmanTree obj2 = null;
        try {
            // Lire le fichier binaire dans un tableau de bytes
            File file = new File("myclass.bin");
            FileInputStream fileIn = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fileIn.read(buffer);
            fileIn.close();

            // Désérialiser l'objet à partir du tableau de bytes
            ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
            ObjectInputStream in = new ObjectInputStream(bis);
            obj2 = (HuffmanTree) in.readObject();
            in.close();
            bis.close();
            System.out.println("L'objet a été désérialisé depuis myclass.bin");

            // Lire les bits qui suivent l'objet désérialisé
            BitInputStream bis2 = new BitInputStream(bis.toString());
            int bit;
            while ((bit = bis2.readBit()) != -1) {
                System.out.print(bit);
            }
            bis2.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        // Désérialiser l'objet depuis le fichier binaire et stocker dans une variable
//        HuffmanTree obj2 = null;
//        try {
//            FileInputStream fileIn = new FileInputStream("myclass.bin");
//            DataInputStream in = new DataInputStream(fileIn);
//            obj2 = (HuffmanTree) new ObjectInputStream(in).readObject();
//            in.close();
//            fileIn.close();
//            System.out.println("L'objet a été désérialisé depuis myclass.bin : " + obj2);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        Map<Integer, String> huffmanObj2 = obj2.getHuffmanCodes(root);
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
