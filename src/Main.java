import laboratoire2.Huffman;

public class Main {
    public static void main(String[] args) {
        Huffman huffman = new Huffman();
        // text Allo dans le png
        String nomFichierEntre = "test.png";
        String nomFichierSortie = "test-out.png";

        huffman.Compresser(nomFichierEntre, nomFichierSortie);
        huffman.Decompresser(nomFichierSortie, nomFichierEntre);
    }
}