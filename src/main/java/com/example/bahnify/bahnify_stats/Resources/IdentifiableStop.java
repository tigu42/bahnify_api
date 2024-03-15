package com.example.bahnify.bahnify_stats.Resources;

import java.time.LocalTime;

public class IdentifiableStop implements IIdentifiable {
    protected String trainNum;
    protected String operator;
    protected String currentStation;
    protected Direction direction;

    protected LocalTime time;

    @Override
    public String toString() {
        return "IdentifiableStop{" +
                "trainNum='" + trainNum + '\'' +
                ", operator='" + operator + '\'' +
                ", currentStation='" + currentStation + '\'' +
                ", direction=" + direction +
                ", time=" + time +
                '}';
    }

    public IdentifiableStop(String trainNum, String operator, String currentStation, Direction direction, LocalTime time) {
        this.trainNum = trainNum;
        this.operator = operator;
        this.currentStation = currentStation;
        this.direction = direction;
        this.time = time;
    }

    @Override
    public String getTrainNum() {
        return trainNum;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public String getCurrentStation() {
        return currentStation;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public LocalTime getTime() {
        return time;
    }
}
