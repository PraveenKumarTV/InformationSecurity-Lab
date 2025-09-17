import java.util.Scanner;
import java.nio.charset.StandardCharsets;
public class SHA1{
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter your message: ");
		String msg=sc.nextLine();
		byte[] msgBytes=msg.getBytes(StandardCharsets.UTF_8);
		int originalLen=msgBytes.length*8;
		System.out.println(originalLen);
		int totLen=((originalLen+64+1+511)/512)*512;
		System.out.println(totLen);
		byte[] padded=new byte[totLen/8];
		System.arraycopy(msgBytes,0,padded,0,msgBytes.length);
		padded[msgBytes.length]=(byte)0x80;
		long msgBitLen=originalLen;
		for(int i=0;i<8;i++){
			padded[padded.length-1-i]=(byte)(msgBitLen>>>(8*i));
		}
		int[] w=new int[80];
		w[0]=((padded[0] & 0xFF)<<24) | ((padded[1] & 0xFF)<<16) |
		((padded[2] & 0xFF)<<8) | ((padded[3] & 0xFF));
		System.out.printf("W[0] : %08x\n",w[0]);
		int a=0x11223344;
		int b=0x55678899;
		int c=0x00AABBCC;
		int d=0xDDEEFF01;
		int e=0x23456789;
		int f=(b&c) | ((~b)&d);
		int k=0x5A827999;
		int tmp=Integer.rotateLeft(a,5)+f+e+k+w[0];
		e=d;
		d=c;
		c=Integer.rotateLeft(b,30);
		b=a;
		a=tmp;
		System.out.printf("a: %08x\n",a);
		System.out.printf("b: %08x\n",b);
		System.out.printf("c: %08x\n",c);
		System.out.printf("d: %08x\n",d);
		System.out.printf("e: %08x\n",e);
	}
}

