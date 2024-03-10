package com.example.bahnify.bahnify_stats.HourlyDelays;


import com.example.bahnify.GlobalDatabaseConnection;
import com.example.bahnify.bahnify_stats.DatabaseConnection;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Component
public class HourlyDelays {
    public HourlyDelaysQuery.QueryResult[] getHourlyDelays() {
        if (needsRefresh) {
            this.hourlyDelays = HourlyDelaysQuery.getHourlyDelays(this);
            this.needsRefresh = false;
        }
        return this.hourlyDelays;
    }
    public String getStation() {
        return station;
    }
    public String[] getRequestedOperators() {
        return requestedOperators;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public String getOperatorType() {return operatorType;}
    public Boolean hasStats() {
        return !needsRefresh;
    }
    public DatabaseConnection getDbCon() { return this.dbCon; }
    public void setStation(String station) {
        if (this.station == null && station == null) return;

        if (this.station != null && Objects.equals(this.station, station)) return;
        this.needsRefresh = true;
        this.station = station;
    }
    public void setRequestedOperators(String[] requestedOperators) {
        if (this.requestedOperators == null && requestedOperators == null) return;

        if (this.requestedOperators != null && Arrays.equals(this.requestedOperators, requestedOperators)) return;
        this.needsRefresh = true;
        this.requestedOperators = requestedOperators;
    }

    public void setOperatorType(String operatorType) {
        if (this.operatorType == null && operatorType == null) return;
        if (this.operatorType != null && Objects.equals(this.operatorType, operatorType)) return;
        this.needsRefresh = true;
        this.operatorType = operatorType;
    }
    public void setTime(LocalDateTime startTime, LocalDateTime endTime) {

        System.out.println(startTime);
        System.out.println(this.startTime);
        if (startTime == null && this.startTime == null) return;
        if (this.startTime != null && startTime == null)
        {
            this.needsRefresh = true;
            System.out.println("fuck");

            this.startTime = startTime;
            this.endTime = endTime;
            return;
        }

        if (this.startTime != null && this.endTime != null && (this.startTime.isEqual(startTime) && this.endTime.isEqual(endTime))) return;
        try {

            this.needsRefresh = true;
            System.out.println("fuck");

            this.startTime = startTime;
            this.endTime = endTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String station;
    private String[] requestedOperators;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String operatorType;
    private HourlyDelaysQuery.QueryResult[] hourlyDelays;
    private DatabaseConnection dbCon;
    private Boolean needsRefresh;

    public HourlyDelays()
    {
        this.station = null;
        this.requestedOperators = null;
        this.startTime = null;
        this.endTime = null;
        this.operatorType = null;
        this.hourlyDelays = new HourlyDelaysQuery.QueryResult[24];
        this.dbCon = GlobalDatabaseConnection.globalDatabaseConnection;
        this.needsRefresh = true;
    }

    public String toString() {
        if (!hasStats()) return "Stat not available yet";
        StringBuilder ret = new StringBuilder();
        for (HourlyDelaysQuery.QueryResult res: hourlyDelays) {
            ret.append(res).append("\n");
        }
        return ret.toString();
    }

}
