package com.example.imdb_poc.converter;


import com.example.imdb_poc.data.ImdbPayload;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImdbPayloadJpaConverterJson implements AttributeConverter<ImdbPayload, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ImdbPayload meta) {
        try {
            if (meta == null)
                return null;
            return objectMapper.writeValueAsString(meta);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    @Override
    public ImdbPayload convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty())
                return null;

            return objectMapper.readValue(dbData, ImdbPayload.class);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

}