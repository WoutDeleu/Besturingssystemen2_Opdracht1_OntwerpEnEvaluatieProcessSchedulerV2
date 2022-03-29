package Schedulers;

import Basics.Process;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SJF {
    public SJF() { }

    public List<Process> schedule(List<Process> processes) {
        List<Process> adjustableList = new ArrayList<>(processes);
        long timer = adjustableList.get(0).getArrivaltime();

        while(!adjustableList.isEmpty()) {
            if(adjustableList.get(0).getArrivaltime() > timer) timer = adjustableList.get(0).getArrivaltime();
            else {
                Process currentProcess = findShortestServiceTime(adjustableList, timer);
                currentProcess.setStarttime(timer);
                timer += currentProcess.getServicetime();
                currentProcess.setEndtime(timer);
            }
        }
        calculateValues(processes);
        return processes;
    }

    private Process findShortestServiceTime(List<Process> adjustableList, long timer) {
        int currentBestServicetime_index = 0;
        Process ret;
        for(int i = 0; i< adjustableList.size(); i++) {
            if( adjustableList.get(i).getServicetime() < adjustableList.get(currentBestServicetime_index).getServicetime() && adjustableList.get(i).getArrivaltime() <= timer ) {
                currentBestServicetime_index = i;
            }
        }
        ret = adjustableList.get(currentBestServicetime_index);
        adjustableList.remove(currentBestServicetime_index);
        return ret;
    }

    private void calculateValues(List<Process> processes) {
        for(Process p : processes) {
            p.setWaittime(p.getEndtime() - p.getServicetime() - p.getArrivaltime());
            p.setTat(p.getWaittime() + p.getServicetime());
            p.setGenTat(p.getTat()/p.getServicetime());
        }
    }
}
