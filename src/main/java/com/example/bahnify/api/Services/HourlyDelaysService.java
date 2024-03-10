package com.example.bahnify.api.Services;

import com.example.bahnify.bahnify_stats.HourlyDelays.HourlyDelays;
import com.example.bahnify.bahnify_stats.HourlyDelays.HourlyDelaysQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HourlyDelaysService {

    // HourlyDelays stat = null;

    private final HourlyDelays stat;

    @Autowired
    public HourlyDelaysService(HourlyDelays stat) {
        this.stat = stat;
    }
    public HourlyDelaysQuery.QueryResult[] getResponse(String[] opList, String station,
                                                       LocalDateTime start, LocalDateTime end, String operatorType) {
        // if (stat == null)  stat = new HourlyDelays();
        stat.setRequestedOperators(opList);
        stat.setStation(station);
        stat.setTime(start, end);
        stat.setOperatorType(operatorType);
        return stat.getHourlyDelays();
    }
}
