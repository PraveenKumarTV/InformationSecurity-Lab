import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Bob {
    static int modPow(int base,int exp,int mod){
        int res=1;
        base=base%mod;
        while(exp>0){
            if((exp&1)==1){
                res=(res*base)%mod;
            }
             exp=exp>>1;
                base=(base*base)%mod;
        }
        return res;
    }
    public static void main(String[] args) {
        DatagramSocket socket;
        int b=15;
        int g=5;
        int p=23;
        try{
            byte[] sendData=String.valueOf(p).getBytes();
            socket=new DatagramSocket();
            DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,InetAddress.getByName("localhost"),5000);
            socket.send(sendPacket);   
            int keyB=modPow(g,b,p);
            byte[] receiveData=new byte[1024];
            DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
            socket.receive(receivePacket);
            String receivedString=new String(receivePacket.getData(),0,receivePacket.getLength());
            int KeyA=Integer.parseInt(receivedString);
            int sharedKeyB=modPow(KeyA,b,p);
            sendPacket=new DatagramPacket(String.valueOf(keyB).getBytes(),String.valueOf(keyB).getBytes().length,receivePacket.getAddress(),receivePacket.getPort());
            socket.send(sendPacket);
            System.out.println("Bob public key: "+keyB);
            System.out.println("Bob shared key: "+sharedKeyB);

        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
            return;
        }
    }
    
}
