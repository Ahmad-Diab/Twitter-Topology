package helper;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class Ranking_All implements Serializable
{
    private final int topN ;
    private final PriorityQueue<Rankable> rankElements = new PriorityQueue<Rankable>();

    public Ranking_All(int topN){ this.topN = topN ; }
    public Ranking_All(Ranking_All merge)
    {
        this(merge.size()) ;
    }

    public  int size() { return topN ; }

    public List<Rankable> getRanking()
    {
        List<Rankable> copy = Lists.newLinkedList() ;
        Stack<Rankable> curr = new Stack<>() ;

        while(!rankElements.isEmpty())
        {
            Rankable r = rankElements.poll() ;
            copy.add(r) ;
            curr.add(r) ;
        }
        while(!curr.isEmpty()) rankElements.add(curr.pop());
        return copy ;
    }
    public void updateWith(Ranking merge) // Total
    {
        for(Rankable o : merge.getRanking())
        {
            synchronized (rankElements)
            {
                rankElements.add(o) ;
                if(rankElements.size() > topN)
                    rankElements.poll() ;
            }
        }
    }
    public void removeZeroElements ()
    {
        while(!rankElements.isEmpty() && rankElements.peek().getCount() == 0)
            rankElements.poll() ;
    }
    public Ranking_All copy()
    {
        return new Ranking_All(this) ;
    }

}
