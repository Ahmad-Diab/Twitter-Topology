import java.util.*;
import java.io.*;

public class Test
{
    public static void main (String [] args) throws Exception
    {
        for(int i = (int)1e3 ;i < 1e4 ; i += 1000)
            System.out.println(i);
        for(int i = (int)1e4 ;i < 1e5 ; i += 10000)
            System.out.println(i);
        for(int i = (int)1e5 ;i <= 1e6 ; i += 100000)
            System.out.println(i);


//        Scanner sc = new Scanner(("newDataSet.csv"));
////        PrintWriter out = new PrintWriter(new FileWriter("newDataSet.csv"));
//        int tot = 0 ;
//        double start = System.currentTimeMillis() ;
//
//        while(sc.isReady()) {
//            tot ++ ;
//            String s = sc.br.readLine();
//            String[] split = s.split(",");
//            for (int i = 5; i < split.length; i++) {
////                if (i > 5)
////                    out.print(",");
//                String curr = split[i].replaceAll("\"", "");
////                out.print(curr);
//            }
////            out.println();
//        }
//        System.out.println(tot);
//        double end = System.currentTimeMillis() ;
//        System.out.println((end - start) / 1e3 + " s");
////        out.flush();
////        out.close();
    }
    static class Scanner {
        BufferedReader br;
        StringTokenizer st;

        Scanner(InputStream in)
        {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        Scanner(String path) throws Exception
        {
            br = new BufferedReader(new FileReader(path)) ;
        }
        String next() throws Exception
        {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine() , "[ .,?!@#$%^&*(+)_=/\\\"'|`~{}]+");
            return st.nextToken();
        }
        int nextInt() throws Exception { return Integer.parseInt(next()); }

        long nextLong() throws Exception { return Long.parseLong(next()); }

        double nextDouble() throws Exception { return Double.parseDouble(next());}

        boolean isReady() throws Exception{return br.ready() || (st != null && st.hasMoreTokens());}
    }
}
