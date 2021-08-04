package com.example.imdb_poc.processing;

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
    List<ImdbMapping> imdbMappingData = null;

    private ImdbMappingService imdbMappingService;

    private AthenaClientService athenaClientService;

    private ApplicationContext applicationContext;


    public ImdbDataProcess(int page, int size) {
        this.page = page;
        this.size = size;
    }


    public void run(){
        try {
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");

            imdbMappingService = (ImdbMappingService) applicationContext.getBean(ImdbMappingService.class);
            athenaClientService = (AthenaClientService) applicationContext.getBean(AthenaClientService.class);

            List<ImdbMapping> imdbMappingData = imdbMappingService.fetchMapping(this.page, this.size);
            System.out.println(imdbMappingData);
            if(imdbMappingData == null || imdbMappingData.isEmpty()) return;

            Map<String, ImdbMapping> imdbIdMapping = new HashMap<>();

            imdbMappingData.forEach(imdbData -> imdbIdMapping.put(imdbData.getImdb_title_id(), imdbData));

            String athenaQuery = "WITH castMembers AS \n" +
                    "    (SELECT u_pcm.nameId,\n" +
                    "     array_join(u_pcm.roles, ', ') as roles,\n" +
                    "     u_pcm.category as category,\n" +
                    "     NULL AS job,\n" +
                    "     tc.titleId as imdb_title_id,\n" +
                    "     tc.originalTitle as imdb_title,\n" +
                    "     tc.genres,\n" +
                    "     tc.imdbRating.rating as imdb_ratings,\n" +
                    "     tc.imdbRating.numberOfVotes as imdb_number_of_votes,\n" +
                    "     tc.remappedTo,\n" +
                    "     tc.titleType\n" +
                    "    FROM title_essential_v1 AS tc\n" +
                    "    CROSS JOIN unnest(tc.principalCastMembers) t(u_pcm)), \n" +
                    "    \n" +
                    "crewMembers AS \n" +
                    "    (SELECT u_pcm.nameId,\n" +
                    "         NULL AS roles,\n" +
                    "         u_pcm.category as category,\n" +
                    "         u_pcm.job AS job,\n" +
                    "     tc.titleId as imdb_title_id,\n" +
                    "     tc.originalTitle as imdb_title,\n" +
                    "     tc.genres,\n" +
                    "     tc.imdbRating.rating as imdb_ratings,\n" +
                    "     tc.imdbRating.numberOfVotes as imdb_number_of_votes,\n" +
                    "     tc.remappedTo,\n" +
                    "     tc.titleType\n" +
                    "    FROM title_essential_v1 AS tc\n" +
                    "    CROSS JOIN unnest(tc.principalCrewMembers) t(u_pcm)),\n" +
                    "  allMembers AS(\n" +
                    "  SELECT * FROM castMembers UNION ALL SELECT * FROM crewMembers)\n" +
                    "  SELECT pcm.imdb_title_id, pcm.imdb_title, pcm.genres, pcm.imdb_ratings, pcm.imdb_number_of_votes, pcm.nameId, pcm.roles, pcm.category, pcm.job, nc.name as member_name FROM allMembers as pcm JOIN name_essential_v1 AS nc ON pcm.nameId = nc.nameId \n"+
                    "  WHERE pcm.imdb_title_id in ('" + String.join("','", new ArrayList<>(imdbIdMapping.keySet())) + "') AND pcm.remappedTo is null \n" +
                    "  AND pcm.titleType = 'movie' ORDER BY pcm.imdb_title_id";
            System.out.println(athenaQuery);

            Map<String, ImdbPayload> athenaResult = athenaClientService.getAthenaData(athenaQuery);

            for(String imdb_id: athenaResult.keySet()){
                ImdbPayload payload = athenaResult.get(imdb_id);

                ImdbMapping imdbMapping = imdbIdMapping.get(imdb_id);

                if(payload.equals(imdbMapping.getPayload())) {
                    imdbMapping.setProcessed(0);
                }
                else {
                    imdbMapping.setProcessed(1);
                    imdbMapping.setPayload(payload);
                }
                System.out.println(imdbMapping.toString());
                imdbMappingService.save(imdbMapping);
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
