package cpjava;
import java.io.*;
import java.util.*;
import java.io.PrintWriter;

public class Io {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);
    private static StringTokenizer st;
    
    public static void out(Object o) {
        out.print(o);
        out.flush();
    }
    public static void outln(Object o) {
        out.println(o);
        out.flush();
    }

    private static String nextToken() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            String line = br.readLine();
            if (line == null)
                return null;
            st = new StringTokenizer(line);
        }
        return st.nextToken();
    }

    public static byte nextByte() throws IOException {
        return Byte.parseByte(nextToken());
    }

    public static short nextShort() throws IOException {
        return Short.parseShort(nextToken());
    }

    public static char nextChar() throws IOException {
        return nextToken().charAt(0);
    }

    public static int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    public static long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    public static float nextFloat() throws IOException {
        return Float.parseFloat(nextToken());
    }

    public static double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    public static boolean nextBoolean() throws IOException {
        return Boolean.parseBoolean(nextToken());
    }

    public static String next() throws IOException {
        return nextToken();
    }

    public static String nextLine() throws IOException {
        st = null; // reset tokenizer to force full line read
        return br.readLine();
    }
    public static boolean hasNext() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            String line = br.readLine();
            if (line == null)
                return false;
            st = new StringTokenizer(line);
        }
        return true;
    }
}
