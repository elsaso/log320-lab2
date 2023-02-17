package laboratoire2;

import java.util.ArrayList;
import java.util.List;

public class TableFrequence {
    private int[] frequences;
    private INoeud racine;

    public TableFrequence(int[] frequences) {
        this.frequences = frequences;
    }

    public Arbre generateTable() {
        List<INoeud> listNodes = new ArrayList<>();
        for (int i = 0; i < frequences.length; i++) {
            if (frequences[i] > 0) {
                listNodes.add(new Feuille(i, frequences[i]));
            }
        }

        for (int i = 0; i < frequences.length; i++) {
            if (listNodes.size() < 2) {
                if (frequences[i] == 0) {
                    listNodes.add(new Noeud(new Feuille(i, frequences[i]), i, 0));
                }
            }
        }

        while (listNodes.size() > 1){
            INoeud n1 = listNodes.remove(0);
            INoeud n2 = listNodes.remove(0);
        }
        return null;
    }

}