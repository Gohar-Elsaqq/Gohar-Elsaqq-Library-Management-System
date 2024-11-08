package com.library.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.library.dto.ContactInfoDTO;

@Converter
public class ContactInfoConverter implements AttributeConverter<ContactInfoDTO, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ContactInfoDTO contactInfo) {
        try {
            return objectMapper.writeValueAsString(contactInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting contact information to JSON", e);
        }
    }

    @Override
    public ContactInfoDTO convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ContactInfoDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error reading contact information from JSON", e);
        }
    }
}