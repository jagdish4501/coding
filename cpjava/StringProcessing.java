package cpjava;
import java.util.ArrayList;
import java.util.HashSet;
import static java.lang.Math.*;
import java.util.Arrays;

public class StringProcessing {

    public static long polyRollHash(String s){
        // polynomial rolling hash function
        //hash=s[0]*p^0 + s[1]*p^1 + s[2]*p^2 + ..... mod m
        //      n-1
        //hash= ∑   s[i]*p^i mod m
        //      i=0
        //hash of substring
        //            j
        // hash[i..j]=∑  s[k]*p^(k-i) mod m
        //            k=i
        // hash[i..j]*p^i = s[i]*p^k + s[i+1]*p^(i+1)+...+s[j]*p^i mod m
        // hash[i..j]*p^i=hash[0..j]-hash[0..i-1] mod m
        //we have to calculate modular multiplicative inverse of p^i
        //now its clear if we can precompute mmi of p^i and hash of each prefix string then we can calculate hash of every substring in O(1)
        // we can calculate mmi of p^i  with fermat little theorem (only when m is prime )
        // mmi_of_p^i=pow(p^i,m-2,m) TC=O(long(m))
        // its not neccessary to calculate mmi_of_p^1 fir comparing two substring hash
        //we can multiply smaller p^i to make hash multiple of same power of p
        //probability of collision 1/m only for (best prime)1e9+9 
        long hash=0;
        long p=31,m=1000000000+9,p_pow=1;
        for(int i=0;i<s.length();i++){
            hash=(hash+(int)s.charAt(i)*p_pow)%m;
            p_pow=p_pow*p;
        }
        return hash;
    }
    
    public static ArrayList<Integer> rabinKarp(String s,String t){
        //RabinKarp algorith is based on string hashing 
        //its use to search matching word
        long p=257,mod=(long)(1e9+9);//for taking the value of p=31,53,131,137 etc
        ArrayList<Integer> ans=new ArrayList<>();
        int n=s.length(),m=t.length();
        if(n>m)
            return ans;
        long[] p_pow=new long[m];
        p_pow[0] = 1;
        long[] hash=new long[m];
        hash[0]=(int)t.charAt(0)%mod;
        for(int i=1;i<m;i++){
            p_pow[i]=p_pow[i-1]*p%mod;
        }
        for(int i=1;i<m;i++){
            hash[i]=(hash[i-1]+(int)t.charAt(i)*p_pow[i]%mod)%mod;
        }
        long  phash=0;
        for(int i=0;i<n;i++){
            phash=(phash+(int)s.charAt(i)*p_pow[i]%mod)%mod;
        }
        for(int i=0;i+n-1<m;i++){
            long cur_hash;
            if(i==0)cur_hash=hash[i+n-1];
            else cur_hash=(hash[i+n-1]-hash[i-1]+mod)%mod;
            //here iff hassh matched then t.substring(i,i+n).equals(s)
            //to detech hash collision 
            if(cur_hash==(phash*p_pow[i]%mod) && t.substring(i,i+n).equals(s))
                ans.add(i);
        }    
        return ans;
    }
    
    public static ArrayList<Integer> kmp(String s,String t){
        String temp=s+"#"+t;
        int n=s.length();
        int[] pi=prefixFunction(temp);
        ArrayList<Integer> ans=new ArrayList<>();
        for(int i=n+1;i<temp.length();i++){
            if(pi[i]==s.length())
                ans.add(i-2*n);
        }
        return ans;
    }
    
    public static int countDistinctSubstring(String s){
        //Time Complexity O(n^2)
        //This can we solved in O(nlogn) with suffix array or O(n) with suffix tree
        //so here logic is very simple callculate the hash of each substring O(n^2)
        // and count the unique hash that is the distinct nuber of  substring
        //
        int n=s.length(),p=257;
        int m=(int)(1e9+9);
        long[] p_pow=new long[n];
        long[] hash=new long[n];
        HashSet<Long> st=new HashSet<>();
        p_pow[0]=1;
        for(int i=1;i<n;i++){
            p_pow[i]=p_pow[i-1]*p%m;
        }
        hash[0]=(int)s.charAt(0);
        for(int i=1;i<n;i++){
            hash[i]=(hash[i-1]+(int)s.charAt(i)*p_pow[i]%m)%m;
        }
        for(int i=0;i<n;i++){
            for(int j=i;j<n;j++){
                long cur_hash;
                if(i==0){
                    cur_hash=hash[j];
                }else{
                    cur_hash=(hash[j]-hash[i-1]+m)%m;
                }
                //here for each substring hash we are normalising hash
                //since hash[j]-hash[i-1]=hash[j-i]*p^i mod m
                //(hash[j]-hash[i-1])*p^(n-i-1)=hash[j-i]*p^(n-1) mod m
                //so for each substring hash is normalise with  p^(n-1)
                cur_hash=cur_hash*p_pow[n-1-i]%m;
                st.add(cur_hash);
            }
        }
        return st.size()+1;
    }
    
