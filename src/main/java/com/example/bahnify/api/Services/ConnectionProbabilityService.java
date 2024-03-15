package com.example.bahnify.api.Services;

import com.example.bahnify.bahnify_stats.ConnectionProbability.ConnectionProbability;
import com.example.bahnify.bahnify_stats.Resources.IIdentifiable;
import org.springframework.stereotype.Component;

@Component
public class ConnectionProbabilityService {
    private final ConnectionProbability connectionProbability;


    public ConnectionProbabilityService(ConnectionProbability connectionProbability) {
        this.connectionProbability = connectionProbability;
    }

    public ConnectionProbability.ConnectionProbabilityResult getResponse(IIdentifiable arriving, IIdentifiable departing) {
        connectionProbability.setArrivingTrain(arriving);
        connectionProbability.setDepartingTrain(departing);
        return connectionProbability.generateResult();
    }
}
