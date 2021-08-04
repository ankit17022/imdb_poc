package com.example.imdb_poc.service;


import com.example.imdb_poc.client.AthenaClientFactory;
import com.example.imdb_poc.client.QueryAthena;
import com.example.imdb_poc.data.ImdbPayload;
import com.example.imdb_poc.data.Member;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AthenaClientServiceImpl implements AthenaClientService{

    private AthenaClientFactory athenaClientFactory = new AthenaClientFactory();

    @Override
    public Map<String, ImdbPayload> getAthenaData(String athenaSqlQuery) {
        Map<String, ImdbPayload> athenaFetched = new HashMap<>();
        try {
            List<Map<String, String>> result = QueryAthena.execute(athenaClientFactory.createClient(), athenaSqlQuery);
            result.remove(0);
            for (Map<String, String> athenaDatum : result) {
                final String imdb_title_id = athenaDatum.get("imdb_title_id");
                final long number_of_votes = Long.parseLong(athenaDatum.get("imdb_number_of_votes"));
                final double rating = Double.parseDouble(athenaDatum.get("imdb_ratings"));
                final String member_name_id = athenaDatum.get("nameId");
                final String member_name = athenaDatum.get("member_name");
                final String member_role = athenaDatum.get("roles");
                final String member_category = athenaDatum.get("category");
                final String member_job = athenaDatum.get("job");
                Member member = new Member(member_name, member_name_id, member_role, member_category, member_job);
                if (!athenaFetched.containsKey(imdb_title_id)) {
                    ImdbPayload imdbFetchedData = new ImdbPayload();
                    imdbFetchedData.setRatings(rating);
                    imdbFetchedData.setNumber_of_votes(number_of_votes);
                    if(member_role == null || member_role.isEmpty())
                        imdbFetchedData.addCastMember(member);
                    else
                        imdbFetchedData.addCrewMember(member);
                    athenaFetched.put(imdb_title_id, imdbFetchedData);
                } else {
                    ImdbPayload existing_data = athenaFetched.get(imdb_title_id);
                    if(member_role == null || member_role.isEmpty())
                        existing_data.addCrewMember(member);
                    else
                        existing_data.addCastMember(member);
                    athenaFetched.put(imdb_title_id, existing_data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return athenaFetched;
    }
}
