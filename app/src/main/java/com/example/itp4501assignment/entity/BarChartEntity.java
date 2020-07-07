package com.example.itp4501assignment.entity;

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

    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public BarChartEntity(Float[] yValue) {
        this.yValue = yValue;
    }

    public Float[] getyValue() {
        return yValue;
    }

    public void setyValue(Float[] yValue) {
        this.yValue = yValue;
        for (float y : yValue) {
            sum+=y;
        }
    }

    public float getSum() {
        return sum;
    }
}
