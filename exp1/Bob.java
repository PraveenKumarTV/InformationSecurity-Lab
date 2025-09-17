import java.net.*;
import java.util.Scanner;
public class Bob{
static String decrypt(String s,int key){
	String res="";
	for(char c:s.toCharArray()){
		if(Character.isUpperCase(c)){
			char a=(char)((c-key-65)%26+65);
			res+=a;
		}else if(Character.isLowerCase(c)){
			char a=(char)((c-key-97)%26+97);
			res+=a;
		}else{
			res+=c;
		}
	}
	return res;
}
	public static void main(String[] args){
		try{
			DatagramSocket socket=new DatagramSocket(12345);
			byte[] receiveData=new byte[1024];
			DatagramPacket packet=new DatagramPacket(receiveData,receiveData.length);
			socket.receive(packet);
			String msg=new String(packet.getData(),0,packet.getLength());
			int key=3;
			String dmsg=decrypt(msg,key);
			System.out.println("Encrypted msg: "+msg);
			System.out.println("Decrypted msg: "+dmsg);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
