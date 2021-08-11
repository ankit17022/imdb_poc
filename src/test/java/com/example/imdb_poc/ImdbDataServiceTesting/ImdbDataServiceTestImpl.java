package com.example.imdb_poc.ImdbDataServiceTesting;

import com.example.imdb_poc.data.ImdbPayload;
import com.example.imdb_poc.data.Member;
import com.example.imdb_poc.model.ImdbMapping;
import com.example.imdb_poc.repo.ImdbMappingRepo;
import com.example.imdb_poc.service.ImdbDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ImdbDataServiceTestImpl implements ImdbDataServiceTest{

    @Autowired
    private ImdbDataService imdbDataService;

    @MockBean
    private ImdbMappingRepo imdbMappingRepo;

    protected ImdbMapping getExampleImdbMapping(int Id) {
        ImdbMapping imdbMapping = new ImdbMapping();
        imdbMapping.setId(Id);
        imdbMapping.setImdb_title_id("example imdb id");
        imdbMapping.setCms_content_id(1100);
        imdbMapping.setScore(1);
        imdbMapping.setProcessed(1);

        ImdbPayload payload = new ImdbPayload();
        payload.setRatings(9.1);
        payload.setNumber_of_votes(10);

        Member castMember = new Member();
        castMember.setMember_id("cast example id");
        castMember.setMember_name("cast example name");
        castMember.setCategory("cast example category");
        castMember.setJob("cast example job");
        castMember.setRole("cast example role");

        Member crewMember = new Member();
        crewMember.setMember_id("crew example id");
        crewMember.setMember_name("crew example name");
        crewMember.setCategory("crew example category");
        crewMember.setJob("crew example job");
        crewMember.setRole("crew example role");

        payload.addCastMember(castMember);
        payload.addCrewMember(crewMember);

        imdbMapping.setPayload(payload);

        return imdbMapping;
    }

    @Test
    @Override
    public void testInsertData() {
        ImdbMapping imdbMapping = getExampleImdbMapping(0);

        Mockito.when(imdbMappingRepo.save(imdbMapping)).thenReturn(imdbMapping);
        assertThat(imdbDataService.insertData(imdbMapping)).isEqualTo(imdbMapping);
    }

    @Test
    @Override
    public void testInsertBulkData() {
        ImdbMapping imdbMapping1 = getExampleImdbMapping(0);
        ImdbMapping imdbMapping2 = getExampleImdbMapping(1);

        List<ImdbMapping> mappings = new ArrayList<>();
        mappings.add(imdbMapping1);
        mappings.add(imdbMapping2);

        Mockito.when(imdbMappingRepo.saveAll(mappings)).thenReturn(mappings);
        assertThat(imdbDataService.insertBulkData(mappings).size()).isEqualTo(2);
    }

    @Test
    @Override
    public void testGetDataByImdbTitleIdOrCmsContentId() {
        ImdbMapping imdbMapping = getExampleImdbMapping(0);

        Mockito.when(imdbMappingRepo.findByImdb_title_idOrCms_content_id("example imdb id")).thenReturn(imdbMapping);
        assertThat(imdbDataService.getData("example imdb id")).isEqualTo(imdbMapping);
    }

    @Test
    @Override
    public void testGetRatingByImdbTitleIdOrCmsContentId() {
        ImdbMapping imdbMapping = getExampleImdbMapping(0);

        Mockito.when(imdbMappingRepo.findByImdb_title_idOrCms_content_id("example imdb id")).thenReturn(imdbMapping);
        assertThat(imdbDataService.getData("example imdb id").getPayload().getRatings()).isEqualTo(imdbMapping.getPayload().getRatings());
    }

    @Test
    @Override
    public void testGetCmsContentId() {
        ImdbMapping imdbMapping = getExampleImdbMapping(0);

        Mockito.when(imdbMappingRepo.findByImdb_title_idOrCms_content_id("example imdb id")).thenReturn(imdbMapping);
        assertThat(imdbDataService.getData("example imdb id").getCms_content_id()).isEqualTo(imdbMapping.getCms_content_id());
    }

    @Test
    @Override
    public void testGetImdbTitleId() {
        ImdbMapping imdbMapping = getExampleImdbMapping(0);

        Mockito.when(imdbMappingRepo.findByImdb_title_idOrCms_content_id("example imdb id")).thenReturn(imdbMapping);
        assertThat(imdbDataService.getData("example imdb id").getImdb_title_id()).isEqualTo(imdbMapping.getImdb_title_id());
    }

    @Test
    @Override
    public void testGetPayload() {
        ImdbMapping imdbMapping = getExampleImdbMapping(0);

        Mockito.when(imdbMappingRepo.findByImdb_title_idOrCms_content_id("example imdb id")).thenReturn(imdbMapping);
        assertThat(imdbDataService.getData("example imdb id").getPayload()).isEqualTo(imdbMapping.getPayload());
    }

    @Test
    @Override
    public void testGetCastMember() {
        ImdbMapping imdbMapping = getExampleImdbMapping(0);

        Mockito.when(imdbMappingRepo.findByImdb_title_idOrCms_content_id("example imdb id")).thenReturn(imdbMapping);
        assertThat(imdbDataService.getData("example imdb id").getPayload().getCast_members()).isEqualTo(imdbMapping.getPayload().getCast_members());
    }

    @Test
    @Override
    public void testGetCrewMember() {
        ImdbMapping imdbMapping = getExampleImdbMapping(0);

        Mockito.when(imdbMappingRepo.findByImdb_title_idOrCms_content_id("example imdb id")).thenReturn(imdbMapping);
        assertThat(imdbDataService.getData("example imdb id").getPayload().getCrew_members()).isEqualTo(imdbMapping.getPayload().getCrew_members());
    }

    @Test
    @Override
    public void testGetProcessedData() {
        ImdbMapping imdbMapping1 = getExampleImdbMapping(0);
        ImdbMapping imdbMapping2 = getExampleImdbMapping(1);

        List<ImdbMapping> mappings = new ArrayList<>();
        mappings.add(imdbMapping1);
        mappings.add(imdbMapping2);

        Mockito.when(imdbMappingRepo.findProcessedData()).thenReturn(mappings);
        assertThat(imdbDataService.getProcessedData().size()).isEqualTo(2);
    }

    @Test
    @Override
    public void testGetNumberOfVotes() {
        ImdbMapping imdbMapping = getExampleImdbMapping(0);

        Mockito.when(imdbMappingRepo.findByImdb_title_idOrCms_content_id("example imdb id")).thenReturn(imdbMapping);
        assertThat(imdbDataService.getData("example imdb id").getPayload().getNumber_of_votes()).isEqualTo(imdbMapping.getPayload().getNumber_of_votes());
    }
}
