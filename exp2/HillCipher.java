import java.util.ArrayList;
import java.util.Scanner;



public class HillCipher {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter plain text: ");
        String plainText=sc.nextLine();
        System.out.println("Enter key: ");
        String key=sc.nextLine();
        int keylen=key.length();
        int row=(int)Math.sqrt(keylen);
        if(plainText.length()%row!=0){
            System.out.println("Invalid key size");
            return;
        }
        int col=plainText.length()/row;
        ArrayList<ArrayList<Integer>> mat1=new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> mat2=new ArrayList<ArrayList<Integer>>();
        int ind=0;
        for(int i=0;i<row;i++){
            ArrayList<Integer> tmp=new ArrayList<>();
            for(int j=0;j<row;j++){
                tmp.add((int)key.charAt(ind)-97);
                ind++;
            }
            mat1.add(tmp);
        }
        ind=0;
        for(int i=0;i<row;i++){
            ArrayList<Integer> tmp=new ArrayList<>();
            for(int j=0;j<col;j++){
                tmp.add((int)plainText.charAt(ind)-97);
                ind++;
            }
            mat2.add(tmp);
        }
        
        ArrayList<ArrayList<Integer>> res=matrixMultiplication(mat1,mat2,row,col);
        for(int i=0;i<res.size();i++){
            ArrayList<Integer> tmp=res.get(i);
            for(int j=0;j<tmp.size();j++){
                System.out.print(tmp.get(j)+" ");
            }
            System.out.println();
        }
    }


    public static ArrayList<ArrayList<Integer>> matrixMultiplication(
    ArrayList<ArrayList<Integer>> k,  // m x m
    ArrayList<ArrayList<Integer>> p,  // m x n
    int m,  // rows in k (and rows in p)
    int n   // columns in p
) {
    ArrayList<ArrayList<Integer>> res = new ArrayList<>();

    for (int i = 0; i < m; i++) {
        ArrayList<Integer> rowResult = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            int sum = 0;
            for (int x = 0; x < m; x++) {
                sum += k.get(i).get(x) * p.get(x).get(j);
            }
            rowResult.add(sum);
        }
        res.add(rowResult);
    }
    return res;
}

}
