// Design an experiment to estimate the amount of time to i) Generate key pair (RSA) ii) 
// Encrypt n bit message (RSA) iii) Decrypt n bit message (RSA) As function of key size, experiment with different
// n-bit messages



import java.util.*;

public class ASSIGN2 {

    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }


    //calculate the private key d
    static int modInverse(int e, int phi) {
        for (int d = 1; d < phi; d++) {
            if ((e * d) % phi == 1)
                return d;
        }
        return -1;
    }

    
    //calculate the encryption/decryption
    static int powerMod(int base, int exp, int mod) {
        int result = 1;
        for (int i = 0; i < exp; i++) {
            result = (result * base) % mod;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== RSA Timing Experiment =====");

        // --- Key Generation ---
        System.out.print("Enter first prime number (p): ");
        int p = sc.nextInt();
        System.out.print("Enter second prime number (q): ");
        int q = sc.nextInt();

        long keyStart = System.nanoTime();
        int n = p * q;
        int phi = (p - 1) * (q - 1);

        // System.out.print("Enter value of e (coprime with " + phi + "): ");
        // int e = sc.nextInt();
        int e;
        do {
            System.out.print("Enter value of e (coprime with " + phi + "): ");
            e = sc.nextInt();
            if (gcd(e, phi) != 1) {
                System.out.println("Invalid! e must be coprime with φ(n). Try again.");
            }
        } while (gcd(e, phi) != 1);

        int d = modInverse(e, phi);
        long keyEnd = System.nanoTime();

        System.out.println("\nPublic Key (e, n): (" + e + ", " + n + ")");
        System.out.println("Private Key (d, n): (" + d + ", " + n + ")");
        System.out.println("Time to Generate Key Pair: " + (keyEnd - keyStart) / 1e6 + " ms");

        // --- Single Message Encryption/Decryption ---
        System.out.print("\nEnter message (number only): ");
        int msg = sc.nextInt();

        // Encryption
        long encStart = System.nanoTime();
        int cipher = powerMod(msg, e, n);
        long encEnd = System.nanoTime();

        // Decryption
        long decStart = System.nanoTime();
        int decrypted = powerMod(cipher, d, n);
        long decEnd = System.nanoTime();

        // Results
        System.out.println("\nOriginal Message: " + msg);
        System.out.println("Encrypted Message: " + cipher);
        System.out.println("Decrypted Message: " + decrypted);
        System.out.println("Encryption Time: " + (encEnd - encStart) / 1e6 + " ms");
        System.out.println("Decryption Time: " + (decEnd - decStart) / 1e6 + " ms");

        sc.close();
    }
}