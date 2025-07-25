import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        
    }
}

class Io {
    static final Scanner sc = new Scanner(System.in);
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter out = new PrintWriter(System.out);
    private static StringTokenizer st;
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


class SetCP<T extends Comparable<T>> implements Iterable<T> {
    private TreeSet<T> treeSet;
    private HashSet<T> hashSet;
    private TreeMap<T, Integer> multiSet;
    public enum Type {
        SORTED_SET,
        UNORDERED_SET,
        MULTISET
    }
    private Type type;
    public SetCP(Type type) {
        this.type = type;
        switch (type) {
            case SORTED_SET -> treeSet = new TreeSet<>();
            case UNORDERED_SET -> hashSet = new HashSet<>();
            case MULTISET -> multiSet = new TreeMap<>();
        }
    }

    public void add(T val) {
        switch (type) {
            case SORTED_SET -> treeSet.add(val);
            case UNORDERED_SET -> hashSet.add(val);
            case MULTISET -> multiSet.put(val, multiSet.getOrDefault(val, 0) + 1);
        }
    }

    public void remove(T val) {
        switch (type) {
            case SORTED_SET -> treeSet.remove(val);
            case UNORDERED_SET -> hashSet.remove(val);
            case MULTISET -> {
                if (multiSet.containsKey(val)) {
                    int count = multiSet.get(val);
                    if (count == 1) multiSet.remove(val);
                    else multiSet.put(val, count - 1);
                }
            }
        }
    }

    // Check if element exists
    public boolean contains(T val) {
        return switch (type) {
            case SORTED_SET -> treeSet.contains(val);
            case UNORDERED_SET -> hashSet.contains(val);
            case MULTISET -> multiSet.containsKey(val);
        };
    }

    // Get frequency (only valid for multiset)
    public int count(T val) {
        if (type == Type.MULTISET) {
            return multiSet.getOrDefault(val, 0);
        }
        return contains(val) ? 1 : 0;
    }

    // Get size
    public int size() {
        return switch (type) {
            case SORTED_SET -> treeSet.size();
            case UNORDERED_SET -> hashSet.size();
            case MULTISET -> multiSet.values().stream().mapToInt(i -> i).sum();
        };
    }
    // Get first element (for TreeSet or MultiSet)
    public T first() {
        return switch (type) {
            case SORTED_SET -> treeSet.first();
            case MULTISET -> multiSet.firstKey();
            default -> throw new UnsupportedOperationException("first() not supported for unordered_set");
        };
    }

    // Get last element
    public T last() {
        return switch (type) {
            case SORTED_SET -> treeSet.last();
            case MULTISET -> multiSet.lastKey();
            default -> throw new UnsupportedOperationException("last() not supported for unordered_set");
        };
    }

    // Clear all elements
    public void clear() {
        switch (type) {
            case SORTED_SET -> treeSet.clear();
            case UNORDERED_SET -> hashSet.clear();
            case MULTISET -> multiSet.clear();
        }
    }

    // Debug print
    public void print() {
        switch (type) {
            case SORTED_SET -> System.out.println(treeSet);
            case UNORDERED_SET -> System.out.println(hashSet);
            case MULTISET -> {
                List<T> list = new ArrayList<>();
                for (Map.Entry<T, Integer> entry : multiSet.entrySet()) {
                    list.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
                }
                System.out.println(list);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return switch (type) {
            case SORTED_SET -> treeSet.iterator();
            case UNORDERED_SET -> hashSet.iterator();
            case MULTISET -> {
                // Expand multiset into a list of repeated elements
                List<T> list = new ArrayList<>();
                for (Map.Entry<T, Integer> entry : multiSet.entrySet()) {
                    list.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
                }
                yield list.iterator();
            }
        };
    }
}

class Shell {
    public static void cmd() {
        try {
            String command = Io.nextLine();
            ProcessBuilder builder = new ProcessBuilder("bash", "-ic",command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                Io.outln(line);
            }
            int exitCode = process.waitFor();
            Io.outln("Exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}