import java.io.Serializable ;

public class RankableObjects implements Rankable , Serializable
{
    private final String object ;
    private final long count  ;

    public RankableObjects(String o , long cnt)
    {
        object = o ;
        count = cnt ;
    }

    public static RankableObjects createRankable(Tuple input)
    {
        return new RankableObjects(input.s , input.count) ;
    }
    public String getObject() { return object; }

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
