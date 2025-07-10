import java.net.*;
import java.util.Scanner;
public class CaesarCipherServer{
    public static void main(String[] args){
        try{
            Scanner sc=new Scanner(System.in);
            DatagramSocket socket=new DatagramSocket(12345);
            System.out.println("Server is running");
            while(true){
            byte[] buff=new byte[1024];
            byte[] buff1=new byte[1024];
            DatagramPacket receivePkt=new DatagramPacket(buff1, buff1.length);
            socket.receive(receivePkt);
            System.out.println("Enter a message:");
            String msg=new String(receivePkt.getData());
            String res="";
            int key=3;
            for(int i=0;i<msg.length();i++){
                char c=msg.charAt(i);
                char ch=(char)(((int)c+key-65)%26+65);
                res+=ch;
                //System.out.println(ch);
            }
            InetAddress client=receivePkt.getAddress();
            buff=res.getBytes();
            int port=receivePkt.getPort();
           DatagramPacket sendPkt=new DatagramPacket(buff,buff.length,client,port);
           socket.send(sendPkt);
        }

        }catch(Exception e){
            System.out.println(e);
        }
    }
}