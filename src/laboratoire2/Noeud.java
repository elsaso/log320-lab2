package laboratoire2;

public class Noeud implements INoeud {
    INoeud enfantGauche;
    INoeud enfantDroit;

    public Noeud(INoeud enfantDroit, INoeud enfantGauche){
        this.enfantGauche = enfantGauche;
        this.enfantDroit = enfantDroit;
    }

    public INoeud getEnfantGauche() {
        return enfantGauche;
    }

    public void setEnfantGauche(INoeud enfantGauche) {
        this.enfantGauche = enfantGauche;
    }

    public INoeud getEnfantDroit() {
        return enfantDroit;
    }

    public void setEnfantDroit(INoeud enfantDroit) {
        this.enfantDroit = enfantDroit;
    }
}
