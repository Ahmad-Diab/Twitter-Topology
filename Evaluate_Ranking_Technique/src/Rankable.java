public interface Rankable extends Comparable<Rankable>
{
    String getObject() ;

    long getCount() ;

    Rankable copy() ;
}
