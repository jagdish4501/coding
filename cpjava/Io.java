package cpjava;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.PrintWriter;

public class Io {
    static final Scanner sc = new Scanner(System.in);
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);
    private static StringTokenizer st;

    // private StringBuilder sb = new StringBuilder();//for optimised output
    // sb.append("name ").append("\n");
    public static void out(Object o) {
        out.print(o);
    }

    public static void outln(Object o) {
        out.println(o);
    }

    public static void flush() {
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

    public static ArrayList<Integer> nextIntArrayList() {
        ArrayList<Integer> list = new ArrayList<>();
        while (sc.hasNextInt()) {
            list.add(sc.nextInt());
        }
        return list;
    }

    public static ArrayList<String> nextStringArrayList() {
        ArrayList<String> list = new ArrayList<>();
        while (sc.hasNext()) {
            list.add(sc.next());
        }
        return list;
    }
}
