import java.util.*;

public class ASSIGN1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char continueChoice;

        do {
            System.out.println("\n===== Classical Encryption Techniques =====");
            System.out.println("1. Caesar Cipher");
            System.out.println("2. Monoalphabetic Cipher");
            System.out.println("3. Polyalphabetic (Vigenère) Cipher");
            System.out.println("4. Rail Fence Cipher");
            System.out.println("5. Vernam Cipher (One-Time Pad Style)");
            System.out.println("6. One-Time Pad Cipher");
            System.out.print("Enter your choice (1-6): ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                // -------------------- 1. Caesar Cipher --------------------
                case 1 -> {
                    System.out.println("===== Caesar Cipher =====");
                    System.out.print("Enter text: ");
                    String text1 = sc.nextLine();

                    System.out.print("Enter key (integer): ");
                    int key = sc.nextInt();
                    sc.nextLine();

                    // Encryption
                    StringBuilder encryptedCaesar = new StringBuilder();
                    for (char c : text1.toCharArray()) {
                        if (Character.isLetter(c)) {
                            char base = Character.isUpperCase(c) ? 'A' : 'a';
                            encryptedCaesar.append((char) ((c - base + key) % 26 + base));
                        } else {
                            encryptedCaesar.append(c);
                        }
                    }

                    // Decryption
                    StringBuilder decryptedCaesar = new StringBuilder();
                    for (char c : encryptedCaesar.toString().toCharArray()) {
                        if (Character.isLetter(c)) {
                            char base = Character.isUpperCase(c) ? 'A' : 'a';
                            decryptedCaesar.append((char) ((c - base - key + 26) % 26 + base));
                        } else {
                            decryptedCaesar.append(c);
                        }
                    }

                    System.out.println("Encrypted Text: " + encryptedCaesar);
                    System.out.println("Decrypted Text: " + decryptedCaesar);
                }

                // -------------------- 2. Monoalphabetic Cipher --------------------
                case 2 -> {
                    System.out.println("===== Monoalphabetic Cipher =====");

                    String keyMono = "QWERTYUIOPASDFGHJKLZXCVBNM";
                    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

                    Map<Character, Character> encryptMap = new HashMap<>();
                    Map<Character, Character> decryptMap = new HashMap<>();

                    for (int i = 0; i < 26; i++) {
                        encryptMap.put(alphabet.charAt(i), keyMono.charAt(i));
                        decryptMap.put(keyMono.charAt(i), alphabet.charAt(i));
                    }

                    System.out.print("Enter text (UPPERCASE only): ");
                    String text2 = sc.nextLine().toUpperCase();
                    StringBuilder encryptedMono = new StringBuilder();
                    StringBuilder decryptedMono = new StringBuilder();

                    for (char c : text2.toCharArray()) {
                        if (Character.isLetter(c)) encryptedMono.append(encryptMap.get(c));
                        else encryptedMono.append(c);
                    }

                    for (char c : encryptedMono.toString().toCharArray()) {
                        if (Character.isLetter(c)) decryptedMono.append(decryptMap.get(c));
                        else decryptedMono.append(c);
                    }

                    System.out.println("Encrypted Text: " + encryptedMono);
                    System.out.println("Decrypted Text: " + decryptedMono);
                }

                // -------------------- 3. Polyalphabetic (Vigenère) Cipher --------------------
                case 3 -> {
                    System.out.println("===== Polyalphabetic (Vigenère) Cipher =====");
                    System.out.print("Enter text (UPPERCASE): ");
                    String text3 = sc.nextLine().toUpperCase();
                    System.out.print("Enter key: ");
                    String keyStr = sc.nextLine().toUpperCase();

                    StringBuilder encryptedPoly = new StringBuilder();
                    StringBuilder decryptedPoly = new StringBuilder();
                    int keyIndex = 0;

                    // Encryption
                    for (char c : text3.toCharArray()) {
                        if (Character.isLetter(c)) {
                            int shift = keyStr.charAt(keyIndex) - 'A';
                            encryptedPoly.append((char) ((c - 'A' + shift) % 26 + 'A'));
                            keyIndex = (keyIndex + 1) % keyStr.length();
                        } else encryptedPoly.append(c);
                    }

                    // Decryption
                    keyIndex = 0;
                    for (char c : encryptedPoly.toString().toCharArray()) {
                        if (Character.isLetter(c)) {
                            int shift = keyStr.charAt(keyIndex) - 'A';
                            decryptedPoly.append((char) ((c - 'A' - shift + 26) % 26 + 'A'));
                            keyIndex = (keyIndex + 1) % keyStr.length();
                        } else decryptedPoly.append(c);
                    }

                    System.out.println("Encrypted Text: " + encryptedPoly);
                    System.out.println("Decrypted Text: " + decryptedPoly);
                }

                // -------------------- 4. Rail Fence Cipher --------------------
                case 4 -> {
                    System.out.println("===== Rail Fence Cipher =====");
                    System.out.print("Enter text (no spaces): ");
                    String text4 = sc.nextLine().replaceAll("\\s", "");
                    System.out.print("Enter number of rails: ");
                    int rails = sc.nextInt();
                    sc.nextLine();

                    // Encryption
                    StringBuilder[] fence = new StringBuilder[rails];
                    for (int i = 0; i < rails; i++) fence[i] = new StringBuilder();

                    int rail = 0, dir = 1;
                    for (char c : text4.toCharArray()) {
                        fence[rail].append(c);
                        rail += dir;
                        if (rail == 0 || rail == rails - 1) dir = -dir;
                    }

                    StringBuilder encryptedRail = new StringBuilder();
                    for (StringBuilder sb : fence) encryptedRail.append(sb);

                    // Decryption
                    char[] decrypted = new char[text4.length()];
                    int cycle = 2 * rails - 2;
                    int index = 0;

                    for (int r = 0; r < rails; r++) {
                        int pos = r;
                        boolean down = true;
                        while (pos < text4.length()) {
                            decrypted[pos] = encryptedRail.charAt(index++);
                            if (r == 0 || r == rails - 1) pos += cycle;
                            else {
                                if (down) pos += 2 * (rails - r - 1);
                                else pos += 2 * r;
                                down = !down;
                            }
                        }
                    }

                    System.out.println("Encrypted Text: " + encryptedRail);
                    System.out.println("Decrypted Text: " + new String(decrypted));
                }

                // -------------------- 5. Vernam Cipher (One-Time Pad Style) --------------------
                case 5 -> {
                    System.out.println("===== Vernam Cipher (One-Time Pad Style) =====");
                    System.out.print("Enter Plaintext (UPPERCASE only): ");
                    String text5 = sc.nextLine().toUpperCase();
                    System.out.print("Enter Key (same length as text, UPPERCASE only): ");
                    String key5 = sc.nextLine().toUpperCase();

                    if (text5.length() != key5.length()) {
                        System.out.println("Error: Key length must be same as plaintext!");
                        break;
                    }

                    // Encryption
                    StringBuilder encryptedVernam = new StringBuilder();
                    for (int i = 0; i < text5.length(); i++) {
                        int ptVal = text5.charAt(i) - 'A';
                        int keyVal = key5.charAt(i) - 'A';
                        int xorVal = ptVal ^ keyVal;
                        if (xorVal >= 26) xorVal -= 26;
                        char cipherChar = (char) ('A' + xorVal);
                        encryptedVernam.append(cipherChar);
                    }

                    // Decryption
                    StringBuilder decryptedVernam = new StringBuilder();
                    for (int i = 0; i < encryptedVernam.length(); i++) {
                        int ctVal = encryptedVernam.charAt(i) - 'A';
                        int keyVal = key5.charAt(i) - 'A';
                        int decVal = ctVal ^ keyVal;
                        if (decVal >= 26) decVal -= 26;
                        char plainChar = (char) ('A' + decVal);
                        decryptedVernam.append(plainChar);
                    }

                    System.out.println("Encrypted Text: " + encryptedVernam);
                    System.out.println("Decrypted Text: " + decryptedVernam);
                }

                // -------------------- 6. One-Time Pad Cipher --------------------
                case 6 -> {
                    System.out.println("===== One-Time Pad Cipher =====");
                    System.out.print("Enter Plaintext (UPPERCASE only): ");
                    String plaintext = sc.nextLine().toUpperCase();

                    // Generate random key (same length)
                    Random rand = new Random();
                    StringBuilder key = new StringBuilder();
                    for (int i = 0; i < plaintext.length(); i++) {
                        char randomChar = (char) ('A' + rand.nextInt(26));
                        key.append(randomChar);
                    }

                    // Encryption
                    StringBuilder cipher = new StringBuilder();
                    for (int i = 0; i < plaintext.length(); i++) {
                        int ptVal = plaintext.charAt(i) - 'A';
                        int keyVal = key.charAt(i) - 'A';
                        int encVal = (ptVal + keyVal) % 26;
                        cipher.append((char) ('A' + encVal));
                    }

                    // Decryption
                    StringBuilder decrypted = new StringBuilder();
                    for (int i = 0; i < cipher.length(); i++) {
                        int ctVal = cipher.charAt(i) - 'A';
                        int keyVal = key.charAt(i) - 'A';
                        int decVal = (ctVal - keyVal + 26) % 26;
                        decrypted.append((char) ('A' + decVal));
                    }

                    System.out.println("Random Key: " + key);
                    System.out.println("Encrypted Text: " + cipher);
                    System.out.println("Decrypted Text: " + decrypted);
                }

                default -> System.out.println("Invalid choice!");
            }

            System.out.print("\nDo you want to continue? (Y/N): ");
            continueChoice = sc.nextLine().toUpperCase().charAt(0);

        } while (continueChoice == 'Y');

        System.out.println("Thank you! Exiting...");
        sc.close();
    }
}