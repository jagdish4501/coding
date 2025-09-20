import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st=new StringTokenizer(br.readLine());
        int t = Integer.parseInt(br.readLine().trim());
        while (t-->0) {
            long n = Long.parseLong(br.readLine().trim()); 
            long ans=0;
            while(n>0){
                int ind=0;
                while(Math.pow(3, ind)<n)ind++;
                if(Math.pow(3, ind)>n)ind--;
                ans+=Math.pow(3, ind+1)+ind*Math.pow(3, ind-1);
                n-=Math.pow(3, ind);
            }
            System.out.println(ans);
        }
    }
}