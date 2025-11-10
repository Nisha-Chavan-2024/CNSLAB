// Aim: Demonstrate how Diffie-Hellman key exchange works with Man-In-The-Middle attack.

// Theory:
// 1.	Importance of Diffie-Hellman key exchange algorithm
// 2.	Working of  Diffie-Hellman key exchange algorithm
// 3.	Example of algorithm.
// 4.	What is Man-In-The-Middle attack?



import java.util.*;

class assign4 {
    public static long modExp(long base, long exp, long mod) {
        long result = 1;
        for(int i=0;i<exp;i++)
        {
            result=(result*base)%mod;
        }
        return result;
    }

    // small helper to print a line with label and value
    private static void print(String label, long value) {
        System.out.println(label + value);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read p and g from user
        System.out.print("Enter prime modulus p: ");
        long p = sc.nextLong();
        System.out.print("Enter generator g: ");
        long g = sc.nextLong();

        // Read private keys for A, B, C
        System.out.print("Enter A's private key (a): ");
        long a = sc.nextLong();
        System.out.print("Enter B's private key (b): ");
        long b = sc.nextLong();
        System.out.print("Enter C's private key (c) [MITM]: ");
        long c = sc.nextLong();

        // Compute public keys
        long A = modExp(g, a, p);   // A's public
        long B = modExp(g, b, p);   // B's public
        long C = modExp(g, c, p);   // C's public

        int choice;
        do {
            System.out.println();
            System.out.println("Choose an option:");
            System.out.println("1. Exchange Public Keys between A and B");
            System.out.println("2. Perform Man-In-The-Middle Attack by C");
            System.out.println("3. Exit");
            System.out.print("Enter 1, 2 or 3: ");

            // guard against non-int input
            while (!sc.hasNextInt()) {
                System.out.print("Please enter a valid number (1, 2 or 3): ");
                sc.next();
            }
            choice = sc.nextInt();

            if (choice == 1) {
                System.out.println("\n--- Normal Diffie-Hellman Exchange ---");
                print("A's Public Key: ", A);
                print("B's Public Key: ", B);

                long S_A = modExp(B, a, p);
                long S_B = modExp(A, b, p);

                print("A computes shared secret: ", S_A);
                print("B computes shared secret: ", S_B);

                if (S_A == S_B)
                    System.out.println("Secure communication established. Shared secret = " + S_A);
                else
                    System.out.println("Shared secrets do not match!");
            }
            else if (choice == 2) {
                System.out.println("\n--- Man-In-The-Middle Attack by C ---");
                print("A's Public Key (original): ", A);
                print("B's Public Key (original): ", B);

                print("C (MITM) sends his Public Key to A: ", C);
                print("C (MITM) sends his Public Key to B: ", C);

                long S_A = modExp(C, a, p); // A with C's public
                long S_B = modExp(C, b, p); // B with C's public

                print("A computes shared secret (with C): ", S_A);
                print("B computes shared secret (with C): ", S_B);

                long S_CwithA = modExp(A, c, p);  // C with A
                long S_CwithB = modExp(B, c, p);  // C with B

                print("C computes shared secret with A: ", S_CwithA);
                print("C computes shared secret with B: ", S_CwithB);

                if (S_A == S_CwithA && S_B == S_CwithB)
                    System.out.println("Man-In-The-Middle attack successful. C can intercept and modify messages.");
                else
                    System.out.println("MITM attack failed!");
            }
            else if (choice == 3) {
                System.out.println("Exiting program. Goodbye!");
            }
            else {
                System.out.println("Invalid option! Please enter 1, 2 or 3.");
            }

        } while (choice != 3);

        sc.close();
    }
}


// 1st case : key exchange without MITM attack
// 1st take the value of the g and n which known to both the users
// then take the private keys of both the users
// then calculate the public keys of both the users and exchange the public keys(A` = g^a mod n , B` = g^b mod n)
// then both the users calculate the shared secret key using each other's public keys (B`^a mod n , A`^b mod n)
// both the shared secret keys will be same

// 2nd case : key exchange with MITM attack
// 1st take the value of the g and n which known to both the users
// then take the private keys of both the users and the attacker
// then calculate the public keys of all the three (A` = g^a mod n , B` = g^b mod n , C` = g^c mod n)
// then the attacker sends his public key to both the users instead of each other's public keys
// then both the users calculate the shared secret key using attacker's public key (C`^a mod n , C`^b mod n)
// then the attacker calculates the shared secret keys with both the users using their public keys (A`^c mod n , B`^c mod n)
// now the attacker can intercept and modify the messages between both the users