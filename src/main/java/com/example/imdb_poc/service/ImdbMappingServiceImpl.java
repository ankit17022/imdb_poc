package com.example.imdb_poc.service;

import com.example.imdb_poc.model.ImdbMapping;
import com.example.imdb_poc.repo.ImdbMappingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImdbMappingServiceImpl implements ImdbMappingService {


    @Autowired
    private ImdbMappingRepo imdbMappingRepo;

    @Override
    public long totalCount() {
        return imdbMappingRepo.count();
    }

    @Override
    public List<ImdbMapping> fetchMapping() {
        List<ImdbMapping> result = imdbMappingRepo.findAll();
        System.out.println(result);
        return result;
    }

    @Override
    public List<ImdbMapping> fetchMapping(int pageNo, int size) {
        Pageable paging = PageRequest.of(pageNo, size);
        Page<ImdbMapping> result = imdbMappingRepo.findAll(paging);

        if(result.hasContent())
            return result.getContent();
        else
            return new ArrayList<>();

    }

    @Override
    public void save(ImdbMapping imdbMapping) {
        imdbMappingRepo.save(imdbMapping);
    }
}
