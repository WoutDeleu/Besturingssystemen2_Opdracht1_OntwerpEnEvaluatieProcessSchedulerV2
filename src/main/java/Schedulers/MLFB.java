package Schedulers;

import Basics.Process;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MLFB {
    private long timeSlice;

    public MLFB(long timeSlice) {
        this.timeSlice = timeSlice;
    }

    public List<Process> schedule(List<Process> processes) {
        List<Queue> queues = new ArrayList<>(5);
        for(int i=0; i<5; i++) {
            queues.add(new LinkedList<Process>());
        }
        Queue<Process> adj = new LinkedList<>(processes);
        List<Process> finished = new ArrayList<>();

        Process current = adj.poll();
        long timer = current.getArrivaltime();
        queues.get(0).add(current);

        while(finished.size() != processes.size()) {
            checkForNewProcesses(adj, queues, timer);

            //queue 1
            if(!queues.get(0).isEmpty()) {
                current = (Process) queues.get(0).poll();
                if(current.getStarttime()==0) current.setStarttime(timer);
                if(timeSlice >= current.getBursttime()) {
                    timer += current.getBursttime();
                    finish(current, finished, timer);
                }
                else {
                    timer += timeSlice;
                    current.setBursttime(current.getBursttime()-timeSlice);
                    queues.get(1).add(current);
                }
            }

            //queue 2
            else if(!queues.get(1).isEmpty()) {
                current = (Process) queues.get(1).poll();
                if(2*timeSlice >= current.getBursttime()) {
                    timer += current.getBursttime();
                    finish(current, finished, timer);
                }
                else {
                    timer += 2*timeSlice;
                    current.setBursttime(current.getBursttime()-2*timeSlice);
                    queues.get(2).add(current);
                }
            }

            //queue 3
            else if(!queues.get(2).isEmpty()) {
                current = (Process) queues.get(2).poll();
                if(4*timeSlice >= current.getBursttime()) {
                    timer += current.getBursttime();
                    finish(current, finished, timer);
                }
                else {
                    timer += 4*timeSlice;
                    current.setBursttime(current.getBursttime()-4*timeSlice);
                    queues.get(3).add(current);
                }

            }

            //queue 4
            else if(!queues.get(3).isEmpty()) {
                current = (Process) queues.get(3).poll();
                if(8*timeSlice >= current.getBursttime()) {
                    timer += current.getBursttime();
                    finish(current, finished, timer);
                }
                else {
                    timer += 8*timeSlice;
                    current.setBursttime(current.getBursttime()-8*timeSlice);
                    queues.get(4).add(current);
                }
            }

            else if(!queues.get(4).isEmpty()) {
                current = (Process) queues.get(4).poll();
                timer += current.getBursttime();
                current.setBursttime(0);
                finish(current, finished, timer);
            }
            else {
                timer = adj.peek().getArrivaltime();
            }
        }

        calculateValues(finished);
        return finished;
    }

    private void finish(Process current, List<Process> finished, long timer) {
        current.setEndtime(timer);
        current.setBursttime(0);
        finished.add(current);
    }

    private void checkForNewProcesses(Queue<Process> adj, List<Queue> queues, long timer) {
        if(!adj.isEmpty()) {
            while(adj.peek().getArrivaltime() <= timer) {
                queues.get(0).add(adj.poll());
                if(adj.isEmpty()) break;
            }
        }
    }

    private void calculateValues(List<Process> processes) {
        for(Process p : processes) {
            p.setWaittime(p.getEndtime() - p.getServicetime() - p.getArrivaltime());
            p.setTat(p.getWaittime() + p.getServicetime());
            p.setGenTat(p.getTat()/p.getServicetime());
        }
    }
}
