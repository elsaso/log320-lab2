package laboratoire2;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Huffman{

    public ArbreBinaire arbre;

    // Ne pas changer ces fonctions, elles seront utilisées pour tester votre programme
    public void Compresser(String nomFichierEntre, String nomFichierSortie){
        BitInputStream in = new BitInputStream(nomFichierEntre);
        BitOutputStream out = new BitOutputStream(nomFichierSortie);
        compresser(in, out);
    }

    public void Decompresser(String nomFichierEntre, String nomFichierSortie){
        BitInputStream in = new BitInputStream(nomFichierEntre);
        BitOutputStream out = new BitOutputStream(nomFichierSortie);
        try {
            deCompresser(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void compresser(BitInputStream in , BitOutputStream out){
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
    }

    public void deCompresser(BitInputStream in , BitOutputStream out) throws IOException {
        int[] initFreqs = new int[257];
        Arrays.fill(initFreqs, 1);
        TableFrequence freqs = new TableFrequence(initFreqs);
        ArbreBinaire arbre = freqs.generateTable();  // Use same algorithm as the compressor
        int compteur = 0;  // Number of bytes written to the output file
        while (true) {
            // Decode and write one byte
            int symbol = read(in, arbre);
            if (symbol == 256)  // EOF symbol
                break;
            out.writeBit(symbol);
            compteur++;

            // Update the frequency table and possibly the code tree
            freqs.incremente(symbol);
            if (compteur < 262144 &&  (compteur > 0 && Integer.bitCount(compteur) == 1) || compteur % 262144 == 0)  // Update code tree
                arbre = freqs.generateTable();
            if (compteur % 262144 == 0)  // Reset frequency table
                freqs = new TableFrequence(initFreqs);
        }
    }

    public int read(BitInputStream in, ArbreBinaire arbre) throws IOException {
        INoeud currentNode = arbre.getRacine();
        while (true) {
            int temp = in.readBit();
            INoeud nextNode = null;
            if (temp == 0) {
                nextNode = ((Noeud)currentNode).getEnfantGauche();
            } else if (temp == 1) {
                nextNode = ((Noeud)currentNode).getEnfantDroit();
            }

            if (nextNode instanceof Feuille)
                return ((Feuille) nextNode).identifiant;
            else if (nextNode instanceof Noeud)
                currentNode = nextNode;

        }
    }

    }