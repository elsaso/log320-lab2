package laboratoire2;

import java.io.*;
import java.util.Map;

public class Huffman {

    // Ne pas changer ces fonctions, elles seront utilisées pour tester votre programme
    public void Compresser(String nomFichierEntre, String nomFichierSortie){
        // text Allo dans le png

        TableFrequence tableFrequence = new TableFrequence(nomFichierEntre);
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
        System.out.println("");


        // Compresser
        Compress compress = new Compress(tree, huffmanCodes, tableFrequence.getBytes());
        try {
            compress.compressfile(nomFichierSortie);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void decompress(String compressedFile, String decompressedFile) {
        Compress.decompresser(compressedFile, decompressedFile);

    }

}
