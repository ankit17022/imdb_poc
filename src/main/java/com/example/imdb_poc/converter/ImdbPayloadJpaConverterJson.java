package com.example.imdb_poc.converter;


import com.example.imdb_poc.data.ImdbPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ImdbPayloadJpaConverterJson implements AttributeConverter<ImdbPayload, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ImdbPayload meta) {
        try {
            if (meta == null)
                return "";
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException ex) {
            return null;
            // or throw an error
        }
    }

    @Override
    public ImdbPayload convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty())
                return null;

            return objectMapper.readValue(dbData, ImdbPayload.class);
        } catch (IOException ex) {
            // logger.error("Unexpected IOEx decoding json from database: " + dbData);
            return null;
        }
    }

}