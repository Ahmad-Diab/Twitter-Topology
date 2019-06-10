package helper;

import java.util.*;
import java.io.*;

public class SlotCounter<T> implements Serializable
{

    private final HashMap<T , long [] > objects = new HashMap<T, long[]>() ;
    private final int slots;

    public SlotCounter(int numSlots) { slots = numSlots;}

    void addObject(T object , int slot)
    {
        long [] counts = objects.get(object) ;

        if(counts == null)
            objects.put(object , counts = new long[slots]) ;
        counts[slot] ++ ;
    }

    long getCount(T object , int slot)
    {
        if(objects.containsKey(object))
            return objects.get(object)[slot] ;
        else
            return 0 ;
    }
    HashMap<T , Long> getAllObjects()
    {
        HashMap<T , Long> res = new HashMap<T, Long>() ;

        for(Map.Entry<T , long [] > e : objects.entrySet())
        {
            T object = e.getKey() ;
            long [] count = e.getValue() ;
            long tot = 0 ;
            for(long x : count) tot += x ;
            res.put(object , tot) ;
        }
        return res ;
    }
    void wipeSlot(int slot)
    {
        for(long [] x : objects.values()) x[slot] = 0 ;
    }

    void pruneObjects()
    {
        HashSet<T> removed = new HashSet<T>() ;

        for(Map.Entry<T , long [] > e : objects.entrySet())
        {
            T object = e.getKey() ;
            long [] count = e.getValue() ;
            long tot = 0 ;
            for(long x : count) tot += x ;
            if(tot == 0)
                removed.add(object) ;
        }
        for(T object : removed)
            objects.remove(object) ;
    }
}
