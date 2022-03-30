package Schedulers;

import java.util.ArrayList;
import java.util.List;
import Basics.Process;

public class RR {
    private long duur;
    public RR(long tq) {
        duur = tq;
    }
    public List<Process> schedule(List<Process> proc){
        List<Process> q = new ArrayList<>();
        List<Process> adjList = new ArrayList<>(proc);
        long timer = adjList.get(0).getArrivaltime();

        Process temp;

        while(!adjList.isEmpty() || !q.isEmpty()){
            boolean added = false;
            if(!q.isEmpty()){
                temp= q.get(0);
                q.remove(0);
                if((temp.getBursttime()-duur)>0){
                    temp.setBursttime(temp.getBursttime()-duur);
                    q.add(temp);
                    added = true;
                    timer=timer+duur;
                }
                else{
                    timer=timer+temp.getBursttime();
                    temp.setBursttime(0);
                    temp.setEndtime(timer);
                }
            }

            if(q.isEmpty() && !adjList.isEmpty() && timer<adjList.get(0).getArrivaltime()) {
                timer = adjList.get(0).getArrivaltime();
            }

            int count = 0;
            while(!adjList.isEmpty() && adjList.get(0).getArrivaltime()<=timer){
                adjList.get(0).setStarttime(timer);
                if(added) q.add(count, adjList.get(0));
                else {
                    q.add(adjList.get(0));
                }
                count++;
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
