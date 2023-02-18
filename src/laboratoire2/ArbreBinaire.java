package laboratoire2;

import java.util.ArrayList;
import java.util.List;

public class ArbreBinaire {
    private INoeud racine;
    private List<List<Integer>> binaire;

    public ArbreBinaire(INoeud racine, int limite) {
        this.racine = racine;
        binaire = new ArrayList<>();
        for (int i = 0; i < limite; i++){
            binaire.add(null);
        }
        recursiveBuilder(racine, new ArrayList<>());
    }

    public void recursiveBuilder(INoeud racine, List<Integer> branches){
        if (racine instanceof Noeud) {
            branches.add(0);
            recursiveBuilder(((Noeud) racine).getEnfantGauche(), branches);
            branches.remove(branches.size() - 1);

            branches.add(1);
            recursiveBuilder(((Noeud) racine).getEnfantDroit(), branches);
            branches.remove(branches.size() - 1);

        } else if (racine instanceof Feuille) {
            binaire.set(((Feuille) racine).identifiant, new ArrayList<Integer>(branches));
        }

    }

    public List<Integer> getByte(int lettre){
        return binaire.get(lettre);
    }
}
