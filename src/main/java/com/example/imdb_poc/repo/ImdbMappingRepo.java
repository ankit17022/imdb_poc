package com.example.imdb_poc.repo;

import com.example.imdb_poc.model.ImdbMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImdbMappingRepo extends JpaRepository<ImdbMapping, Integer> {
    Page<ImdbMapping> findAll(Pageable page);
    ImdbMapping save(ImdbMapping imdbMapping);
}
