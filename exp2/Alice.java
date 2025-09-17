import java.net.*;
import java.util.Scanner;
public class Alice{
	static int[][] key={{6,24,1},{13,16,10},{20,17,15}};
	static String encrypt(String s){
		String res="";
		for(int i=0;i<s.length();i+=3){
			int[] block=new int[3];
			for(int j=0;j<3;j++){
				block[j]=s.charAt(i+j)-'A';
			}
			int[] encryptedBlock=new int[3];
			for(int row=0;row<3;row++){
				encryptedBlock[row]=0;
				for(int col=0;col<3;col++){
					encryptedBlock[row]+=key[row][col]*block[col];
				}
				encryptedBlock[row]%=26;
			}
			for(int val:encryptedBlock){
				res+=(char)(val+'A');
			}
		}
		return res;
	}
	public static void main(String[] args){
	try{
		DatagramSocket socket=new DatagramSocket();
		InetAddress addr=InetAddress.getByName("localhost");
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter message: ");
		String msg=sc.nextLine();
		while((msg.length())%3!=0){
			msg+="X";
		}
		String ciphertxt=encrypt(msg);
		byte[] sendData=ciphertxt.getBytes();
		DatagramPacket packet=new DatagramPacket(sendData,sendData.length,addr,12345);
		socket.send(packet);
		System.out.println("Sent encypted message to Bob: "+ciphertxt);
	}catch(Exception e){
		e.printStackTrace();
	}

	}
}
