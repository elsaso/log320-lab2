package laboratoire2;

public class Noeud implements INoeud {
    Noeud enfantGauche;
    Noeud enfantDroit;

    public Noeud(Noeud enfantDroit, Noeud enfantGauche){
        enfantGauche = enfantGauche;
        enfantDroit = enfantDroit;
    }
}
