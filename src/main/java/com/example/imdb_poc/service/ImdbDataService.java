package com.example.imdb_poc.service;

import com.example.imdb_poc.data.ImdbPayload;
import com.example.imdb_poc.data.Member;
import com.example.imdb_poc.model.ImdbMapping;

import java.util.List;
import java.util.Set;

public interface ImdbDataService {
    public ImdbMapping getData(String imdb_title_idOrCms_content_id);
    public double getRating(String imdb_title_idOrCms_content_id);
    public int getCmsContentId(String imdb_title_idOrCms_content_id);
    public String getImdbTitleId(String imdb_title_idOrCms_content_id);
    public long getNumberOfVotes(String imdb_title_idOrCms_content_id);
    public Set<Member> getCastMember(String imdb_title_idOrCms_content_id);
    public Set<Member> getCrewMember(String imdb_title_idOrCms_content_id);
    public ImdbPayload getPayload(String imdb_title_idOrCms_content_id);
    public List<ImdbMapping> getProcessedData();
    public ImdbMapping insertData(ImdbMapping mapping);
    public List<ImdbMapping> insertBulkData(List<ImdbMapping> mapping);
}
