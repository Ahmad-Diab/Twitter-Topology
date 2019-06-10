package helper;

import com.google.common.collect.Lists;

import java.util.*;
import java.io.*;

public class Ranking implements Serializable
{
    private final int topN ;
    private final List<Rankable> rankElements = Lists.newArrayList() ;

    public Ranking(int topN) { this.topN = topN ; }

    public Ranking(Ranking merge)
    {
        this(merge.size()) ;
    }

    public  int size() { return topN ; }

    public List<Rankable> getRanking()
    {
        List<Rankable> copy = Lists.newLinkedList() ;

        for(Rankable r : rankElements) copy.add(r.copy()) ;

        return copy ;
    }
    // add an element to ranking list

    public void updateWith(Rankable r) // Intermediate , Worst Case Complexity(O(N))
    {
        synchronized (rankElements)
        {
            Integer rank = rankElements.indexOf(r) ;                    // O(N)
            if(rank == -1)
                rankElements.add(r) ;                                    // O(1)
            else
                rankElements.set(rank , r) ;                             // O(1)
            Collections.sort(rankElements, Collections.reverseOrder()); // O(N log N)
            if(rankElements.size() > topN)
                rankElements.remove(0) ;                           //O(N)
        }
    }

    public void updateWith(Ranking merge) // Worst Case Complexity(O(N log N))
    {
        for(Rankable r : merge.getRanking())
        {
            Integer rank = rankElements.indexOf(r) ;                    // O(N)
            if(rank == -1)
                rankElements.add(r) ;                                    // O(1)
            else
                rankElements.set(rank , r) ;                             // O(1)

            Collections.sort(rankElements, Collections.reverseOrder()); // O(N log N)

            if(rankElements.size() > topN)
                rankElements.remove(0) ;                           //O(N)
        }

    }
    public void removeZeroElements () //
    {
        while(rankElements.size() > 0 && rankElements.get(0).getCount() == 0)
            rankElements.remove(0) ;
    }
    public Ranking copy() // Total
    {
        return new Ranking(this) ;
    }
}
