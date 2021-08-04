package com.example.imdb_poc.services;

import com.example.imdb_poc.model.ImdbMapping;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ImdbMappingService {
    List<ImdbMapping> fetchMapping(int pageNo, int size);
    List<ImdbMapping> fetchMapping();
    void save(ImdbMapping imdbMapping);
    long totalCount();
}
