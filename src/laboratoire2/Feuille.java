package laboratoire2;

public class Feuille implements INoeud {

    public int frequences;
    int count;
    public Feuille(int count, int freq){
        this.count = count;
        this.frequences = freq;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
