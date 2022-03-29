package Basics;

import java.util.Comparator;

public class ServiceTimeComparator implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        return (new Long(o1.getServicetime()).compareTo(new Long(o2.getServicetime())));
    }
}