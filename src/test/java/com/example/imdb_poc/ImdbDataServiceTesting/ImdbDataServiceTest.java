package com.example.imdb_poc.ImdbDataServiceTesting;

public interface ImdbDataServiceTest {
    public void testInsertData();
    public void testInsertBulkData();
    public void testGetDataByImdbTitleIdOrCmsContentId();
    public void testGetRatingByImdbTitleIdOrCmsContentId();
    public void testGetCmsContentId();
    public void testGetImdbTitleId();
    public void testGetPayload();
    public void testGetCastMember();
    public void testGetCrewMember();
    public void testGetProcessedData();
    public void testGetNumberOfVotes();
}
