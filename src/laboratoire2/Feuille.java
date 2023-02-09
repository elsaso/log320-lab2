package laboratoire2;

public class Feuille implements INoeud {

    int count;
    public Feuille(int count){
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
