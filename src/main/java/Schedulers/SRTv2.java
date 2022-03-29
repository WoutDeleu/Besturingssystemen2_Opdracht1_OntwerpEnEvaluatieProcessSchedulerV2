package Schedulers;

import Basics.Process;

import java.util.ArrayList;
import java.util.List;

public class SRTv2 {
    public SRTv2() { }
    public List<Process> schedule(List <Process> processes) {
        List<Process> adjust = new ArrayList<>(processes);
        List<Process> queue = new ArrayList<>();
        List<Process> finished = new ArrayList<>();

        Process current = new Process(adjust.get(0));
        long timerDiff;
        long timer = current.getArrivaltime();
        current.setStarttime(timer);
        adjust.remove(0);

        while(finished.size()!=processes.size()) {
            if(adjust.isEmpty()) {
                timer += current.getServicetime();
                finish(processes, current, finished, timer);
                current = findNewCurrent(current, queue, timer);
            }
            else if(queue.isEmpty()) {
                timerDiff = adjust.get(0).getArrivaltime()-timer;
                timer = adjust.get(0).getArrivaltime();
                updateCurrentTimer(current, timerDiff);
                if(isCurrentToBeReplaced(current, adjust.get(0))) {
                    current = replace_current(current, adjust, queue, timer);
                }
                else {
                    addToQueue(adjust, queue);
                }
            }
            else {
                //if event "process" out of adjust arrrives first -> true
                if(isArrivalFirst(current, adjust, timer)) {
                    timerDiff = adjust.get(0).getArrivaltime()-timer;
                    timer = adjust.get(0).getArrivaltime();
                    updateCurrentTimer(current, timerDiff);
                    if(isCurrentToBeReplaced(current, adjust.get(0))) {
                        current = replace_current(current, adjust, queue, timer);
                    }
                    else {
                        addToQueue(adjust, queue);
                    }
                }
                else {
                    timer += current.getServicetime();
                    finish(processes, current, finished, timer);
                    current = findNewCurrent(current, queue, timer);
                }
            }
        }
        calculateValues(processes);
        return processes;
    }

    private Process findNewCurrent(Process current, List<Process> queue, long timer) {
        long shortestRemainingService = queue.get(0).getServicetime();
        int shortestIndex = 0;
        for(int i = 1 ; i<queue.size(); i++) {
            if(queue.get(i).getServicetime() < shortestRemainingService) shortestIndex = i;
        }
        current = new Process(queue.get(shortestIndex));
        queue.remove(shortestIndex);
        if(current.getStarttime() == 0) current.setStarttime(timer);

        return current;
    }

    private void finish(List<Process> processes, Process current, List<Process> finished, long timer) {
        processes.get(current.getId()-1).setEndtime(timer);
        processes.get(current.getId()-1).setStarttime(current.getStarttime());
        finished.add(processes.get(current.getId()-1));
    }

    private void addToQueue(List<Process> adjust, List<Process> queue) {
        queue.add(new Process(adjust.get(0)));
        adjust.remove(0);
    }

    private Process replace_current(Process current, List<Process> adjust, List<Process> queue, long timer) {
        queue.add(new Process(current));
        current = new Process(adjust.get(0));
        current.setStarttime(timer);
        adjust.remove(0);
        return current;
    }

    private boolean isCurrentToBeReplaced(Process current, Process process) {
        return process.getServicetime() < current.getServicetime();
    }

    private void updateCurrentTimer(Process current, long timeDiff) {
        current.setServicetime(current.getServicetime()- timeDiff);
    }

    private boolean isArrivalFirst(Process current, List<Process> adjust, long timer) {
        long currentFinish_time = current.getServicetime();
        long arrival_time = adjust.get(0).getArrivaltime()-timer;
        return arrival_time < currentFinish_time;
    }

    private void calculateValues(List<Process> processes) {
        for(Process p : processes) {
            p.setWaittime(p.getEndtime() - p.getServicetime() - p.getArrivaltime());
            p.setTat(p.getWaittime() + p.getServicetime());
            p.setGenTat(p.getTat()/p.getServicetime());
        }
    }
}
