package com.BubbleWrap.SearchEmptySeat.utils;

import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter(autoApply = true)
public class JsonConverterList implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(ErrorCode.JSON_PROCESSING_ERROR.getCode() +": " + e.getMessage());
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ArrayList.class);
        } catch (IOException e) {
            throw new RuntimeException(ErrorCode.JSON_PROCESSING_ERROR.getCode() +": " + e.getMessage());
        }
    }
}
