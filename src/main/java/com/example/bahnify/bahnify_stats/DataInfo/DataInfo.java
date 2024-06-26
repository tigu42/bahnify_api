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
    private List<String> NATrainOps;
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
    public List<String> getNATrainOps() {
        if (NATrainOps == null) {

            NATrainOps = NATrainQuery.getNATrainTypes(this);
        }
        return NATrainOps;
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
