package com.example.bahnify.api.Controllers;

import com.example.bahnify.api.Services.DataInfoService;
import com.example.bahnify.bahnify_stats.Resources.TrainOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("/api/data")
public class DataInfoController {

    private final DataInfoService service;
    @Autowired
    public DataInfoController(DataInfoService service) {
        this.service = service;
    }

    @GetMapping("/stations")
    public List<String> stationsEndpoint() {
        return service.getStationsResponse();
    }
    @GetMapping("/operators")
    public List<TrainOperator> trainOperatorsEndpoint() {
        return service.getTrainOperatorsResponse();
    }
    @GetMapping("/naops")
    public List<String> naOpsEndpoint() {return service.getNAOpsLookupResponse();}
}
