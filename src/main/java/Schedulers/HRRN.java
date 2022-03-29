package Schedulers;

import Basics.Process;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HRRN {
    public HRRN() { }

    public List<Process> schedule(List<Process> processes) {
        List<Process> waiting = new ArrayList<>();
        Queue<Process> q = new LinkedList<>(processes);
        List<Process> finished = new ArrayList<>();

        Process current = q.poll();
        long timer = current.getArrivaltime();
        current.setStarttime(timer);

        while(true) {

            timer = updateTimer(timer, current);
            finish(current, finished, timer);

            if(finished.size() == processes.size()) break;
            while(true) {
                if(q.peek() == null) break;
                if(q.peek().getArrivaltime() > timer) break;
                else waiting.add(q.poll());
            }
            if(!waiting.isEmpty()) current = findNewCurrentInQ(current, waiting, timer);
            else {
                current = q.poll();
                timer = current.getArrivaltime();
                current.setStarttime(timer);
            }
        }
        calculateValues(finished);
        return finished;
    }

    private Process findNewCurrentInQ(Process current, List<Process> waiting, long timer) {
        long maxTat = 0;
        Process temp = new Process();
        long rr;

        for(Process p : waiting) {
            rr = calculateRR(p, timer);
            if(rr > maxTat) {
                maxTat = rr;
                temp = p;
            }
        }
        current = temp;
        waiting.remove(temp);
        current.setStarttime(timer);
        return current;
    }

    private long calculateRR(Process p, long timer) {
        long ts = p.getServicetime();
        long te = timer + ts;
        long ta = p.getArrivaltime();
        long tat = (te - ta)/ts;
        return tat;
    }

    private void finish(Process current, List<Process> finished, long timer) {
        current.setEndtime(timer);
        //finished.add(new Process(current.getId(), current.getArrivaltime(), current.getServicetime(), current.getBursttime(), current.getStarttime(), current.getEndtime()));
        finished.add(current);
    }

    private long updateTimer(long timer, Process current) {
        return timer + current.getServicetime();
    }

    private void calculateValues(List<Process> processes) {
        for(Process p : processes) {
            p.setWaittime(p.getEndtime() - p.getServicetime() - p.getArrivaltime());
            p.setTat(p.getWaittime() + p.getServicetime());
            p.setGenTat(p.getTat()/p.getServicetime());
        }
    }
}
