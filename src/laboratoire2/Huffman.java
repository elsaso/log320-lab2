package laboratoire2;

import java.io.*;
import java.util.List;

public class Huffman {

    public ArbreBinaire arbre;

    // Ne pas changer ces fonctions, elles seront utilisées pour tester votre programme
    public void Compresser(String nomFichierEntre, String nomFichierSortie) {
        BitInputStream in = new BitInputStream(nomFichierEntre);
        BitOutputStream out = new BitOutputStream(nomFichierSortie);
        compresserHelper(in, out);
    }

    public void Decompresser(String nomFichierEntre, String nomFichierSortie) {
        BitInputStream in = new BitInputStream(nomFichierEntre);
        BitOutputStream out = new BitOutputStream(nomFichierSortie);
        try {
            deCompresserWrapper(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void compresserHelper(BitInputStream in, BitOutputStream out) {
        TableFrequence freqs = null;
        try {
            freqs = getFrequences(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        freqs.incremente(256);
        ArbreBinaire arb = freqs.generateTable();
        SchemaArbre arbre1 = new SchemaArbre(arb, freqs.getFrequences().length);
        arb = arbre1.getArbre();
        for (int i = 0; i < arbre1.longeur.length; i++) {
            int val = arbre1.getCodeLength(i);
            // For this file format, we only support codes up to 255 bits long
            if (val >= 256)
                throw new RuntimeException("The code for a symbol is too long");

            // Write value as 8 bits in big endian
            for (int j = 7; j >= 0; j--)
                out.writeBit((val >>> j) & 1);
        }
        writeCompressed(arb, in, out);
    }

    private void writeCompressed(ArbreBinaire arbre, BitInputStream in, BitOutputStream out) {
        while (true) {
            int b = in.readBit();
            if (b == -1)
                break;
            List<Integer> bits = arbre.getByte(b);
            for (int bbb : bits)
                out.writeBit(bbb);
        }
        List<Integer> bits = arbre.getByte(256);
        for (int bbb : bits)
            out.writeBit(bbb);
    }

    public void deCompresserWrapper(BitInputStream in, BitOutputStream out) throws IOException {
        SchemaArbre canonCode = readCodeLengthTable(in);
        ArbreBinaire code = canonCode.getArbre();
        decompressWrite(code, in, out);
    }

    private void decompressWrite(ArbreBinaire code, BitInputStream in, BitOutputStream out) throws IOException {
        while (true) {
            int lettre = read(in, code);
            if (lettre == 256)  // EOF symbol
                break;
            out.writeBit(lettre);
        }
    }

    public int read(BitInputStream in, ArbreBinaire arbre) throws IOException {
        INoeud currentNode = arbre.getRacine();
        while (true) {
            int temp = in.readBit();
            if (temp == -1) {  // added check for end-of-stream
                return 256;
            }
            INoeud nextNode = null;
            if (temp == 0) {
                nextNode = ((Noeud) currentNode).getEnfantGauche();
            } else if (temp == 1) {
                nextNode = ((Noeud) currentNode).getEnfantDroit();
            }

            if (nextNode instanceof Feuille)
                return ((Feuille) nextNode).identifiant;
            else if (nextNode instanceof Noeud)
                currentNode = nextNode;
        }
    }

    private TableFrequence getFrequences(BitInputStream in) throws IOException {
        TableFrequence freqs = new TableFrequence(new int[257]);
            while (true) {
                int b = in.readBit();
                if (b == -1)
                    break;
                freqs.incremente(b);
        }
        return freqs;
    }

    static SchemaArbre readCodeLengthTable(BitInputStream in) throws IOException {
        int[] codeLengths = new int[257];
        for (int i = 0; i < codeLengths.length; i++) {
            // For this file format, we read 8 bits in big endian
            int val = 0;
            for (int j = 0; j < 8; j++)
                val = (val << 1) | in.readBit();
            codeLengths[i] = val;
        }
        return new SchemaArbre(codeLengths);
    }

}