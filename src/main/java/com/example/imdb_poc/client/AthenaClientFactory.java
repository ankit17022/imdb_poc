package com.example.imdb_poc.client;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.AthenaClientBuilder;

public class AthenaClientFactory {
    private final AthenaClientBuilder builder = AthenaClient.builder()
            .credentialsProvider(ProfileCredentialsProvider.create("preprodmfa"))
            .region(Region.AP_SOUTH_1);

    public AthenaClient createClient() {
        return builder.build();
    }
}