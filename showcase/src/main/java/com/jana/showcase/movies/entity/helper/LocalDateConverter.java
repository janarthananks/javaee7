/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jana.showcase.movies.entity.helper;

import java.time.Instant;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author JANARTHANANS
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    /**
     * 
     * @param attribute
     * @return 
     */
    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        return (attribute!=null)?Date.valueOf(attribute):null;
    }

    /**
     * 
     * @param dbData
     * @return 
     */
    @Override
    public LocalDate convertToEntityAttribute(java.sql.Date dbData) {
        if(dbData==null) return null;
        return LocalDate.from(dbData.toLocalDate());
    }
    
    
}
