import java.io.*;
import java.net.*;
import java.util.*;
public class CaesarCipherClient{
    public static void main(String[] args) {
        try {
            Scanner sc=new Scanner(System.in);
            DatagramSocket socket=new DatagramSocket();
            InetAddress addr=InetAddress.getByName("localhost");
            int port=12345;
            System.out.println("Enter a msg:");
            String msg=sc.next();
            byte[] buff1=new byte[1024];
            byte[] buff2=new byte[1024];
            buff1=msg.getBytes();
            DatagramPacket sendPkt=new DatagramPacket(buff1, buff1.length,addr,port);
            socket.send(sendPkt);
            DatagramPacket receivePkt=new DatagramPacket(buff2, buff2.length);
            socket.receive(receivePkt);
            String cipherText=new String(receivePkt.getData());
            //int key=3;
             System.out.println(cipherText.substring(0, msg.length()-1));
             int it=0;String predictTxt=cipherText;
            int key=1;
            while(!predictTxt.equals(msg) && key<26) {
                String tmp="";
            for(int i=0;i<msg.length();i++){
                char c=cipherText.charAt(i);
                char ch=(char)(((int)c-key+65)%26+65);
                tmp+=ch;
                //System.out.print(ch);
            }
            System.out.println(tmp);
            predictTxt=tmp;
            it++;key++;
        }
        System.out.println("The key is: "+(key-1));
            //System.out.println(cipherText);
        } catch (Exception e) {
        }
    }
}