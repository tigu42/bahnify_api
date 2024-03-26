package com.example.bahnify.api.Services;

import com.example.bahnify.bahnify_stats.ConnectionProbability.ConnectionProbability;
import com.example.bahnify.bahnify_stats.DataInfo.DataInfo;
import com.example.bahnify.bahnify_stats.Resources.ChangeProbability;
import com.example.bahnify.bahnify_stats.Resources.IIdentifiable;
import com.example.bahnify.bahnify_stats.TrainNumLookup.TrainNumLookup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConnectionProbabilityService {
    private final ConnectionProbability connectionProbability;
    private final TrainNumLookup trainNumLookup;
    private final DataInfo dataInfo;

    public ConnectionProbabilityService(ConnectionProbability connectionProbability, TrainNumLookup trainNumLookup, DataInfo dataInfo) {
        this.connectionProbability = connectionProbability;
        this.trainNumLookup = trainNumLookup;
        this.dataInfo = dataInfo;
    }

    public ConnectionProbability.ConnectionProbabilityResult getResponse(IIdentifiable arriving, IIdentifiable departing) {
        connectionProbability.setArrivingTrain(arriving);
        connectionProbability.setDepartingTrain(departing);
        return connectionProbability.generateResult();
    }

    public ConnectionProbability.ConnectionProbabilityResult getResponseFromAlias(IIdentifiable arriving, IIdentifiable departing) {
        List<String> naOps = dataInfo.getNATrainOps();

        TrainNumLookup tnm = new TrainNumLookup();
        ConnectionProbability cp = new ConnectionProbability();

        String opArr = arriving.getOperator();
        if (!naOps.contains(opArr)) {
            tnm.setTrain(arriving);
            arriving.setTrainNum(tnm.getTrainNum());
        }

        String opDep = departing.getOperator();
        if (!naOps.contains(opDep)) {
            tnm.setTrain(departing);
            departing.setTrainNum(tnm.getTrainNum());
        }

        cp.setArrivingTrain(arriving);
        cp.setDepartingTrain(departing);
        return cp.generateResult();
    }
}
