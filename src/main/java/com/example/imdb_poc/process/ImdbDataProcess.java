package com.example.imdb_poc.process;

import com.example.imdb_poc.constant.AthenaQueries;
import com.example.imdb_poc.data.ImdbPayload;
import com.example.imdb_poc.model.ImdbMapping;
import com.example.imdb_poc.service.AthenaClientService;
import com.example.imdb_poc.service.ImdbMappingService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImdbDataProcess implements Runnable, ApplicationContextAware {

    int page = 0;
    int size = 2;

    private ImdbMappingService imdbMappingService;
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

            imdbMappingService = applicationContext.getBean(ImdbMappingService.class);
            athenaClientService = applicationContext.getBean(AthenaClientService.class);

            List<ImdbMapping> imdbMappingData = imdbMappingService.fetchMapping(this.page, this.size);
            if(imdbMappingData == null || imdbMappingData.isEmpty()) return;

            // Mapping imdb_id and IMDBMapping Object
            Map<String, ImdbMapping> imdbIdToImdbMapping = new HashMap<>();

            imdbMappingData.forEach(imdbData -> imdbIdToImdbMapping.put(imdbData.getImdb_title_id(), imdbData));

            // Generating Athena query with query as Where imdb_id in ('', '')
            String athenaQuery = String.format(AthenaQueries.athenaFetchImdbPayload, "'" + String.join("','", new ArrayList<>(imdbIdToImdbMapping.keySet())) + "'");

            Map<String, ImdbPayload> athenaResult = athenaClientService.getAthenaData(athenaQuery);

            // Iterating to every key in map(athena result)
            for(String imdb_id: athenaResult.keySet()){
                ImdbMapping imdbMapping = imdbIdToImdbMapping.get(imdb_id);

                ImdbPayload athenaImdbPayload = athenaResult.get(imdb_id);

                if (!athenaImdbPayload.equals(imdbMapping.getPayload())) {
                    imdbMapping.setProcessed(1);
                    imdbMapping.setPayload(athenaImdbPayload);
                    imdbMappingService.save(imdbMapping);
                }
            }
        }catch(NullPointerException p){
            p.printStackTrace();
        }catch (Exception e) {
            System.out.println("Exception is caught");
            e.getStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
