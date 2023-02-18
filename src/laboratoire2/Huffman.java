package laboratoire2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Huffman{

    public ArbreBinaire arbre;

    // Ne pas changer ces fonctions, elles seront utilisées pour tester votre programme
    public void Compresser(String nomFichierEntre, String nomFichierSortie){
        BitInputStream in = new BitInputStream(nomFichierEntre);
        BitOutputStream out = new BitOutputStream(nomFichierSortie);
        creerTableFrequence(in, out);
    }

    public void Decompresser(String nomFichierEntre, String nomFichierSortie){

    }

    public TableFrequence creerTableFrequence(BitInputStream in , BitOutputStream out){
        int[] initFreqs = new int[257];
        Arrays.fill(initFreqs, 1);
        TableFrequence tableFreq = new TableFrequence(initFreqs);
        ArbreBinaire arbre = tableFreq.generateTable();
        int compteur = 0;
        while (true) {
            int lettre = in.readBit();
            if (lettre == -1){
                break;
            }
            if (arbre == null)
                throw new NullPointerException("l'arbre est null");
            List<Integer> bits = arbre.getByte(lettre);
            for (int b : bits)
                out.writeBit(b);
            compteur++;
            tableFreq.incremente(lettre);
            if (compteur < 262144 && (compteur > 0 && Integer.bitCount(compteur) == 1) || compteur % 262144 == 0)
                arbre = tableFreq.generateTable();
            if (compteur % 262144 == 0)  // Reset frequency table
                tableFreq = new TableFrequence(initFreqs);
        }
        List<Integer> bits = arbre.getByte(256);
        for (int b : bits)
            out.writeBit(b);
        return null;
    }

}