package cpjava;

public class Trie {
    private class TrieNode {
        public TrieNode[] child = new TrieNode[26];
        int count = 0;
        boolean isEnd = false;
    }
    private TrieNode root = new TrieNode();
    public void insert(String s) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            if (cur.child[c - 'a'] == null)
                cur.child[c - 'a'] = new TrieNode();
            cur.count = cur.count + 1;
            cur = cur.child[c - 'a'];
        }
        cur.isEnd = true;
    }
    public boolean search(String s) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            if (cur.child[c - 'a'] == null)
                return false;
            cur = cur.child[c - 'a'];
        }
        return cur.isEnd;
    }

    public boolean startWith(String s) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            if (cur.child[c - 'a'] == null)
                return false;
            cur = cur.child[c - 'a'];
        }
        return true;
    }
}