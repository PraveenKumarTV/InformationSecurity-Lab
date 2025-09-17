import java.net.*;
import java.util.Scanner;
class MatrixUtil{
	public static int determinant(int[][] mat){
		int res=mat[0][0]*(mat[1][1]*mat[2][2]-mat[1][2]*mat[2][1])
		-mat[0][1]*(mat[1][0]*mat[2][2]-mat[1][2]*mat[2][0])+mat[0][2]*(mat[1][0]*mat[2][1]-mat[1][1]*mat[2][0]);
		return ((res%26)+26)%26;
		
	}
	public static int modInv(int a,int m){
		a=((a%m)+m)%m;
		for(int x=1;x<m;x++){
			if((a*x)%m==1) return x;
		}
		return -1;
	}
	public static int[][] adjugate(int[][] m){
		int[][] adj=new int[3][3];
		adj[0][0] = (m[1][1]*m[2][2] - m[1][2]*m[2][1]);
		        adj[0][1] = -(m[1][0]*m[2][2] - m[1][2]*m[2][0]);
		        adj[0][2] = (m[1][0]*m[2][1] - m[1][1]*m[2][0]);
		
		        adj[1][0] = -(m[0][1]*m[2][2] - m[0][2]*m[2][1]);
		        adj[1][1] = (m[0][0]*m[2][2] - m[0][2]*m[2][0]);
		        adj[1][2] = -(m[0][0]*m[2][1] - m[0][1]*m[2][0]);
		
		        adj[2][0] = (m[0][1]*m[1][2] - m[0][2]*m[1][1]);
		        adj[2][1] = -(m[0][0]*m[1][2] - m[0][2]*m[1][0]);
		        adj[2][2] = (m[0][0]*m[1][1] - m[0][1]*m[1][0]);
		
		        // Transpose and mod 26
		        int[][] adjT = new int[3][3];
		        for (int i = 0; i < 3; i++)
		            for (int j = 0; j < 3; j++)
		                adjT[i][j] = ((adj[j][i] % 26) + 26) % 26;
		
		        return adjT;
	}
	public static int[][] inverse(int[][] matrix){
		int det=determinant(matrix);
		int detInv=modInv(det,26);
		int[][] adj=adjugate(matrix);
		int[][] inv=new int[3][3];
		for(int i=0;i<3;i++){
		for(int j=0;j<3;j++){
			inv[i][j]=(adj[i][j]*detInv)%26;
			}
		}
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				inv[i][j]=(inv[i][j]+26)%26;
			}
		}
		return inv;
	}
				   
}
public class Bob{
static int[][] key={
    {6, 24, 1},
    {13, 16, 10},
    {20, 17, 15}
};
public static String decrypt(String msg,int[][] key){
	String res="";
	for(int i=0;i<msg.length();i+=3){
		int[] block=new int[3];
		for(int j=0;j<3;j++){
			block[j]=msg.charAt(i+j)-'A';
		}
		int[] decryptedBlock=new int[3];
		for(int row=0;row<3;row++){
		decryptedBlock[row]=0;
			for(int col=0;col<3;col++){
				decryptedBlock[row]+=key[row][col]*block[col];
			}
			decryptedBlock[row]%=26;
			res+=(char)(decryptedBlock[row]+'A');
		}
	}
	return res;
}
public static void main(String[] args){
	int[][] invKey=MatrixUtil.inverse(key);
	try{
		DatagramSocket socket=new DatagramSocket(12345);
		byte[] receiveData=new byte[1024];
		DatagramPacket packet=new DatagramPacket(receiveData,receiveData.length);
		socket.receive(packet);
		String msg=new String(packet.getData(),0,packet.getLength());
		String decryptMsg=decrypt(msg,invKey);
		System.out.println("Decrypted message: "+decryptMsg);
	}catch(Exception e){
		e.printStackTrace();
	}
}
	
}
