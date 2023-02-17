package laboratoire2;

public class NoeudWrapper {
    public Noeud noeud;
    public int plusPetitIdentifiant;
    public long frenquence;  // Using wider type prevents overflow


    public NoeudWrapper(Noeud noeud, int id, long freq) {
        this.noeud = noeud;
        this.plusPetitIdentifiant = id;
        this.frenquence = freq;
    }
}
