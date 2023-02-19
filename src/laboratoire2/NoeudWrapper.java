package laboratoire2;

public class NoeudWrapper implements Comparable<NoeudWrapper>{
    private INoeud noeud;
    private int plusPetitIdentifiant;
    private long frenquence;  // Using wider type prevents overflow


    public NoeudWrapper(INoeud noeud, int id, long freq) {
        this.noeud = noeud;
        this.plusPetitIdentifiant = id;
        this.frenquence = freq;
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
        return frenquence;
    }

    public void setFrenquence(long frenquence) {
        this.frenquence = frenquence;
    }

    
}
