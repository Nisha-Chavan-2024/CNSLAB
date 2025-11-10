import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Random;
import java.util.Scanner;

public class RSAWithSignature {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== RSA Digital Signature (Simplified) ===");

        // ---------- Key Generation ----------
        BigInteger p = BigInteger.probablePrime(256, new Random());
        BigInteger q = BigInteger.probablePrime(256, new Random());
        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = BigInteger.valueOf(65537); // public exponent
        if (!phi.gcd(e).equals(BigInteger.ONE))
            e = BigInteger.valueOf(3);

        BigInteger d = e.modInverse(phi); // private exponent

        System.out.println("\nPublic Key (e, n): " + e + ", " + n);
        System.out.println("Private Key (d, n): " + d + ", " + n);



        // ---------- Input ----------
        System.out.print("\nEnter message: ");
        String message = sc.nextLine();

        // ---------- Sender ----------
        System.out.println("\n--- Sender Side ---");
        byte[] hashBytes = MessageDigest.getInstance("SHA-256").digest(message.getBytes());
        String hashHex = HexFormat.of().formatHex(hashBytes);
        System.out.println("SHA-256 Hash: " + hashHex);

        BigInteger hashInt = new BigInteger(1, hashBytes);
        BigInteger signature = hashInt.modPow(d, n); // sign (encrypt hash with private key)
        System.out.println("Digital Signature: " + signature);

        System.out.println("\nMessage and Signature sent to Receiver...");





        // ---------- Receiver ----------
        System.out.println("\n--- Receiver Side ---");

        // Receiver computes hash of received message
        byte[] verifyHashBytes = MessageDigest.getInstance("SHA-256").digest(message.getBytes());
         String verifyHashHex = HexFormat.of().formatHex(verifyHashBytes);
        System.out.println("Receiver’s Calculated Hash: " + verifyHashHex);

        // Receiver decrypts signature using public key
        BigInteger verifyHashInt = new BigInteger(1, verifyHashBytes);
        BigInteger decryptedHashInt = signature.modPow(e, n);
        
        String decryptedHashHex = decryptedHashInt.toString(16);

        // Pad with leading zeros if needed (for equal length)
        while (decryptedHashHex.length() < verifyHashHex.length()) {
            decryptedHashHex = "0" + decryptedHashHex;
        }

        
        System.out.println("Decrypted Hash from Signature: " + decryptedHashHex);




        // Verification
        if (verifyHashInt.equals(decryptedHashInt))
            System.out.println("\n✅ Signature Verified — Message is Authentic.");
        else
            System.out.println("\n❌ Signature Invalid — Message may be Tampered.");

        sc.close();
    }
}
