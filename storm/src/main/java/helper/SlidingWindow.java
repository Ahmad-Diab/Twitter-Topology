package helper;

import java.util.*;
import java.io.*;

public class SlidingWindow<T> implements Serializable
{
    private SlotCounter<T> objects ;
    private int windowLength ;
    private int currentSlot = 0 ;

    public SlidingWindow(int window)
    {
        windowLength = window ;
        objects = new SlotCounter<T>(windowLength) ;

    }

    public void addObject(T object) { objects.addObject(object , currentSlot); }

    public HashMap<T , Long> getCountsThenAdvanceWindow()
    {
        HashMap<T , Long> counts = objects.getAllObjects() ;
        objects.pruneObjects();
        objects.wipeSlot(currentSlot = nextSlot(currentSlot));
        return counts ;
    }

    private int nextSlot(int slot) { return (slot + 1) % windowLength ; }
}
