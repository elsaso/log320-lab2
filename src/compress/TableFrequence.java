package compress;

import module.BitInputStream;

import java.util.ArrayList;

public class TableFrequence {

    // Initialiser le tableau des fréquences d'octets
    int[] byteFrequencies = new int[256];

    // lire le fichier en binaire et le convertir en table d'octet
    byte[] bytes;
    // Lire le fichier octet par octet et compter la fréquence de chaque symbole en octet


    public TableFrequence(String file) {
        BitInputStream in = new BitInputStream(file);
        ArrayList<Integer> bits = new ArrayList<>();
        while (true) {
            int bit = in.readBit();
            if (bit == -1)
                break;
            bits.add(bit);
        }
        int numBits = bits.size();
        int numBytes = (int) Math.ceil(numBits / 8.0);
        this.bytes = new byte[numBytes];

        for (int i = 0; i < numBytes; i++) {
            int byteValue = 0;
            for (int j = 0; j < 8; j++) {
                int bitIndex = i * 8 + j;
                if (bitIndex < numBits) {
                    int bitValue = bits.get(bitIndex);
                    byteValue |= bitValue << (7 - j);
                }
            }
            this.bytes[i] = (byte) byteValue;
        }
        System.out.println("octet : " + this.bytes.length);
        this.makeFrequenceTable();
    }

    public void makeFrequenceTable() {
        for (byte b : bytes) {
            this.byteFrequencies[b & 0xFF]++;
        }
        // Trier le tableau des fréquences d'octets par ordre croissant
        int[] sortedFrequencies = byteFrequencies.clone();
        quickSort(sortedFrequencies, 0, sortedFrequencies.length - 1);;

        // Afficher la fréquence de chaque symbole en octet
        System.out.println("LA TABLE DE FREQUENCE" );
        for (int i = 0; i < 256; i++) {
            if (byteFrequencies[i] > 0) {
                System.out.println("Octet: " + i + " Fréquence: " + byteFrequencies[i]);
            }
        }
    }

    public int[] quickSort( int[] arr, int left, int right){
        if (left < right) {
            int pivot = arr[right];
            int i = left - 1;
            for (int j = left; j < right; j++) {
                if (arr[j] < pivot) {
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            int temp = arr[i + 1];
            arr[i + 1] = arr[right];
            arr[right] = temp;
            pivot = i + 1;
            quickSort(arr, left, pivot - 1);
            quickSort(arr, pivot + 1, right);
        } else {
            return arr;
        }
        return arr;
    }

    public int[] getTableFrequence() {
        return this.byteFrequencies;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
