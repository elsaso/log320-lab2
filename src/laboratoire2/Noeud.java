package laboratoire2;

public class Noeud implements INoeud {
    private int frequences;
    Noeud enfantGauche;
    Noeud enfantDroit;

    public Noeud(Noeud enfantDroit, Noeud enfantGauche, int freq){
        this.enfantGauche = enfantGauche;
        this.enfantDroit = enfantDroit;
        this.frequences = freq;
    }
}
