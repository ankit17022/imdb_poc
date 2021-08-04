package com.example.imdb_poc.constants;

import java.util.ArrayList;

public class AthenaQueries {
    public static String athenaFetchImdbPayload = "WITH castMembers AS \n" +
            "    (SELECT u_pcm.nameId,\n" +
            "     array_join(u_pcm.roles, ', ') as roles,\n" +
            "     u_pcm.category as category,\n" +
            "     NULL AS job,\n" +
            "     tc.titleId as imdb_title_id,\n" +
            "     tc.originalTitle as imdb_title,\n" +
            "     tc.genres,\n" +
            "     tc.imdbRating.rating as imdb_ratings,\n" +
            "     tc.imdbRating.numberOfVotes as imdb_number_of_votes,\n" +
            "     tc.remappedTo,\n" +
            "     tc.titleType\n" +
            "    FROM title_essential_v1 AS tc\n" +
            "    CROSS JOIN unnest(tc.principalCastMembers) t(u_pcm)), \n" +
            "    \n" +
            "crewMembers AS \n" +
            "    (SELECT u_pcm.nameId,\n" +
            "         NULL AS roles,\n" +
            "         u_pcm.category as category,\n" +
            "         u_pcm.job AS job,\n" +
            "     tc.titleId as imdb_title_id,\n" +
            "     tc.originalTitle as imdb_title,\n" +
            "     tc.genres,\n" +
            "     tc.imdbRating.rating as imdb_ratings,\n" +
            "     tc.imdbRating.numberOfVotes as imdb_number_of_votes,\n" +
            "     tc.remappedTo,\n" +
            "     tc.titleType\n" +
            "    FROM title_essential_v1 AS tc\n" +
            "    CROSS JOIN unnest(tc.principalCrewMembers) t(u_pcm)),\n" +
            "  allMembers AS(\n" +
            "  SELECT * FROM castMembers UNION ALL SELECT * FROM crewMembers)\n" +
            "  SELECT pcm.imdb_title_id, pcm.imdb_title, pcm.genres, pcm.imdb_ratings, pcm.imdb_number_of_votes, pcm.nameId, pcm.roles, pcm.category, pcm.job, nc.name as member_name FROM allMembers as pcm JOIN name_essential_v1 AS nc ON pcm.nameId = nc.nameId \n"+
            "  WHERE pcm.imdb_title_id in (%s) AND pcm.remappedTo is null \n" +
            "  AND pcm.titleType = 'movie' ORDER BY pcm.imdb_title_id";
}
