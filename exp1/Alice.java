import java.net.*;
import java.util.Scanner;
public class Alice{
	static String encrypted(String s,int key){
		String res="";
		for(char c:s.toCharArray()){
			if(Character.isUpperCase(c)){
				char a=(char)((c+key-65)%26+65);
				res+=a;
			}else if(Character.isLowerCase(c)){
				char a=(char)((c+key-97)%26+97);
				res+=a;
			}else{
				res+=c;
			}
		}
		return res;
	}
	public static void main(String[] args){
		String addr="localhost";
		int port=12345;
		int key=3;
		try{
			DatagramSocket socket=new DatagramSocket();
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter msg to send to Bob:  ");
			String msg=sc.nextLine();
			String encrypted=encrypted(msg,key);
			byte[] sendData=encrypted.getBytes();
			InetAddress ip=InetAddress.getByName(addr);
			DatagramPacket packet=new DatagramPacket(sendData,sendData.length,ip,port);
			socket.send(packet);
			System.out.println("Encrypted msg sent: "+encrypted);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
