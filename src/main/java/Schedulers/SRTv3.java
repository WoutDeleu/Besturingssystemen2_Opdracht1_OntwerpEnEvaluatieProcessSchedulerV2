package Schedulers;

import Basics.Process;

import java.util.ArrayList;
import java.util.List;

public class SRTv3 {
    public SRTv3() {
    }

    public List<Process> schedule(List<Process> processes) {
        List<Process> adjust = new ArrayList<>(processes);
        List<Process> queue = new ArrayList<>();
        List<Process> finished = new ArrayList<>();

        List<Process> currentProcess = new ArrayList<>();
        List<Process> waiting = new ArrayList<>();
        List<Process> que = new ArrayList<>(processes);



        Process current;
        Process temp;
        int timer = 0;

        while(finished.size()!=processes.size()) {
            if(!currentProcess.isEmpty()) {
                temp = currentProcess.get(0);
                temp.decreaseService();

                if(temp.isDone()) {
                    temp = currentProcess.get(0);
                    currentProcess.remove(0);
                    temp.setEndtime(timer);

                    finished.add(temp);
                }
            }
            while(!que.isEmpty() && que.get(0).getArrivaltime() <= timer) {
                waiting.add(adjust.get(0));
            }
            if(currentProcess.isEmpty() && !waiting.isEmpty()) {
                temp = waiting.get(0);
                waiting.remove(0);
                temp.setStarttime(timer);
                currentProcess.add(temp);
            }
            else if(!currentProcess.isEmpty() && !waiting.isEmpty()) {
                temp=currentProcess.get(0);

                //wanneer er processen wachten met lagere servicetime needed -> switchen
                if(temp.getServicetime()>waiting.get(0).getServicetime()){
                    temp=currentProcess.get(0);
                    currentProcess.remove(0);
                    Process p=waiting.get(0);
                    if(p.getStarttime()==0)
                        p.setStarttime(timer);

                    currentProcess.add(waiting.get(0));
                    waiting.remove(0);
                    waiting.add(temp);
                }
            }

            timer++;
        }
        return finished;
    }
}
