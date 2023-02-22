package module;
import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class HuffmanTree implements Serializable {

    // Classe interne pour représenter un nœud de l'arbre de Huffman
    public static class Node implements Serializable, Comparator<Node>{
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

        @Override
        public int compare(Node node, Node t1) {
            return 0;
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

    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            nodes.add(node);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return nodes;
    }

    public Map<Integer, String> getCanonicalHuffmanCodes() {
        Map<Integer, String> codes = new HashMap<>();
        List<Node> nodeList = getNodes();

        // Sort the nodes by their weights and then by their values
        Collections.sort(nodeList, new Comparator<>() {
            @Override
            public int compare(Node o1, Node o2) {
                int cmp = Integer.compare(o1.freq, o2.freq);
                if (cmp != 0) {
                    return cmp;
                }
                return Integer.compare(o1.octet, o2.octet);
            }
        });

        int[] lengths = new int[nodeList.size()];
        int[] codesCount = new int[nodeList.size()];

        int i = 0;
        int code = 0;
        int length = 0;
        int prevWeight = -1;

        for (Node node : nodeList) {
            int weight = node.freq;

            if (weight != prevWeight) {
                code <<= (weight - length);
                length = weight;
            }

            lengths[i] = length;
            codesCount[length]++;
            node.octet = code;

            code++;
            i++;
            prevWeight = weight;
        }

        int[] firstCode = new int[nodeList.size()];
        code = 0;

        for (i = 1; i < codesCount.length; i++) {
            firstCode[i] = code;
            code += codesCount[i];
        }

        for (i = 0; i < nodeList.size(); i++) {
            Node node = nodeList.get(i);
            int lengthNew = lengths[i];
            int c = node.octet - firstCode[lengthNew];
            String codeString = Integer.toBinaryString(c);
            int padding = lengthNew - codeString.length();
            if (padding > 0) {
                codeString = String.format("%0" + padding + "d", 0) + codeString;
            }
            codes.put(node.octet, codeString);
        }

        return codes;
    }

    public int decode(BitInputStream input, Node node) throws IOException {
        if (node.getLeft() == null && node.right == null) {
            return node.octet;
        } else {
            int bit = input.readBit();
            if (bit < 0) {
                throw new EOFException("Unexpected end of input");
            }
            if (bit == 0) {
                return decode(input, node.getLeft());
            } else {
                return decode(input, node.getRight());
            }
        }
    }

}
