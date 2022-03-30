package Schedulers;

import Basics.Process;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SRT {
    public SRT() { }

    public List<Process> schedule(List<Process> processes) {
        List<Process> adjustableList = new ArrayList<>(processes);
        List<Process> q = new ArrayList<>();
        //int currentIndexTimer = 0;
        long timer = adjustableList.get(0).getArrivaltime();

        Process current = adjustableList.get(0);
        current.setStarttime(current.getArrivaltime());
        adjustableList.remove(0);
        Process temp;

        while(!adjustableList.isEmpty() || !q.isEmpty()) {
            while(!adjustableList.isEmpty() && (adjustableList.get(0).getArrivaltime()<= timer)){
                adjustableList.get(0).setStarttime(adjustableList.get(0).getArrivaltime());
                q.add(adjustableList.get(0));
                adjustableList.remove(0);
            }
            if(!adjustableList.isEmpty()){
                q.removeIf(p -> p.getBursttime() == 0);
                if(q.size()>1) current=shortestBurstTime(current, q);
                if(adjustableList.get(0).getArrivaltime()<(timer+current.getBursttime())){
                    current.setBursttime((timer+current.getBursttime())-adjustableList.get(0).getArrivaltime());
                    timer = adjustableList.get(0).getArrivaltime();
                }
                else if(adjustableList.get(0).getArrivaltime()==(timer+current.getBursttime())){
                    current.setBursttime(0);
                    current.setEndtime(timer);
                }
                else {
                    long time = adjustableList.get(0).getArrivaltime()-timer;
                    if(q.isEmpty()) {
                        if((current.getBursttime()-time)>=0){
                            current.setBursttime(current.getBursttime()-time);
                        }
                        else {
                            current.setBursttime(0);
                        }
                        timer=timer+time;
                    }
                    else{
                        while(time>0 && !q.isEmpty()){
                            if(current.getBursttime()<=time){
                                current.setEndtime(timer+ current.getBursttime());
                                time=time-current.getBursttime();
                                timer=timer+ current.getBursttime();
                                current.setBursttime(0);
                                temp=current;
                                current=shortestBurstTime(current, q);
                                q.remove(temp);
                            }
                            else {
                                current.setBursttime(current.getBursttime()-time);
                                timer=timer+time;
                                time=0;
                            }
                        }
                    }
                }
            }
            else {
                while(!q.isEmpty()){
                    current= shortestBurstTime(current, q);
                    q.remove(current);
                    timer=timer+current.getBursttime();
                    current.setBursttime(0);
                    current.setEndtime(timer);
                }
            }
        }
        calculateValues(processes);
        return processes;
    }

    private Process shortestBurstTime(Process p, List<Process> proc){
        if(p.getBursttime()==0) p = proc.get(0);
        else proc.add(p);
        boolean changed = false;
        for( Process a : proc){
            if((a.getBursttime()<p.getBursttime())) {
                p = a;
                changed = true;
            }
        }
        if(changed) proc.remove(p);
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
