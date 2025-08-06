import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Scanner;

public class DSSDemo {
    public static void main(String[] args) {
        try {
            SecureRandom random = new SecureRandom();

            // === Step 1: Generate q (160-bit prime) ===
            BigInteger q;
            do {
                q = new BigInteger(160, 64, random);
            } while (!q.isProbablePrime(64));

            // === Step 2: Generate p such that p = q * k + 1 and p is prime (1024-bit) ===
            BigInteger p, k;
            do {
                k = new BigInteger(864, random); // 1024 - 160 = 864 bits for k
                p = q.multiply(k).add(BigInteger.ONE);
            } while (!p.isProbablePrime(64));

            // === Step 3: Generate g = h^((p-1)/q) mod p ===
            BigInteger g, h;
            BigInteger exp = p.subtract(BigInteger.ONE).divide(q);
            do {
                h = new BigInteger(p.bitLength() - 1, random);
                g = h.modPow(exp, p);
            } while (g.compareTo(BigInteger.ONE) <= 0);

            // === Step 4: Generate private key x (0 < x < q) ===
            BigInteger x;
            do {
                x = new BigInteger(q.bitLength(), random);
            } while (x.compareTo(BigInteger.ZERO) <= 0 || x.compareTo(q) >= 0);

            // === Step 5: Compute public key y = g^x mod p ===
            BigInteger y = g.modPow(x, p);

            // === Step 6: Get message from user and compute SHA-1 hash ===
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the message to be signed:");
            String message = sc.nextLine();

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(message.getBytes());
            BigInteger H = new BigInteger(1, hash); // positive integer

            // === Step 7: Generate signature (r, s) ===
            BigInteger r, s;
            do {
                
                do {
                    k = new BigInteger(q.bitLength(), random);
                } while (k.compareTo(BigInteger.ZERO) <= 0 || k.compareTo(q) >= 0);

                r = g.modPow(k, p).mod(q);
                BigInteger kInv = k.modInverse(q);
                s = kInv.multiply(H.add(x.multiply(r))).mod(q);

            } while (r.equals(BigInteger.ZERO) || s.equals(BigInteger.ZERO));

            // === Output Signature and Public Key ===
            System.out.println("\nSignature (r, s):");
            System.out.println("r = " + r);
            System.out.println("s = " + s);

            System.out.println("\nPublic key components:");
            System.out.println("p = " + p);
            System.out.println("q = " + q);
            System.out.println("g = " + g);
            System.out.println("y = " + y);

            // === Step 8: Signature Verification ===
            BigInteger w = s.modInverse(q);
            BigInteger u1 = H.multiply(w).mod(q);
            BigInteger u2 = r.multiply(w).mod(q);

            BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);

            System.out.println("\nVerification values:");
            System.out.println("w = " + w);
            System.out.println("u1 = " + u1);
            System.out.println("u2 = " + u2);
            System.out.println("v = " + v);
            System.out.println("r = " + r);

            // === Final Check ===
            if (v.equals(r)) {
                System.out.println("\n✅ Signature is VALID (v == r)");
            } else {
                System.out.println("\n❌ Signature is INVALID (v != r)");
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
