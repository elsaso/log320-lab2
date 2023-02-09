package laboratoire2;

import java.util.ArrayList;
import java.util.List;

public class TableFrequence {
    private int[] frequences;
    private INoeud racine;

    public TableFrequence(int[] frequences){
        this.frequences = frequences;
    }

    public Arbre generateTable(){
        List<INoeud> listNodes = new ArrayList<>();
        for (int i = 0; i < frequences.length; i++){
            new Feuille(i);
            listNodes.add()
        }
    }

}