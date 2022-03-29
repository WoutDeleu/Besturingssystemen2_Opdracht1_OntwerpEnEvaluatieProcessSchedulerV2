package Basics;

import Schedulers.*;

public class Process {
    private int id;
    private long arrivaltime;

    public Process() {
        //System.out.println("fault is being made, this should not be called!");
    }

    private long servicetime;
    private long bursttime;

    private long starttime;
    private long endtime;
    private long waittime;
    private long tat;
    private long genTat;



    public Process(int id, long arrivaltime, long servicetime) {
        this.id = id;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.bursttime = servicetime;
    }

    public Process(long arrivaltime, long servicetime, long waittime, long tat, long genTat) {
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.bursttime = servicetime;
        this.waittime = waittime;
        this.tat = tat;
        this.genTat = genTat;
    }

    public Process(int id, long arrivaltime, long servicetime, long bursttime, long starttime, long endtime) {
        this.id = id;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.bursttime = bursttime;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public Process(Process p) {
        id = p.id;
        arrivaltime = p.arrivaltime;
        servicetime = p.servicetime;
        bursttime = p.bursttime;
    }

    public void reset() {
        this.starttime = 0;
        this.endtime = 0;
        this.waittime = 0;
        this.tat = 0;
        this.genTat = 0;
        this.bursttime = this.servicetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArrivaltime(long arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public void setServicetime(long servicetime) {
        this.servicetime = servicetime;
    }

    public long getArrivaltime() {
        return arrivaltime;
    }

    public long getServicetime() {
        return servicetime;
    }

    public long getWaittime() {
        return waittime;
    }

    public long getTat() {
        return tat;
    }

    public long getGenTat() {
        return genTat;
    }

    public void setWaittime(long waittime){
        this.waittime = waittime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public void setTat(long tat) {
        this.tat = tat;
    }

    public void setGenTat(long genTat) {
        this.genTat = genTat;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getStarttime() {
        return starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public long getBursttime() {
        return this.bursttime;
    }

    public void setBursttime( long bt){
        this.bursttime=bt;
    }

    public void decreaseService() {
        servicetime--;
    }

    public boolean isDone() {
        return servicetime == 0;
    }
}
