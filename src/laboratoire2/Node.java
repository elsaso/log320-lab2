package laboratoire2;

public class Node implements INode{
    Node enfantGauche;
    Node enfantDroit;

    public Node(Node enfantDroit, Node enfantGauche){
        enfantGauche = enfantGauche;
        enfantDroit = enfantDroit;
    }
}
