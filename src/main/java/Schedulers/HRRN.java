package Schedulers;

import Basics.Process;

import java.util.ArrayList;
import java.util.List;

public class HRRN {
    public HRRN() { }

    public List<Process> schedule(List<Process> processes) {
        List<Process> adjList = new ArrayList<>(processes);
        List<Process> q = new ArrayList<>();
        long timer = adjList.get(0).getArrivaltime();
        Process current = adjList.get(0);
        adjList.remove(0);

        while(!adjList.isEmpty() || !q.isEmpty()){
            //verwerkte processen verwijderen
            q.removeIf(p -> p.getBursttime() == 0);
            //nieuwe processen toevoegen
            while(!adjList.isEmpty() && (adjList.get(0).getArrivaltime()<= timer)){
                adjList.get(0).setStarttime(adjList.get(0).getArrivaltime());
                adjList.get(0).setStarttime(adjList.get(0).getArrivaltime());
                q.add(adjList.get(0));
                adjList.remove(0);
            }
            //process verwerken
            timer+=current.getServicetime();
            current.setBursttime(0);
            current.setEndtime(timer);
            //volgend te verwerken process kiezen
            calculateResponseRatios(q, timer);
            if(q.isEmpty() && !adjList.isEmpty()){
                if(timer<adjList.get(0).getArrivaltime()) timer = adjList.get(0).getArrivaltime();
            }
            else if(!q.isEmpty()) {
                current = biggestResponseRatio(q);
            }
        }
        //laatste process nog verwerken
        timer = timer+current.getBursttime();
        current.setBursttime(0);
        current.setEndtime(timer);
        //waittime, tat en genormaliseerde tat berekenen voor alle processen
        calculateValues(processes);
        return processes;
    }

    private void calculateResponseRatios(List<Process> proc, long time) {
        for(Process p : proc){
            p.setWaittime(time - p.getArrivaltime());
            p.setTat(p.getWaittime()+p.getServicetime());
            p.setGenTat(p.getTat()/p.getServicetime());
        }
    }

    private Process biggestResponseRatio(List<Process> proc){
        Process p = proc.get(0);
        for (Process a : proc) {
            if ((a.getGenTat() > p.getGenTat())) {
                p = a;
            }
        }
        proc.remove(p);
        return p;
    }

    private void calculateValues(List<Process> processes) {
        for(Process p : processes) {
            p.setWaittime(p.getEndtime() - p.getServicetime() - p.getArrivaltime());
            p.setTat(p.getWaittime() + p.getServicetime());
            p.setGenTat(p.getTat()/p.getServicetime());
        }
    }
}
