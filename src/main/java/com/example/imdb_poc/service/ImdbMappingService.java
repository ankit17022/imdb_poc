package com.example.imdb_poc.service;

import com.example.imdb_poc.model.ImdbMapping;

import java.util.List;

public interface ImdbMappingService {
    long totalCount();
    List<ImdbMapping> fetchMapping();
    List<ImdbMapping> fetchMapping(int pageNo, int size);
    void save(ImdbMapping imdbMapping);
}
