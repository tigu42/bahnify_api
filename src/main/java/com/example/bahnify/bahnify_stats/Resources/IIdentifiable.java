package com.example.bahnify.bahnify_stats.Resources;

import java.time.LocalTime;

public interface IIdentifiable {
    String getTrainNum();

    String getOperator();

    String getCurrentStation();

    Direction getDirection();
    LocalTime getTime();
}

