package laboratoire2;

import compress.Compress;
import compress.TableFrequence;
import module.HuffmanTree;

import java.io.*;
import java.util.Map;

public class Huffman {

    // Ne pas changer ces fonctions, elles seront utilisées pour tester votre programme
    public void Compresser(String nomFichierEntre, String nomFichierSortie){
        TableFrequence tableFrequence = new TableFrequence(nomFichierEntre);
        int tab[] = tableFrequence.getTableFrequence();
        HuffmanTree tree = new HuffmanTree(tab);
        HuffmanTree.Node root = tree.getRoot();

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

        // Sérialiser l'objet en binaire
        try {
            FileOutputStream fileOut = new FileOutputStream(nomFichierSortie);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tree);
            out.close();
            fileOut.close();
            System.out.println("");
            System.out.println("L'objet a été sérialisé en binaire dans " + nomFichierSortie);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Compress compress = new Compress(tree, huffmanCodes, tableFrequence.getBytes());
        try {
            compress.compressfile(nomFichierSortie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Decompresser(String nomFichierEntre, String nomFichierSortie){
        // Désérialiser l'objet depuis le fichier binaire et stocker dans une variable
        HuffmanTree obj2 = null;
        try {
            FileInputStream fileIn = new FileInputStream("myclass.bin");
            DataInputStream in = new DataInputStream(fileIn);
            obj2 = (HuffmanTree) new ObjectInputStream(in).readObject();
            in.close();
            fileIn.close();
            System.out.println("L'objet a été désérialisé depuis myclass.bin : " + obj2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Map<Integer, String> huffmanObj2 = obj2.getHuffmanCodes(root);
        System.out.println("les chemin d'accés de l'arbre Déserialisé");
        for (Map.Entry<Integer, String> entry : huffmanObj2.entrySet()) {
            System.out.println("Octet: " + entry.getKey() + ", Code de Huffman: " + entry.getValue());
        }

    }

}
