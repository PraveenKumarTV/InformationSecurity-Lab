import java.net.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import java.security.MessageDigest;
public class Alice{
static BigInteger getHashVal(String s)throws Exception{
	MessageDigest sha256=MessageDigest.getInstance("SHA-256");
	byte[] digest=sha256.digest(s.getBytes());
	BigInteger h=new BigInteger(1,digest);
	return h;
}
	public static void main(String[] args){
	try{
		SecureRandom random=new SecureRandom();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter message: ");
		String msg=sc.nextLine();
		BigInteger hm=getHashVal(msg);
		BigInteger q=BigInteger.probablePrime(256,random);
		hm=hm.mod(q);
		int l=2048,n=256;
		BigInteger p,k;
		while(true){
			k=new BigInteger(l-n,random);
			p=q.multiply(k).add(BigInteger.ONE);
			if(p.isProbablePrime(100)){
				break;
			}
		}
		BigInteger g=BigInteger.ONE;
		BigInteger h=BigInteger.TWO;
		while(true){
			g=h.modPow(k,p);
			if(!g.equals(BigInteger.ONE)){
				break;
			}
			h=h.add(BigInteger.ONE);
		}
		BigInteger x;
		do{
			x=new BigInteger(256,random);
		}while(x.compareTo(BigInteger.ONE)<0 || x.compareTo(q)>=0);
		BigInteger K;
		do{
			K=new BigInteger(256,random);
		}while(K.compareTo(BigInteger.ONE)<0 || K.compareTo(q)>=0);
		BigInteger y=g.modPow(x,p);
		BigInteger r=g.modPow(K,p).mod(q);
		BigInteger kinv=K.modInverse(q);
		BigInteger s=(kinv.multiply(hm.add(x.multiply(r)))).mod(q);
		String sign=hm.toString()+","+r.toString()+","+s.toString()+","+p.toString()+","+q.toString()+","+g.toString()+","+y.toString();
		DatagramSocket socket=new DatagramSocket();
		InetAddress addr=InetAddress.getByName("localhost");
		byte[] res=sign.getBytes();
		DatagramPacket resPkt=new DatagramPacket(res,res.length,addr,12345);
		socket.send(resPkt);
		System.out.println("Signature is sent");
		System.out.println(r);
	
	}catch(Exception ex){
		ex.printStackTrace();
	}
	}
}
