package cpjava;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;


public class SetCp<T extends Comparable<T>> implements Iterable<T> {
    private TreeSet<T> treeSet;
    private HashSet<T> hashSet;
    private TreeMap<T, Integer> multiSet;

    public enum Type {
        SORTED_SET,
        UNORDERED_SET,
        MULTISET
    }

    private Type type;

    public SetCp(Type type) {
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
                    if (count == 1)
                        multiSet.remove(val);
                    else
                        multiSet.put(val, count - 1);
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
