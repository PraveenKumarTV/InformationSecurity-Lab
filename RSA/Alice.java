import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;
public class Alice{
	public static void main(String[] args){
		try{
			InetAddress addr=InetAddress.getByName("localhost");
		//	BigInteger e=new BigInteger("65537");
			Scanner sc=new Scanner(System.in);
		//	System.out.println("Enter your message: ");
			byte[] req="Send Public key".getBytes();
			DatagramSocket socket=new DatagramSocket();
			DatagramPacket reqPkt=new DatagramPacket(req,req.length,addr,12345);
			socket.send(reqPkt);
			byte[] res=new byte[2048];
			DatagramPacket resPkt=new DatagramPacket(res,res.length);
			socket.receive(resPkt);
			String keyStr=new String(resPkt.getData(),0,resPkt.getLength());
			String[] keyParts=keyStr.split(",");
			BigInteger e=new BigInteger(keyParts[0]);
			BigInteger n=new BigInteger(keyParts[1]);
			System.out.println("Public key parts (e,n): "+e+" , "+n);
			System.out.println("Enter your message: ");
			String msg=sc.nextLine();
			BigInteger msgInt=new BigInteger(msg.getBytes());
			BigInteger encrypted=msgInt.modPow(e,n);
			byte[] encryptedBytes=encrypted.toString().getBytes();
			DatagramPacket sendPkt=new DatagramPacket(encryptedBytes,encryptedBytes.length,addr,12345);
			socket.send(sendPkt);
			System.out.println("Encrypted message is sent: "+encrypted.toString());
			
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
