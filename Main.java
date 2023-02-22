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
        String nomFichierEntre = "testahmed.txt";
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
        compress.compressfile("myclass.bin");
        compress.decompresser("myclass.bin");
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
