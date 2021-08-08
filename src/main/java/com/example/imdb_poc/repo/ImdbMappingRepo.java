package com.example.imdb_poc.repo;

import com.example.imdb_poc.model.ImdbMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImdbMappingRepo extends JpaRepository<ImdbMapping, Integer> {
    Page<ImdbMapping> findAll(Pageable page);
    ImdbMapping save(ImdbMapping imdbMapping);

    @Query("from ImdbMapping where imdb_title_id=:title_id or cms_content_id=:title_id")
    ImdbMapping findByImdb_title_idOrCms_content_id(@Param("title_id") String titleId);

    @Query("from ImdbMapping where processed=1")
    List<ImdbMapping> findProcessedData();
}
