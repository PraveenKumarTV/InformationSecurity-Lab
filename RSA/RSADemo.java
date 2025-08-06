import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class RSADemo{
    private BigInteger p,q,n,phi,e,d;
    private int bitLen=1024;
    private SecureRandom random=new SecureRandom();

    public RSADemo(){
        p=BigInteger.probablePrime(bitLen,random);
        q=BigInteger.probablePrime(bitLen,random);
        System.out.println("p: " + p);
        System.out.println("q: " + q);
        n=p.multiply(q);
        System.out.println("n: " + n);
        phi=p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e=new BigInteger("65537");
        while(phi.gcd(e).intValue()>1){
            e=e.add(BigInteger.TWO);
        }
        d=e.modInverse(phi);
    }  

    public BigInteger encrypt(BigInteger msg){
        return msg.modPow(e,n);
    } 

    public BigInteger decrypt(BigInteger ciphertext){
        return ciphertext.modPow(d,n);
    }

    public static void main(String[] args){
        RSADemo rsa=new RSADemo();
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter a message:");
        String msg=sc.nextLine();
        BigInteger msgnum=new BigInteger(msg.getBytes());
        BigInteger ciphertext=rsa.encrypt(msgnum);
        System.out.println("Encrypted: "+ciphertext);
        String ct=new String(ciphertext.toByteArray());
        System.out.println("Ciphertext as string: " + ct);
        BigInteger decryptMsg=rsa.decrypt(ciphertext);
        String decryptedMsg1=new String(decryptMsg.toByteArray());
        System.out.println("Decrypted: " + decryptedMsg1);
    }
}