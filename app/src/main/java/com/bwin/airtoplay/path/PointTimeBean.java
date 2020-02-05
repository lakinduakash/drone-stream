package com.bwin.airtoplay.path;

import android.graphics.PointF;

public class PointTimeBean {
    public float distance;
    public PointF point = new PointF();
    public float time;

    public PointTimeBean(float x, float y, float time2, float distance2) {
        this.point.set(x, y);
        this.time = time2;
        this.distance = distance2;
    }

    public PointTimeBean(PointTimeBean bean) {
        this.point.set(bean.point);
        this.time = bean.time;
        this.distance = bean.distance;
    }

    public void set(float x, float y, long time2, float distance2) {
        this.point.set(x, y);
        this.time = (float) time2;
        this.distance = distance2;
    }
}
