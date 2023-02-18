package laboratoire2;

import java.util.ArrayList;
import java.util.List;

public class TableFrequence {
    private int[] frequences;
    private INoeud racine;

    public TableFrequence(int[] frequences) {
        this.frequences = frequences;
    }

    public ArbreBinaire generateTable() {
        List<NoeudWrapper> listNodes = new ArrayList<>();
        for (int i = 0; i < frequences.length; i++) {
            if (frequences[i] > 0) {
                NoeudWrapper n = new NoeudWrapper(new Feuille(i), i, frequences[i]);
                listNodes.add(n);
            }
        }

        for (int i = 0; i < frequences.length; i++) {
            if (listNodes.size() < 2) {
                if (frequences[i] == 0) {
                    NoeudWrapper n = new NoeudWrapper(new Feuille(i), i, 0);
                    listNodes.add(n);
                }
            }
        }

        while (listNodes.size() > 1){
            NoeudWrapper n1 = listNodes.remove(0);
            NoeudWrapper n2 = listNodes.remove(0);
            Noeud nouvoNo = new Noeud(n1.getNoeud(), n2.getNoeud());
            int minimumId = Math.min(n1.getPlusPetitIdentifiant(), n2.getPlusPetitIdentifiant());
            NoeudWrapper wrapper = new NoeudWrapper(nouvoNo, minimumId, n1.getFrenquence() + n2.getFrenquence());
            listNodes.add(wrapper);
        }
        return new ArbreBinaire((INoeud) listNodes.remove(0).getNoeud());
    }

    public void incremente(int lettre) {
        frequences[lettre]++;
    }
}