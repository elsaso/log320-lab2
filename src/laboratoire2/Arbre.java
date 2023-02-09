package laboratoire2;

import java.util.ArrayList;
import java.util.List;

public class Arbre {
    private List<List<Integer>> codes;

    public Noeud racine;

    public Arbre(Noeud racine){
        this.racine = racine;
        codes = new ArrayList<>();
        battireLaList(racine, new ArrayList<>());
    }
    
    public void battireLaList(INoeud noeud, List<Integer> list){
        if (noeud instanceof Noeud) {
            Noeud noeudOfficiel = (Noeud)noeud;
            list.add(0);
            battireLaList(noeudOfficiel.enfantGauche, list);
            list.remove(list.size() - 1);

            list.add(1);
            battireLaList(noeudOfficiel.enfantDroit, list);
            list.remove(list.size() - 1);

        } else if (noeud instanceof Feuille) {
            Feuille feuille = (Feuille)noeud;
            if (feuille.getCount() >= codes.size())
                throw new IllegalArgumentException("Symbol exceeds symbol limit");
            if (codes.get(feuille.getCount()) != null)
                throw new IllegalArgumentException("Symbol has more than one code");
            codes.set(feuille.getCount(), new ArrayList<Integer>(list));

        }
    }

}
