package laboratoire2;

public class Noeud implements INoeud {
    Noeud enfantGauche;
    Noeud enfantDroit;
    int frequences;  // Using wider type prevents overflow

    public Noeud(Noeud enfantDroit, Noeud enfantGauche){
        this.enfantGauche = enfantGauche;
        this.enfantDroit = enfantDroit;
        this.frequences = 0;
    }
}
