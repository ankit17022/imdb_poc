package com.example.imdb_poc.processing;

import com.example.imdb_poc.constants.AthenaQueries;
import com.example.imdb_poc.data.ImdbPayload;
import com.example.imdb_poc.model.ImdbMapping;
import com.example.imdb_poc.services.AthenaClientService;
import com.example.imdb_poc.services.ImdbMappingService;
import com.example.imdb_poc.services.ImdbMappingServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImdbDataProcess implements Runnable, ApplicationContextAware {

    int page = 0;
    int size = 2;

    @Autowired
    private ImdbMappingService imdbMappingService;

    @Autowired
    private AthenaClientService athenaClientService;

    private ApplicationContext applicationContext;


    public ImdbDataProcess(int page, int size, ApplicationContext applicationContext) {
        this.page = page;
        this.size = size;
        this.applicationContext = applicationContext;
    }


    public void run(){
        try {
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");

            imdbMappingService = (ImdbMappingService) applicationContext.getBean(ImdbMappingService.class);
            athenaClientService = (AthenaClientService) applicationContext.getBean(AthenaClientService.class);

            List<ImdbMapping> imdbMappingData = imdbMappingService.fetchMapping(this.page, this.size);
            System.out.println(imdbMappingData);
            if(imdbMappingData == null || imdbMappingData.isEmpty()) return;

            Map<String, ImdbMapping> imdbIdToImdbMapping = new HashMap<>();

            imdbMappingData.forEach(imdbData -> imdbIdToImdbMapping.put(imdbData.getImdb_title_id(), imdbData));

            String athenaQuery = String.format(AthenaQueries.athenaFetchImdbPayload, "'" + String.join("','", new ArrayList<>(imdbIdToImdbMapping.keySet())) + "'");
//            System.out.println(athenaQuery);

            Map<String, ImdbPayload> athenaResult = athenaClientService.getAthenaData(athenaQuery);

            for(String imdb_id: athenaResult.keySet()){
                ImdbPayload payload = athenaResult.get(imdb_id);

                ImdbMapping imdbMapping = imdbIdToImdbMapping.get(imdb_id);

                if (!payload.equals(imdbMapping.getPayload())) {
                    imdbMapping.setProcessed(1);
                    imdbMapping.setPayload(payload);
                    System.out.println(imdbMapping);
                    imdbMappingService.save(imdbMapping);
                }
            }
        }catch(NullPointerException p){
            p.printStackTrace();
        }catch (Exception e) {
            System.out.println("Exception is caught");
            System.out.println(e.toString());
            e.getStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println(applicationContext);
    }
}
