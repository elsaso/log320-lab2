package laboratoire2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Huffman{

    // Ne pas changer ces fonctions, elles seront utilisées pour tester votre programme
    public void Compresser(String nomFichierEntre, String nomFichierSortie){
        BitInputStream in = new BitInputStream(nomFichierEntre);
        BitOutputStream out = new BitOutputStream(nomFichierSortie);

    }

    public void Decompresser(String nomFichierEntre, String nomFichierSortie){

    }

    public TableFrequence creerTableFrequence(BitInputStream in , BitOutputStream out){
        TableFrequence tableFreq = new TableFrequence(new int[257]);
    }

}