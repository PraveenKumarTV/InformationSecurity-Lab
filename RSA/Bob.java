import java.math.BigInteger;
import java.net.*;
import java.security.SecureRandom;
public class Bob{
	public static void main(String[] args){
		SecureRandom random=new SecureRandom();
		BigInteger p=BigInteger.probablePrime(512,random);
		BigInteger q=BigInteger.probablePrime(512,random);
		BigInteger n=p.multiply(q);
		BigInteger phi=p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger e=BigInteger.valueOf(65537);
		BigInteger d=e.modInverse(phi);
		System.out.println("Bob's public key: ");
		System.out.println("e= "+e);
		System.out.println("n= "+n);
		try{
		DatagramSocket socket=new DatagramSocket(12345);
		byte[] receiveBuf=new byte[1024];
		DatagramPacket receive=new DatagramPacket(receiveBuf,receiveBuf.length);
		socket.receive(receive);
		InetAddress addr=receive.getAddress();
		int port=receive.getPort();
		String publicKey=e.toString()+","+n.toString();
		byte[] publicBytes=publicKey.getBytes();
		DatagramPacket send=new DatagramPacket(publicBytes,publicBytes.length,addr,port);
		socket.send(send);
		System.out.println("Public key is sent to Alice");
		byte[] encryptedBuf=new byte[2048];
		DatagramPacket encryptedPacket=new DatagramPacket(encryptedBuf,encryptedBuf.length);
		socket.receive(encryptedPacket);
		String encryptedMsg=new String(encryptedPacket.getData(),0,encryptedPacket.getLength());
		BigInteger encrypted=new BigInteger(encryptedMsg);
		System.out.println("Encrypted message: "+encrypted);
		BigInteger decrypted=encrypted.modPow(d,n);
		String msg=new String(decrypted.toByteArray());
		System.out.println("Decrypted Message: "+msg);
		
	}catch(Exception ex){
		ex.printStackTrace();
	}
	}
}
