import java.net.*;
import java.nio.channels.DatagramChannel;
class Point{
    int x,y;boolean isInfinity;
    Point(int a,int b){
        x=a;y=b;
        isInfinity=false;
    }
    Point(){
        isInfinity=true;
    }
    public String toString(){
        return isInfinity?"infnity":"("+x+","+y+")";
    }

}
public class Bob{
    static final int a=2;
    static final int b=2;
    static final int p=17;
    static int modInverse(int k,int p){
        k=k%p;
        for(int x=1;x<p;x++){
            if((k*x)%p==1) return x;
        }
        throw new ArithmeticException("No modular inverse");
    }
     static Point add(Point P, Point Q) {
        if (P.isInfinity) return Q;
        if (Q.isInfinity) return P;

        if (P.x == Q.x && (P.y != Q.y || P.y == 0)) {
            return new Point(); // Point at infinity
        }

        int m;
        if (P.x == Q.x && P.y == Q.y) {
            // Point doubling
            int numerator = (3 * P.x * P.x + a) % p;
            int denominator = (2 * P.y) % p;
            m = (numerator * modInverse(denominator, p)) % p;
        } else {
            // Point addition
            int numerator = (Q.y - P.y + p) % p;
            int denominator = (Q.x - P.x + p) % p;
            m = (numerator * modInverse(denominator, p)) % p;
        }

        int rx = (m * m - P.x - Q.x + p * 2) % p;
        int ry = (m * (P.x - rx) - P.y + p * 2) % p;

        return new Point(rx, ry);
    }

    static Point scalarMultiply(Point p,int k){
        Point result=new Point();
        Point addend=p;
        while(k>0){
            if((k&1)==1){
                result=add(result,addend);
            }
            addend=add(addend,addend);
            k>>=1;
        }
        return result;
    }
    public static void main(String[] args){
        int privateKey=3;
        Point g=new Point(5,1);
        Point publicKey=scalarMultiply(g,privateKey);
        try {
            DatagramSocket socket=new DatagramSocket(12345);
            byte[] req=new byte[1024];
            DatagramPacket reqPkt=new DatagramPacket(req,req.length);
            socket.receive(reqPkt);
            String aKey=new String(reqPkt.getData(),0,reqPkt.getLength());
            String[] coords=aKey.split(",");
            int x=Integer.parseInt(coords[0]);
            int y=Integer.parseInt(coords[1]);
            Point aplc=new Point(x,y);
            String bkey=publicKey.x+","+publicKey.y;
            InetAddress addr=reqPkt.getAddress();
            int port=reqPkt.getPort();
            byte[] res=bkey.getBytes();
            DatagramPacket resPkt=new DatagramPacket(res,res.length,addr,port);
            socket.send(resPkt);
            System.out.println(aKey);


        } catch (Exception e) {
        }
        System.out.println("Base point G: "+g);
        System.out.println("Private key d: "+privateKey);
        System.out.println("Public key Q: "+publicKey);
    }
}
