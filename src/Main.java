import laboratoire2.Huffman;

public class Main {
    public static void main(String[] args) {
        Huffman huffman = new Huffman();
        // text Allo dans le png
        String nomFichierEntre = "test.txt";
        String nomFichierSortie = "test-out.txt";
        String uncompressed = "post.txt";
        huffman.Compresser(nomFichierEntre, nomFichierSortie);
        huffman.Decompresser(nomFichierSortie, uncompressed);

    }
}