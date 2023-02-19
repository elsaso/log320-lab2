package laboratoire2;

public class Noeud implements INoeud, Comparable<Noeud> {
    INoeud enfantGauche;
    INoeud enfantDroit;
    boolean isLeaf = false;
    String character;
    int frequence;

    public Noeud(INoeud enfantDroit, INoeud enfantGauche, String character, int frequence){
        this.enfantGauche = enfantGauche;
        this.enfantDroit = enfantDroit;
        this.frequence = frequence;
        this.character = character;
    }

    public Noeud(String character, int frequence){
        this.enfantGauche = null;
        this.enfantDroit = null;
        this.frequence = frequence;
        this.character = character;
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

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getFrequence() {
        return frequence;
    }

    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }


    @Override
    public int compareTo(Noeud noeud) {
        return Integer.compare(this.frequence, noeud.getFrequence());
    }
}
