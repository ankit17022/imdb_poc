package com.example.imdb_poc.controllers;

import com.amazonaws.services.opsworks.model.App;
import com.example.imdb_poc.data.ImdbPayload;
import com.example.imdb_poc.model.ImdbMapping;
import com.example.imdb_poc.processing.ImdbDataProcess;
import com.example.imdb_poc.services.AthenaClientService;
import com.example.imdb_poc.services.ImdbMappingService;
import com.example.imdb_poc.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/imdb")
public class ImdbMappingController {

    @Autowired
    private ImdbMappingService imdbMappingService;

    @Autowired
    private AthenaClientService athenaClientService;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("")
    public List<ImdbMapping> fetchMapping(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "1") int size) {
        List<ImdbMapping> result = imdbMappingService.fetchMapping(page, size);
        return result;
    }

    @GetMapping("/set")
    public String set() {
        ImdbPayload imdb = new ImdbPayload(2, 3, new ArrayList<>(), new ArrayList<>());
        ImdbMapping imdbMapping = new ImdbMapping(55, "t123", 123, 1, 1, imdb);
        imdbMappingService.save(imdbMapping);
        return "Done";
    }

    @GetMapping("/start_fetcher")
    public String startFetcher() {
        long totalRows = imdbMappingService.totalCount();
        int numberOfThreads = (int) Math.ceil((double)totalRows / AppConstants.fetchSize);
        for (int i = 0; i < numberOfThreads; i++) {
//            ImdbDataProcess imdbDataProcess = new ImdbDataProcess(i, AppConstants.fetchSize, applicationContexttext);
//            imdbDataProcess.setApplicationContext(applicationContext);
//            Thread threadedProcess = new Thread(imdbDataProcess);
//            threadedProcess.start();
            ExecutorService es = Executors.newCachedThreadPool();
            es.execute(new ImdbDataProcess(i, AppConstants.fetchSize));
        }


//        List<ImdbMapping> imdbMappingData = imdbMappingService.fetchMapping();
//        System.out.println(imdbMappingData);
////        if(imdbMappingData == null || imdbMappingData.isEmpty()) return "";
//
//        Map<String, ImdbMapping> imdbIdMapping = new HashMap<>();
//
//        imdbMappingData.forEach(imdbData -> imdbIdMapping.put(imdbData.getImdb_title_id(), imdbData));
//
//        String athenaQuery = "WITH castMembers AS \n" +
//                "    (SELECT u_pcm.nameId,\n" +
//                "     array_join(u_pcm.roles, ', ') as roles,\n" +
//                "     u_pcm.category as category,\n" +
//                "     NULL AS job,\n" +
//                "     tc.titleId as imdb_title_id,\n" +
//                "     tc.originalTitle as imdb_title,\n" +
//                "     tc.genres,\n" +
//                "     tc.imdbRating.rating as imdb_ratings,\n" +
//                "     tc.imdbRating.numberOfVotes as imdb_number_of_votes,\n" +
//                "     tc.remappedTo,\n" +
//                "     tc.titleType\n" +
//                "    FROM title_essential_v1 AS tc\n" +
//                "    CROSS JOIN unnest(tc.principalCastMembers) t(u_pcm)), \n" +
//                "    \n" +
//                "crewMembers AS \n" +
//                "    (SELECT u_pcm.nameId,\n" +
//                "         NULL AS roles,\n" +
//                "         u_pcm.category as category,\n" +
//                "         u_pcm.job AS job,\n" +
//                "     tc.titleId as imdb_title_id,\n" +
//                "     tc.originalTitle as imdb_title,\n" +
//                "     tc.genres,\n" +
//                "     tc.imdbRating.rating as imdb_ratings,\n" +
//                "     tc.imdbRating.numberOfVotes as imdb_number_of_votes,\n" +
//                "     tc.remappedTo,\n" +
//                "     tc.titleType\n" +
//                "    FROM title_essential_v1 AS tc\n" +
//                "    CROSS JOIN unnest(tc.principalCrewMembers) t(u_pcm)),\n" +
//                "  allMembers AS(\n" +
//                "  SELECT * FROM castMembers UNION ALL SELECT * FROM crewMembers)\n" +
//                "  SELECT pcm.imdb_title_id, pcm.imdb_title, pcm.genres, pcm.imdb_ratings, pcm.imdb_number_of_votes, pcm.nameId, pcm.roles, pcm.category, pcm.job, nc.name as member_name FROM allMembers as pcm JOIN name_essential_v1 AS nc ON pcm.nameId = nc.nameId \n" +
//                "  WHERE pcm.imdb_title_id in ('" + String.join("','", new ArrayList<>(imdbIdMapping.keySet())) + "') AND pcm.remappedTo is null \n" +
//                "  AND pcm.titleType = 'movie' ORDER BY pcm.imdb_title_id";
//        System.out.println(athenaQuery);
//
//        Map<String, ImdbPayload> athenaResult = athenaClientService.getAthenaData(athenaQuery);
//
//        for (String imdb_id : athenaResult.keySet()) {
//            ImdbPayload payload = athenaResult.get(imdb_id);
//
//            ImdbMapping imdbMapping = imdbIdMapping.get(imdb_id);
//
//            if (payload.equals(imdbMapping.getPayload()) && imdbMapping.getProcessed() != 0) {
//                imdbMapping.setProcessed(0);
//            } else {
//                imdbMapping.setProcessed(1);
//                imdbMapping.setPayload(payload);
//            }
//            System.out.println(imdbMapping.toString());
//            imdbMappingService.save(imdbMapping);
//
//
//        }
        return "Started";

    }
}
