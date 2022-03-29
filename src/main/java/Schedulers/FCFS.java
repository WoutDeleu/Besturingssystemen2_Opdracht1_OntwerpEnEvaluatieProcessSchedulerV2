package Schedulers;

import Basics.Process;

import java.util.List;

public class FCFS {
    public FCFS() { }

    public List<Process> schedule(List<Process> temp) {
        List<Process> processes = temp;
        int c = 0;
        long currentEndTime = 0;
        for(Process p : processes){
            if(c == 0) {
                p.setWaittime(0);
            }
            else {
                if (currentEndTime - p.getArrivaltime() < 0) p.setWaittime(0);
                else p.setWaittime(currentEndTime - p.getArrivaltime());
            }
            currentEndTime = p.getArrivaltime()+p.getServicetime()+p.getWaittime();
            p.setEndtime(currentEndTime);
            p.setStarttime(p.getArrivaltime()+p.getWaittime());
            p.setTat(p.getServicetime()+p.getWaittime());
            p.setGenTat(p.getTat()/p.getServicetime());
            c++;
        }
        return processes;
    }
}
