package com.example.imdb_poc.controller;

import com.example.imdb_poc.data.ImdbPayload;
import com.example.imdb_poc.model.ImdbMapping;
import com.example.imdb_poc.process.ImdbDataProcess;
import com.example.imdb_poc.service.AthenaClientService;
import com.example.imdb_poc.service.ImdbMappingService;
import com.example.imdb_poc.constant.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/imdb")
public class ImdbMappingController {

    @Autowired
    private ImdbMappingService imdbMappingService;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("")
    public List<ImdbMapping> fetchMapping(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "1") int size) {
        List<ImdbMapping> result = imdbMappingService.fetchMapping(page, size);
        return result;
    }

    @GetMapping("/all")
    public List<ImdbMapping> fetchAllMapping() {
        List<ImdbMapping> result = imdbMappingService.fetchMapping();
        return result;
    }

//    @GetMapping("/set")
//    public String set() {
//        ImdbPayload imdb = new ImdbPayload(2, 3, new ArrayList<>(), new ArrayList<>());
//        ImdbMapping imdbMapping = new ImdbMapping(55, "t123", 123, 1, 1, imdb);
//        imdbMappingService.save(imdbMapping);
//        return "Done";
//    }

    @GetMapping("/start_fetcher")
    public String startFetcher() {
        long totalRows = imdbMappingService.totalCount();
        int numberOfThreads = (int) Math.ceil((double)totalRows / AppConstants.fetchSize);
        for (int i = 0; i < numberOfThreads; i++) {
            ImdbDataProcess imdbDataProcess = new ImdbDataProcess(i, AppConstants.fetchSize, applicationContext);
            Thread threadedProcess = new Thread(imdbDataProcess);
            threadedProcess.start();
        }
        return "Started";
    }
}
