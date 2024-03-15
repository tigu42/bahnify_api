package com.example.bahnify.bahnify_stats.ConnectionProbability;

import com.example.bahnify.GlobalDatabaseConnection;
import com.example.bahnify.bahnify_stats.DatabaseConnection;
import com.example.bahnify.bahnify_stats.Resources.ChangeProbability;
import com.example.bahnify.bahnify_stats.Resources.IIdentifiable;
import com.example.bahnify.bahnify_stats.Resources.IdentifiableStop;
import com.example.bahnify.bahnify_stats.Resources.TrainStopStats;
import org.springframework.stereotype.Component;

@Component
public class ConnectionProbability {

    public record ConnectionProbabilityResult(TrainStopStats arriving, TrainStopStats departing, ChangeProbability summary) {

    }
    public IIdentifiable getArrivingTrain() {
        return arrivingTrain;
    }

    public IIdentifiable getDepartingTrain() {
        return departingTrain;
    }

    public void setArrivingTrain(IIdentifiable arrivingTrain) {
        this.arrivingTrain = arrivingTrain;
    }
    public void setDepartingTrain(IIdentifiable departingTrain) {
        this.departingTrain = departingTrain;
    }

    public ConnectionProbabilityResult generateResult() {
        return new  ConnectionProbabilityResult(ConnectionProbabilityQuery.getStats(getArrivingTrain(), dbCon),
                                                ConnectionProbabilityQuery.getStats(getDepartingTrain(), dbCon),
                                                ConnectionProbabilityQuery.getChangeProbability(getArrivingTrain(), getDepartingTrain(), dbCon));
    }

    IIdentifiable arrivingTrain;
    IIdentifiable departingTrain;
    DatabaseConnection dbCon;
    public ConnectionProbability(IIdentifiable arrivingTrain, IIdentifiable departingTrain) {
        this.arrivingTrain = arrivingTrain;
        this.departingTrain = departingTrain;
        this.dbCon = GlobalDatabaseConnection.globalDatabaseConnection;
    }

    public ConnectionProbability() {
        this.arrivingTrain = null;
        this.departingTrain = null;
        this.dbCon = GlobalDatabaseConnection.globalDatabaseConnection;
    }



}
