package com.example.imdb_poc.service;

import com.example.imdb_poc.data.ImdbPayload;
import com.example.imdb_poc.data.Member;
import com.example.imdb_poc.model.ImdbMapping;
import com.example.imdb_poc.repo.ImdbMappingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ImdbDataServiceImpl implements ImdbDataService {

    @Autowired
    private ImdbMappingRepo imdbMappingRepo;

    @Override
    public ImdbMapping getData(String imdb_title_idOrCms_content_id){
        return imdbMappingRepo.findByImdb_title_idOrCms_content_id(imdb_title_idOrCms_content_id);
    }

    @Override
    public double getRating(String imdb_title_idOrCms_content_id) {
        return imdbMappingRepo.findByImdb_title_idOrCms_content_id(imdb_title_idOrCms_content_id).getPayload().getRatings();
    }

    @Override
    public int getCmsContentId(String imdb_title_idOrCms_content_id) {
        return imdbMappingRepo.findByImdb_title_idOrCms_content_id(imdb_title_idOrCms_content_id).getCms_content_id();
    }

    @Override
    public String getImdbTitleId(String imdb_title_idOrCms_content_id) {
        return imdbMappingRepo.findByImdb_title_idOrCms_content_id(imdb_title_idOrCms_content_id).getImdb_title_id();
    }

    @Override
    public long getNumberOfVotes(String imdb_title_idOrCms_content_id) {
        return imdbMappingRepo.findByImdb_title_idOrCms_content_id(imdb_title_idOrCms_content_id).getPayload().getNumber_of_votes();
    }

    @Override
    public Set<Member> getCastMember(String imdb_title_idOrCms_content_id) {
        return imdbMappingRepo.findByImdb_title_idOrCms_content_id(imdb_title_idOrCms_content_id).getPayload().getCast_members();
    }

    @Override
    public Set<Member> getCrewMember(String imdb_title_idOrCms_content_id) {
        return imdbMappingRepo.findByImdb_title_idOrCms_content_id(imdb_title_idOrCms_content_id).getPayload().getCrew_members();
    }

    @Override
    public ImdbPayload getPayload(String imdb_title_idOrCms_content_id) {
        return imdbMappingRepo.findByImdb_title_idOrCms_content_id(imdb_title_idOrCms_content_id).getPayload();
    }

    @Override
    public List<ImdbMapping> getProcessedData() {
        return imdbMappingRepo.findProcessedData();
    }

    @Override
    public ImdbMapping insertData(ImdbMapping mapping) {
        return imdbMappingRepo.save(mapping);
    }

    @Override
    public List<ImdbMapping> insertBulkData(List<ImdbMapping> mapping) {
        return imdbMappingRepo.saveAll(mapping);
    }

}
