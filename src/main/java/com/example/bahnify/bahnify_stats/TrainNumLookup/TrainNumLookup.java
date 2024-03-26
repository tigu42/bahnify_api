package com.example.bahnify.bahnify_stats.TrainNumLookup;

import com.example.bahnify.GlobalDatabaseConnection;
import com.example.bahnify.bahnify_stats.DatabaseConnection;
import com.example.bahnify.bahnify_stats.Resources.IIdentifiable;
import org.springframework.stereotype.Component;

@Component
public class TrainNumLookup {
    public IIdentifiable getTrain() {
        return train;
    }

    public DatabaseConnection getDbCon() {
        return dbCon;
    }

    public void setTrain(IIdentifiable train) {
        this.train = train;
    }



    private IIdentifiable train;
    private DatabaseConnection dbCon;
    public TrainNumLookup() {
        train = null;
        this.dbCon = GlobalDatabaseConnection.globalDatabaseConnection;
    }

    public TrainNumLookup(IIdentifiable train) {
        this.train = train;
        this.dbCon = GlobalDatabaseConnection.globalDatabaseConnection;
    }

    public String getTrainNum() {
        return TrainNumLookupQuery.getTrainNum(this);
    }



}
