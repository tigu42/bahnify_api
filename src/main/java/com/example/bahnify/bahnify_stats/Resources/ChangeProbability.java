package com.example.bahnify.bahnify_stats.Resources;
/*
    all_planned_journeys
    all_connections
    missed_connecting_train
    canceled_connecting_train
    canceled_arriving_train

    */
public record ChangeProbability(int all_planned_journeys, int all_connections, int missed_connecting_train,
                                int canceled_connecting_train, int canceled_arriving_train) {

}
