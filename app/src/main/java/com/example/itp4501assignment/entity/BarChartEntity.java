package com.example.itp4501assignment.entity;

/*
 * store the Bar chart X and Y Axis data
 */
public class BarChartEntity {
    private String xLabel;
    private Float[] yValue;
    private float sum;

    public BarChartEntity(String xLabel, Float[] yValue) {
        this.xLabel = xLabel;
        this.yValue = yValue;
        for (float y : yValue) {
            sum+=y;
        }
    }

    public String getxLabel() {
        return xLabel;
    }

    public Float[] getyValue() {
        return yValue;
    }

    public float getSum() {
        return sum;
    }
}
