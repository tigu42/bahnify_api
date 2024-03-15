package com.example.bahnify.bahnify_stats.Resources;

import java.time.LocalTime;

public class TrainStopStats {

    @Override
    public String toString() {
        return "TrainStopStats{" +
                "trainIdentifier=" + trainIdentifier +
                ", lastStation='" + lastStation + '\'' +
                ", alias='" + alias + '\'' +
                ", countCanceledTrains=" + countCanceledTrains +
                ", averageDelay=" + averageDelay +
                ", unpunctual=" + unpunctual +
                ", punctual=" + punctual +
                ", punctuality=" + punctuality +
                ", canceled=" + canceled +
                ", trainCount=" + trainCount +
                '}';
    }

    public TrainStopStats(IIdentifiable trainIdentifier, String lastStation, String alias, Boolean countCanceledTrains, double averageDelay, int unpunctual, int punctual, double punctuality, int canceled, int trainCount) {
        this.trainIdentifier = trainIdentifier;
        this.lastStation = lastStation;
        this.alias = alias;
        this.countCanceledTrains = countCanceledTrains;
        this.averageDelay = averageDelay;
        this.unpunctual = unpunctual;
        this.punctual = punctual;
        this.punctuality = punctuality;
        this.canceled = canceled;
        this.trainCount = trainCount;
    }

    IIdentifiable trainIdentifier;
    String lastStation;
    String alias;
    Boolean countCanceledTrains;
    double averageDelay;
    int unpunctual;
    int punctual;
    double punctuality;
    int canceled;
    int trainCount;

    public IIdentifiable getTrainIdentifier() {
        return trainIdentifier;
    }

    public String getLastStation() {
        return lastStation;
    }

    public String getAlias() {
        return alias;
    }

    public Boolean getCountCanceledTrains() {
        return countCanceledTrains;
    }

    public double getAverageDelay() {
        return averageDelay;
    }

    public int getUnpunctual() {
        return unpunctual;
    }

    public int getPunctual() {
        return punctual;
    }

    public double getPunctuality() {
        return punctuality;
    }




}
