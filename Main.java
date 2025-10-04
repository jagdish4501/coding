import java.io.*;
import java.util.*;
#import cpjava.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=new StringTokenizer(br.readLine().trim());
		String s=st.nextToken();
		Io.outln(s);
        Io.outln("hell from Notepad");
    }
}