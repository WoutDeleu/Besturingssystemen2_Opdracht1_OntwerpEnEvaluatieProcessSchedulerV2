package Schedulers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Basics.Process;

public class RR {
    private long duur;
    public RR(long tq) {
        duur = tq;
    }
    public List<Process> schedule(List<Process> proc){
        Queue<Process> q = new LinkedList<>();
        List<Process> adjList = new ArrayList<>(proc);
        long timer = adjList.get(0).getArrivaltime();

        Process temp;

        while(!adjList.isEmpty() || !q.isEmpty()){
            if(!q.isEmpty()){
                temp= q.peek();
                q.remove();
                if((temp.getBursttime()-duur)>0){
                    temp.setBursttime(temp.getBursttime()-duur);
                    q.add(temp);
                    timer=timer+duur;
                }
                else{
                    timer=timer+temp.getBursttime();
                    temp.setBursttime(0);
                    temp.setEndtime(timer);
                }
            }
            if(q.isEmpty() && !adjList.isEmpty()) {
                timer = adjList.get(0).getArrivaltime();
            }

            while(!adjList.isEmpty() && adjList.get(0).getArrivaltime()<=timer){
                adjList.get(0).setStarttime(timer);
                q.add(adjList.get(0));
                adjList.remove(0);
            }
        }

        calculateValues(proc);
        return proc;
    }

    private void calculateValues(List<Process> processes) {
        for(Process p : processes) {
            p.setWaittime(p.getEndtime() - p.getServicetime() - p.getArrivaltime());
            p.setTat(p.getWaittime() + p.getServicetime());
            p.setGenTat(p.getTat()/p.getServicetime());
        }
    }

}
