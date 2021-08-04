package com.example.imdb_poc.constant;

public class AthenaConstants {
    public static final int CLIENT_EXECUTION_TIMEOUT = 100000;
    public static final String ATHENA_OUTPUT_BUCKET = "s3://bulk-image-upload-dev-in-ap-south-1/imdb_poc_output";
    public static final long SLEEP_AMOUNT_IN_MS = 1000;
    public static final String ATHENA_DEFAULT_DATABASE = "imdb_poc_sample";
}
