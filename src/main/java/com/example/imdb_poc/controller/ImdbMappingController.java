package com.example.imdb_poc.controller;

import com.example.imdb_poc.data.ImdbPayload;
import com.example.imdb_poc.data.Member;
import com.example.imdb_poc.model.ImdbMapping;
import com.example.imdb_poc.process.ImdbDataProcess;
import com.example.imdb_poc.service.ImdbDataService;
import com.example.imdb_poc.service.ImdbMappingService;
import com.example.imdb_poc.constant.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/imdb")
public class ImdbMappingController {

    @Autowired
    private ImdbMappingService imdbMappingService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ImdbDataService imdbDataService;

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

    @GetMapping("/{imdb_title_idOrCms_content_id}")
    public ImdbMapping getData(@PathVariable String imdb_title_idOrCms_content_id){
        return imdbDataService.getData(imdb_title_idOrCms_content_id);
    }

    @GetMapping("/{imdb_title_idOrCms_content_id}/get_rating")
    public Double getRating(@PathVariable String imdb_title_idOrCms_content_id) {
        return imdbDataService.getRating(imdb_title_idOrCms_content_id);
    }

    @GetMapping("/{imdb_title_idOrCms_content_id}/get_number_of_votes")
    public Long getNumberOfVotes(@PathVariable String imdb_title_idOrCms_content_id) {
        return imdbDataService.getNumberOfVotes(imdb_title_idOrCms_content_id);
    }

    @GetMapping("/{imdb_title_idOrCms_content_id}/get_cms_content_id")
    public Integer getCmsContentId(@PathVariable String imdb_title_idOrCms_content_id) {
        return imdbDataService.getCmsContentId(imdb_title_idOrCms_content_id);
    }

    @GetMapping("/{imdb_title_idOrCms_content_id}/get_imdb_title_id")
    public String getImdbTitleId(@PathVariable String imdb_title_idOrCms_content_id){
        return imdbDataService.getImdbTitleId(imdb_title_idOrCms_content_id);
    }

    @GetMapping("/{imdb_title_idOrCms_content_id}/get_cast_member")
    public Set<Member> getCastMember(@PathVariable String imdb_title_idOrCms_content_id) {
        return imdbDataService.getCastMember(imdb_title_idOrCms_content_id);
    }

    @GetMapping("/{imdb_title_idOrCms_content_id}/get_crew_member")
    public Set<Member> getCrewMember(@PathVariable String imdb_title_idOrCms_content_id) {
        return imdbDataService.getCrewMember(imdb_title_idOrCms_content_id);
    }

    @GetMapping("/{imdb_title_idOrCms_content_id}/get_payload")
    public ImdbPayload getPayload(@PathVariable String imdb_title_idOrCms_content_id) {
        return imdbDataService.getPayload(imdb_title_idOrCms_content_id);
    }

    @GetMapping("/get_processed_data")
    public List<ImdbMapping> getProcessedData() {
        return imdbDataService.getProcessedData();
    }

    @PostMapping("/insert_data")
    public ImdbMapping insertData(@RequestBody ImdbMapping mapping) {
        return imdbDataService.insertData(mapping);
    }

    @PostMapping("/insert_bulk_data")
    public List<ImdbMapping> insertBulkData(@RequestBody List<ImdbMapping> mappings) {
        return imdbDataService.insertBulkData(mappings);
    }
}
