package laboratoire2;

public class NoeudWrapper implements Comparable<NoeudWrapper>{
    private INoeud noeud;
    private int plusPetitIdentifiant;
    private long frequence;  // Using wider type prevents overflow


    public NoeudWrapper(INoeud noeud, int id, long freq) {
        this.noeud = noeud;
        this.plusPetitIdentifiant = id;
        this.frequence = freq;
    }

    public INoeud getNoeud() {
        return noeud;
    }

    public void setNoeud(INoeud noeud) {
        this.noeud = noeud;
    }

    public int getPlusPetitIdentifiant() {
        return plusPetitIdentifiant;
    }

    public void setPlusPetitIdentifiant(int plusPetitIdentifiant) {
        this.plusPetitIdentifiant = plusPetitIdentifiant;
    }

    public long getFrenquence() {
        return frequence;
    }

    public void setFrenquence(long frenquence) {
        this.frequence = frenquence;
    }


    public int compareTo(NoeudWrapper noeud) {
        if (this.frequence < noeud.frequence)
            return -1;
        else if (this.frequence > noeud.frequence)
            return 1;
        else
            return 0;
    }

    
}
