import java.net.*;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Scanner;
public class Bob{
	public static void main(String[] args){
		try{
			DatagramSocket socket=new DatagramSocket(12345);
			byte[] req=new byte[4026];
			DatagramPacket reqPkt=new DatagramPacket(req,req.length);
			socket.receive(reqPkt);
			String signature=new String(reqPkt.getData(),0,reqPkt.getLength());
			//System.out.println(signature);
			String[] pts=signature.split(",");
			BigInteger hm=new BigInteger(pts[0]);
			BigInteger r=new BigInteger(pts[1]);
			BigInteger s=new BigInteger(pts[2]);
			BigInteger p=new BigInteger(pts[3]);
			BigInteger q=new BigInteger(pts[4]);
			BigInteger g=new BigInteger(pts[5]);
			BigInteger y=new BigInteger(pts[6]);
			BigInteger w=s.modInverse(q);
			BigInteger u1=hm.multiply(w).mod(q);
			BigInteger u2=r.multiply(w).mod(q);
			BigInteger v=g.modPow(u1,p).multiply(y.modPow(u2,p)).mod(p).mod(q);
			System.out.println(v);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
