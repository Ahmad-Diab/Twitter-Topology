
import java.util.* ;
import java.io.* ;

public class Ranking
{
    private final int topN ;

    public final AVL_Tree<Rankable> rankElements ;
    public final PriorityQueue<Rankable> queue ;
//    public final List<Rankable> rankElements = new ArrayList<>() ;
//    public final List<Rankable> queue = new ArrayList<>() ;

    public Ranking(int topN) {
        this.topN = topN ;
        rankElements = new AVL_Tree<>((r1 , r2) -> ((Rankable)r1).getObject().compareTo(((Rankable)r2).getObject())) ;
        queue = new PriorityQueue<>((r1 , r2) ->Long.compare(((Rankable)r1).getCount(),(((Rankable)r2).getCount())));

    }

    public Ranking(Ranking merge)
    {
        this(merge.size()) ;
    }

    public  int size() { return topN ; }

    public List<Rankable> getRanking()
    {
       return rankElements.getList() ;
//        return rankElements;
    }
    public void updateWith(Rankable r) // Worst Case Scenario O(log N)
    {

//        synchronized (rankElements)
//        {
//            Integer rank = rankElements.indexOf(r) ;                    // O(N)
//            if(rank == -1)
//                rankElements.add(r) ;                                    // O(1)
//            else
//                rankElements.set(rank , r) ;                             // O(1)
//            Collections.sort(rankElements, Collections.reverseOrder()); // O(N log N)
//            if(rankElements.size() > topN)
//                rankElements.remove(0) ;                           //O(N)
//        }
        if(r.getCount() == 0)
            rankElements.remove(r) ; // log(N)
        else
            rankElements.add(r) ;   //log(N)
    }

    public void updateWith(Ranking merge) //O(M log N)
    {
        for(Rankable o : merge.rankElements) // O(M)
        {
            queue.add(o); // O(log N)
            if(queue.size() > topN)// O(1)
                queue.poll() ;  // O(log N)
        }
//        for(Rankable r : merge.rankElements)
//        {
//            Integer rank = queue.indexOf(r) ;                    // O(N)
//            if(rank == -1)
//                queue.add(r) ;                                    // O(1)
//            else
//                queue.set(rank , r) ;                             // O(1)
//
//            Collections.sort(queue, Collections.reverseOrder()); // O(N log N)
//
//            if(queue.size() > topN)
//                queue.remove(0) ;                           //O(N)
//        }

    }

    public Ranking copy()
    {
        return new Ranking(this) ;
    }

}
