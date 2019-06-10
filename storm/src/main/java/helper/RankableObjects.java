package helper;

import org.apache.storm.tuple.Tuple;

import java.io.Serializable;

public class RankableObjects implements Rankable , Serializable
{
    private final Object object ;
    private final long count  ;

    public RankableObjects(Object o , long cnt)
    {
        object = o ;
        count = cnt ;
    }
    public static RankableObjects createRankable(Tuple input)
    {
        return new RankableObjects(input.getValue(0) , input.getLong(1)) ;
    }

    public Object getObject() { return object; }

    public long getCount() { return count; }

    public Rankable copy() { return new RankableObjects(getObject() , getCount()); }

    public int compareTo(Rankable o)
    {
        long diff = o.getCount() - this.getCount() ;
        return diff < 0 ? - 1 : diff == 0 ? 0 : 1 ;
    }
    @Override
    public boolean equals(Object o)
    {
        RankableObjects r = (RankableObjects) o;
        return this == o || (getObject().equals(r.getObject()) &&  getCount() == r.getCount());
    }
}
