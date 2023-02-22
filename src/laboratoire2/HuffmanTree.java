package laboratoire2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree implements Serializable {

    // Classe interne pour représenter un nœud de l'arbre de Huffman
    public static class Node implements Serializable{
        int freq;
        Node left;
        Node right;

        int octet;

        public int getFreq() {
            return freq;
        }
        public Node getLeft() {
            return left;
        }
        public Node getRight() {
            return right;
        }
        public int getOctet() {
            return octet;
        }
    }

    private Node root;

    public HuffmanTree(int[] freqs) {
        this.root = buildTree(freqs);
    }

    // Méthode pour construire l'arbre de Huffman à partir du tableau de fréquences
    private Node buildTree(int[] freqs) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.freq - b.freq);

        // Créer un nœud de base pour chaque symbole avec une fréquence non nulle
        for (int i = 0; i < freqs.length; i++) {
            if (freqs[i] > 0) {
                Node n = new Node();
                n.freq = freqs[i];
                n.octet = i;
                pq.add(n);
            }
        }

        // Combinaison des nœuds de base pour former l'arbre de Huffman
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node();
            parent.freq = left.freq + right.freq;
            parent.left = left;
            parent.right = right;
            pq.add(parent);
        }

        // Retourner la racine de l'arbre de Huffman
        return pq.poll();
    }

    public Node getRoot() {
        return root;
    }

    public static Map<Integer, String> getHuffmanCodes(Node root) {
        Map<Integer, String> huffmanCodes = new HashMap<>();
        buildCode(huffmanCodes, root, "");
        return huffmanCodes;
    }

    private static void buildCode(Map<Integer, String> huffmanCodes, Node node, String code) {
        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.octet, code);
            return;
        }
        buildCode(huffmanCodes, node.left, code + "0");
        buildCode(huffmanCodes, node.right, code + "1");
    }

}
