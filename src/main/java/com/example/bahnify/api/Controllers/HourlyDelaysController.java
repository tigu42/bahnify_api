package com.example.bahnify.api.Controllers;

import com.example.bahnify.api.Services.HourlyDelaysService;
import com.example.bahnify.bahnify_stats.HourlyDelays.HourlyDelaysQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


// @CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://192.168.178.45:3000", "http://localhost:3000"})
public class HourlyDelaysController {

    private final HourlyDelaysService service;

    @Autowired
    public HourlyDelaysController(HourlyDelaysService service) {
        this.service = service;
    }

    // http://localhost:8080/api/hourlyDelays?station=N%C3%BCrnberg%20Hbf&start=2024-02-02T00:00:00&end=2024-02-05T23:59:59&opList=RB,RE,S
    //
    @GetMapping("/hourlyDelays")
    public HourlyDelaysQuery.QueryResult[] hourlyDelaysEndpoint(@RequestParam(value = "start", required = false)
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                LocalDateTime start,

                                                                @RequestParam(value = "end", required = false)
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                LocalDateTime end,

                                                                @RequestParam(value = "opList", required = false) String[] opList,

                                                                @RequestParam(value = "station", required = false) String station,
                                                                @RequestParam(value = "opType", required = false) String opType
                                                                    ) {

        return service.getResponse(opList, station, start, end, opType);
    }


}
