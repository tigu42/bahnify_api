package com.example.bahnify.api.Controllers;

import com.example.bahnify.api.Services.ConnectionProbabilityService;
import com.example.bahnify.bahnify_stats.ConnectionProbability.ConnectionProbability;
import com.example.bahnify.bahnify_stats.Resources.Direction;
import com.example.bahnify.bahnify_stats.Resources.IdentifiableStop;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/api")
public class ConnectionProbabilityController {
    private final ConnectionProbabilityService service;

    public ConnectionProbabilityController(ConnectionProbabilityService service) {
        this.service = service;
    }


    // http://192.168.178.45:8080/api/connectionProbability?station=N%C3%BCrnberg%20Hbf&trainNumArr=929&opArr=ICE&timeArr=22:28&trainNumDep=3418&opDep=RE&timeDep=22:37
    // http://192.168.178.45:8080/api/connectionProbability?station=N%C3%BCrnberg%20Hbf&trainNumArr=929&opArr=ICE&timeArr=22:28&trainNumDep=3418&opDep=RE&timeDep=22
    @GetMapping("/connectionProbability")
    public ConnectionProbability.ConnectionProbabilityResult ConnectionProbabilityEndpoint(
            @RequestParam(value = "station") String station,
            @RequestParam(value = "trainNumArr") String trainNumArr,
            @RequestParam(value = "opArr") String operatorArr,
            @RequestParam(value = "timeArr") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime timeArr,
            @RequestParam(value = "trainNumDep") String trainNumDep,
            @RequestParam(value = "opDep") String operatorDep,
            @RequestParam(value = "timeDep") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime timeDep) {
        
        IdentifiableStop arrivingTrain = new IdentifiableStop(trainNumArr, operatorArr, station, Direction.arriving, timeArr);
        IdentifiableStop departingTrain = new IdentifiableStop(trainNumDep, operatorDep, station, Direction.departing, timeDep);
        return service.getResponse(arrivingTrain, departingTrain);
    }
    @GetMapping("/extension/connectionProbability")
    public ConnectionProbability.ConnectionProbabilityResult ConnectionProbabilityExtensionEndpoint(
            @RequestParam(value = "station") String station,
            @RequestParam(value = "trainNumArr") String trainNumArr,
            @RequestParam(value = "opArr") String operatorArr,
            @RequestParam(value = "timeArr") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime timeArr,
            @RequestParam(value = "trainNumDep") String trainNumDep,
            @RequestParam(value = "opDep") String operatorDep,
            @RequestParam(value = "timeDep") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime timeDep) {

        IdentifiableStop arrivingTrain = new IdentifiableStop(trainNumArr, operatorArr, station, Direction.arriving, timeArr);
        IdentifiableStop departingTrain = new IdentifiableStop(trainNumDep, operatorDep, station, Direction.departing, timeDep);
        return service.getResponseFromAlias(arrivingTrain, departingTrain);
    }
}
