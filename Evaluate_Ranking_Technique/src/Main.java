import java.util.*;
import java.io.*;

public class Main
{
    HashMap<String,Integer> map = new HashMap<>();
    private String[] skipWords = {"rt", "to", "me","la","on","that","que",
            "followers","watch","know","not","have","like","I'm","new","good","do",
            "more","es","te","followers","Followers","las","you","and","de","my","is",
            "en","una","in","for","this","go","en","all","no","don't","up","are",
            "http","http:","https","https:","http://","https://","with","just","your",
            "para","want","your","you're","really","video","it's","when","they","their","much",
            "would","what","them","todo","FOLLOW","retweet","RETWEET","even","right","like",
            "bien","Like","will","Will","pero","Pero","can't","were","Can't","Were","TWITTER",
            "make","take","This","from","about","como","esta","follows","followed" , "again" , "morning" , "think" , "never"};

    void solve(Scanner sc , PrintWriter out , int cnt) throws Exception {
        Random r = new Random() ;
        int topN = cnt ;
        int MAX = (int)1e3 ;

        outer:
        for(int i = 0 ; sc.isReady() && i <= MAX ; i++) {
            String s = sc.next() ;
            if(s.length() < 5) continue ;
            for(String ss : skipWords)
                if(ss.equals(s))
                    continue outer;
            map.put(s , map.getOrDefault(s , 0) + 1) ;
        }

        IntermediateRanking [] inter_ranking = new IntermediateRanking[4] ;
        TotalRanking totRanking = new TotalRanking(topN) ;

        for(int i = 0 ;i < 4 ; i++) inter_ranking [i] = new IntermediateRanking(topN) ;

        for(String o : map.keySet()) inter_ranking[r.nextInt(4)].updateRanking(new Tuple(o, map.get(o)));

        for(int i = 0 ; i < 4 ; i++) totRanking.updateRanking(inter_ranking[i].execute());

//        out.print("----------------------------------------------------------------\n");

//        out.printf("Top %d Ranking Elements :-\n" , Math.min(totRanking.getRanking().queue.size() , topN));
        Stack<Rankable> print = new Stack<>() ;

        for(; !totRanking.getRanking().queue.isEmpty();) print.push(totRanking.getRanking().queue.poll());

//        for(Rankable curr : totRanking.getRanking().queue) print.push(curr) ;

        for(int i = 0 ; !print.isEmpty() && i <= topN ; i++)
        {
            Rankable rank = print.pop() ;
//            out.println(rank.getObject()+" "+rank.getCount());
        }
//        out.printf("Top %d Ranking Elements :-\n" , Math.min(totRanking.getRanking().size() , topN));
//        for(Rankable rank : totRanking.getRanking().getRanking())
//            out.println(rank.getObject()+" "+rank.getCount());

//        out.print("----------------------------------------------------------------\n\n");

    }
    public static void main (String [] args) throws Exception {
        for(int cnt = (int)1e3 ; cnt < 1e6 ; cnt*= 10)
            for(int i = cnt ;i <= cnt * 10 ; i+= cnt)
            {
                Scanner sc = new Scanner("newDataSet.csv");
                PrintWriter out = new PrintWriter(System.out) ;
                long startTime = System.currentTimeMillis();
                (new Main()).solve(sc, out , i);
//                out.println(i);
                out.println((System.currentTimeMillis() - startTime)+ "");

                out.flush();
            }

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
