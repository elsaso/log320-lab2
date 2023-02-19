package laboratoire2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SchemaArbre {
    public int[] longeur;

    SchemaArbre(ArbreBinaire arbre, int limit) {
        longeur = new int[limit];
        buildlongeur(arbre.getRacine(), 0);
    }
    
    SchemaArbre(int[] longeurLettre){
        // Copy once and check for tree validity
        longeur = longeurLettre.clone();
        Arrays.sort(longeur);
        int currentLevel = longeur[longeur.length - 1];
        int numNodesAtLevel = 0;
        for (int i = longeur.length - 1; i >= 0 && longeur[i] > 0; i--) {
            int cl = longeur[i];
            while (cl < currentLevel) {
                numNodesAtLevel /= 2;
                currentLevel--;
            }
            numNodesAtLevel++;
        }
        while (currentLevel > 0) {
          numNodesAtLevel /= 2;
            currentLevel--;
        }
        // Copy again
        System.arraycopy(longeurLettre, 0, longeur, 0, longeurLettre.length);
    }

    private void buildlongeur(INoeud racine, int hauteur) {
        if (racine instanceof Noeud) {
            buildlongeur(((Noeud) racine).enfantDroit, hauteur + 1);
            buildlongeur(((Noeud) racine).enfantGauche, hauteur + 1);
        } else if (racine instanceof Feuille) {
            int symbol = ((Feuille) racine).identifiant;
            longeur[symbol] = hauteur;
        }
    }


    ArbreBinaire getArbre() {
        List<INoeud> nodes = new ArrayList<INoeud>();
        for (int i = max(longeur); i >= 0; i--) {
            List<INoeud> newNodes = new ArrayList<INoeud>();

            if (i > 0) {
                for (int j = 0; j < longeur.length; j++) {
                    if (longeur[j] == i)
                        newNodes.add(new Feuille(j));
                }
            }

            for (int j = 0; j < nodes.size(); j += 2)
                newNodes.add(new Noeud(nodes.get(j), nodes.get(j + 1)));
            nodes = newNodes;
        }

        return new ArbreBinaire((Noeud) nodes.get(0), longeur.length);
    }

    public int getCodeLength(int symbol) {
        return longeur[symbol];
    }

    private static int max(int[] array) {
        int result = array[0];
        for (int x : array)
            result = Math.max(x, result);
        return result;
    }
}
