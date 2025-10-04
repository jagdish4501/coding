package cpjava;

public class BinaryExpon {
    public static long bin_pow(long a, long n) {
        // a^(x+n)=a^x * a^n
        // { 1 if n==0
        // a^n = { (a^2)^n/2 mod m if n%2==1
        // { a*(a^2)^n/2 mond m if n%2==0
        //
        long ans = 1;
        while (n > 0) {
            if (n % 2 == 1) {
                ans = ans * a;
			}
            a = a * a;
            n >>= 1;
        }
        return ans;
    }
    public static long bin_pow(long a, long n, long m) {
        // a^(x+n)=a^x * a^n
        // { 1 if n==0
        // a^n = { (a^2)^n/2 mod m if n%2==1
        // { a*(a^2)^n/2 mond m if n%2==0
        //
        long ans = 1;
        while (n > 0) {
            if (n % 2 == 1) {
                ans = ans * a % m;
            }
            a = a * a % m;
            n >>= 1;
        }
        return ans;
    }
    private static void bin_power(long[][] base, long exp, long[][] result) {
        //binary exponantian for matrix
        while (exp > 0) {
            if ((exp & 1) == 1)
                multiply(result, base);
            multiply(base, base);
            exp >>= 1;
        }
    } 
    public static long fib(long n) {
        //matrix exponentiation
        // time complexity O(log(n))
        //  [1  1] [fn  ]   =  [fn + fn-1]   =  [fn+1]
        //  [1  0] [fn-1]      [fn       ]      [fn]
        //
        if (n == 0)
            return 0;
        long[][] result = { { 1, 0 }, { 0, 1 } }; // Identity matrix
        long[][] fibMatrix = { { 1, 1 }, { 1, 0 } };
        bin_power(fibMatrix, n , result);
        return result[0][1];
    }
    private static void multiply(long[][] a, long[][] b) {
        //matrix multiplication = mm
        //mm only possible when ca==rb
        //int ra=a.length,ca=a[0].length;
        //int rb=b.length,cb=b[0].length;
        long x = a[0][0] * b[0][0] + a[0][1] * b[1][0];
        long y = a[0][0] * b[0][1] + a[0][1] * b[1][1];
        long z = a[1][0] * b[0][0] + a[1][1] * b[1][0];
        long w = a[1][0] * b[0][1] + a[1][1] * b[1][1];
        a[0][0] = x;
        a[0][1] = y;
        a[1][0] = z;
        a[1][1] = w;
    }
}