    public static int[] prefixFunction(String s){
        /*The prefix function for the string is defined as an array pi of length n where 
        pi[i] is the length of the longest proper prefix of the substring s[0..i] wich is also suffix of substring
        A proper prefix of a string is a prefix that is not equal to the string itself
        ## A proper prefix of a string is a prefix that is not equal to the string itself

        Observation 1: the values of the prefix function can only increase by at most one or decrease by some amount.
        .....................................................

        kmp algorithm ==> prefix function ==>  (s+"#"+text)
        count all pi[i] with  value n=s.length() and indexOf(s) will be i-2*n
        */
        int n=s.length();
        int[] pi=new int[n];
        for(int i=1;i<n;i++){
            int j=pi[i-1];
            while(j>0 && s.charAt(i)!=s.charAt(j))
                j=pi[j-1];
            if(s.charAt(i)==s.charAt(j))
                j++;
            pi[i]=j;
        }
        return pi;
    }
    
    public static int[] zFunction(String s){
        // z[i]=length of the longest substring starting at i which is also a prefix of s
        // so z[0] will be s.length() but we assume it as 0
        //
        int n=s.length();
        int[] z=new int[n];
        int l=0,r=0;
        //[l, r) is rightmost segment match with prefix
        for(int i=1;i<n;i++){
            if(i<r){
                z[i]=min(r-i,z[i-l]);
                // s="aaaabcaa"
                // this string example show why min(r-i,z[i-l])
            }
            while(i+z[i]<n && s.charAt(z[i])==s.charAt(i+z[i])){
                z[i]++;
            }
            if(i+z[i]>r){
                l=i;
                r=i+z[i];
            }
        }
        return z;
    }
}


class PrefixDoublingSuffixArray {
    /*
     * ✅ 1. What is a Suffix Array?
     * A suffix array is an array of integers that shows the starting positions of
     * all suffixes of a string in sorted order.
     * 
     * 
     * ✅ 2. What is an LCP Array?
     * LCP = Longest Common Prefix
     *
     * The LCP Array tells us how much two consecutive suffixes (in the suffix
     * array) have in common at the beginning.
     * 
     * 
     */
    static class Suffix implements Comparable<Suffix> {
        int index;
        int rank;
        int nextRank;
        Suffix(int i, int r, int nr) {
            index = i;
            rank = r;
            nextRank = nr;
        }
        public int compareTo(Suffix s) {
            if (this.rank != s.rank)
                return Integer.compare(this.rank, s.rank);
            return Integer.compare(this.nextRank, s.nextRank);
        }
    }

    public static int[] buildSuffixArray(String s) {
        int n = s.length();
        Suffix[] suffixes = new Suffix[n];
        // Step 1: Initial ranking based on first 2 characters
        for (int i = 0; i < n; i++) {
            int next = (i + 1 < n) ? s.charAt(i + 1) : -1;
            suffixes[i] = new Suffix(i, s.charAt(i), next);
        }
        Arrays.sort(suffixes);
        // At this point we start doubling
        int[] indices = new int[n]; // to map original index of suffix to its position in suffixes[]
        
        for (int k = 4; k < 2 * n; k *= 2) {
            int rank = 0;
            int prev_rank = suffixes[0].rank;
            suffixes[0].rank = rank;
            indices[suffixes[0].index] = 0;

            // Assign ranks
            for (int i = 1; i < n; i++) {
                if (suffixes[i].rank == prev_rank && suffixes[i].nextRank == suffixes[i - 1].nextRank) {
                    prev_rank = suffixes[i].rank;
                    suffixes[i].rank = rank;
                } else {
                    prev_rank = suffixes[i].rank;
                    suffixes[i].rank = ++rank;
                }
                indices[suffixes[i].index] = i;
            }

            // Assign next rank
            for (int i = 0; i < n; i++) {
                int nextIndex = suffixes[i].index + k / 2;
                suffixes[i].nextRank = (nextIndex < n) ? suffixes[indices[nextIndex]].rank : -1;
            }
            Arrays.sort(suffixes);
        }
        // Extract suffix array
        int[] suffixArr = new int[n];
        for (int i = 0; i < n; i++) {
            suffixArr[i] = suffixes[i].index;
        }
        return suffixArr;
    }
}
