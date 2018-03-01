package com.haieros.hosroom.widget.loading;

/**
 * Created by Kang on 2017/10/25.
 */

class ARC {

    private int startDegree;
    private int totalArc;

    public ARC(int startDegree, int totalArc) {
        this.startDegree = startDegree;
        this.totalArc = totalArc;
    }

    public int getStartDegree() {
        return startDegree;
    }

    public void setStartDegree(int startDegree) {
        this.startDegree = startDegree;
    }

    public int getTotalArc() {
        return totalArc;
    }

    public void setTotalArc(int totalArc) {
        this.totalArc = totalArc;
    }

    @Override
    public String toString() {
        return "ARC{" +
                "startDegree=" + startDegree +
                ", totalArc=" + totalArc +
                '}';
    }
}
