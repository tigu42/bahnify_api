package com.example.bahnify.bahnify_stats.PunctualityByTrainType;


import com.example.bahnify.GlobalDatabaseConnection;
import com.example.bahnify.bahnify_stats.DatabaseConnection;
// BROKEN: TODO: FIX needsRefresh problems

public class PunctualityByTrainType {
    public void setStation(String station) {
        if (this.station != null && this.station.equals(station)) return;
        this.needsRefresh = true;
        this.station = station;
    }

    public void setMaxDelay(int maxDelay) {
        if (this.maxDelay == maxDelay) return;
        this.needsRefresh = true;
        this.maxDelay = maxDelay;
    }

    public Boolean hasStats() {
        return !this.needsRefresh;
    }

    public String getStation() {
        return station;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public DatabaseConnection getDbCon() {
        return dbCon;
    }

    public PunctualityByTrainTypeQuery.QueryResult[] getPunctualityByTrainType() {
        if (needsRefresh) {
            this.punctualityByTrainType = PunctualityByTrainTypeQuery.getPunctualityByTrainType(this);
            this.needsRefresh = false;
        }
        return this.punctualityByTrainType;
    }

    public void setCountCanceledTrains(Boolean countCanceledTrains) {
        if (this.countCanceledTrains == countCanceledTrains) return;
        needsRefresh = true;
        this.countCanceledTrains = countCanceledTrains;
    }

    public Boolean getCountCanceledTrains() {
        return countCanceledTrains;
    }
    private Boolean countCanceledTrains;
    private String station;
    private int maxDelay;
    private DatabaseConnection dbCon;
    private Boolean needsRefresh;
    private PunctualityByTrainTypeQuery.QueryResult[] punctualityByTrainType;
    public PunctualityByTrainType() {
        this.station = null;
        this.maxDelay = 6;
        this.dbCon = GlobalDatabaseConnection.globalDatabaseConnection;
        this.needsRefresh = true;
        this.countCanceledTrains = false;
    }

    public String toString() {
        if (!hasStats()) return "Stat is not available yet";
        StringBuilder ret = new StringBuilder();
        String head = "type\t\tpunctuality\t\tavgDelay\t\tmaxDelay\t\tcount\n";
        ret.append(head).append("\n");
        for (PunctualityByTrainTypeQuery.QueryResult res: punctualityByTrainType) {
            ret.append(res).append("\n");
        }
        return ret.toString();
    }
}
