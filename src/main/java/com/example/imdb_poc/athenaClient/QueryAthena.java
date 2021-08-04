package com.example.imdb_poc.athenaClient;


import com.example.imdb_poc.constants.AthenaConstants;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.*;

import java.util.*;

public class QueryAthena {
    public static List<Map<String, String>> execute(AthenaClient athenaClient, String athenaQuery) throws InterruptedException {
        String queryExecutionId = submitAthenaQuery(athenaClient, athenaQuery);
        waitForQueryToComplete(athenaClient, queryExecutionId);
        return processResultRows(athenaClient, queryExecutionId);
    }
    public static String submitAthenaQuery(AthenaClient athenaClient, String athenaQuery) {
        try {
            QueryExecutionContext queryExecutionContext = QueryExecutionContext.builder()
                    .database(AthenaConstants.ATHENA_DEFAULT_DATABASE).build();

            ResultConfiguration resultConfiguration = ResultConfiguration.builder()
                    .outputLocation(AthenaConstants.ATHENA_OUTPUT_BUCKET)
                    .build();

            StartQueryExecutionRequest startQueryExecutionRequest = StartQueryExecutionRequest.builder()
                    .queryString(athenaQuery)
                    .queryExecutionContext(queryExecutionContext)
                    .resultConfiguration(resultConfiguration)
                    .build();

            StartQueryExecutionResponse startQueryExecutionResponse = athenaClient.startQueryExecution(startQueryExecutionRequest);
            return startQueryExecutionResponse.queryExecutionId();

        } catch (AthenaException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return "";
    }

    public static void waitForQueryToComplete(AthenaClient athenaClient, String queryExecutionId) throws InterruptedException {
        GetQueryExecutionRequest getQueryExecutionRequest = GetQueryExecutionRequest.builder()
                .queryExecutionId(queryExecutionId).build();

        GetQueryExecutionResponse getQueryExecutionResponse;
        boolean isQueryStillRunning = true;
        while (isQueryStillRunning) {
            getQueryExecutionResponse = athenaClient.getQueryExecution(getQueryExecutionRequest);
            String queryState = getQueryExecutionResponse.queryExecution().status().state().toString();
            if (queryState.equals(QueryExecutionState.FAILED.toString())) {
                throw new RuntimeException("The Amazon Athena query failed to run with error message: " + getQueryExecutionResponse
                        .queryExecution().status().stateChangeReason());
            } else if (queryState.equals(QueryExecutionState.CANCELLED.toString())) {
                throw new RuntimeException("The Amazon Athena query was cancelled.");
            } else if (queryState.equals(QueryExecutionState.SUCCEEDED.toString())) {
                isQueryStillRunning = false;
            } else {
                Thread.sleep(AthenaConstants.SLEEP_AMOUNT_IN_MS);
            }
            System.out.println("The current status is: " + queryState);
        }
    }

    public static List<Map<String, String>> processResultRows(AthenaClient athenaClient, String queryExecutionId) {

        try {
            GetQueryResultsRequest getQueryResultsRequest = GetQueryResultsRequest.builder()
                    .queryExecutionId(queryExecutionId)
                    .build();

            GetQueryResultsResponse getQueryResultsResults = athenaClient.getQueryResults(getQueryResultsRequest);

            List<ColumnInfo> columnInfoList = getQueryResultsResults.resultSet().resultSetMetadata().columnInfo();
            List<Row> results = getQueryResultsResults.resultSet().rows();
            return processRow(results, columnInfoList);
        } catch (AthenaException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private static List<Map<String, String>> processRow(List<Row> row, List<ColumnInfo> columnInfoList) {
        List<Map<String, String>> result= new ArrayList<>();
        for (Row myRow : row) {
            int index = 0;
            Map<String, String> tempMap = new HashMap<>();
            for(Datum data : myRow.data()) {
                final String columnName = columnInfoList.get(index).name();
                final String rowData = data.varCharValue();
                tempMap.put(columnName, rowData);
                index++;
            }
            result.add(tempMap);
        }
        return result;
    }
}
