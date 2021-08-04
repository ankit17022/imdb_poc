package com.example.imdb_poc.service;

import com.example.imdb_poc.data.ImdbPayload;

import java.util.Map;

public interface AthenaClientService {
    Map<String, ImdbPayload> getAthenaData(String sqlQuery);
}
