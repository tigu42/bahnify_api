package com.example.bahnify.bahnify_stats.DataInfo;

import com.example.bahnify.GlobalDatabaseConnection;
import com.example.bahnify.bahnify_stats.DatabaseConnection;
import com.example.bahnify.bahnify_stats.Resources.TrainOperator;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class DataInfo {
    private List<String> stations;
    private List<TrainOperator> operators;
    private final DatabaseConnection dbCon;

    public DataInfo() {
        dbCon = GlobalDatabaseConnection.globalDatabaseConnection;
    }

    public List<String> getStations() {
        if (stations == null) {

            stations = StationsQuery.getStations(this);
        }
        return stations;
    }

    public void Test() {
        for (String s: getStations()){
            System.out.println(s);
        }
    }

    public void Test2() {
        for (TrainOperator t: getOperators()) {
            System.out.println(t);
        }
    }

    public List<TrainOperator> getOperators() {

        if (operators == null) {
            operators = TrainOperatorQuery.getTrainOperators(this);
        }
        return operators;
    }

    public DatabaseConnection getDbCon () {
        return this.dbCon;
    }
}
