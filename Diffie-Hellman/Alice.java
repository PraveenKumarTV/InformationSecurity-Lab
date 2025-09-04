import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Alice {
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
        int g=5;
        int a=6;
        DatagramSocket socket;
        try{
            socket=new DatagramSocket(5000);
            byte[] receiveData=new byte[1024];
            DatagramPacket receivePacket=new DatagramPacket(receiveData,receiveData.length);
            socket.receive(receivePacket);
            String receivedString=new String(receivePacket.getData(),0,receivePacket.getLength());
            int p=Integer.parseInt(receivedString);
            int keyA=modPow(g,a,p);
            byte[] sendData=String.valueOf(keyA).getBytes();
            DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,receivePacket.getAddress(),receivePacket.getPort());
            socket.send(sendPacket);
            socket.receive(receivePacket);
            String receivedString2=new String(receivePacket.getData(),0,receivePacket.getLength());
            int KeyB=Integer.parseInt(receivedString2);
            int sharedKeyA=modPow(KeyB,a,p);
            System.out.println("Alice public key: "+keyA);
            System.out.println("Alice shared key: "+sharedKeyA);
            socket.close();
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
            return;
        }
    }
    
}
