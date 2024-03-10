package com.example.bahnify.api.Services;

import com.example.bahnify.bahnify_stats.DataInfo.DataInfo;
import com.example.bahnify.bahnify_stats.Resources.TrainOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInfoService {
    private final DataInfo dataInfo;

    @Autowired
    public DataInfoService(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }

    public List<String> getStationsResponse() {
        return dataInfo.getStations();
    }

    public List<TrainOperator> getTrainOperatorsResponse() {
        return dataInfo.getOperators();
    }
}
